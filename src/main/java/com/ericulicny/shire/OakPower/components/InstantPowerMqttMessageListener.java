package com.ericulicny.shire.OakPower.components;

import com.ericulicny.shire.OakPower.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class InstantPowerMqttMessageListener implements Runnable {
    @Autowired
    MqttSubscriber mqttSubscriber;

    @Autowired
    MessageService messageService;

    @Value("${mqtt.topic:default}")
    private String mqttTopic;

    @Value("${mqtt.user:}")
    private String mqttUser;

    @Value("${mqtt.pass:}")
    private String mqttPass;

    @Value("${mqtt.broker}")
    private String mqttBroker;

    @Value("${mqtt.port:1883}")
    private Integer mqttPort;

    private static final Logger logger = LoggerFactory.getLogger(InstantPowerMqttMessageListener.class);

    @Override
    public void run() {

            try {
                connect();

                while (true) {
                    try {
                        mqttSubscriber.subscribeMessage(mqttTopic);

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Inner Event Loop Exception reason = " + e.getMessage());
                        logger.info("Connection died will try to reconnect in 30s");
                        mqttSubscriber.disconnect();
                        Thread.sleep(30000);
                        connect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Connection Loop Exception reason =" + e.getMessage());
                mqttSubscriber.disconnect();
                logger.info("Connection died will try to reconnect in 30s");
                try {
                    Thread.sleep(30000);
                    connect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        }

    }

    private void connect() throws Exception {
        logger.info("Will attempt to connect to topic " + mqttTopic);
        mqttSubscriber.config(mqttBroker, mqttPort, mqttUser, mqttPass, messageService);
        mqttSubscriber.connect();
    }


    @PreDestroy
    public void onExit() {
        logger.info("Shutting down API...");
        mqttSubscriber.disconnect();
    }
}
