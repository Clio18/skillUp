package com.obolonyk.skillup.querygenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class QueryGeneratorTest {

    @Test
    @DisplayName("Test findAll and return query")
    void testFindAll() {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String query = queryGenerator.findAll(User.class);
        String expectedQuery = "SELECT id, user_name, age FROM users;";
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test insert and return query")
    void testInsert() throws IllegalAccessException {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        User user = new User();
        user.setId(1L);
        user.setName("Tom");
        user.setAge(20);
        String query = queryGenerator.insert(user);
        String expectedQuery = "INSERT INTO users (id, user_name, age) VALUES (" + user.getId()
                + ", " + "'" + user.getName()
                + "', " + user.getAge()
                + ");";
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test update and return query")
    void testUpdate() throws IllegalAccessException, InvocationTargetException {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        User user = new User();
        user.setId(1L);
        user.setName("Tom");
        user.setAge(20);
        String query = queryGenerator.update(user);
        String expectedQuery = "UPDATE users SET user_name = " + "'" + user.getName()
                + "'" + ", age = " + user.getAge()
                + " WHERE id = " + user.getId()
                + ";";
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test findById and return query")
    void testFindById() {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        long id = 1L;
        String query = (queryGenerator).findById(User.class, id);
        String expectedQuery = "SELECT id, user_name, age FROM users WHERE id = " + id + ";";
        assertEquals(expectedQuery, query);
    }

    @Test
    @DisplayName("Test delete and return query")
    void testDelete() {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        long id = 1L;
        String query = queryGenerator.delete(User.class, id);
        String expectedQuery = "DELETE FROM users WHERE id = " + id + ";";
        assertEquals(expectedQuery, query);
    }

}