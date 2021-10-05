package me.astero.mlb_redo;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Sensor implements SensorEventListener {



    private SensorManager sensorManager;
    private android.hardware.Sensor sensor;

    public Sensor(Context context)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        System.out.println(event.values[0]);
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }
}
