package com.cubee.game.ui.popup;

import java.text.DecimalFormat;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.plugins.Chronometer;
import com.cubee.engine.plugins.touch.TouchHandler;
import com.cubee.engine.ui.UIElement;
import com.cubee.game.assets.Assets;

public class CalibrationPopup extends UIElement
{
	private Image popup = Assets.calibrationPopup;
	private Game game = null;
	private Chronometer chronometer = new Chronometer(false);
	private int posX, posY;
	
	private final float CALIBRATION_TIME = 2;
	
	public CalibrationPopup(Game game)
	{
		this.game = game;
		this.posX = this.game.size.x / 2 - (this.popup.getWidth()/2);
		this.posY = this.game.size.y / 2 - (this.popup.getHeight()/2);
	}
	
	@Override
	public void internalDraw(Graphics graphics) 
	{
		if(this.show)
		{
			graphics.drawImage(this.popup, posX, posY);
		}
	}
	
	@Override
	protected void internalRun() 
	{
		TouchHandler touch = this.game.getInput().getTouch();
		
		for(int i = 0; i < 1; i++)
		{
			if(touch.getTouchX(i) >= this.posX && touch.getTouchX(i) <= (this.posX + this.popup.getWidth()))
			{
				if(touch.getTouchY(i) >= this.posY && touch.getTouchY(i) <= (this.posY + this.popup.getHeight()))
				{
					if(!this.chronometer.isStarted())
					{
						this.chronometer.start();
					}
					else if(this.chronometer.getSeconds() >= CALIBRATION_TIME)
					{
						this.chronometer.stop();
						this.setChanged();
						this.notifyObservers();
						
						while(touch.isTouchDown(i))
						{
							
						}
						
						this.setChanged();
						this.notifyObservers("released");
					}
				}
				else
				{
					this.chronometer.stop();
					this.chronometer.reset();
				}
			}
			else
			{
				this.chronometer.stop();
				this.chronometer.reset();
			}
		}
		
		try 
		{
			Thread.sleep(150);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	public int getWidth() 
	{
		return this.popup.getWidth();
	}
	
	public int getHeight()
	{
		return this.popup.getWidth();
	}
	
	public int getPosX()
	{
		return this.posX;
	}
	
	public int getPosY()
	{
		return this.posY;
	}
	
	public float getCalibrationTime()
	{
		return this.CALIBRATION_TIME;
	}
	
	public String getTimeTouched()
	{
		double time =  Math.abs(this.CALIBRATION_TIME - this.chronometer.getSeconds());
		
		if(!this.game.getInput().getTouch().isTouchDown(0))
		{
			time = (double) this.CALIBRATION_TIME;
		}
		
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(time);
	}
}
