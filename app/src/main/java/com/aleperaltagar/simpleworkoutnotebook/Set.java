package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;

public class Set {

    private int id;
    private int exerciseId;
    private String reps;
    private String weight;
    private String restingTime;
    private String executionTime;
    private boolean failure;
    private String RIR;
    private String targetReps;
    private String mood;
    private String comment;
    private String note;
    private String personal1;
    private String personal2;

    public Set(Context context, int exerciseId) {
        this.id = Utils.getSetID(context);
        this.exerciseId = exerciseId;
        this.reps = "";
        this.weight = "";
        this.restingTime = "";
        this.executionTime = "";
        this.failure = false;
        this.RIR = "";
        this.targetReps = "";
        this.mood = "";
        this.comment = "";
        this.note = "";
        this.personal1 = "";
        this.personal2 = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRestingTime() {
        return restingTime;
    }

    public void setRestingTime(String restingTime) {
        this.restingTime = restingTime;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public String getRIR() {
        return RIR;
    }

    public void setRIR(String RIR) {
        this.RIR = RIR;
    }

    public String getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(String targetReps) {
        this.targetReps = targetReps;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPersonal1() {
        return personal1;
    }

    public void setPersonal1(String personal1) {
        this.personal1 = personal1;
    }

    public String getPersonal2() {
        return personal2;
    }

    public void setPersonal2(String personal2) {
        this.personal2 = personal2;
    }

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", exerciseId=" + exerciseId +
                ", reps='" + reps + '\'' +
                ", weight='" + weight + '\'' +
                ", restingTime='" + restingTime + '\'' +
                ", executionTime='" + executionTime + '\'' +
                ", failure=" + failure +
                ", RIR='" + RIR + '\'' +
                ", targetReps='" + targetReps + '\'' +
                ", mood=" + mood +
                ", comment='" + comment + '\'' +
                ", note='" + note + '\'' +
                ", personal1='" + personal1 + '\'' +
                ", personal2='" + personal2 + '\'' +
                '}';
    }
}
