package tech.logicforge.moneymanager.controller;

import tech.logicforge.moneymanager.dto.ExpenseDto;
import tech.logicforge.moneymanager.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto createdExpense = expenseService.addExpense(expenseDto);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    /**
     * Retrieves all expense records for the logged-in user within the current month.
     */
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getCurrentMonthExpense() {

        List<ExpenseDto> expenses = expenseService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpenseForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }

}
