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

import java.util.List;


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

            throw new RuntimeException("Category with name already exists");
        }

        CategoryEntity newCategory = categoryMapper.toEntity(categoryDto);

        newCategory.setProfile(currentProfile);

        CategoryEntity savedCategory = categoryRepository.save(newCategory);

        return categoryMapper.toDto(savedCategory);
    }

    // get categories for current user
    @Override
    public List<CategoryDto> getCategoriesForCurrentUser() {

        ProfileEntity currProfile = profileService.getCurrentProfile();

        List<CategoryEntity> categoryEntities = categoryRepository.findByProfileId(currProfile.getId());

        return categoryMapper.toDto(categoryEntities);
    }

    @Override
    public List<CategoryDto> getCategoriesByTypeForCurrentUser(String type) {

        ProfileEntity currProfile = profileService.getCurrentProfile();

        List<CategoryEntity> categoryEntities =
                categoryRepository.findByProfileIdAndType(currProfile.getId(), type);

        return categoryMapper.toDto(categoryEntities);
    }


    @Override
    public CategoryDto updateById(Long categoryId, CategoryDto categoryDto) {

        ProfileEntity currentProfile = profileService.getCurrentProfile();

        CategoryEntity existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found with id: " + categoryId));

        // ownership check — make sure this category actually belongs to the caller
        if (!existingCategory.getProfile().getId().equals(currentProfile.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You are not authorized to updateById this category");
        }

        // optional: block renaming into a name that already exists for this user
        if (categoryRepository.existsByNameAndProfileId(categoryDto.getName(), currentProfile.getId())
                && !existingCategory.getName().equals(categoryDto.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Category with name already exists");
        }

        existingCategory.setName(categoryDto.getName());
        existingCategory.setIcon(categoryDto.getIcon());
        // (profile is intentionally never overwritten here — see note below)

        CategoryEntity updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toDto(updatedCategory);
    }
}

