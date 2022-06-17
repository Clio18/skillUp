package com.obolonyk.skillup.querygenerator;

import com.obolonyk.skillup.querygenerator.annotations.DBColumn;
import com.obolonyk.skillup.querygenerator.annotations.DBId;
import com.obolonyk.skillup.querygenerator.annotations.DBTable;

@DBTable (name = "users")
public class User {

    @DBId
    @DBColumn
    private long id;
    @DBColumn (name = "user_name")
    private String name;
    @DBColumn
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
