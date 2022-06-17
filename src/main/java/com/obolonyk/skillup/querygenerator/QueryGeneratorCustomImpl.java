package com.obolonyk.skillup.querygenerator;

import com.obolonyk.skillup.querygenerator.annotations.DBColumn;
import com.obolonyk.skillup.querygenerator.annotations.DBId;
import com.obolonyk.skillup.querygenerator.annotations.DBTable;

import java.lang.reflect.Field;
import java.util.StringJoiner;

import static com.obolonyk.skillup.querygenerator.util.Util.*;

@SuppressWarnings("unchecked")
public class QueryGeneratorCustomImpl implements QueryGenerator<User> {

    @Override
    public String findAll(Class<User> userClass) {
        // SELECT id, user_name, age FROM users;

        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        stringBuilder.append(SELECT);
        stringBuilder.append(SPACE);

        String tableName = getTableName(userClass);

        for (Field declaredField : userClass.getDeclaredFields()) {
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
    public String insert(User user) {
        //"INSERT INTO users (id, user_name, age) VALUES (" + "'" + user.getId()
        //                + "', " + "'" + user.getName()
        //                + "', " + "'" + user.getAge()
        //                + "'" + ");";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(INSERT);
        stringBuilder.append(SPACE);
        stringBuilder.append(INTO);
        stringBuilder.append(SPACE);

        Class<? extends User> userClass = user.getClass();
        String tableName = getTableName((Class<User>) userClass);

        stringBuilder.append(tableName);
        stringBuilder.append(SPACE);
        stringBuilder.append(OPEN_BRACKET);

        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        for (Field declaredField : userClass.getDeclaredFields()) {
            extractFieldNames(stringJoiner, declaredField);
        }

        stringBuilder.append(stringJoiner);
        stringBuilder.append(CLOSE_BRACKET);
        stringBuilder.append(SPACE);
        stringBuilder.append(VALUES);
        stringBuilder.append(SPACE);

        stringBuilder.append(OPEN_BRACKET);
        stringBuilder.append(QUOTER);
        stringBuilder.append(user.getId());
        stringBuilder.append(QUOTER);
        stringBuilder.append(DELIMITER);

        stringBuilder.append(QUOTER);
        stringBuilder.append(user.getName());
        stringBuilder.append(QUOTER);
        stringBuilder.append(DELIMITER);

        stringBuilder.append(QUOTER);
        stringBuilder.append(user.getAge());
        stringBuilder.append(QUOTER);
        stringBuilder.append(CLOSE_BRACKET);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }


    @Override
    public String update(User user) throws IllegalAccessException {
        //"UPDATE users SET user_name = " + "'" + user.getName()
        //                + "'" + ", age = " + "'" + user.getAge()
        //                + "'" + " WHERE id = " + "'" + user.getId()
        //                + "'" + ";";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE);
        stringBuilder.append(SPACE);

        Class<? extends User> userClass = user.getClass();
        String tableName = getTableName((Class<User>) userClass);
        stringBuilder.append(tableName);

        stringBuilder.append(SPACE);
        stringBuilder.append(SET);
        stringBuilder.append(SPACE);

        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        for (Field declaredField : userClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            DBColumn column = declaredField.getAnnotation(DBColumn.class);
            if (column != null && declaredField.getAnnotation(DBId.class) == null) {
                String fieldName = column.name().isEmpty() ? declaredField.getName() : column.name();
                String expression = fieldName + SPACE + EQUAL + SPACE + QUOTER + declaredField.get(user) + QUOTER;
                stringJoiner.add(expression);
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
        stringBuilder.append(user.getId());
        stringBuilder.append(QUOTER);
        stringBuilder.append(END_QUERY);

        return stringBuilder.toString();
    }

    @Override
    public String findById(Class<User> userClass, long id) {
        //"SELECT id, user_name, age FROM users WHERE id = " + "'" + id + "'" + ";"

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT);
        stringBuilder.append(SPACE);

        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        for (Field declaredField : userClass.getDeclaredFields()) {
            extractFieldNames(stringJoiner, declaredField);
        }

        stringBuilder.append(stringJoiner);
        stringBuilder.append(SPACE);
        stringBuilder.append(FROM);
        stringBuilder.append(SPACE);

        String tableName = getTableName(userClass);
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
    public String delete(Class<User> userClass, long id) {
        //"DELETE FROM users WHERE id = " + "'" + id + "'" + ";"

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELETE);
        stringBuilder.append(SPACE);
        stringBuilder.append(FROM);
        stringBuilder.append(SPACE);

        String tableName = getTableName(userClass);
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

    private void extractFieldNames(StringJoiner stringJoiner, Field declaredField) {
        declaredField.setAccessible(true);
        DBColumn column = declaredField.getAnnotation(DBColumn.class);
        if (column != null) {
            String fieldName = column.name().isEmpty() ? declaredField.getName() : column.name();
            stringJoiner.add(fieldName);
        }
    }

    private String getTableName(Class<User> userClass) {
        DBTable table = userClass.getAnnotation(DBTable.class);
        if (table == null) {
            throw new IllegalArgumentException("Annotation @DBTable is missing");
        }
        return table.name().isEmpty() ? userClass.getSimpleName() : table.name();
    }
}
