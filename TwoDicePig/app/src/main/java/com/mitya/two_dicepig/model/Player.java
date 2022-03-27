package com.mitya.two_dicepig.model;

import android.widget.TextView;

public class Player {
    private String name;
    private int totalScore;

    public Player(String name, int totalScore) {
        this.name = name;
        this.totalScore = totalScore;
    }

    public Player(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        if (name.equalsIgnoreCase("Player 1")) {
            TextView totalScor;
        }
        this.totalScore = totalScore;
    }
}
