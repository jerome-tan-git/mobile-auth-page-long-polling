package com.jerome;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Thermometer {

//    public static final String BROKER_URL = "tcp://192.168.103.18:8080";
    //public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";

    public static final String TOPIC = "test/topass";

    private MqttClient client;
	private String host = "tcp://192.168.103.18:1883";
//	private String host = "tcp://112.124.31.61:1883";
	
	private String userName = "system";
	private String passWord = "1234qwer";

    public Thermometer() {
        try {
            client = new MqttClient(host, MqttClient.generateClientId(), new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start(String _msg) {
    	MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
        try {
            client.connect();

            publishTemperature(_msg);

            client.disconnect();

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void publishTemperature(String _msg) throws MqttException {
        final MqttTopic temperatureTopic = client.getTopic(TOPIC);

        final int temperature = createRandomNumberBetween(-20, 4);

        MqttMessage message=null;
		try {
			message = new MqttMessage(_msg.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        temperatureTopic.publish(message);

        System.out.println("Published data. Topic: " + temperatureTopic.getName() + "  Message: " +_msg);
    }

    public static int createRandomNumberBetween(int min, int max) {

        return new Random().nextInt(max - min + 1) + min;
    }

//    public static void main(String... args) {
//        final Thermometer thermometer = new Thermometer();
//        thermometer.start();
//    }
}
