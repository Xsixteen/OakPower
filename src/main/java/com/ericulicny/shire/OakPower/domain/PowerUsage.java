package com.ericulicny.shire.OakPower.domain;

public class PowerUsage {

    Double currentPowerUsageWatts;
    String updateTime;
    Long epochSeconds;

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

    public Long getEpochSeconds() {
        return epochSeconds;
    }

    public void setEpochSeconds(Long epochSeconds) {
        this.epochSeconds = epochSeconds;
    }
}
