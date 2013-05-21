package com.cubee.engine.framework.includes.input;

public class OrientationInput 
{
	private float x,y,z;
	private float realX, realY, realZ;
	private float initX, initY, initZ;
	private final float X_DEAD_ZONE = 20;
	private final float Y_DEAD_ZONE = 15; // Degree
	private float leftXDeadZone, rightXDeadZone;
	private float topYDeadZone, downYDeadZone;
	
	private boolean calibrated = false;
	
	public OrientationInput()
	{
		this.reset();
	}
	
	public void update(float x, float y, float z)
	{
		this.x = (float) Math.round((Math.toDegrees(x)));
		this.y = (float) Math.round((Math.toDegrees(y)));
		this.z = (float) Math.round((Math.toDegrees(z)));
		
		this.x = (this.x+360)%360;
		this.y = (this.y+360)%360;
		this.z = (this.y+360)%360;
		
		this.realX = x;
		this.realY = y;
		this.realZ = z;
		
		// 360 degree fix
		if(this.x < 0.f)
		{
			this.x = 180.f + Math.abs(this.x);
		}
		if(this.y < 0.f)
		{
			this.y = 180.f + Math.abs(this.y);
		}
		if(this.z < 0.f)
		{
			this.z = 180.f + Math.abs(this.z);
		}
	}
	
	public void calibrate()
	{
		this.initX = this.x;
		this.initY = this.y;
		this.initZ = this.z;
		
		float rX = this.x - this.X_DEAD_ZONE;
		float lX = this.x + this.X_DEAD_ZONE;
		float rY = this.y - this.Y_DEAD_ZONE;
		float lY = this.y + this.Y_DEAD_ZONE;
		
		// Fix X
		if(lX < 0.f)
		{
			this.leftXDeadZone = 360.f - Math.abs(lX);
		}
		else if(lX > 360.f)
		{
			this.leftXDeadZone = lX - 360.f;
		}
		else
		{
			this.leftXDeadZone = lX;
		}
		if(rX < 0.f)
		{
			this.rightXDeadZone = 360.f - Math.abs(rX);
		}
		else if(rX > 360.f)
		{
			this.rightXDeadZone = rX - 360.f;
		}
		else
		{
			this.rightXDeadZone = rX;
		}
		
		// Fix Y
		if(rY < 0.f)
		{
			this.downYDeadZone = 360.f - Math.abs(rY);
		}
		else if(rY > 360.f)
		{
			this.downYDeadZone = rY - 360.f;
		}
		else
		{
			this.downYDeadZone = rY;
		}
		if(lY < 0.f)
		{
			this.topYDeadZone = 360.f - Math.abs(lY);
		}
		else if(lY > 360.f)
		{
			this.topYDeadZone = lY - 360.f;
		}
		else
		{
			this.topYDeadZone = lY;
		}
		
		// Calibrated
		this.calibrated = true;
	}
	
	public void reset()
	{
		this.x = 0.f;
		this.y = 0.f;
		this.z = 0.f;
		this.initX = 0.f;
		this.initY = 0.f;
		this.initZ = 0.f;
		this.calibrated = false;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getRealX()
	{
		return this.realX;
	}
	
	public float getGameX()
	{
		if(this.calibrated)
		{
			// Right check
			if(this.rightXDeadZone > 180.f || this.leftXDeadZone > 180.f)
			{
				if(this.x < this.rightXDeadZone && this.x > 180.f)
				{
					//System.out.println("1");
					return 1.f;
				}
				else if(this.x > this.leftXDeadZone && this.x < 180.f)
				{
					//System.out.println("2");
					return -1.f;
				}
			}
			else if(this.rightXDeadZone < 180.f || this.leftXDeadZone < 180.f)
			{
				if((this.x > this.rightXDeadZone && this.x < 180.f) 
					|| (this.x > this.rightXDeadZone && this.x > 180.f))
				{
					//System.out.println("3");
					return 1.f;
				}
				else if((this.x > this.leftXDeadZone && this.x > 180.f)
					|| (this.x > this.leftXDeadZone && this.x < 180.f))
				{
					//System.out.println("4");
					return -1.f;
				}
			}
			
			//System.out.println("5");
			return 0.f;
		}
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public float getRealY()
	{
		return this.realY;
	}
	
	public float getGameY()
	{
		if(this.calibrated)
		{
			// Right check
			if(this.topYDeadZone > 180.f)
			{
				if(this.y > this.topYDeadZone && this.y > 180.f)
				{
					//System.out.println("1");
					return 1.f;
				}
			}
			if(this.downYDeadZone > 180.f)
			{
				if(this.y < this.downYDeadZone && this.y > 180.f)
				{
					//System.out.println("2");
					return -1.f;
				}
			}
			if(this.topYDeadZone < 180.f)
			{
				if((this.y > this.topYDeadZone && this.y < 180.f) 
					|| (this.y > this.topYDeadZone && this.y < 180.f))
				{
					//System.out.println("3");
					return 1.f;
				}
			}
			if(this.downYDeadZone < 180.f)
			{
				if((this.y > this.downYDeadZone && this.y > 180.f)
					|| (this.y > this.downYDeadZone && this.y < 180.f))
				{
					//System.out.println("4");
					return -1.f;
				}
			}
			
			//System.out.println("5");
			return 0.f;
		}
		return this.y;
	}
	
	public float getZ()
	{
		return this.z;
	}
	
	public float getRealZ()
	{
		return this.realZ;
	}
	
	@Deprecated
	public float getGameZ()
	{
		return this.z;
	}
	
	public float[] getData()
	{
		float[] data = new float[]{this.x, this.y, this.z};
		return data;
	}
}
