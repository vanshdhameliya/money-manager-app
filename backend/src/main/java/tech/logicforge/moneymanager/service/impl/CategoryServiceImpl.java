package tech.logicforge.moneymanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.logicforge.moneymanager.dto.CategoryDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;
import tech.logicforge.moneymanager.entity.ProfileEntity;
import tech.logicforge.moneymanager.mapper.CategoryMapper;
import tech.logicforge.moneymanager.mapper.ProfileMapper;
import tech.logicforge.moneymanager.repository.CategoryRepository;
import tech.logicforge.moneymanager.repository.ProfileRepository;
import tech.logicforge.moneymanager.service.CategoryService;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ProfileServiceImpl profileService;
    private final ProfileRepository profileRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CategoryDto categoryDto) {

        ProfileEntity currentProfile = profileService.getCurrentProfile();

        if (categoryRepository.existsByNameAndProfileId(
                categoryDto.getName(), currentProfile.getId())) {

            throw new ResponseStatusException(HttpStatus.CONFLICT,"Category with name already exists");
        }

        CategoryEntity newCategory = categoryMapper.toEntity(categoryDto);

        newCategory.setProfile(currentProfile);

        CategoryEntity savedCategory = categoryRepository.save(newCategory);

        return categoryMapper.toDto(savedCategory);
    }



}
