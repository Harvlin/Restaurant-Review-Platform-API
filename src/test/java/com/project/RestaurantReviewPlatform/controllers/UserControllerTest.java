package com.project.RestaurantReviewPlatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.RestaurantReviewPlatform.TestDataUtility;
import com.project.RestaurantReviewPlatform.domain.dtos.UserDto;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerTest {
    private UserService userService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(UserService userService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void TestThatCreateAuthorSuccessfullyReturnHttp201Created() throws Exception {
        UserDto userDto = TestDataUtility.createTestUserDtoA();
        userDto.setId(null);
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void TestThatListUserReturnHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatGetOneUserReturnHttp200() throws Exception {
        UserEntity user = TestDataUtility.createTestUserA();
        userService.save(user);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/id/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatGetUserByNameReturnHttp200() throws Exception {
        UserEntity user = TestDataUtility.createTestUserA();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/name/" + user.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Skip the full update and partial update test case
}
