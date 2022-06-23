package test;

import com.haw.main.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user = new User("thorben");

    @org.junit.jupiter.api.Test
    void testGetNameTrue() {
        assertEquals("thorben", user.getName());
    }

    @org.junit.jupiter.api.Test
    void testGetNameFalse() {
        assertNotEquals("markus", user.getName());
    }

    @org.junit.jupiter.api.Test
    void testToStringTrue() {
        assertEquals("User: thorben", user.toString());
    }

    @org.junit.jupiter.api.Test
    void testToStringFalse() {
        assertNotEquals("User: luis", user.toString());
    }
}