package com.cubee.engine.ui.animations.fade;

import com.cubee.engine.framework.Game;

public class FadeOut extends FadeAnimation
{

	public FadeOut(Game game) 
	{
		super(game);
	}

	@Override
	protected void setupValues() 
	{
		this.value = 0.f;
		this.interval = 0.05f;
	}

	@Override
	public void nextFrame() 
	{
		if(this.value < 1.f)
		{
			this.value += this.interval;
			
			if(this.value > 1.f)
			{
				this.value = 1.f;
			}
			
			//System.out.println("FADEOUT value: " + this.value);
		}
		else
		{
			this.end = true;
			this.start = false;
		}
	}

	@Override
	public void previousFrame() 
	{
		// No use
	}

}
