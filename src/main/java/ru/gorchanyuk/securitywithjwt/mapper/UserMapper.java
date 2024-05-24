package ru.gorchanyuk.securitywithjwt.mapper;

import org.mapstruct.Mapper;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;

@Mapper
public interface UserMapper {


    UserResponse entityToOutDto(User user);

    User dtoToEntity(UserRequest dto);
}
