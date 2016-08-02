package com.example.chan.smartcar;

import android.app.Activity;
import android.content.Context;

import android.widget.Switch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by hachan on 30/7/2016.
 * Logics for message received.
 * Switch Case logic.
 */
public class SubscribeCallback implements MqttCallback {
    public Context context;

    //Constructor for passing Activity object
    public SubscribeCallback(Context _context) {
        this.context = _context;
    }
    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message arrived. Topic: " + topic + " Message: " + mqttMessage.toString());
        switch (topic) {
            case "headlights/status":
                if (mqttMessage.toString().equals("ON")) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_headlights = (Switch) ((Activity) context).findViewById(R.id.switch_headlights);
                            switch_headlights.setChecked(true);
                        }
                    });
                }
                break;
            case "leftdoor/status":
                if (mqttMessage.toString().equals("ON")) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_leftdoor = (Switch) ((Activity) context).findViewById(R.id.switch_leftdoor);
                            switch_leftdoor.setChecked(true);
                        }
                    });
                }
                break;
            case "rightdoor/status":
                if (mqttMessage.toString().equals("ON")) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_rightdoor = (Switch) ((Activity) context).findViewById(R.id.switch_rightdoor);
                            switch_rightdoor.setChecked(true);
                        }
                    });
                }
                break;
            case "aircond/status":
                if (mqttMessage.toString().equals("ON")) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_aircond = (Switch) ((Activity) context).findViewById(R.id.switch_aircond);
                            switch_aircond.setChecked(true);
                        }
                    });
                }
                break;
            case "alarm/status":
                if (mqttMessage.toString().equals("ON")) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_alarm = (Switch) ((Activity) context).findViewById(R.id.switch_alarm);
                            switch_alarm.setChecked(true);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
