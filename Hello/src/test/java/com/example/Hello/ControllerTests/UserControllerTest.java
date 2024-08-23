package com.example.Hello.ControllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Hello.Controller.UserController;
import com.example.Hello.Entity.User;
import com.example.Hello.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        // No need for this as MockMvc is already provided by @WebMvcTest
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testGetUsers() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        List<User> users = Collections.singletonList(user);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    public void testEditUserSuccess() throws Exception {
        User existingUser = new User();
        existingUser.setUsername("oldUsername");
        User updatedUser = new User();
        updatedUser.setUsername("newUsername");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/user?userId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newUsername"));
    }

    @Test
    public void testEditUserNotFound() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/user?userId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        mockMvc.perform(delete("/user?userId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/user?userId=1"))
                .andExpect(status().isNoContent());
    }
}
