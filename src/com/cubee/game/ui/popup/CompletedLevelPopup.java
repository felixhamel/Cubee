package com.cubee.game.ui.popup;

import android.graphics.Color;
import android.graphics.Paint;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.level.Level;
import com.cubee.engine.plugins.touch.TouchHandler;
import com.cubee.engine.ui.UIElement;
import com.cubee.game.assets.Assets;

public class CompletedLevelPopup extends UIElement
{
	
	private Image popup = Assets.successPopup;
	private Game game = null;
	private int posX, posY;
	private Level level = null;
	private Paint paint = new Paint();
	private int nbTries = 0;
	
	public CompletedLevelPopup(Game game, Level level, int nbTries)
	{
		super(false, false);
		
		this.game = game;
		this.level = level;
		this.nbTries = nbTries;
		
		this.posX = this.game.size.x / 2 - (this.popup.getWidth()/2);
		this.posY = this.game.size.y / 2 - (this.popup.getHeight()/2);
		
		this.setupPaint();
	}
	
	private void setupPaint()
	{
		this.paint.setColor(Color.BLACK);
		this.paint.setAntiAlias(true);
		this.paint.setTextSize(35);
	}
	
	@Override
	public void internalDraw(Graphics graphics) 
	{
		if(this.show)
		{
			graphics.drawARGB(200, 0, 0, 0);
			graphics.drawImage(this.popup, posX, posY);
			graphics.drawString(this.level.getTime(), this.posX + 140, this.posY + 145, this.paint);
			graphics.drawString(String.valueOf(this.nbTries), this.posX + 460, this.posY + 145, this.paint);
		}
	}
	
	@Override
	protected void internalRun() 
	{
		TouchHandler touch = this.game.getInput().getTouch();
		
		for(int i = 0; i < 2; i++)
		{
			if(touch.getTouchX(i) >= this.posX && touch.getTouchX(i) <= (this.posX + this.popup.getWidth()))
			{
				if(touch.getTouchY(i) >= this.posY && touch.getTouchY(i) <= (this.posY + this.popup.getHeight()))
				{
					this.setChanged();
					this.notifyObservers();
				}
			}
		}
	}

}
