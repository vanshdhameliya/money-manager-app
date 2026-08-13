package tech.logicforge.moneymanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.logicforge.moneymanager.dto.CategoryDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // DTO -> Entity
    @Mapping(target = "profile", ignore = true)
    CategoryEntity toEntity(CategoryDto categoryDto);

    // Entity -> DTO
    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(target = "color", ignore = true)
    CategoryDto toDto(CategoryEntity categoryEntity);
}