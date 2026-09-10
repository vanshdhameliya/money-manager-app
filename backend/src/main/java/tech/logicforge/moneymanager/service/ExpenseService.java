package tech.logicforge.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.ExpenseDto;


public interface ExpenseService {

    // Adds new expense to the database
        ExpenseDto addExpense(ExpenseDto expenseDto);
}
