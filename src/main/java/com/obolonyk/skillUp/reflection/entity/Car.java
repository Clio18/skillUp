package com.obolonyk.skillUp.reflection.entity;

public class Car {

    private int wheels;

    public Car() {
    }

    public Car(int wheels) {
        this.wheels = wheels;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheels=" + wheels +
                '}';
    }
}
