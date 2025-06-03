package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.ExpenseCategorySummary;
import com.technokratos.kirillakhmetov.dto.FinanceDto;
import com.technokratos.kirillakhmetov.dto.ProfitAnalysisDto;
import com.technokratos.kirillakhmetov.dto.response.*;
import com.technokratos.kirillakhmetov.entity.Finance;
import com.technokratos.kirillakhmetov.repository.FinanceRepository;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.util.mapper.FinanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private static final int LIMIT = 5;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final OwnerRepository ownerRepository;
    private final FinanceRepository financeRepository;
    private final FinanceMapper financeMapper;


    public void addRevenue(FinanceDto financeDto) {
        financeRepository.save(financeMapper.toFinance(
                financeDto,
                ownerRepository.findById(financeDto.ownerId())
                        .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(financeDto.ownerId()))),
                "Доход"));
    }

    public void addExpense(FinanceDto financeDto) {
        financeRepository.save(financeMapper.toFinance(
                financeDto,
                ownerRepository.findById(financeDto.ownerId())
                        .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(financeDto.ownerId()))),
                "Расход"));
    }

    public FinanceResponse calculateRevenue(Long id) {
        double sumRevenue = financeRepository.sumAllRevenueByOwnerId(id).orElse(0D);
        return new FinanceResponse(sumRevenue);
    }

    public FinanceResponse calculateExpense(Long id) {
        double sumExpense = financeRepository.sumAllExpenseByOwnerId(id).orElse(0D);
        System.out.println(id + " " + sumExpense);
        return new FinanceResponse(sumExpense);
    }

    public FinanceResponse calculateProfit(Long idOwner) {
        double profit = financeRepository.sumAllRevenueByOwnerId(idOwner).orElse(0D)
                - financeRepository.sumAllExpenseByOwnerId(idOwner).orElse(0D);
        return new FinanceResponse(profit);
    }

    public ProfitResponse analyzeProfit(Long ownerId) {
        List<ProfitAnalysisDto> finances = financeRepository.profitAnalysisByOwnerId(ownerId);
        List<String> dates = new ArrayList<>();
        List<String> amounts = new ArrayList<>();
        String forecastDate = null;
        if (!finances.isEmpty()) {
            for (ProfitAnalysisDto finance : finances) {
                dates.add(finance.date().toLocalDate().format(FORMATTER));
                amounts.add(String.valueOf(finance.amount()));
            }
            forecastDate = LocalDate.parse(dates.getLast(), FORMATTER).plusMonths(1).format(FORMATTER);
            dates.add(forecastDate);
        }
        String forecastValue = String.valueOf(Math.round(calculateAverageDifference(amounts) / 100) * 100);
        return new ProfitResponse(dates, amounts, forecastDate, forecastValue);
    }

    public ExpenseResponse analyzeExpense(Long idOwner) {
        List<ExpenseCategorySummary> finances = financeRepository.expenseAnalysisByOwnerId(idOwner);
        List<String> categories = new ArrayList<>();
        List<String> amounts = new ArrayList<>();
        for (ExpenseCategorySummary expenseCategorySummary : finances) {
            categories.add(String.valueOf(expenseCategorySummary.category()));
            amounts.add(String.valueOf(expenseCategorySummary.totalAmount()));
        }
        return new ExpenseResponse(categories, amounts);
    }

    public List<FinancePaginationDto> getPage(Long idOwner, int page) {
        List<Finance> finances = financeRepository.getPartRevenuesAndExpenses(
                idOwner, LIMIT, LIMIT * (page - 1)
        );
        List<FinancePaginationDto> financePaginationDtos = new ArrayList<>();
        for (Finance finance : finances) {
            financePaginationDtos.add(new FinancePaginationDto(
                    finance.getType(),
                    finance.getAmount(),
                    finance.getCategory(),
                    finance.getDate().format(FORMATTER)
            ));
        }
        return financePaginationDtos;
    }

    public String getCountItems(Long idOwner) {
        int result = (int) Math.ceil((double) financeRepository.countRevenuesAndExpensesByOwnerId(idOwner) / LIMIT);
        return "{\"totalPages\": %s}".formatted(result);
    }

    private double calculateAverageDifference(List<String> numbers) {
        double totalDifference = 0.0;
        int count = 0;
        double sum = 0;
        for (int i = 0; i < numbers.size() - 1; i++) {
            totalDifference += Double.parseDouble(numbers.get(i + 1)) - Double.parseDouble(numbers.get(i));
            sum += Double.parseDouble(numbers.get(i));
            count++;
        }
        return (sum + totalDifference) / count;
    }

    public MonthInfoResponse getMonthInfo(Long id) {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        double monthRevenue = financeRepository.getSumRevenuesMonthById(id).orElse(0D);
        double monthExpenses = financeRepository.getSumExpensesMonthById(id).orElse(0D);
        double prevMonthRecord =
                financeRepository.getMonthProfitById(
                        id,
                        now.minusMonths(1).getMonthValue(),
                        now.minusMonths(1).getYear()).orElse(0D)
                        - financeRepository.getMonthProfitById(
                        id,
                        now.getMonthValue(),
                        now.getYear()).orElse(0D);
        return new MonthInfoResponse(
                now.format(FORMATTER),
                monthRevenue,
                monthExpenses,
                prevMonthRecord < 0 ? 0 : (double) Math.round(prevMonthRecord * 100) / 100
        );
    }
}
