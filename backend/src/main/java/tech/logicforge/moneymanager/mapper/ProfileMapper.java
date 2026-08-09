package tech.logicforge.moneymanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.logicforge.moneymanager.dto.ProfileDto;
import tech.logicforge.moneymanager.entity.ProfileEntity;

import java.util.UUID;

 @Mapper(componentModel = "spring", imports = UUID.class)
    public interface ProfileMapper {

//        @Mapping(target = "password", ignore = true)
        @Mapping(target = "id", ignore = true)
//        @Mapping(target = "createdAt", ignore = true)
//        @Mapping(target = "updatedAt", ignore = true)
        ProfileEntity toEntity(ProfileDto profileDto);

        @Mapping(target = "password", ignore = true)
        ProfileDto toDto(ProfileEntity profileEntity);
    }


