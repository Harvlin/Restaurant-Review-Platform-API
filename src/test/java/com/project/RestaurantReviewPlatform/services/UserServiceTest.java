package com.project.RestaurantReviewPlatform.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.RestaurantReviewPlatform.TestDataUtility;
import com.project.RestaurantReviewPlatform.controllers.UserController;
import com.project.RestaurantReviewPlatform.domain.dtos.UserDto;
import com.project.RestaurantReviewPlatform.domain.entity.UserEntity;
import com.project.RestaurantReviewPlatform.mappers.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.UUID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private Mapper<UserEntity, UserDto> userMapper;

    @InjectMocks
    private UserController userController; // Inject the controller into the test

    @Test
    public void TestThatCreateUserSuccessfullyReturnSavedUser() throws Exception {
        UserDto userDto = TestDataUtility.createTestUserDtoA();
        userDto.setId(null);
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("User A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("a desc for user A")
        );
    }

    @Test
    public void TestThatListUserReturnListOfUser() throws Exception {
        UserEntity userEntity = TestDataUtility.createTestUserA();
        userService.save(userEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("User A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].description").value("a desc for user A")
        );
    }

    @Test
    public void TestThatGetOneUserReturnListOfUser() throws Exception {
        UserEntity userEntity = TestDataUtility.createTestUserA();
        userService.save(userEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/id/{id}", userEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("User A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("a desc for user A")
        );
    }

    @Test
    public void TestThatFullUpdateUserReturnUpdatedUser() throws Exception {
        UUID userId = UUID.fromString("73165e0c-3311-4029-bd98-60d9555c5885");
        UserDto inputDto = TestDataUtility.createTestUserDtoA();
        inputDto.setId(userId);
        UserEntity mappedEntity = TestDataUtility.createTestUserA();
        mappedEntity.setId(userId);
        UserEntity savedEntity = TestDataUtility.createTestUserA();
        savedEntity.setId(userId);
        UserDto expectedDto = TestDataUtility.createTestUserDtoA();

        // Mock service methods
        when(userService.isExist(userId)).thenReturn(true);
        when(userService.getUserById(userId)).thenReturn(savedEntity);
        when(userMapper.mapFrom(inputDto)).thenReturn(mappedEntity);
        when(userService.save(mappedEntity)).thenReturn(savedEntity);
        when(userMapper.mapTo(savedEntity)).thenReturn(expectedDto);

        /*
        // Perform the PUT request
        String userJson = objectMapper.writeValueAsString(inputDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andReturn();

        assertEquals("HTTP Status should be 200 OK", 200, result.getResponse().getStatus());
         */
        // Verify the expected interactions
        verify(userService).isExist(userId);  // Ensure this method is called
        verify(userService).getUserById(userId);
        verify(userMapper).mapFrom(inputDto);
        verify(userService).save(mappedEntity);
        verify(userMapper).mapTo(savedEntity);
    }
}