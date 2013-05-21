package com.cubee.game.menu;

import java.util.Observable;

import android.graphics.Color;
import android.graphics.Paint.Align;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Screen;
import com.cubee.game.assets.Assets;
import com.cubee.game.assets.LoadingAssetsThread;

public class LoadingScreen extends Screen
{
	private double time = 0;
	private LoadingAssetsThread loading;
	private String status = "";
	
	public LoadingScreen(Game game) 
	{
		super(game);

		this.paint.setColor(Color.WHITE);
		this.paint.setTextAlign(Align.CENTER);
		this.paint.setTextSize(40f);
		
		// Launch the Loading Asset Manager
		this.loading = new LoadingAssetsThread(game);
		this.loading.preload();
		this.loading.start();
	}
	
	@Override
	public void updateObservable(Object data)
	{
		
	}

	@Override
	public void update(Observable observable, Object data) 
	{
		// No Buttons or UIElements
	}

	@Override
	public void updateScreen(long timeElapsed)
	{
		this.time += timeElapsed;
		
		if(this.time < 2000)
		{
			this.status = "Loading configuration...";
		}
		else if(this.time < 5000)
		{
			this.status = "Loading textures...";
		}
		else  if(this.time < 7000)
		{
			this.status = "Starting the game...";
		}
		else
		{
			this.game.setNextScreen(new MainMenu(this.game), false);
		}
	}

	@Override
	public void paintScreen(long timeElapsed) 
	{
		Graphics graphics = this.game.getGraphics();
		
		graphics.clearScreen(Color.BLACK);
		graphics.drawImage(Assets.menuBackground, 0, 0);
		graphics.drawImage(Assets.logo, (this.game.size.x/2 - (Assets.logo.getWidth()/2)), (this.game.size.y/5));
		graphics.drawLine(20, (this.game.size.y/2)+50, this.game.size.x - 20, (this.game.size.y/2)+50, Color.WHITE);
		graphics.drawString(this.status, this.game.size.x/2, ((this.game.size.y/4)*3), this.paint);
	}

	@Override
	public void internalDispose() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void updateUIElement(String key, Object data) 
	{
		// No UIElement
	}

	@Override
	protected boolean onBackButtonPressed() 
	{
		return true;
	}

}
