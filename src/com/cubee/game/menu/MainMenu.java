package com.cubee.game.menu;

import android.graphics.Color;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Screen;
import android.graphics.Point;
import com.cubee.game.assets.Assets;
import com.cubee.game.ui.MenuItem;

public class MainMenu extends Screen
{	
	public MainMenu(Game game) 
	{
		super(game);
		this.addUIElement(
				new MenuItem(
						"Select Level", 
						new Point(40,300), 
						this.game.getInput()), 
				"SelectLevel"
		);
		this.addUIElement(
				new MenuItem(
						"Options",
						new Point(40,425),
						this.game.getInput()),
				"Options"
		);
		/*this.addUIElement(
				new MenuItem(
						"High Scores",
						new Point(40, 550),
						this.game.getInput()),
					"HighScores"
		);*/
		
		this.paint.setTextSize(20);
		this.paint.setColor(Color.WHITE);
		
		// Prepare the buffer
		this.buffer.drawImage(Assets.menuBackground, 0, 0);
		this.buffer.drawImage(Assets.logo, 50, 50);
		this.buffer.drawImage(Assets.menuCube, 800, 280);
		this.buffer.drawString("By Felix Hamel & Marc-Antoine Gervais", 20, 720, this.paint);
	}

	@Override
	public void updateScreen(long timeElapsed) 
	{
		
	}

	@Override
	public void paintScreen(long timeElapsed) 
	{
		Graphics graphics = this.game.getGraphics();
		
		// Clear the screen
		graphics.clearScreen(Color.BLACK);
		
		// Animate background
		
		// Print the background image
		graphics.drawBitmap(this.buffer.getFramebuffer());
		
		// Print the buttons
		this.drawUIElements(graphics);
	}

	@Override
	public void internalDispose() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateObservable(Object data)
	{
		
	}

	@Override
	public void updateUIElement(String key, Object data)
	{
		System.out.println(key + " CALLED !");
		if(key.equals("SelectLevel"))
		{
			this.game.setNextScreen(new SelectLevelMenu(this.game), true);
		}
		else if(key.equals("Options"))
		{
			this.game.setNextScreen(new OptionMenu(this.game), true);
		}
		/*else if(key.equals("HighScores"))
		{
			this.game.setNextScreen(new HighScoresMenu(this.game), true);
		}*/
	}

	@Override
	protected boolean onBackButtonPressed() 
	{
		return true;
	}

}
