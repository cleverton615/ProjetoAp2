package com.ap2.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClienteWeather {

    private static String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } finally {
            urlConnection.disconnect();
        }
    }

    private static String APPid = "weather?q=caruaru&appid=e54fe2431d61e3469a42df3b9dacb45c";

    public static Localizacao localizar(String name) throws JSONException, IOException {
        String resposta = request("http://api.openweathermap.org/data/2.5/weather?q=" + name + APPid);
        JSONObject obj = new JSONObject(resposta);
        String id = obj.getString("Id");
        String description = obj.getString("description");
        return new Localizacao(id, name, description);
    }

}
