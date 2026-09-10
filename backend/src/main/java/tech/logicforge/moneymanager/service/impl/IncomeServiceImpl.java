package tech.logicforge.moneymanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;
import tech.logicforge.moneymanager.entity.IncomeEntity;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.mapper.IncomeMapper;
import tech.logicforge.moneymanager.repository.CategoryRepository;
import tech.logicforge.moneymanager.repository.IncomeRepository;
import tech.logicforge.moneymanager.service.IncomeService;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileServiceImpl profileService;
    private final IncomeMapper incomeMapper;

    @Override
    public IncomeDto addIncome(IncomeDto incomeDto) {

        ProfileEntity currentProfile = profileService.getCurrentProfile();

        CategoryEntity category = categoryRepository.findById(incomeDto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found with ID: " + incomeDto.getCategoryId()));

        IncomeEntity incomeEntity = incomeMapper.toEntity(incomeDto);

        // Explicitly link the database entities
        incomeEntity.setProfileEntity(currentProfile);
        incomeEntity.setCategoryEntity(category);

        IncomeEntity savedEntity = incomeRepository.save(incomeEntity);
        return incomeMapper.toDto(savedEntity);
    }
}
