package com.technokratos.kirillakhmetov.service.impl;

import com.technokratos.kirillakhmetov.dto.ExpenseCategoryDto;
import com.technokratos.kirillakhmetov.dto.ProfitAnalysisDto;
import com.technokratos.kirillakhmetov.dto.response.*;
import com.technokratos.kirillakhmetov.entity.Finance;
import com.technokratos.kirillakhmetov.form.ExpenseForm;
import com.technokratos.kirillakhmetov.form.RevenueForm;
import com.technokratos.kirillakhmetov.repository.FinanceRepository;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.service.FinanceService;
import com.technokratos.kirillakhmetov.util.mapper.FinanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {
    private static final int LIMIT = 5;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final OwnerRepository ownerRepository;
    private final FinanceRepository financeRepository;
    private final FinanceMapper financeMapper;

    @Override
    public void saveExpense(long ownerId, ExpenseForm expenseForm) {
        financeRepository.save(financeMapper.toFinance(
                expenseForm,
                ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(ownerId))),
                "Расход"));
    }

    @Override
    public void addRevenue(long ownerId, RevenueForm revenueForm) {
        financeRepository.save(financeMapper.toFinance(
                revenueForm,
                ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(ownerId))),
                "Доход"));
    }

    @Override
    public FinanceResponse calculateRevenue(Long id) {
        double sumRevenue = financeRepository.sumAllRevenueByOwnerId(id).orElse(0D);
        return new FinanceResponse(sumRevenue);
    }

    @Override
    public FinanceResponse calculateExpense(Long id) {
        double sumExpense = financeRepository.sumAllExpenseByOwnerId(id).orElse(0D);
        return new FinanceResponse(sumExpense);
    }

    @Override
    public FinanceResponse calculateProfit(Long idOwner) {
        double profit = financeRepository.sumAllRevenueByOwnerId(idOwner).orElse(0D)
                - financeRepository.sumAllExpenseByOwnerId(idOwner).orElse(0D);
        return new FinanceResponse(profit);
    }

    @Override
    public ProfitResponse analyzeProfit(Long ownerId) {
        List<ProfitAnalysisDto> finances = financeRepository.profitAnalysisByOwnerId(ownerId);

        if (finances.isEmpty()) {
            return new ProfitResponse(List.of(), List.of(), null, null);
        }

        List<String> dates = finances.stream()
                .map(finance -> finance.date().toLocalDate().format(FORMATTER))
                .toList();

        List<String> amounts = finances.stream()
                .map(finance -> String.valueOf(finance.amount()))
                .toList();

        String lastDate = LocalDate.parse(dates.getLast(), FORMATTER).plusMonths(1).format(FORMATTER);
        List<String> updatedDates = new ArrayList<>(dates);
        updatedDates.add(lastDate);

        double averageDiff = calculateAverageDifference(amounts);
        String forecastValue = String.valueOf(Math.round(averageDiff / 100.0) * 100);

        return new ProfitResponse(updatedDates, amounts, lastDate, forecastValue);
    }

    @Override
    public ExpenseResponse analyzeExpense(Long ownerId) {
        List<ExpenseCategoryDto> finances = financeRepository.expenseAnalysisByOwnerId(ownerId);

        List<String> categories = finances.stream()
                .map(ExpenseCategoryDto::category)
                .toList();

        List<String> amounts = finances.stream()
                .map(e -> String.valueOf(e.totalAmount()))
                .toList();

        return new ExpenseResponse(categories, amounts);
    }

    @Override
    public List<FinancePaginationResponse> getPage(Long ownerId, int page) {
        List<Finance> finances = financeRepository.getPartRevenuesAndExpenses(
                ownerId, LIMIT, LIMIT * (page - 1)
        );

        return financeMapper.toResponse(finances);
    }

    @Override
    public PageResponse getCountItems(Long idOwner) {
        int result = (int) Math.ceil((double) financeRepository.countRevenuesAndExpensesByOwnerId(idOwner) / LIMIT);
        return new PageResponse(result);
    }

    @Override
    public MonthInfoResponse getMonthInfo(Long id) {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        double monthRevenue = financeRepository.getSumRevenuesMonthById(id).orElse(0D);
        double monthExpenses = financeRepository.getSumExpensesMonthById(id).orElse(0D);

        int previousMonth = now.minusMonths(1).getMonthValue();
        int previousYear = now.minusMonths(1).getYear();

        double currentProfit = monthRevenue - monthExpenses;
        double previousProfit = financeRepository.getMonthProfitById(id, previousMonth, previousYear)
                .orElse(financeRepository.getMonthProfitById(id, currentMonth, currentYear).orElse(0D));

        double profitChange = currentProfit - previousProfit;

        return new MonthInfoResponse(
                now.format(FORMATTER),
                monthRevenue,
                monthExpenses,
                profitChange < 0 ? 0 : Math.round(profitChange * 100) / 100.0
        );
    }

    private double calculateAverageDifference(List<String> numbers) {
        if (numbers == null || numbers.size() < 2) {
            return 0d;
        }

        double first = Double.parseDouble(numbers.getFirst());
        double last = Double.parseDouble(numbers.getLast());

        return Double.parseDouble(numbers.getLast()) + (last - first) / (numbers.size() - 1);
    }
}
