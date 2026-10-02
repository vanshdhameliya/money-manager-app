package tech.logicforge.moneymanager.service;

import tech.logicforge.moneymanager.dto.IncomeDto;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeService {

    public IncomeDto addIncome(IncomeDto incomeDto);

    public List<IncomeDto> getCurrentMonthIncomeForCurrentUser();

    public void deleteIncomeForCurrentUser(Long incomeId);

    public List<IncomeDto> getLatest5ExpensesForCurrentUser();

    public BigDecimal getTotalExpenseForCurrentUser();
}
