package com.obolonyk.skillup.querygenerator;

import com.obolonyk.skillup.querygenerator.annotations.Column;
import com.obolonyk.skillup.querygenerator.annotations.Id;
import com.obolonyk.skillup.querygenerator.annotations.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class DefaultQueryGenerator implements QueryGenerator {
    private static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE ";
    private static final String INSERT = "INSERT ";
    private static final String VALUES = "VALUES ";
    private static final String SET = "SET ";
    private static final String UPDATE = "UPDATE ";
    private static final String INTO = "INTO ";
    private static final String FROM = "FROM ";
    private static final String WHERE = "WHERE ";
    private static final String ID = "id";
    private static final String END_QUERY = ";";
    private static final String QUOTER = "'";
    private static final String DELIMITER = ", ";
    private static final String SPACE = " ";
    private static final String EQUAL = " = ";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";

    @Override
    public String findAll(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        stringBuilder.append(SELECT);
        String tableName = getTableName(clazz);
        for (Field declaredField : clazz.getDeclaredFields()) {
            fillJoinerByExtractedFieldNames(stringJoiner, declaredField);
        }
        stringBuilder.append(stringJoiner).append(SPACE);
        stringBuilder.append(FROM);
        stringBuilder.append(tableName);
        stringBuilder.append(END_QUERY);
        return stringBuilder.toString();
    }


    @Override
    public String findById(Class<?> clazz, Serializable id) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT);
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        StringBuilder idValueExpression = new StringBuilder();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            fillJoinerByExtractedFieldNames(stringJoiner, declaredField);
            Column column = declaredField.getAnnotation(Column.class);
            if (column != null && declaredField.getAnnotation(Id.class) != null) {
                makeExpressionForFieldValueDependsOnItsType(declaredField, id.toString(), idValueExpression);
            }
        }
        stringBuilder.append(stringJoiner).append(SPACE);
        stringBuilder.append(FROM);
        String tableName = getTableName(clazz);
        stringBuilder.append(tableName).append(SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(ID).append(EQUAL);
        stringBuilder.append(idValueExpression);
        stringBuilder.append(END_QUERY);
        return stringBuilder.toString();
    }

    @Override
    public String delete(Class<?> clazz, Serializable id) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELETE);
        stringBuilder.append(FROM);
        String tableName = getTableName(clazz);
        stringBuilder.append(tableName).append(SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(ID).append(EQUAL);

        StringBuilder idValueExpression = new StringBuilder();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Column column = declaredField.getAnnotation(Column.class);
            if (column != null && declaredField.getAnnotation(Id.class) != null) {
                makeExpressionForFieldValueDependsOnItsType(declaredField, id.toString(), idValueExpression);
            }
        }
        stringBuilder.append(idValueExpression);
        stringBuilder.append(END_QUERY);
        return stringBuilder.toString();
    }

    @Override
    public String insert(Object value) throws IllegalAccessException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(INSERT);
        stringBuilder.append(INTO);
        Class<?> clazz = value.getClass();
        String tableName = getTableName(clazz);
        stringBuilder.append(tableName).append(SPACE);
        stringBuilder.append(OPEN_BRACKET);
        StringJoiner stringJoinerForName = new StringJoiner(DELIMITER);
        StringJoiner stringJoinerForValues = new StringJoiner(DELIMITER);
        for (Field declaredField : clazz.getDeclaredFields()) {
            fillJoinerByExtractedFieldNames(stringJoinerForName, declaredField);
            fillJoinerByExtractedFieldValues(stringJoinerForValues, declaredField, value);
        }
        stringBuilder.append(stringJoinerForName);
        stringBuilder.append(CLOSE_BRACKET).append(SPACE);
        stringBuilder.append(VALUES);
        stringBuilder.append(OPEN_BRACKET);
        stringBuilder.append(stringJoinerForValues);
        stringBuilder.append(CLOSE_BRACKET);
        stringBuilder.append(END_QUERY);
        return stringBuilder.toString();
    }


    @Override
    public String update(Object value) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UPDATE);
        Class<?> userClass = value.getClass();
        String tableName = getTableName(userClass);
        stringBuilder.append(tableName).append(SPACE);
        stringBuilder.append(SET);
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        StringBuilder idValueExpression = new StringBuilder();
        for (Field declaredField : userClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            String fieldValue = declaredField.get(value).toString();
            Column column = declaredField.getAnnotation(Column.class);
            if (column != null && declaredField.getAnnotation(Id.class) == null) {
                String fieldName = column.name().isEmpty() ? declaredField.getName() : column.name();
                StringBuilder expression = new StringBuilder();
                expression.append(fieldName);
                expression.append(EQUAL);
                makeExpressionForFieldValueDependsOnItsType(declaredField, fieldValue, expression);
                stringJoiner.add(expression);
            } else {
                makeExpressionForFieldValueDependsOnItsType(declaredField, fieldValue, idValueExpression);
            }
        }
        stringBuilder.append(stringJoiner).append(SPACE);
        stringBuilder.append(WHERE);
        stringBuilder.append(ID).append(EQUAL);
        stringBuilder.append(idValueExpression);
        stringBuilder.append(END_QUERY);
        return stringBuilder.toString();
    }

    private void makeExpressionForFieldValueDependsOnItsType(Field declaredField, String fieldValue, StringBuilder expression) {
        if (declaredField.getType().equals(String.class)) {
            wrapInQuoter(expression, fieldValue);
        } else {
            expression.append(fieldValue);
        }
    }

    private void wrapInQuoter(StringBuilder expression, String value) {
        expression.append(QUOTER);
        expression.append(value);
        expression.append(QUOTER);
    }

    private void fillJoinerByExtractedFieldNames(StringJoiner stringJoiner, Field declaredField) {
        declaredField.setAccessible(true);
        Column column = declaredField.getAnnotation(Column.class);
        if (column != null) {
            String fieldName = column.name().isEmpty() ? declaredField.getName() : column.name();
            stringJoiner.add(fieldName);
        }
    }

    private void fillJoinerByExtractedFieldValues(StringJoiner stringJoiner, Field declaredField, Object value) throws IllegalAccessException {
        declaredField.setAccessible(true);
        Column column = declaredField.getAnnotation(Column.class);
        if (column != null) {
            if (declaredField.getType().equals(String.class)) {
                stringJoiner.add(QUOTER + declaredField.get(value) + QUOTER);
            } else {
                stringJoiner.add(declaredField.get(value).toString());
            }
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
