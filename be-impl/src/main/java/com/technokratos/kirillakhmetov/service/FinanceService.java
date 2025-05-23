package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.FinanceResponse;
import com.technokratos.kirillakhmetov.repository.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final FinanceRepository financeRepository;
    private static final int LIMIT = 5;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

//    public void addRevenue(FinanceDto financeDto) {
//        financeRepository.save(Finance.builder()
//                .ownerId(financeDto.owner_id())
//                .type("Доход")
//                .amount(financeDto.amount())
//                .category(financeDto.category())
//                .date(financeDto.date())
//                .build()
//        );
//    }

//    public void addExpense(FinanceDto financeDto) {
//        financeRepository.save(Finance.builder()
//                .ownerId(financeDto.owner_id())
//                .type("Расход")
//                .amount(financeDto.amount())
//                .category(financeDto.category())
//                .date(financeDto.date())
//                .build()
//        );
//    }

    public FinanceResponse calculateRevenue(Long id) {
        double sumRevenue = financeRepository.sumAllRevenue(id);
        return new FinanceResponse(sumRevenue);
    }

    public FinanceResponse calculateExpense(Long id) {
        double sumExpense = financeRepository.sumAllExpense(id);
        return new FinanceResponse(sumExpense);
    }

    public FinanceResponse calculateProfit(Long idOwner) {
        double profit = financeRepository.sumAllRevenue(idOwner) - financeRepository.sumAllExpense(idOwner);
        return new FinanceResponse(profit);
    }

//    public ProfitResponse analyzeProfit(Long idOwner) {
//        List<Finance> finances = financeRepository.profitAnalysisByOwnerid(idOwner);
//        List<String> dates = new ArrayList<>();
//        List<String> amounts = new ArrayList<>();
//        String forecastDate = null;
//        if (!finances.isEmpty()) {
//            for (Finance finance : finances) {
//                dates.add(finance.getDate().format(FORMATTER));
//                amounts.add(String.valueOf(finance.getAmount()));
//            }
//            forecastDate = LocalDate.parse(dates.getLast(), FORMATTER).plusMonths(1).format(FORMATTER);
//            dates.add(forecastDate);
//        }
//        String forecastValue = String.valueOf(Math.round(calculateAverageDifference(amounts) / 100) * 100);
//        return new ProfitResponse(dates, amounts, forecastDate, forecastValue);
//    }

//    public ExpenseResponse analyzeExpense(Long idOwner) {
//        List<Finance> finances = financeRepository.expenseAnalysisByOwnerid(idOwner);
//        List<String> categories = new ArrayList<>();
//        List<String> amounts = new ArrayList<>();
//        for (Finance finance : finances) {
//            categories.add(String.valueOf(finance.getCategory()));
//            amounts.add(String.valueOf(finance.getAmount()));
//        }
//        return new ExpenseResponse(categories, amounts);
//    }

//    public List<FinancePaginationResponse> getPage(Long idOwner, int page) {
//        List<Finance> finances = financeRepository.getPartRevenuesAndExpenses(
//                idOwner, LIMIT, LIMIT * (page - 1)
//        );
//        List<FinancePaginationResponse> financePaginationDtos = new ArrayList<>();
//        for (Finance finance : finances) {
//            financePaginationDtos.add(new FinancePaginationDto(
//                    finance.getType(),
//                    finance.getAmount(),
//                    finance.getCategory(),
//                    finance.getDate().format(FORMATTER)
//            ));
//        }
//        return financePaginationDtos;
//    }

//    public String getCountItems(Long idOwner) {
//        int result = (int) Math.ceil((double) financeRepository.countRevenuesAndExpensesByOwnerId(idOwner) / LIMIT);
//        return "{\"totalPages\": %s}".formatted(result);
//    }

//    private double calculateAverageDifference(List<String> numbers) {
//        double totalDifference = 0.0;
//        int count = 0;
//        double sum = 0;
//        for (int i = 0; i < numbers.size() - 1; i++) {
//            totalDifference += Double.parseDouble(numbers.get(i + 1)) - Double.parseDouble(numbers.get(i));
//            sum += Double.parseDouble(numbers.get(i));
//            count++;
//        }
//        return (sum + totalDifference) / count;
//    }

//    public MonthInfoResponse getMonthInfo(Long id) {
//        LocalDate dateNow = LocalDate.now();
//        double monthRevenue = financeRepository.getSumRevenuesMonthById(id);
//        double monthExpenses = financeRepository.getSumExpensesMonthById(id);
//        double prevMonthRecord = financeRepository.getMonthProfitById(id, dateNow.minusMonths(1))
//                - financeRepository.getMonthProfitById(id, dateNow);
//        return new MonthInfoResponse(
//                dateNow.format(FORMATTER),
//                monthRevenue,
//                monthExpenses,
//                prevMonthRecord < 0 ? 0 : (double) Math.round(prevMonthRecord * 100) / 100
//        );
//    }
}
