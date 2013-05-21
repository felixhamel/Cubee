package com.cubee.game.menu;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Point;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Screen;
import com.cubee.engine.ui.button.UISwitch;
import com.cubee.engine.utils.ConfigurationManager;
import com.cubee.game.assets.Assets;

public class OptionMenu extends Screen
{

	public OptionMenu(Game game) 
	{
		super(game);
		
		this.addUIElement(new UISwitch( new Point(675, 360), 
				this.game.getInput(),
				ConfigurationManager.getInstance().getValue("ACTIVATE_ACCELEROMETER")),
				"Accelerometer"
				);
		this.addUIElement(new UISwitch( new Point(675, 260), 
				this.game.getInput(),
				ConfigurationManager.getInstance().getValue("ACTIVATE_DIRECTIONAL_TOUCHPAD")),
				"Touchpad"
				);
		this.addUIElement(new UISwitch( new Point(675, 560), 
				this.game.getInput(),
				ConfigurationManager.getInstance().getValue("ACTIVATE_SOUND")),
				"Sound"
				);
		this.paint.setTextSize(50);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextAlign(Align.RIGHT);
		
		//Prepare buffer
		this.buffer.drawString("Accelerometer Controls", 600, 400, this.paint);
		this.buffer.drawString("Touchpad Controls", 600, 300, this.paint);
		this.buffer.drawString("Sound", 600, 600, this.paint);
	}

	@Override
	public void updateObservable(Object data)
	{
		
	}
	
	@Override
	public void updateUIElement(String key, Object data) 
	{
		System.out.println(key + " CALLED !");
		if(key == "Accelerometer")
		{
			if((((UISwitch)this.getUIElement("Touchpad")).getSwitchValue() 
					!= ((UISwitch)this.getUIElement("Accelerometer")).getSwitchValue()))
			{
				((UISwitch)this.getUIElement("Accelerometer")).switchState();
				((UISwitch)this.getUIElement("Touchpad")).switchState();
				ConfigurationManager.getInstance().setValue("ACTIVATE_ACCELEROMETER", 
						((UISwitch)this.getUIElement("Accelerometer")).getSwitchValue());
				ConfigurationManager.getInstance().setValue("ACTIVATE_DIRECTIONAL_TOUCHPAD", 
						((UISwitch)this.getUIElement("Touchpad")).getSwitchValue());
				ConfigurationManager.getInstance().saveConfig();
				
			}
			else 
			{
				((UISwitch)this.getUIElement("Accelerometer")).switchState();
				ConfigurationManager.getInstance().setValue("ACTIVATE_ACCELEROMETER", 
						((UISwitch)this.getUIElement("Accelerometer")).getSwitchValue());
				ConfigurationManager.getInstance().saveConfig();
			}
		}
		else if(key == "Touchpad")
		{
			if((((UISwitch)this.getUIElement("Touchpad")).getSwitchValue() 
					!= ((UISwitch)this.getUIElement("Accelerometer")).getSwitchValue()))
			{
				((UISwitch)this.getUIElement("Accelerometer")).switchState();
				((UISwitch)this.getUIElement("Touchpad")).switchState();
				ConfigurationManager.getInstance().setValue("ACTIVATE_ACCELEROMETER", 
						((UISwitch)this.getUIElement("Accelerometer")).getSwitchValue());
				ConfigurationManager.getInstance().setValue("ACTIVATE_DIRECTIONAL_TOUCHPAD", 
						((UISwitch)this.getUIElement("Touchpad")).getSwitchValue());
				ConfigurationManager.getInstance().saveConfig();
				
			}
			else 
			{
				((UISwitch)this.getUIElement("Touchpad")).switchState();
				ConfigurationManager.getInstance().setValue("ACTIVATE_DIRECTIONAL_TOUCHPAD", 
						((UISwitch)this.getUIElement("Touchpad")).getSwitchValue());
				ConfigurationManager.getInstance().saveConfig();
			}
				
		}
		else if(key == "Sound")
		{
			((UISwitch)this.getUIElement("Sound")).switchState();
			ConfigurationManager.getInstance().setValue("ACTIVATE_SOUND", ((UISwitch)this.getUIElement("Sound")).getSwitchValue());
			ConfigurationManager.getInstance().saveConfig();
		}
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
		
		// Print the background image
		graphics.drawBitmap(this.buffer.getFramebuffer());
		
		//Draw logo
		graphics.drawImage(Assets.logoMini, 50, 50);
		
		// Print the buttons
		this.drawUIElements(graphics);
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
