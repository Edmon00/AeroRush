package com.EdmonAntonyan.aerorush;
public class UserScore {
    private String name;
    private int points;

    public UserScore() {
        // Пустой конструктор нужен для Firebase
    }

    public UserScore(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}