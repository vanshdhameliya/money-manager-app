package tech.logicforge.moneymanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.entity.IncomeEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    // 1. Single Source Mapping (DTO -> Entity)
    @Mapping(target = "categoryEntity", ignore = true)
    @Mapping(target = "profileEntity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    IncomeEntity toEntity(IncomeDto incomeDto);

    // 2. Entity -> DTO
    @Mapping(source = "categoryEntity.id", target = "categoryId")
    @Mapping(source = "categoryEntity.name", target = "categoryName")
    IncomeDto toDto(IncomeEntity incomeEntity);

    // 3. List Mapping
    List<IncomeDto> toDto(List<IncomeEntity> entities);

    // 4. In-Place Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryEntity", ignore = true)
    @Mapping(target = "profileEntity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(IncomeDto dto, @MappingTarget IncomeEntity entity);
}