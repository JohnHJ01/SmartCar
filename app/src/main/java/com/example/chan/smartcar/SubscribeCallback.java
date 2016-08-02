package com.example.chan.smartcar;

import android.app.Activity;
import android.content.Context;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Switch;
import android.widget.Toast;

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
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_headlights = (Switch) ((Activity) context).findViewById(R.id.switch_headlights);
                            switch_headlights.setChecked(false);
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
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_leftdoor = (Switch) ((Activity) context).findViewById(R.id.switch_leftdoor);
                            switch_leftdoor.setChecked(false);
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
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_rightdoor = (Switch) ((Activity) context).findViewById(R.id.switch_rightdoor);
                            switch_rightdoor.setChecked(false);
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
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_aircond = (Switch) ((Activity) context).findViewById(R.id.switch_aircond);
                            switch_aircond.setChecked(false);
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
                            Toast.makeText(context, "ALARM is RINGING!!!", Toast.LENGTH_SHORT).show();
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                            Ringtone r = RingtoneManager.getRingtone(context, notification);
                            r.play();
                            // TODO: 2/8/2016 Add a stop function to stop the ALARM LOL 
                        }
                    });
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_alarm = (Switch) ((Activity) context).findViewById(R.id.switch_alarm);
                            switch_alarm.setChecked(false);

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
