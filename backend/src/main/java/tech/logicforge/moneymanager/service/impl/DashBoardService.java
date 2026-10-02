package tech.logicforge.moneymanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.ExpenseDto;
import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.dto.RecentTransactionDto;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.service.ExpenseService;
import tech.logicforge.moneymanager.service.IncomeService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final IncomeService incomeService;
    private final ProfileServiceImpl profileService;
    private final ExpenseService expenseService;

    public Map<String, Object> getDashBoardData() {
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String, Object> returnValue = new HashMap<>();

        // 1. Fetch latest raw records
        List<IncomeDto> latestIncome = incomeService.getLatest5ExpensesForCurrentUser();
        List<ExpenseDto> latestExpense = expenseService.getLatest5ExpensesForCurrentUser();

        // 2. Safely merge and sort into unified transaction objects
        List<RecentTransactionDto> recentTransactions = Stream.concat(
            latestIncome.stream().map(income -> RecentTransactionDto.builder()
                    .ProfileId(profile.getId())
                    .icon(income.getIcon())
                    .name(income.getName())
                    .amount(income.getAmount())
                    .date(income.getDate())
                    .type("income")
                    .build()),
            latestExpense.stream().map(expense -> RecentTransactionDto.builder()
                    .ProfileId(profile.getId())
                    .icon(expense.getIcon())
                    .name(expense.getName())
                    .amount(expense.getAmount())
                    .date(expense.getDate())
                    .type("expense")
                    .build())
        )
        .sorted((a, b) -> b.getDate().compareTo(a.getDate())) // Sorts descending by date
        .limit(5) // Keep only the overall top 5 latest transactions
        .collect(Collectors.toList());

        // 3. Calculate financial totals
        BigDecimal totalIncome = incomeService.getTotalExpenseForCurrentUser();
        BigDecimal totalExpense = expenseService.getTotalExpenseForCurrentUser();
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        returnValue.put("totalBalance", totalBalance);
        returnValue.put("totalIncome", totalIncome);
        returnValue.put("totalExpense", totalExpense);
        returnValue.put("recentTransactions", recentTransactions);

        return returnValue;
    }
}
