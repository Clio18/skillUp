package com.obolonyk.skillup.querygenerator;

import com.obolonyk.skillup.querygenerator.annotations.Column;
import com.obolonyk.skillup.querygenerator.annotations.Id;
import com.obolonyk.skillup.querygenerator.annotations.Table;

@Table(name = "users")
public class User {

    @Id
    @Column
    private long id;
    @Column(name = "user_name")
    private String name;
    @Column
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
