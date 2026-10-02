package tech.logicforge.moneymanager.controller;

import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDto> createIncome(@Valid @RequestBody IncomeDto incomeDto) {
        IncomeDto createdIncome = incomeService.addIncome(incomeDto);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }

    /**
     * Retrieves all income records for the logged-in user within the current month.
     */
    @GetMapping("/current-month")
    public ResponseEntity<List<IncomeDto>> getCurrentMonthIncome() {

        List<IncomeDto> incomes = incomeService.getCurrentMonthIncomeForCurrentUser();
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {

        incomeService.deleteIncomeForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }

}
