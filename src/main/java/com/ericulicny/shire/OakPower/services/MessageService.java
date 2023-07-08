package com.ericulicny.shire.OakPower.services;

import com.ericulicny.shire.OakPower.components.MqttSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class MessageService {

    private Double currentUsageWatts;
    private String updatedTime;

    private static final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);

    public void logCurrentUsageWatts(Double currentUsageWatts) {
        this.currentUsageWatts = currentUsageWatts;
        updateTime();
    }

    public Double getCurrentUsageWatts() {
        return this.currentUsageWatts;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    private void updateTime() {
        this.updatedTime = new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Timestamp(System.currentTimeMillis()));
        logger.info("Message Service registered at : " + this.updatedTime);

    }

}
