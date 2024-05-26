package ru.gorchanyuk.securitywithjwt.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестирование маппера UserMapper")
public class UserMapperTest {

    private static UserMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("Тест маппинга из User в UserResponse")
    void testEntityToOutDto() {
        User user = ModelGenerator.createUser("username");

        UserResponse result = mapper.entityToOutDto(user);

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getRoles(), result.getRoles());
    }

    @Test
    @DisplayName("Тест маппинга из UserRequest в User")
    void testDtoToEntity() {
        UserRequest request = ModelGenerator.createUserRequest();

        User result = mapper.dtoToEntity(request);

        assertEquals(request.getUsername(), result.getUsername());
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getRoles(), result.getRoles());
        assertEquals(request.getPassword(), result.getPassword());
    }
}
