package com.cubee.engine.framework.includes.input;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerSensor implements SensorEventListener
{
	private float x, y, z;
	private float initX, initY;
	private final float DEAD_ZONE = 25;
	
	public AccelerometerSensor()
	{
		this.init();
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			this.x = event.values[0];
			this.y = event.values[1];
			this.z = event.values[2];
		}
	}
	
	private void init()
	{
		this.x = 0.f;
		this.y = 0.f;
		this.z = 0.f;
		this.initX = 0.f;
		this.initY = 0.f;
	}
	
	public void calibrate(float posX, float posY)
	{
		this.initX = posX;
		this.initY = posY;
	}
	
	// Gets
	public float[] getValues()
	{
		if(this.x != 0.f && this.y != 0.f && this.z != 0.f)
		{
			float[] values = new float[]{this.x, this.y, this.z};
			return values;
		}
		return null;
	}
	
	public float getX()
	{
		if(this.initX != 0.f)
		{
			if(this.x > (this.DEAD_ZONE + this.initX))
			{
				return this.x - this.initX - this.DEAD_ZONE;
			}
			else if(this.x < (this.initX - this.DEAD_ZONE))
			{
				return this.x + this.initX + this.DEAD_ZONE;
			}
			else
			{
				return 0.f;
			}
		}
		return this.x;
	}
	
	public float getY()
	{
		if(this.initX != 0.f)
		{
			if(this.y > (this.DEAD_ZONE + this.initY))
			{
				return this.y - this.initX - this.DEAD_ZONE;
			}
			else if(this.y < (this.initY - this.DEAD_ZONE))
			{
				return this.y + this.initX + this.DEAD_ZONE;
			}
			else
			{
				return 0.f;
			}
		}
		return this.y;
	}
	
	public float getZ()
	{
		return this.z;
	}
}
