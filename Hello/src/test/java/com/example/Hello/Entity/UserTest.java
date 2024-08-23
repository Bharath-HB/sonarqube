package com.example.Hello.Entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
    }

    @Test
    public void testGettersAndSetters() {
        // Set values
        user.setUserId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        // Test getters
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testDefaultConstructor() {
        // Check default values
        User defaultUser = new User();
        assertThat(defaultUser.getUserId()).isNull();
        assertThat(defaultUser.getUsername()).isNull();
        assertThat(defaultUser.getEmail()).isNull();
    }

    @Test
    public void testParameterizedConstructor() {
        // Check initialization with parameters
        User user = new User(1L, "testUser", "test@example.com");
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }
}

