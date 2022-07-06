package com.obolonyk.skillup.querygenerator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public interface QueryGenerator {

    String findAll(Class<?> type);

    String insert(Object value) throws IllegalAccessException;

    String update(Object value) throws IllegalAccessException;

    String findById(Class<?> type, Serializable id);

    String delete(Class<?> type, Serializable id);
}