package com.example.serviceweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tempview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(receiver, new IntentFilter(GisService.CHANNEL), RECEIVER_EXPORTED);

        Intent intent = new Intent(this, GisService.class);
        startService(intent);

        tempview = findViewById(R.id.t);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, GisService.class);
        stopService(intent);

    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String response = intent.getStringExtra(GisService.INFO);
            try {
                JSONObject start = new JSONObject(response);
                JSONObject main = start.getJSONObject("main");
                String temp = main.getString("temp");
                tempview.setText(temp + " градусов Цельсия");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };
}