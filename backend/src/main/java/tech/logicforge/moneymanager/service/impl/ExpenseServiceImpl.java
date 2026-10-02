package tech.logicforge.moneymanager.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.ExpenseDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;
import tech.logicforge.moneymanager.entity.ExpenseEntity;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.exception.ResourceNotFoundException;
import tech.logicforge.moneymanager.mapper.ExpenseMapper;
import tech.logicforge.moneymanager.repository.CategoryRepository;
import tech.logicforge.moneymanager.repository.ExpenseRepository;
import tech.logicforge.moneymanager.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final ProfileServiceImpl profileService;
    private final ExpenseMapper expenseMapper;


    @Override
    public ExpenseDto addExpense(ExpenseDto expenseDto) {

        ProfileEntity currentProfile = profileService.getCurrentProfile();

        CategoryEntity category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found with ID: " + expenseDto.getCategoryId()));

        ExpenseEntity expenseEntity = expenseMapper.toEntity(expenseDto);

        // Explicitly link the database entities
        expenseEntity.setProfileEntity(currentProfile);
        expenseEntity.setCategoryEntity(category);

        ExpenseEntity savedEntity = expenseRepository.save(expenseEntity);
        return expenseMapper.toDto(savedEntity);
    }

    @Override
    public List<ExpenseDto> getCurrentMonthExpensesForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        List<ExpenseEntity> list =
                expenseRepository.findByProfileEntityIdAndDateBetween(profile.getId(),startDate,endDate);

        return expenseMapper.toDto(list);
    }

    // Inside your ExpenseService / ExpenseServiceImpl

    @Transactional
    public void deleteExpenseForCurrentUser(Long expenseId) {

        ProfileEntity profile = profileService.getCurrentProfile();

        // 2. Find the expense belonging to this user
        ExpenseEntity expense = expenseRepository.findByIdAndProfileEntityId(expenseId, profile.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found or unauthorized to delete"));

        expenseRepository.delete(expense);
    }

    @Override
    public List<ExpenseDto> getLatest5ExpensesForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list =
                expenseRepository.findTop5ByProfileEntityIdOrderByDateDesc(profile.getId());

        return expenseMapper.toDto(list);


    }

    @Override
    public BigDecimal getTotalExpenseForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal totalExpense =
                expenseRepository.findTotalExpenseByProfileId(profile.getId());

        return totalExpense != null ? totalExpense : BigDecimal.ZERO;
    }


}
