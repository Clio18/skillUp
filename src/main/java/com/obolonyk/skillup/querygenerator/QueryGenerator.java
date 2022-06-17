package com.obolonyk.skillup.querygenerator;

public interface QueryGenerator<T> {

    String findAll(Class<T> t);

    String insert(T t);

    String update(T t) throws IllegalAccessException;

    String findById(Class<T> t, long id);

    String delete(Class<T> t, long id);
}