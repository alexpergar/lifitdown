package com.aleperaltagar.simpleworkoutnotebook;

import java.util.ArrayList;

public class Exercise {

    private int id;
    private String name;
    private ArrayList<Set> sets;

    public Exercise(String name) {
        this.id = Utils.getID();
        this.name = name;
        this.sets = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sets=" + sets +
                '}';
    }
}
