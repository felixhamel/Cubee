package com.cubee.engine.framework;

import android.hardware.SensorManager;

import com.cubee.engine.framework.includes.input.AccelerometerSensor;
import com.cubee.engine.framework.includes.input.GeomagneticSensor;
import com.cubee.engine.framework.includes.input.OrientationInput;
import com.cubee.engine.plugins.touch.MultiTouchHandler;
import com.cubee.engine.plugins.touch.TouchHandler;

public class Input extends Thread
{
	// Touch
	private TouchHandler touchHandler = null;
	
	// Space
	private AccelerometerSensor accelerometer = null;
	private GeomagneticSensor geomagnetic = null;
	private OrientationInput orientation = null;

	public Input(View view, float scaleX, float scaleY)
	{
		// Touch
		this.touchHandler = new MultiTouchHandler(view, scaleX, scaleY);		
		
		// Position in space
		this.accelerometer = new AccelerometerSensor();
		this.geomagnetic = new GeomagneticSensor();
		this.orientation = new OrientationInput();
		
		// Remap	
		this.start();
	}
	
	public TouchHandler getTouch()
	{
		return this.touchHandler;
	}
	
	public AccelerometerSensor getAccelerometerSensor()
	{
		return this.accelerometer;
	}
	
	public GeomagneticSensor getGeomagneticSensor()
	{
		return this.geomagnetic;
	}
	
	public OrientationInput getOrientationInput()
	{
		return this.orientation;
	}
	
	public void run()
	{
		while(true)
		{
			if(this.accelerometer.getValues() != null && this.geomagnetic.getData() != null)
			{
				float R[] = new float[9];
				//float I[] = new float[9];
				
				if(SensorManager.getRotationMatrix(R, null, this.accelerometer.getValues(), this.geomagnetic.getData()))
				{
					// Remap
					/*float newR[] = new float[9];
					SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_X, SensorManager.AXIS_Z, newR);*/
					
					float O[] = new float[3];
					SensorManager.getOrientation(R, O);
					this.orientation.update(O[1], O[2], O[0]);
					//System.out.println("Orientation->X:" + O[1] + ":" + this.orientation.getX() + ":" + this.orientation.getGameX()+" | Y:" + O[2] + ":" + this.orientation.getY() + ":" + this.orientation.getGameY());
				}
			}
			
			try 
			{
				Thread.sleep(16);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
