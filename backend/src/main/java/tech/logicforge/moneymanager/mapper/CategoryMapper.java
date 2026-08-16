package tech.logicforge.moneymanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.logicforge.moneymanager.dto.CategoryDto;
import tech.logicforge.moneymanager.entity.CategoryEntity;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // DTO -> Entity
    @Mapping(target = "profile", ignore = true)
    CategoryEntity toEntity(CategoryDto categoryDto);

    // Entity -> DTO
    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(target = "color", ignore = true)
    CategoryDto toDto(CategoryEntity categoryEntity);

    List<CategoryDto> toDto(List<CategoryEntity> entities);

    // in CategoryMapper
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    void updateEntityFromDto(CategoryDto dto, @MappingTarget CategoryEntity entity);

}