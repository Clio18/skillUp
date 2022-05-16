package com.obolonyk.skillUp.reflection;

public class Truck extends Car {

    private double weight;

    public Truck() {
    }

    public Truck(int wheels, double weight) {
        super(wheels);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
