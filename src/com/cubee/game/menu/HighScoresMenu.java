package com.cubee.game.menu;

import android.graphics.Color;
import android.graphics.Paint.Align;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Screen;

public class HighScoresMenu extends Screen
{
	public HighScoresMenu(Game game) 
	{
		super(game);
		
		this.paint.setTextSize(50);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextAlign(Align.CENTER);
		this.buffer.drawString("Not available for the moment", (1280/2), Math.round((736/2)), this.paint);
	}
	
	@Override
	public void updateObservable(Object data)
	{
		
	}

	@Override
	public void updateUIElement(String key, Object data) 
	{
		
	}

	@Override
	public void updateScreen(long timeElapsed) 
	{
		
	}

	@Override
	public void paintScreen(long timeElapsed) 
	{
		this.drawBuffer();
	}

	@Override
	public void internalDispose() 
	{
		
	}

	@Override
	protected boolean onBackButtonPressed() 
	{
		return true;
	}
}
