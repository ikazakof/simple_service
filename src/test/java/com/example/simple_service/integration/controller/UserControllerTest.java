package com.example.simple_service.integration.controller;

import com.example.simple_service.config.PostgresContainerWrapper;
import com.example.simple_service.dto.UserDto;
import com.example.simple_service.utils.web.WebConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testing user controller functionality.")
public class UserControllerTest {

    @Container
    private static final PostgreSQLContainer<PostgresContainerWrapper> postgresContainer = new PostgresContainerWrapper();

    @DynamicPropertySource
    public static void initSystemParams(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @DisplayName("Create user. Should be successful")
    void createUserSuccessfullyTest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setEmail("one@mail.ru");
        userDto.setPhone("+71111111111");
        userDto.setFirstName("Igor");
        userDto.setLastName("Barmaleev");
        userDto.setMiddleName("Sergeevich");
        userDto.setPassword("12345asd");
        userDto.setCity("Москва");


        UserDto resultUserDto = new UserDto();
        resultUserDto.setIsDeleted(false);
        resultUserDto.setEmail("one@mail.ru");
        resultUserDto.setPhone("+71111111111");
        resultUserDto.setCity("Москва");
        resultUserDto.setFirstName("Igor");
        resultUserDto.setLastName("Barmaleev");
        resultUserDto.setMiddleName("Sergeevich");
        resultUserDto.setPassword("12345asd");

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.isDeleted", Matchers.equalTo(resultUserDto.getIsDeleted())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(resultUserDto.getEmail())))
                .andExpect(jsonPath("$.phone", Matchers.equalTo(resultUserDto.getPhone())))
                .andExpect(jsonPath("$.city", Matchers.equalTo(resultUserDto.getCity())))
                .andExpect(jsonPath("$.firstName", Matchers.equalTo(resultUserDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.equalTo(resultUserDto.getLastName())))
                .andExpect(jsonPath("$.middleName", Matchers.equalTo(resultUserDto.getMiddleName())))
                .andExpect(jsonPath("$.regDate", Matchers.notNullValue()))
                .andExpect(jsonPath("$.updateDate", Matchers.notNullValue()))
                .andExpect(jsonPath("$.password", Matchers.equalTo(resultUserDto.getPassword())));

    }

    @Test
    @DisplayName("Create user with duplicated email. Should be throw bad request")
    void createUserEmailExist_ThenFail() throws Exception {

        UserDto firstUserDto = new UserDto();
        firstUserDto.setEmail("two@mail.ru");
        firstUserDto.setPhone("+72222222222");
        firstUserDto.setFirstName("Igor");
        firstUserDto.setLastName("Barmaleev");
        firstUserDto.setMiddleName("Sergeevich");
        firstUserDto.setPassword("12345asd");
        firstUserDto.setCity("Москва");

        UserDto secondUserDtoWithSameEmail = new UserDto();
        secondUserDtoWithSameEmail.setEmail("two@mail.ru");
        secondUserDtoWithSameEmail.setPhone("+72222222222");
        secondUserDtoWithSameEmail.setFirstName("Vladimir");
        secondUserDtoWithSameEmail.setLastName("Bardin");
        secondUserDtoWithSameEmail.setMiddleName("Orekhovich");
        secondUserDtoWithSameEmail.setPassword("12345aawd32sd");
        secondUserDtoWithSameEmail.setCity("Москва");

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(asJsonString(firstUserDto)));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(secondUserDtoWithSameEmail)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Create user with duplicated phone number. Should be throw bad request")
    void createUserPhoneExist_ThenFail() throws Exception {

        UserDto firstUserDto = new UserDto();
        firstUserDto.setEmail("three@mail.ru");
        firstUserDto.setPhone("+73333333333");
        firstUserDto.setFirstName("Igor");
        firstUserDto.setLastName("Barmaleev");
        firstUserDto.setMiddleName("Sergeevich");
        firstUserDto.setPassword("12345asd");
        firstUserDto.setCity("Москва");

        UserDto secondUserDtoWithSameEmail = new UserDto();
        secondUserDtoWithSameEmail.setEmail("four@mail.ru");
        secondUserDtoWithSameEmail.setPhone("+73333333333");
        secondUserDtoWithSameEmail.setFirstName("Vladimir");
        secondUserDtoWithSameEmail.setLastName("Bardin");
        secondUserDtoWithSameEmail.setMiddleName("Orekhovich");
        secondUserDtoWithSameEmail.setPassword("12345aawd32sd");
        secondUserDtoWithSameEmail.setCity("Москва");

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content(asJsonString(firstUserDto)));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(secondUserDtoWithSameEmail)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Create user with a non-existent city. Should be throw not found")
    void createUserCityNotExist_ThenFail() throws Exception {

        UserDto firstUserDto = new UserDto();
        firstUserDto.setEmail("five@mail.ru");
        firstUserDto.setPhone("+74444444444");
        firstUserDto.setFirstName("Igor");
        firstUserDto.setLastName("Barmaleev");
        firstUserDto.setMiddleName("Sergeevich");
        firstUserDto.setPassword("12345asd");
        firstUserDto.setCity("НетТакогоГорода");


        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(firstUserDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Find user by id. Should be successful")
    void getUserByIdTest() throws Exception {

        UserDto firstUserDto = new UserDto();
        firstUserDto.setEmail("six@mail.ru");
        firstUserDto.setPhone("+75555555555");
        firstUserDto.setFirstName("Igor");
        firstUserDto.setLastName("Barmaleev");
        firstUserDto.setMiddleName("Sergeevich");
        firstUserDto.setPassword("12345asd");
        firstUserDto.setCity("Москва");


        String savedUserJson = mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(firstUserDto)))
                .andReturn().getResponse().getContentAsString();

        UUID savedUserUUID = userDtoUuidFromString(savedUserJson);

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/users/{id}", savedUserUUID)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUserUUID.toString()));

    }

    @Test
    @DisplayName("Find user by not exist id. Should be throw not found")
    void getUserByIdNotExist_ThenFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/users/{id}", UUID.randomUUID())
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Update exist user. Should be successful")
    void updateUserSuccessfullyTest() throws Exception {

        UserDto userDtoToSave = new UserDto();
        userDtoToSave.setEmail("seven@mail.ru");
        userDtoToSave.setPhone("+76666666666");
        userDtoToSave.setFirstName("Igor");
        userDtoToSave.setLastName("Barmaleev");
        userDtoToSave.setMiddleName("Sergeevich");
        userDtoToSave.setPassword("12345asd");
        userDtoToSave.setCity("Москва");

        String savedUserJson = mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToSave)))
                .andReturn().getResponse().getContentAsString();

        UUID savedUserUUID = userDtoUuidFromString(savedUserJson);

        UserDto userDtoToUpdate = new UserDto();
        userDtoToUpdate.setId(savedUserUUID);
        userDtoToUpdate.setEmail("seven@mail.ru");
        userDtoToUpdate.setPhone("+76666667777");
        userDtoToUpdate.setFirstName("Maksim");
        userDtoToUpdate.setLastName("Barmaleev");
        userDtoToUpdate.setMiddleName("Antonovich");
        userDtoToUpdate.setPassword("12345asd");
        userDtoToUpdate.setCity("Москва");

        UserDto resultUserDto = new UserDto();
        resultUserDto.setId(savedUserUUID);
        resultUserDto.setIsDeleted(false);
        resultUserDto.setEmail("seven@mail.ru");
        resultUserDto.setPhone("+76666667777");
        resultUserDto.setCity("Москва");
        resultUserDto.setFirstName("Maksim");
        resultUserDto.setLastName("Barmaleev");
        resultUserDto.setMiddleName("Antonovich");
        resultUserDto.setPassword("12345asd");

        mockMvc.perform(MockMvcRequestBuilders.put(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(resultUserDto.getId().toString())))
                .andExpect(jsonPath("$.isDeleted", Matchers.equalTo(resultUserDto.getIsDeleted())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(resultUserDto.getEmail())))
                .andExpect(jsonPath("$.phone", Matchers.equalTo(resultUserDto.getPhone())))
                .andExpect(jsonPath("$.city", Matchers.equalTo(resultUserDto.getCity())))
                .andExpect(jsonPath("$.firstName", Matchers.equalTo(resultUserDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.equalTo(resultUserDto.getLastName())))
                .andExpect(jsonPath("$.middleName", Matchers.equalTo(resultUserDto.getMiddleName())))
                .andExpect(jsonPath("$.regDate", Matchers.notNullValue()))
                .andExpect(jsonPath("$.updateDate", Matchers.notNullValue()))
                .andExpect(jsonPath("$.password", Matchers.equalTo(resultUserDto.getPassword())));

    }

    @Test
    @DisplayName("Update user by not exist id. Should be throw not found")
    void updateUserByIdNotExist_ThenFail() throws Exception {

        UserDto userDtoToUpdate = new UserDto();
        userDtoToUpdate.setId(UUID.randomUUID());

        mockMvc.perform(MockMvcRequestBuilders.put(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToUpdate)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Update user by not exist city. Should be throw not found")
    void updateUserByIdWhenCityNotExist_ThenFail() throws Exception {

        UserDto userDtoToSave = new UserDto();
        userDtoToSave.setEmail("eight@mail.ru");
        userDtoToSave.setPhone("+78888888888");
        userDtoToSave.setFirstName("Igor");
        userDtoToSave.setLastName("Barmaleev");
        userDtoToSave.setMiddleName("Sergeevich");
        userDtoToSave.setPassword("12345asd");
        userDtoToSave.setCity("Москва");

        String savedUserJson = mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToSave)))
                .andReturn().getResponse().getContentAsString();

        UUID savedUserUUID = userDtoUuidFromString(savedUserJson);

        UserDto userDtoToUpdate = new UserDto();
        userDtoToUpdate.setId(savedUserUUID);
        userDtoToUpdate.setCity("НетТакогоГорода");

        mockMvc.perform(MockMvcRequestBuilders.put(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToUpdate)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete user soft. Should be successful")
    void deleteUserSoftSuccessfullyTest() throws Exception {

        UserDto userDtoToSave = new UserDto();
        userDtoToSave.setEmail("nine@mail.ru");
        userDtoToSave.setPhone("+79999999999");
        userDtoToSave.setFirstName("Igor");
        userDtoToSave.setLastName("Barmaleev");
        userDtoToSave.setMiddleName("Sergeevich");
        userDtoToSave.setPassword("12345asd");
        userDtoToSave.setCity("Москва");

        String savedUserJson = mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToSave)))
                .andReturn().getResponse().getContentAsString();

        UUID savedUserUUID = userDtoUuidFromString(savedUserJson);

        String expectedString = "User with id - " + savedUserUUID + " soft deleted";

        mockMvc.perform(MockMvcRequestBuilders.delete(WebConstant.VERSION_URL + "/users/{id}", savedUserUUID)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/users/{id}", savedUserUUID)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUserUUID.toString()))
                .andExpect(jsonPath("$.isDeleted").value(true));

    }

    @Test
    @DisplayName("Delete user hard. Should be successful")
    void deleteUserHardSuccessfullyTest() throws Exception {

        UserDto userDtoToSave = new UserDto();
        userDtoToSave.setEmail("ten@mail.ru");
        userDtoToSave.setPhone("+71010101010");
        userDtoToSave.setFirstName("Igor");
        userDtoToSave.setLastName("Barmaleev");
        userDtoToSave.setMiddleName("Sergeevich");
        userDtoToSave.setPassword("12345asd");
        userDtoToSave.setCity("Москва");

        String savedUserJson = mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(userDtoToSave)))
                .andReturn().getResponse().getContentAsString();

        UUID savedUserUUID = userDtoUuidFromString(savedUserJson);

        String expectedString = "User with id - " + savedUserUUID + " hard deleted";

        mockMvc.perform(MockMvcRequestBuilders.delete(WebConstant.VERSION_URL + "/users/{id}", savedUserUUID)
                        .param("hardDelete", "true")
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/users/{id}", savedUserUUID)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete user by not exist id. Should be throw not found")
    void deleteUserByIdNotExist_ThenFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/users/{id}", UUID.randomUUID())
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UUID userDtoUuidFromString(String input) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            UserDto userDto = objectMapper.readValue(input, UserDto.class);
            return userDto.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
