package com.obolonyk.skillup.querygenerator;

import com.obolonyk.skillup.querygenerator.annotations.Column;
import com.obolonyk.skillup.querygenerator.annotations.Id;
import com.obolonyk.skillup.querygenerator.annotations.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

import static com.obolonyk.skillup.querygenerator.util.Util.*;

public class QueryGeneratorCustomImpl implements QueryGenerator {

    @Override
    public String findAll(Class<?> clazz) {
        // SELECT id, user_name, age FROM users;

        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        stringBuilder.append(SELECT);
        stringBuilder.append(SPACE);

        String tableName = getTableName(clazz);

        for (Field declaredField : clazz.getDeclaredFields()) {
            extractFieldNames(stringJoiner, declaredField);
        }

        stringBuilder.append(stringJoiner);
        stringBuilder.append(SPACE);
        stringBuilder.append(FROM);
        stringBuilder.append(SPACE);
        stringBuilder.append(tableName);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }


    @Override
    public String findById(Class<?> clazz, Serializable id) {
        //"SELECT id, user_name, age FROM users WHERE id = " + "'" + id + "'" + ";"

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT);
        stringBuilder.append(SPACE);

        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        for (Field declaredField : clazz.getDeclaredFields()) {
            extractFieldNames(stringJoiner, declaredField);
        }

        stringBuilder.append(stringJoiner);
        stringBuilder.append(SPACE);
        stringBuilder.append(FROM);
        stringBuilder.append(SPACE);

        String tableName = getTableName(clazz);
        stringBuilder.append(tableName);

        stringBuilder.append(SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(SPACE);
        stringBuilder.append(ID);
        stringBuilder.append(SPACE);
        stringBuilder.append(EQUAL);
        stringBuilder.append(SPACE);
        stringBuilder.append(QUOTER);
        stringBuilder.append(id);
        stringBuilder.append(QUOTER);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }

    @Override
    public String delete(Class<?> clazz, Serializable id) {
        //"DELETE FROM users WHERE id = " + "'" + id + "'" + ";"

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELETE);
        stringBuilder.append(SPACE);
        stringBuilder.append(FROM);
        stringBuilder.append(SPACE);

        String tableName = getTableName(clazz);
        stringBuilder.append(tableName);

        stringBuilder.append(SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(SPACE);
        stringBuilder.append(ID);
        stringBuilder.append(SPACE);
        stringBuilder.append(EQUAL);
        stringBuilder.append(SPACE);
        stringBuilder.append(QUOTER);
        stringBuilder.append(id);
        stringBuilder.append(QUOTER);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }

    @Override
    public String insert(Object value) throws IllegalAccessException {
        //"INSERT INTO users (id, user_name, age) VALUES (" + "'" + user.getId()
        //                + "', " + "'" + user.getName()
        //                + "', " + "'" + user.getAge()
        //                + "'" + ");";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(INSERT);
        stringBuilder.append(SPACE);
        stringBuilder.append(INTO);
        stringBuilder.append(SPACE);

        Class<?> clazz = value.getClass();
        String tableName = getTableName(clazz);

        stringBuilder.append(tableName);
        stringBuilder.append(SPACE);
        stringBuilder.append(OPEN_BRACKET);

        StringJoiner stringJoinerForName = new StringJoiner(DELIMITER);
        StringJoiner stringJoinerForValues = new StringJoiner(DELIMITER);
        for (Field declaredField : clazz.getDeclaredFields()) {
            extractFieldNames(stringJoinerForName, declaredField);
            extractFieldValues(stringJoinerForValues, declaredField, value);
        }

        stringBuilder.append(stringJoinerForName);
        stringBuilder.append(CLOSE_BRACKET);
        stringBuilder.append(SPACE);
        stringBuilder.append(VALUES);
        stringBuilder.append(SPACE);


        stringBuilder.append(OPEN_BRACKET);

        stringBuilder.append(stringJoinerForValues);

        stringBuilder.append(CLOSE_BRACKET);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }


    @Override
    public String update(Object value) throws IllegalAccessException {
        //"UPDATE users SET user_name = " + "'" + user.getName()
        //                + "'" + ", age = " + "'" + user.getAge()
        //                + "'" + " WHERE id = " + "'" + user.getId()
        //                + "'" + ";";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE);
        stringBuilder.append(SPACE);

        Class<?> userClass = value.getClass();
        String tableName = getTableName(userClass);
        stringBuilder.append(tableName);

        stringBuilder.append(SPACE);
        stringBuilder.append(SET);
        stringBuilder.append(SPACE);

        StringJoiner stringJoiner = new StringJoiner(DELIMITER);

        String idValue = null;
        for (Field declaredField : userClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Column column = declaredField.getAnnotation(Column.class);
            if (column != null && declaredField.getAnnotation(Id.class) == null) {
                String fieldName = column.name().isEmpty() ? declaredField.getName() : column.name();
                String expression = fieldName + SPACE + EQUAL + SPACE + QUOTER + declaredField.get(value) + QUOTER;
                stringJoiner.add(expression);
            } else {
                idValue = String.valueOf(declaredField.get(value));
            }
        }

        stringBuilder.append(stringJoiner);
        stringBuilder.append(SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(SPACE);
        stringBuilder.append(ID);
        stringBuilder.append(SPACE);
        stringBuilder.append(EQUAL);
        stringBuilder.append(SPACE);
        stringBuilder.append(QUOTER);
        stringBuilder.append(idValue);
        stringBuilder.append(QUOTER);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }


    private void extractFieldNames(StringJoiner stringJoiner, Field declaredField) {
        declaredField.setAccessible(true);
        Column column = declaredField.getAnnotation(Column.class);
        if (column != null) {
            String fieldName = column.name().isEmpty() ? declaredField.getName() : column.name();
            stringJoiner.add(fieldName);
        }
    }

    private void extractFieldValues(StringJoiner stringJoiner, Field declaredField, Object value) throws IllegalAccessException {
        declaredField.setAccessible(true);
        Column column = declaredField.getAnnotation(Column.class);
        if (column != null) {
            stringJoiner.add(QUOTER + declaredField.get(value) + QUOTER);
        }
    }

    private String getTableName(Class<?> userClass) {
        Table table = userClass.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("Annotation @DBTable is missing");
        }
        return table.name().isEmpty() ? userClass.getSimpleName() : table.name();
    }
}
