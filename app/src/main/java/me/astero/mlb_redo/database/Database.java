package me.astero.mlb_redo.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class Database {



    public static String IP_ADDRESS = "http://192.168.1.9:8080/";

    public static String post(String route, String body) throws IOException {
        URL url = new URL(IP_ADDRESS + route);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonBody = "{" + body + "}";


        try(OutputStream os = connection.getOutputStream())
        {
            os.write(jsonBody.getBytes());
        }



        InputStream is = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        StringBuilder results = new StringBuilder();

        while((line = reader.readLine()) != null)
        {

            results.append(line);
        }

        return results.toString();




    }


    public static String get(String route) throws IOException {
        URL url = new URL(IP_ADDRESS + route);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        connection.setRequestMethod("GET");


        InputStream is = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder builder = new StringBuilder();

        String line;

        while((line = reader.readLine()) != null)
        {
            builder.append(line);
        }

        return builder.toString();




    }
}
