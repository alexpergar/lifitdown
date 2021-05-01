package com.aleperaltagar.simpleworkoutnotebook;

public class Set {

    private int id;
    private int exerciseId;
    private String date;
    private int reps;
    private double weight;

    public Set(int exerciseId) {
        this.id = Utils.getSetID();
        this.exerciseId = exerciseId;
        this.date = "none";
        this.reps = 0;
        this.weight = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Set{" +
                "exerciseId=" + exerciseId +
                ", date='" + date + '\'' +
                ", reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
