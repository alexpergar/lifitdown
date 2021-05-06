package com.aleperaltagar.simpleworkoutnotebook;

public class Set {

    private int id;
    private int exerciseId;
    private String date;
    private String reps;
    private String weight;

    public Set(int exerciseId) {
        this.id = Utils.getSetID();
        this.exerciseId = exerciseId;
        this.date = "none";
        this.reps = "";
        this.weight = "";
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

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
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
