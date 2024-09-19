package com.fernando.course.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.fernando.course.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "Fernando", "fernando@example.com", "123456789", "password123");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1L, user.getId());
        assertEquals("Fernando", user.getName());
        assertEquals("fernando@example.com", user.getEmail());
        assertEquals("123456789", user.getPhone());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetters() {
        user.setId(2L);
        user.setName("Carlos");
        user.setEmail("carlos@example.com");
        user.setPhone("987654321");
        user.setPassword("newpassword");

        assertEquals(2L, user.getId());
        assertEquals("Carlos", user.getName());
        assertEquals("carlos@example.com", user.getEmail());
        assertEquals("987654321", user.getPhone());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user2 = new User(1L, "Fernando", "fernando@example.com", "123456789", "password123");
        User user3 = new User(2L, "Carlos", "carlos@example.com", "987654321", "newpassword");

        assertEquals(user, user2);
        assertNotEquals(user, user3);
        assertEquals(user.hashCode(), user2.hashCode());
        assertNotEquals(user.hashCode(), user3.hashCode());
    }
}
