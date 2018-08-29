package com.techinterview.onlinestore.domain;

/**
 * Backpack product definition.
 * SEE TASK DESCRIPTION IN ProductListProcessor CLASS!
 */
public class BackPack extends Product {

    /**
     * Maximum weight of content that can be placed inside a backpack.
     */
    private double maxContentWeight;

    /**
     * Backpack color.
     */
    private String color;

    /**
     * Capacity (in liters) of all parts of back pack.
     */
    private double capacity;

    public BackPack(String guid, String name) {
        super(guid, name);
    }

    public double getMaxContentWeight() {
        return maxContentWeight;
    }

    public void setMaxContentWeight(double maxContentWeight) {
        this.maxContentWeight = maxContentWeight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}
