package tech.logicforge.moneymanager.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;
import tech.logicforge.moneymanager.entity.IncomeEntity;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.exception.ResourceNotFoundException;
import tech.logicforge.moneymanager.mapper.IncomeMapper;
import tech.logicforge.moneymanager.repository.CategoryRepository;
import tech.logicforge.moneymanager.repository.IncomeRepository;
import tech.logicforge.moneymanager.service.IncomeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<IncomeDto> getCurrentMonthIncomeForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        List<IncomeEntity> list =
                incomeRepository.findByProfileEntityIdAndDateBetween(profile.getId(), startDate, endDate);

        return incomeMapper.toDto(list);
    }

    @Transactional
    public void deleteIncomeForCurrentUser(Long incomeId) {

        ProfileEntity profile = profileService.getCurrentProfile();

        // 2. Find the income record belonging to this user
        IncomeEntity income = incomeRepository.findByIdAndProfileEntityId(incomeId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Income record not found or unauthorized to delete"));

        incomeRepository.delete(income);
    }

    @Override
    public List<IncomeDto> getLatest5ExpensesForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list =
                incomeRepository.findTop5ByProfileEntityIdOrderByDateDesc(profile.getId());

        return incomeMapper.toDto(list);


    }

    @Override
    public BigDecimal getTotalExpenseForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal totalExpense =
                incomeRepository.findTotalExpenseByProfileId(profile.getId());

        return totalExpense != null ? totalExpense : BigDecimal.ZERO;
    }
}
