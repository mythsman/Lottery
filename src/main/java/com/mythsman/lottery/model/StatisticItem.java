package com.mythsman.lottery.model;

/**
 * Created by myths on 5/12/17.
 */
public class StatisticItem {
    String name;
    int total;
    int maxContinue;
    int maxLoss;
    int currentLoss;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMaxContinue() {
        return maxContinue;
    }

    public void setMaxContinue(int maxContinue) {
        this.maxContinue = maxContinue;
    }

    public int getMaxLoss() {
        return maxLoss;
    }

    public void setMaxLoss(int maxLoss) {
        this.maxLoss = maxLoss;
    }

    public int getCurrentLoss() {
        return currentLoss;
    }

    public void setCurrentLoss(int currentLoss) {
        this.currentLoss = currentLoss;
    }
}
