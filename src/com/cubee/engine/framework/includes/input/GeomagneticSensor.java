package com.cubee.engine.framework.includes.input;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class GeomagneticSensor implements SensorEventListener
{
	private float[] data = null;

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
		{
			this.data = event.values;
		}
	}
	
	public float[] getData()
	{
		return this.data;
	}
}
