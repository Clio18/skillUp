package com.obolonyk.skillUp.reflection.entity;

import com.obolonyk.skillUp.reflection.entity.carInterface.Driveable;

import java.util.Objects;

public class Ford extends Truck implements Driveable {

    private String type;
    private boolean isRunAndDrive;
    private int passengers;

    public Ford(int wheels, double weight, String type, boolean isRunAndDrive, int passengers) {
        super(wheels, weight);
        this.type = type;
        this.isRunAndDrive = isRunAndDrive;
        this.passengers = passengers;
    }

    public Ford() {

    }

    public Ford(String type, boolean isRunAndDrive, int passengers) {
        this.type = type;
        this.isRunAndDrive = isRunAndDrive;
        this.passengers = passengers;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRunAndDrive() {
        return isRunAndDrive;
    }

    public void setRunAndDrive(boolean runAndDrive) {
        isRunAndDrive = runAndDrive;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    @Override
    public void drive() {
        System.out.println("This Ford is driving well!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ford ford = (Ford) o;
        return isRunAndDrive == ford.isRunAndDrive && passengers == ford.passengers && Objects.equals(type, ford.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, isRunAndDrive, passengers, super.getWeight(), super.getWheels());
    }

    @Override
    public String toString() {
        return "Ford{" +
                "type='" + type + '\'' +
                ", isRunAndDrive=" + isRunAndDrive +
                ", passengers=" + passengers +
                ", wheels=" + super.getWheels() +
                ", weight=" + super.getWeight() +
                '}';
    }
}
