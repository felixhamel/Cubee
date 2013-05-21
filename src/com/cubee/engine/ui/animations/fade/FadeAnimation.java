package com.cubee.engine.ui.animations.fade;

import com.cubee.engine.framework.Game;
import com.cubee.engine.ui.animations.AbstractAnimation;

public abstract class FadeAnimation extends AbstractAnimation
{
	// Setup
	protected float value = 0;
	protected float interval = 0.05f;
	
	public FadeAnimation(Game game) 
	{
		super(game);
		this.setupValues();
	}
	
	protected abstract void setupValues();
	public abstract void nextFrame();
	public abstract void previousFrame();
	
	protected void draw()
	{
		this.game.getGraphics().drawARGB(Math.round(this.value * 255), 0, 0, 0);
		//this.game.getView().setAlpha(this.value);
	}
	
	public void drawOnScreen()
	{
		this.draw();
	}
}