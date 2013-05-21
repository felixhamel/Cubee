package com.cubee.engine.ui.button;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.framework.Input;
import com.cubee.engine.plugins.touch.TouchHandler;
import android.graphics.Point;
import com.cubee.engine.ui.Points;
import com.cubee.engine.ui.UIElement;
import com.google.common.base.Preconditions;

public abstract class UIButton extends UIElement implements Runnable
{	
	// Properties
	protected Image image = null;
	protected String text = "";
	protected UIButtonState state = UIButtonState.NORMAL;
	protected Paint paint = new Paint();

	protected int height = 0;
	protected int width = 0;
	protected int fingerTouch = -1;
	protected Point position;

	
	// Image properties
	protected Points normalStatePoints = null;
	protected Points hoveredStatePoints = null;
	//protected Points clickedStatePoints = null;
	
	// Touch
	protected TouchHandler touch;
	
	
	public UIButton(String text, Point position, Input input)
	{
		// Validation
		Preconditions.checkNotNull(text);
		Preconditions.checkNotNull(position);
		Preconditions.checkNotNull(input);
		
		// Initial Setup
		this.text = text;
		this.position = position;
		this.touch = input.getTouch();
		
		// Default paint
		this.paint.setTextAlign(Align.CENTER);
		this.paint.setColor(Color.WHITE);
		this.paint.setAntiAlias(true);
		
		// Setup
		this.setupPaint();
		this.setupImageProperties();
		
		this.changeState(UIButtonState.NORMAL);
	}
	
	public abstract void setupPaint();	
	public abstract void setupImageProperties();
	
	/**
	 * Draw the button
	 * @param graphics
	 */
	public void draw(Graphics graphics)
	{
		// Draw Button
		switch(this.state)
		{
			case NORMAL :
				graphics.drawImage(
						this.image, 
						this.position.x,
						this.position.y, 
						this.normalStatePoints.start.x, 
						this.normalStatePoints.start.y, 
						this.width,
						this.height
				);				
			break;
			case HOVERED :
				graphics.drawImage(
						this.image, 
						this.position.x,
						this.position.y, 
						this.hoveredStatePoints.start.x, 
						this.hoveredStatePoints.start.y, 
						this.width,
						this.height
				);
			break;
		}
		
		// Draw String
		if(this.text.length() > 0)
		{
			graphics.drawString(
					this.text, 
					this.position.x + (this.width / 2), 
					this.position.y + (this.height / 2), 
					this.paint);
			//System.out.println("Text: " + this.text + " Width: " + this.width + " Height: " + this.height + " | PositionX: " + (this.position.x + (this.height / 2)) + " | PositionY: " + (this.position.y + (this.width / 2)));
		}	
	}
	
	@Deprecated
	public boolean isTouched()
	{
		if(this.state == UIButtonState.HOVERED)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Reset the button to the normal state
	 */
	public void reset()
	{
		this.changeState(UIButtonState.NORMAL);
		this.fingerTouch = -1;
	}
	
	public void changeState(UIButtonState state)
	{
		this.state = state;
		switch(this.state)
		{
			case NORMAL :
				this.width = (this.normalStatePoints.end.x - this.normalStatePoints.start.x);
				this.height = (this.normalStatePoints.end.y - this.normalStatePoints.start.y);
			break;
			case HOVERED :
				this.width = (this.hoveredStatePoints.end.x - this.hoveredStatePoints.start.x);
				this.height = (this.hoveredStatePoints.end.y - this.hoveredStatePoints.start.y);
			break;
		}
	
	}
	@Override
	protected void internalRun() 
	{
		for(int i = 0; i < 2; i++)
		{
			if(this.touch.getTouchX(i) >= this.position.x && this.touch.getTouchX(i) <= (this.width + this.position.x))
			{
				if(this.touch.getTouchY(i) >= this.position.y && this.touch.getTouchY(i) <= (this.height + this.position.y))
				{
					if(this.state != UIButtonState.HOVERED)
					{
						this.fingerTouch = i;
						this.changeState(UIButtonState.HOVERED);
					}
				}
				else
				{
					//System.out.println("POINTER" + i + " X:" + this.touch.getTouchX(i) + " | Y:" + this.touch.getTouchY(i));
					this.reset();
				}
			}
			else if(i == this.fingerTouch && this.state != UIButtonState.NORMAL)
			{
				if(this.touch.isTouchDown(this.fingerTouch))
				{	
					//System.out.println("POINTER" + i + " X:" + this.touch.getTouchX(i) + " | Y:" + this.touch.getTouchY(i));
					this.reset();
				}
				else
				{
					this.setChanged();
					this.notifyObservers();
					this.reset();
				}
			}
		}
	}
}
