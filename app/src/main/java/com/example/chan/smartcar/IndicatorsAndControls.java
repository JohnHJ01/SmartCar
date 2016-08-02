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

public class IndicatorsAndControls extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_and_controls);

        // TODO: 31/7/2016 Check Wifi connection before trying to connect. Shows prompting box to enable Wifi as in BT Terminal app

        //create all switches and textviews and register OnClickListener
        Switch switch_headlights = (Switch)findViewById(R.id.switch_headlights);
        Switch switch_leftdoor = (Switch) findViewById(R.id.switch_leftdoor);
        Switch switch_rightdoor = (Switch) findViewById(R.id.switch_rightdoor);
        Switch switch_aircond = (Switch) findViewById(R.id.switch_aircond);
        Switch switch_alarm = (Switch)findViewById(R.id.switch_alarm);


        Button button_connect = (Button) findViewById(R.id.button_connect);

        TextView textview_headlights = (TextView)findViewById(R.id.textView_headlights);
        TextView textview_doorlocks = (TextView) findViewById(R.id.textView_leftdoor);
        TextView textview_alarm = (TextView) findViewById(R.id.textView_rightdoor);

        switch_headlights.setOnClickListener(this); //this acitivity is an OnClickListener itself
        switch_leftdoor.setOnClickListener(this);
        switch_rightdoor.setOnClickListener(this);
        switch_aircond.setOnClickListener(this);
        switch_alarm.setOnClickListener(this);
        button_connect.setOnClickListener(this);
    }

    //    String ip = "10.161.72.128";
//    String ip = "192.168.1.10";
    String ip = "test.mosquitto.org";
    MqttClient mqttClient;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // TODO: 2/8/2016 Aircond and alarm send command. Flood alarm.
            // TODO: 1/8/2016 Air_cond and Flood Alarm. Floor Alarm is passive, textview maybe? Separate doorlocks to left and right
                        
            case R.id.switch_headlights:
                TextView textview1 = (TextView)findViewById(R.id.textView_headlights);
                textview1.setText("Headlights clicked!");
                Switch switch_headlights = (Switch) view;

                if (switch_headlights.isChecked()) { //check the state of the switch, if it's in ON state(caused by a click), send a command to turn on
                    try {
                        mqttClient.publish("headlights/control", new MqttMessage("turnON".getBytes()));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                }

                break;
            case R.id.switch_leftdoor:
                TextView textview2 = (TextView) findViewById(R.id.textView_leftdoor);
                textview2.setText("leftdoor clicked!");
                break;
            case R.id.switch_rightdoor:
                TextView textview3 = (TextView) findViewById(R.id.textView_rightdoor);
                textview3.setText("rightdoor clicked!");
                break;
            case R.id.switch_aircond:
                break;
            case R.id.switch_alarm:
                break;
            case R.id.button_connect:
                //MQTT client
                try {
                    // TODO: 1/8/2016 Id and Password Login Page
                    mqttClient = new MqttClient("tcp://" + ip + ":1883", "CHAN_LAMBORGHINI", new MemoryPersistence());
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setCleanSession(true);
                    mqttClient.connect(options);
                    if (mqttClient.isConnected()){
                        Button button = (Button)findViewById(R.id.button_connect);
                        button.setText("Connected to " + ip);
                    }
                    //get initial status from myRIO
                    mqttClient.setCallback(new SubscribeCallback(this));
                    mqttClient.subscribe("headlights/status");
                    mqttClient.publish("headlights/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("leftdoor/status");
                    mqttClient.publish("leftdoor/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("rightdoor/status");
                    mqttClient.publish("rightdoor/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("aircond/status");
                    mqttClient.publish("aircond/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("alarm/status");
                    mqttClient.publish("alarm/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("flooadalarm/status");
                    mqttClient.publish("floodalarm/status", new MqttMessage("getStatus".getBytes()));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
