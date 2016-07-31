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
        Switch switch_doorlocks = (Switch)findViewById(R.id.switch_doorlocks);
        Switch switch_alarm = (Switch)findViewById(R.id.switch_alarm);

        Button button_connect = (Button) findViewById(R.id.button_connect);

        TextView textview_headlights = (TextView)findViewById(R.id.textView_headlights);
        TextView textview_doorlocks = (TextView)findViewById(R.id.textView_doorlocks);
        TextView textview_alarm = (TextView)findViewById(R.id.textView_alarm);

        switch_headlights.setOnClickListener(this); //this acitivity is an OnClickListener itself
        switch_doorlocks.setOnClickListener(this);
        switch_alarm.setOnClickListener(this);
        button_connect.setOnClickListener(this);
    }

    //    String ip = "10.161.72.128";
//    String ip = "192.168.1.10";
    String ip = "test.mosquitto.org";

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switch_headlights:
                TextView textview1 = (TextView)findViewById(R.id.textView_headlights);
                textview1.setText("Headlights clicked!");
                break;
            case R.id.switch_doorlocks:
                TextView textview2= (TextView)findViewById(R.id.textView_doorlocks);
                textview2.setText("Doorlocks clicked!");
                break;
            case R.id.switch_alarm:
                TextView textview3 = (TextView)findViewById(R.id.textView_alarm);
                textview3.setText("Alarm clicked!");
                break;
            case R.id.button_connect:
                //MQTT client
                try {
                    MqttClient mqttClient = new MqttClient("tcp://" + ip + ":1883", "CHAN_LAMBORGHINI", new MemoryPersistence());
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setCleanSession(true);
                    mqttClient.connect(options);
                    if (mqttClient.isConnected()){
                        Button button = (Button)findViewById(R.id.button_connect);
                        button.setText("Connected to " + ip);
                    }
                    //get initial status from myRIO
                    mqttClient.setCallback(new SubscribeCallback(this));
                    mqttClient.subscribe("debug");
                    mqttClient.subscribe("headlights/status");
                    mqttClient.publish("headlights/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("doorlocks/status");
                    mqttClient.publish("doorlocks/status", new MqttMessage("getStatus".getBytes()));
                    mqttClient.subscribe("alarm/status");
                    mqttClient.publish("alarm/status", new MqttMessage("getStatus".getBytes()));

                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
