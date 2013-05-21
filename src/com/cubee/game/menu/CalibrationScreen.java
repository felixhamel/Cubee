package com.cubee.game.menu;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Screen;
import com.cubee.game.assets.Assets;
import com.cubee.game.ui.popup.CalibrationPopup;

public class CalibrationScreen extends Screen
{
	private CalibrationPopup popup = null;
	private int levelId = -1;
	private boolean calibrated = false;
	private Paint paint = new Paint();
	
	public CalibrationScreen(Game game, int levelId) 
	{
		super(game);
		this.popup = new CalibrationPopup(this.game);
		this.levelId = levelId;
		this.addUIElement(this.popup, "calibration");
		
		// Activate the button
		this.popup.show();
		this.popup.resume();
		
		// Buffer
		this.buffer.drawImage(Assets.logoMini, 20, 20);
		
		// Activate the accelerometer
		//this.game.getInput().enableAccelerometer();
		
		// Paint
		this.paint.setAntiAlias(true);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextAlign(Align.CENTER);
		this.paint.setTextSize(25);
	}

	@Override
	public void updateObservable(Object data) 
	{
		
	}

	@Override
	public void updateUIElement(String key, Object data) 
	{
		if(!this.calibrated)
		{
			// Update the accelerometer
			//this.game.getInput().getAccelerometer().calibrate();
			this.game.getInput().getOrientationInput().calibrate();
			
			this.calibrated = true;
		}
		else if(data instanceof String && this.calibrated)
		{
			if(data.equals("released"))
			{
				// Play
				this.game.setNextScreen(new GameLevel(this.levelId, this.game), false);
			}
		}
	}

	@Override
	public void updateScreen(long timeElapsed) 
	{
		
	}

	@Override
	public void paintScreen(long timeElapsed) 
	{
		this.game.getGraphics().clearScreen(Color.BLACK);
		this.drawBuffer();
		
		if(!this.calibrated)
		{
			this.drawUIElements(this.game.getGraphics());

			this.game.getGraphics().drawString(
					"Press the Calibration button for "+String.valueOf(this.popup.getCalibrationTime())+" seconds to calibrate the game", 
					this.popup.getPosX() + (this.popup.getWidth() / 2),
					this.popup.getPosY() + 200,
					this.paint
			);
			this.game.getGraphics().drawString(
					"Time remaining : " + this.popup.getTimeTouched() + "s",
					this.popup.getPosX() + (this.popup.getWidth() / 2),
					this.popup.getPosY() + 250,
					this.paint
			);
			
			try 
			{
				Thread.sleep(16);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			this.game.getGraphics().drawString(
					"Screen calibrated\nRelease the screen to play", 
					this.popup.getPosX() + (this.popup.getWidth() / 2),
					this.popup.getPosY() + 200,
					this.paint
			);
		}
	}

	@Override
	public void internalDispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean onBackButtonPressed() 
	{
		return true;
	}

}
