package me.astero.mlb_redo.database;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import me.astero.mlb_redo.R;
import me.astero.mlb_redo.database.data.LibraryData;

public class LibraryDatabase {

    private static LibraryDatabase instance = null;

    public List<String> libraryDataList = new LinkedList<>();

    public static LibraryDatabase getInstance(Context context) throws IOException, JSONException {


        if(instance == null)
        {
            instance = new LibraryDatabase();

            InputStream is = context.getResources().openRawResource(R.raw.libraries);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuilder sb = new StringBuilder();

            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }


            JSONArray jsonArray = new JSONArray(sb.toString());

            for(int i = 0; i < jsonArray.length(); i++)
            {

                JSONArray array = new JSONArray(new JSONObject(
                        jsonArray.get(i).toString()).getJSONArray("libraries").toString());




                for(int x = 0; x < array.length(); x++)
                {
                    JSONObject object = new JSONObject(array.get(x).toString());

                    LibraryData data = new LibraryData();
                    data.id = object.getInt("id");
                    data.name = object.getString("name");


                    System.out.println(data.name + new JSONObject(
                            jsonArray.get(i).toString()).getString("region") + " REGION");

                    instance.libraryDataList.add(data.name);

                }

            }




        }

        return instance;



    }
}
