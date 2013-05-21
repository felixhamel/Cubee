package com.cubee.engine.ui;

import java.util.Observable;

import android.graphics.Point;

import com.cubee.engine.framework.Graphics;

public abstract class UIElement extends Observable implements Runnable
{
	protected boolean show = true;
	protected boolean alive = true;
	protected boolean running = true;
	protected Point position = null;
	
	public UIElement()
	{
		
	}
	
	public UIElement(boolean show, boolean running)
	{
		this.show = show;
		this.running = running;
	}
	
	public void dispose()
	{
		this.show = false;
		this.alive = false;
		this.running = false;
		this.position = null;
		System.gc();
	}
	
	public void resume()
	{
		this.running = true;
	}
	
	public void pause()
	{
		this.running = false;
	}
	
	public void show()
	{
		this.show = true;
	}
	
	public void hide()
	{
		this.show = false;
	}
	
	public void draw(Graphics graphics)
	{
		if(this.show)
		{
			this.internalDraw(graphics);
		}
	}
	
	public abstract void internalDraw(Graphics graphics);
	
	@Override
	public void run() 
	{
		try
		{
			while(this.alive)
			{
				while(this.running)
				{
					this.internalRun();
					Thread.sleep(16);
				}
				Thread.sleep(100);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	protected abstract void internalRun();
}