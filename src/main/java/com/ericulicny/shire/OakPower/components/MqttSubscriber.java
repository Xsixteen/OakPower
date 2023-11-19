package com.ericulicny.shire.OakPower.components;

import com.ericulicny.shire.OakPower.services.MessageService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public class MqttSubscriber implements MqttCallback {
        protected String broker;
        protected final int qos = 1;
        protected Integer port = 1883; /* Default port */
        protected String userName;
        protected String password;
        protected String TCP = "tcp://";

        private String brokerUrl = null;
        final private String colon = ":";
        final private String clientId = UUID.randomUUID().toString();

        private MqttClient mqttClient = null;
        private MqttConnectOptions connectionOptions = null;
        private MemoryPersistence persistence = null;

        private MessageService messageService;

        private static final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);

        public MqttSubscriber(String broker, Integer port, String userName, String password, MessageService messageService) throws Exception {
            this.messageService = messageService;
            config(broker, port, userName, password, messageService);
        }

        public MqttSubscriber() {
        }

        @Override
        public void connectionLost(Throwable cause) {
            logger.info("Connection Lost" + cause);
        }

        public void config(String broker, Integer port, String userName, String password, MessageService messageService) throws Exception {
            logger.info("Inside Parameter Config");

            this.messageService = messageService;
            this.broker = broker;
            this.port = port;
            this.userName = userName;
            this.password = password;

            String protocal = this.TCP;


            this.brokerUrl = protocal + this.broker + colon + port;
            this.persistence = new MemoryPersistence();
            this.connectionOptions = new MqttConnectOptions();

            logger.info("Setting Broker: " + brokerUrl + " Client: " + clientId + " Persistence:" + persistence);
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);

            if(this.password != null && !this.password.isEmpty()) {
                this.connectionOptions.setPassword(this.password.toCharArray());
            }

            if (this.userName != null && !this.userName.isEmpty()) {
                this.connectionOptions.setUserName(this.userName);
            }


        }


        public void subscribeMessage(String topic) throws Exception{
            try {

                this.mqttClient.subscribe(topic, this.qos);
            } catch (MqttException me) {
                logger.error("Not able to Read Topic  " + topic);
                me.printStackTrace();
                throw new Exception("Unable to read topic.  Reason = " + me.getMessage());
            }
        }

        public void disconnect() {
            try {
                this.mqttClient.disconnect();
            } catch (MqttException me) {
                logger.error("ERROR", me);
            }
        }

        public void connect() throws Exception {
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        }


        @Override
        public void messageArrived(String mqttTopic, MqttMessage mqttMessage) throws Exception {
            String time = new Timestamp(System.currentTimeMillis()).toString();
            logger.info("Message Arrived at Time: " + time + "  Topic: " + mqttTopic + "  Message: "
                    + new String(mqttMessage.getPayload()));
            JSONObject jsonObject = new JSONObject(new String(mqttMessage.getPayload()));
            messageService.logCurrentUsageWatts(jsonObject.getDouble("value"));
//    System.out.println("***********************************************************************");


        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
}
