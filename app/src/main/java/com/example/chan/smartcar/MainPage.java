package com.example.chan.smartcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import de.greenrobot.event.EventBus;

public class MainPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button button_connect = (Button) findViewById(R.id.button_connect); //setOnClickListener of Connect Button
        button_connect.setOnClickListener(this);
    }

    MqttClient mqttClient;

    @Override
    public void onClick(View view) {
        EditText username = (EditText) findViewById(R.id.editText_username);
        EditText password = (EditText) findViewById(R.id.editText_password);
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT).show(); //shows connecting if ID and password match

            String ip = ((EditText) findViewById(R.id.editText_IP)).getText().toString();
            try {
                mqttClient = new MqttClient("tcp://" + ip + ":1883", "NI_LAMBORGHINI", new MemoryPersistence());
                MqttConnectOptions options = new MqttConnectOptions();
                options.setCleanSession(true);
                mqttClient.connect(options);
                if (mqttClient.isConnected()) {
                    Intent intent = new Intent(this, IndicatorsAndControls.class);
                    EventBus.getDefault().postSticky(mqttClient);
                    startActivity(intent);
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Username or Password.", Toast.LENGTH_SHORT).show();
        }
    }
}
