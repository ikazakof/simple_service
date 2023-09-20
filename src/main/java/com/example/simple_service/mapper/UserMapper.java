package com.example.simple_service.mapper;

import com.example.simple_service.dto.UserDto;
import com.example.simple_service.entity.User;
import org.mapstruct.*;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class)
public interface UserMapper {

    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "regDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "updateDate", expression = "java(ZonedDateTime.now())")
    User convertDtoToUser(UserDto userDto);

    UserDto convertUserToDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User getUserUpdatedByDto(UserDto userDto, @MappingTarget User user);

}
