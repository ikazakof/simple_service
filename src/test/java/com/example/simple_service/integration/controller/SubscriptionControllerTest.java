package com.example.simple_service.integration.controller;

import com.example.simple_service.config.PostgresContainerWrapper;
import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.utils.web.WebConstant;
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

import static com.example.simple_service.integration.controller.UserControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testing subscription controller functionality.")
public class SubscriptionControllerTest {

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
    @DisplayName("Create subscription with exist users. Should be successful")
    void createSubscriptionSuccessfullyTest() throws Exception {

        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));
        subscriptionDto.setSubscriptionId(UUID.fromString("86759ec4-d46d-4aa7-b908-041becce066f"));

        SubscriptionDto resultSubscriptionDto = new SubscriptionDto();
        resultSubscriptionDto.setUserId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));
        resultSubscriptionDto.setSubscriptionId(UUID.fromString("86759ec4-d46d-4aa7-b908-041becce066f"));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Matchers.equalTo(resultSubscriptionDto.getUserId().toString())))
                .andExpect(jsonPath("$.subscriptionId", Matchers.equalTo(resultSubscriptionDto.getSubscriptionId().toString())))
                .andExpect(jsonPath("$.subDate", Matchers.notNullValue()));

    }

    @Test
    @DisplayName("Create subscription with user id not exist. Should be throw not found")
    void createSubscriptionUserIdNotExist_ThenFail() throws Exception {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(UUID.randomUUID());
        subscriptionDto.setSubscriptionId(UUID.fromString("86759ec4-d46d-4aa7-b908-041becce066f"));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Create subscription with subscription id not exist. Should be throw not found")
    void createSubscriptionSubscriptionIdNotExist_ThenFail() throws Exception {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));
        subscriptionDto.setSubscriptionId(UUID.randomUUID());

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Create subscription with subscription id not exist. Should be throw bad request")
    void createSubscriptionForYourself_ThenFail() throws Exception {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));
        subscriptionDto.setSubscriptionId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Create subscription when she already exist. Should be throw bad request")
    void createSubscriptionAlreadyExist_ThenFail() throws Exception {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUserId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));
        subscriptionDto.setSubscriptionId(UUID.fromString("6e14b7fe-c784-42db-904f-5f1f6e0bdbca"));

        SubscriptionDto resultSubscriptionDto = new SubscriptionDto();
        resultSubscriptionDto.setUserId(UUID.fromString("dcc20e8e-490f-4653-a561-4c80c4402ba4"));
        resultSubscriptionDto.setSubscriptionId(UUID.fromString("6e14b7fe-c784-42db-904f-5f1f6e0bdbca"));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Matchers.equalTo(resultSubscriptionDto.getUserId().toString())))
                .andExpect(jsonPath("$.subscriptionId", Matchers.equalTo(resultSubscriptionDto.getSubscriptionId().toString())))
                .andExpect(jsonPath("$.subDate", Matchers.notNullValue()));

        mockMvc.perform(MockMvcRequestBuilders.post(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Get all user subscriptions. Should be successful")
    void getAllUserSubscriptionSuccessfullyTest() throws Exception {
        UUID preparedLiquibaseUser = UUID.fromString("6e14b7fe-c784-42db-904f-5f1f6e0bdbca");

        String preparedFirstSubscriberId = "86759ec4-d46d-4aa7-b908-041becce066f";
        String preparedSecondSubscriberId = "dcc20e8e-490f-4653-a561-4c80c4402ba4";

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/subscriptions/{id}", preparedLiquibaseUser)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].subscriptionId").value(preparedFirstSubscriberId))
                .andExpect(jsonPath("$[1].subscriptionId").value(preparedSecondSubscriberId));

    }

    @Test
    @DisplayName("Get all not exist user subscriptions. Should be throw not found")
    void getAllNotExistUserSubscription_ThenFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/subscriptions/{id}", UUID.randomUUID())
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete subscription. Should be successful")
    void deleteSubscriptionSuccessfullyTest() throws Exception {
        UUID preparedLiquibaseUser = UUID.fromString("86759ec4-d46d-4aa7-b908-041becce066f");

        String preparedFirstSubscriberId = "6e14b7fe-c784-42db-904f-5f1f6e0bdbca";
        String preparedSecondSubscriberId = "dcc20e8e-490f-4653-a561-4c80c4402ba4";

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/subscriptions/{id}", preparedLiquibaseUser)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].subscriptionId").value(preparedFirstSubscriberId))
                .andExpect(jsonPath("$[1].subscriptionId").value(preparedSecondSubscriberId));

        SubscriptionDto subscriptionDtoForDelete = new SubscriptionDto();
        subscriptionDtoForDelete.setUserId(preparedLiquibaseUser);
        subscriptionDtoForDelete.setSubscriptionId(UUID.fromString(preparedFirstSubscriberId));

        String expectedString = "Subscription - " + subscriptionDtoForDelete + " successfully deleted";

        mockMvc.perform(MockMvcRequestBuilders.delete(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDtoForDelete)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));

        mockMvc.perform(MockMvcRequestBuilders.get(WebConstant.VERSION_URL + "/subscriptions/{id}", preparedLiquibaseUser)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].subscriptionId").value(preparedSecondSubscriberId));
    }

    @Test
    @DisplayName("Delete not exist subscription. Should be throw not found")
    void deleteNotExistSubscription_ThenFail() throws Exception {

        SubscriptionDto subscriptionDtoForDelete = new SubscriptionDto();
        subscriptionDtoForDelete.setUserId(UUID.randomUUID());
        subscriptionDtoForDelete.setSubscriptionId(UUID.randomUUID());

        mockMvc.perform(MockMvcRequestBuilders.delete(WebConstant.VERSION_URL + "/subscriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(asJsonString(subscriptionDtoForDelete)))
                .andExpect(status().isNotFound());



    }

}
