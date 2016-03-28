package com.example.jose.alcoholimia;

/**
 * Created by Jose on 3/28/2016.
 */

public class Dare {
    private long id;
    private String dare;
    private int drinks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDare() {
        return dare;
    }

    public void setDare(String dare) {
        this.dare = dare;
    }

    public int getDrinks() {
        return drinks;
    }

    public void setDrinks(int drinks) {
        this.drinks = drinks;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return dare;
    }
}
