package com.cubee.game.ui.popup;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.plugins.touch.TouchHandler;
import com.cubee.engine.ui.UIElement;
import com.cubee.game.assets.Assets;

public class StartLevelPopup extends UIElement
{
	private Image popup = Assets.startPopup;
	private Game game = null;
	
	private int posX, posY;
	
	public StartLevelPopup(Game game)
	{
		super(true, true);
		
		this.game = game;
		this.posX = this.game.size.x / 2 - (this.popup.getWidth()/2);
		this.posY = this.game.size.y / 2 - (this.popup.getHeight()/2);
	}
	
	@Override
	public void internalDraw(Graphics graphics) 
	{
		graphics.drawARGB(150, 0, 0, 0);
		graphics.drawImage(this.popup, posX, posY);
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
