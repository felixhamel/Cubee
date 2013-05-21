package com.cubee.game.ui.popup;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.plugins.touch.TouchHandler;
import com.cubee.engine.ui.UIElement;
import com.cubee.game.assets.Assets;

public class PauseLevelPopup extends UIElement
{
	private Image image = Assets.pausePopup;
	private Game game = null;
	private int posX, posY;
	
	public PauseLevelPopup(Game game)
	{
		super(false, false);
		
		this.game = game;
		this.posX = this.game.size.x / 2 - (this.image.getWidth()/2);
		this.posY = this.game.size.y / 2 - (this.image.getHeight()/2);
	}
	
	@Override
	protected void internalRun() 
	{
		TouchHandler touch = this.game.getInput().getTouch();
		
		for(int i = 0; i < 2; i++)
		{
			if(touch.getTouchX(i) >= this.posX && touch.getTouchX(i) <= (this.posX + this.image.getWidth()))
			{
				if(touch.getTouchY(i) >= this.posY && touch.getTouchY(i) <= (this.posY + this.image.getHeight()))
				{
					this.setChanged();
					this.notifyObservers();
				}
			}
		}
	}

	@Override
	public void internalDraw(Graphics graphics) 
	{
		if(this.show)
		{
			graphics.drawARGB(200, 0, 0, 0);
			graphics.drawImage(this.image, posX, posY);
		}
	}
}
