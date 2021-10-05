package me.astero.mlb_redo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Util {


    public static int getImageResourceId(Context context, String name)
    {
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
        return context.getResources().getIdentifier(name, "drawables", context.getPackageName());



    }


    public static void showSnackbar(View view, View.OnClickListener clickListener, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Dismiss", clickListener)
                .setBackgroundTint(view.getResources().getColor(R.color.design_default_color_on_primary))
                .setTextColor(Color.BLACK)
                .show();
    }

    public static void test()
    {

        Map<String, Integer> map = new HashMap<>();


        map.put("James", 5);
        map.put("Aden", 1);
        map.put("Adrian", 1);
        map.put("Jae", 50);
        map.put("Sally", 150);
        map.put("Kelly", 50);


        List<Integer> sortedList = new LinkedList();
        Map<String, Integer> sortedMap = new LinkedHashMap<>();

        for(int i : map.values())
        {
            sortedList.add(i);
        }

        Collections.sort(sortedList);

        for(int i : sortedList)
        {
            for(String name : map.keySet())
            {
                if(map.get(name) == i)
                {
                    sortedMap.put(name, i);
                    map.remove(name);
                    break;
                }
            }
        }

        System.out.println(sortedMap.toString() + " MAPPY");
    }
}
