package tech.logicforge.moneymanager.controller;

import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDto> createIncome(@Valid @RequestBody IncomeDto incomeDto) {
        IncomeDto createdIncome = incomeService.addIncome(incomeDto);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }
}
