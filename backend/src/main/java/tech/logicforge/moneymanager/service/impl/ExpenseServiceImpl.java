package tech.logicforge.moneymanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.ExpenseDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;
import tech.logicforge.moneymanager.entity.ExpenseEntity;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.mapper.ExpenseMapper;
import tech.logicforge.moneymanager.repository.CategoryRepository;
import tech.logicforge.moneymanager.repository.ExpenseRepository;
import tech.logicforge.moneymanager.service.ExpenseService;

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


}
