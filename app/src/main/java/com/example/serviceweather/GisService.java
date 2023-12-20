package com.example.serviceweather;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.MalformedURLException;

public class GisService extends Service {

    public static final String CHANNEL = "GIS_SERVICE";

    public static final String INFO = "INFO";

    private Handler h;

    @Override
    public void onCreate() {

        super.onCreate();
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                String response = (String) msg.obj;
                Intent i = new Intent(CHANNEL);
                i.putExtra(INFO, response);
                sendBroadcast(i);

            }
        };
        Toast.makeText(this, "Служба создана", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();

        Thread weatherThread = null;
        weatherThread = new Thread(new HTTPRequest(h));

        weatherThread.start();


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Служба завершена", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
