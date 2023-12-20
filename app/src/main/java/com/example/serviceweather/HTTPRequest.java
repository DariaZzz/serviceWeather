package com.example.serviceweather;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class HTTPRequest implements Runnable{

    public static final String CITY = "Chekhov";
    public static final String APIKEY = "f9cf4413bc073d83bee10ccf5942792d";

    public static String api = "https://api.openweathermap.org/data/2.5/weather";

    private URL url;

    private Handler handler;

    HTTPRequest(Handler h) {
        this.handler = h;
        try{
            this.url = new URL(api + "?q=" + CITY + "&appid=" + APIKEY + "&units=metric");
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Scanner in = new Scanner(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while(in.hasNext()) {
                response.append(in.nextLine());

            }
            in.close();
            connection.disconnect();
            Message msg = Message.obtain();
            msg.obj = response.toString();
            handler.sendMessage(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
