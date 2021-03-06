package com.example.chan.smartcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import de.greenrobot.event.EventBus;

public class IndicatorsAndControls extends AppCompatActivity implements View.OnClickListener{
    MqttClient mqttClient;
    int qos = 0;
    boolean retained = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_and_controls);

        mqttClient = EventBus.getDefault().removeStickyEvent(MqttClient.class);
        mqttClient.setCallback(new SubscribeCallback(this));
        try {
            mqttClient.subscribe("headlights/status");
            mqttClient.publish("headlights/status", "getStatus".getBytes(), qos, retained);
            mqttClient.subscribe("leftdoor/status");
            mqttClient.publish("leftdoor/status", "getStatus".getBytes(), qos, retained);
            mqttClient.subscribe("rightdoor/status");
            mqttClient.publish("rightdoor/status", "getStatus".getBytes(), qos, retained);
            mqttClient.subscribe("aircond/status");
            mqttClient.publish("aircond/status", "getStatus".getBytes(), qos, retained);
            mqttClient.subscribe("alarm/status");
            mqttClient.publish("alarm/status", "getStatus".getBytes(), qos, retained);
            mqttClient.subscribe("floodalarm/status");
            mqttClient.publish("floodalarm/status", "getStatus".getBytes(), qos, retained); //Subscribe and get the status of the car
        } catch (MqttException e) {
            e.printStackTrace();
        }

        //create all switches and register OnClickListener
        Switch switch_headlights = (Switch)findViewById(R.id.switch_headlights);
        Switch switch_leftdoor = (Switch) findViewById(R.id.switch_leftdoor);
        Switch switch_rightdoor = (Switch) findViewById(R.id.switch_rightdoor);
        Switch switch_aircond = (Switch) findViewById(R.id.switch_aircond);
        Switch switch_alarm = (Switch)findViewById(R.id.switch_alarm);

        switch_headlights.setOnClickListener(this); //this activity is an OnClickListener itself
        switch_leftdoor.setOnClickListener(this);
        switch_rightdoor.setOnClickListener(this);
        switch_aircond.setOnClickListener(this);
        switch_alarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switch_headlights:
                Switch switch_headlights = (Switch) view;
                if (switch_headlights.isChecked()) { //check the state of the switch, if it's in ON state(caused by a click), send a command to turn on
                    try {
                        mqttClient.publish("headlights/control", "turnON".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        mqttClient.publish("headlights/control", "turnOFF".getBytes(), qos, retained); //if not, turn it off
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.switch_leftdoor:
                Switch switch_leftdoor = (Switch) view;
                if (switch_leftdoor.isChecked()) { //check the state of the switch, if it's in ON state(caused by a click), send a command to turn on
                    try {
                        mqttClient.publish("leftdoor/control", "turnON".getBytes(), qos, retained);

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        mqttClient.publish("leftdoor/control", "turnOFF".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.switch_rightdoor:
                Switch switch_rightdoor = (Switch) view;
                if (switch_rightdoor.isChecked()) { //check the state of the switch, if it's in ON state(caused by a click), send a command to turn on
                    try {
                        mqttClient.publish("rightdoor/control", "turnON".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        mqttClient.publish("rightdoor/control", "turnOFF".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.switch_aircond:
                Switch switch_aircond = (Switch) view;
                if (switch_aircond.isChecked()) { //check the state of the switch, if it's in ON state(caused by a click), send a command to turn on
                    try {
                        mqttClient.publish("aircond/control", "turnON".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        mqttClient.publish("aircond/control", "turnOFF".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.switch_alarm:
                Switch switch_alarm = (Switch) view;
                if (switch_alarm.isChecked()) { //check the state of the switch, if it's in ON state(caused by a click), send a command to turn on
                    try {
                        mqttClient.publish("alarm/control", "turnON".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        mqttClient.publish("alarm/control", "turnOFF".getBytes(), qos, retained);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
