package com.example.chan.smartcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
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
 * Switch Case logic for every topics and messages.
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
                            Toast.makeText(context, "ALARM is RINGING!!!", Toast.LENGTH_SHORT).show();//
                            Intent startIntent = new Intent(context, RingtonePlayingService.class);
                            context.startService(startIntent);
                        }
                    });
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Switch switch_alarm = (Switch) ((Activity) context).findViewById(R.id.switch_alarm);
                            switch_alarm.setChecked(false);
                            Intent i = new Intent(context, RingtonePlayingService.class);
                            context.stopService(i);
                        }
                    });
                }
                break;
            case "floodalarm/status":
                if (mqttMessage.toString().equals("ON")) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Your car is flooded!!!", Toast.LENGTH_SHORT).show();//
                            Intent startIntent = new Intent(context, RingtonePlayingService.class);
                            context.startService(startIntent);
                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Flood Alert");
                            alertDialog.setMessage("Please move your car to a safe position.");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK, I will.",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(context, RingtonePlayingService.class);
                                            context.stopService(i);
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    });
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(context, RingtonePlayingService.class);
                            context.stopService(i);
                        }
                    });
                }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
