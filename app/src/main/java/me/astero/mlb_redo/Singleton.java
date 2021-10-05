package me.astero.mlb_redo;

import com.google.android.gms.maps.GoogleMap;

public class Singleton {


    private static Singleton instance;


    public GoogleMap map;

    public static Singleton getInstance()
    {
        if(instance == null)
        {
            instance = new Singleton();
        }

        return instance;
    }
}
