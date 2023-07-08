package com.ericulicny.shire.OakPower.domain;

public class PowerUsage {

    Double currentPowerUsageWatts;
    String updateTime;

    public Double getCurrentPowerUsageWatts() {
        return currentPowerUsageWatts;
    }

    public void setCurrentPowerUsageWatts(Double currentPowerUsageWatts) {
        this.currentPowerUsageWatts = currentPowerUsageWatts;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
