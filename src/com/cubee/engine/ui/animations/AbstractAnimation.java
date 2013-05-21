package com.cubee.engine.ui.animations;

import com.cubee.engine.framework.Game;

public abstract class AbstractAnimation
{
	protected Game game;
	protected boolean start = false;
	protected boolean end = false;
	
	public AbstractAnimation(Game game)
	{
		this.game = game;
	}
	
	public void start()
	{
		if(!this.start)
		{
			this.start = true;
			this.end = false;
		}
	}
	
	public boolean isStarted()
	{
		return this.start;
	}
	
	protected void end()
	{
		this.start = false;
		this.end = true;
	}
	
	public boolean isFinished()
	{
		return this.end;
	}
	
	public abstract void nextFrame();
	public abstract void previousFrame();
	protected abstract void draw();
}
