package tech.logicforge.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.ExpenseDto;

import java.math.BigDecimal;
import java.util.List;


public interface ExpenseService {

    // Adds new expense to the database
        ExpenseDto addExpense(ExpenseDto expenseDto);

        // Retrieves all expenses for current month or start date nd end date
    List<ExpenseDto> getCurrentMonthExpensesForCurrentUser();

    public void deleteExpenseForCurrentUser(Long expenseId);

    public List<ExpenseDto> getLatest5ExpensesForCurrentUser();

    public BigDecimal getTotalExpenseForCurrentUser();
    }


