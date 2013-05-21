package com.cubee.game.menu;

import android.graphics.Point;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Screen;
import com.cubee.engine.framework.includes.input.OrientationInput;
import com.cubee.engine.level.Level;
import com.cubee.engine.level.LevelState;
import com.cubee.engine.level.player.PlayerController;
import com.cubee.engine.ui.button.Direction;
import com.cubee.engine.ui.button.UIDPad;
import com.cubee.game.ui.popup.CompletedLevelPopup;
import com.cubee.game.ui.popup.DieLevelPopup;
import com.cubee.game.ui.popup.PauseLevelPopup;
import com.cubee.game.ui.popup.StartLevelPopup;

public class GameLevel extends Screen
{
	private Level level = null;
	private int levelId = -1;
	private int nbTries = 1;
	
	// Popups
	private StartLevelPopup start = null;
	private DieLevelPopup die = null;
	private PauseLevelPopup pause = null;
	private CompletedLevelPopup complete = null;
	
	public GameLevel(int levelId, Game game) 
	{
		super(game);
				
		// Create popups
		this.start = new StartLevelPopup(this.game);
		this.die = new DieLevelPopup(this.game);
		this.pause = new PauseLevelPopup(this.game);
		
		// Add the Popup
		this.addUIElement(this.start, "start");
		this.addUIElement(this.die, "die");
		this.addUIElement(this.pause, "pause");

		this.addUIElement(new UIDPad(new Point(100, 600), this.game.getInput(), this), "dpad");

		// Disable other popups
		this.die.pause();
		
		// Load the level
		this.levelId = levelId;
		this.loadLevel(this.levelId);
		this.level.setStart();
		
		// Completed level
		this.complete = new CompletedLevelPopup(this.game, this.level, this.nbTries);
		this.addUIElement(this.complete, "completed");
	}
	
	private void loadLevel(int levelId)
	{
		// Level
		try
		{
			this.level = new Level(levelId, game);
			this.level.addObserver(this);
			this.level.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception catched : " + e.getMessage());
			System.exit(1);
		}
	}
	
	private void reset()
	{
		System.out.println("[GAMELEVEL] Reset level");
		// Hide all UI except the Start POPUP
		this.getUIElement("die").pause();
		this.getUIElement("die").hide();
		
		while(this.game.getInput().getTouch().isTouchDown(0))
		{
			// Waiting for the user to remove it finger
		}
		
		this.getUIElement("start").show();

		while(this.game.getInput().getTouch().isTouchDown(0))
		{
			// Waiting for the user to remove it finger
		}
		
		try 
		{
			Thread.sleep(200);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		this.getUIElement("start").resume();
		this.loadLevel(this.levelId);
		this.nbTries++;
	}
	
	public final int getNbTries()
	{
		return this.nbTries;
	}
	
	@Override
	public void resume()
	{
		this.level.resume();
	}
	
	@Override
	public void pause()
	{
		this.level.pause();
	}
	
	@Override
	public void updateObservable(Object data) 
	{
		if(data instanceof LevelState)
		{
			if(data.equals(LevelState.START))
			{
				System.out.println("SSSSSSSSTTTTTTTTTTAAAAARRRRRRRRTTTTTT !!!!!!!!");
				this.start.resume();
				this.start.show();
			}
			else if(data.equals(LevelState.PLAY))
			{
				System.out.println("PPPPPPLLLLLLLLAAAAAAAAAYYYYYYYYYYY !!!!!!!!");
				
				this.start.hide();
				this.start.pause();
				
				this.pause.hide();
				this.pause.pause();
			}
			else if(data.equals(LevelState.DIE))
			{
				System.out.println("DDDDDDDDDDIIIIIIIIIIIIEEEEEEEEEEE !!!!!!");
				
				this.die.resume();
				this.die.show();
			}
			else if(data.equals(LevelState.PAUSE))
			{
				this.pause.resume();
				this.pause.show();
			}
			else if(data.equals(LevelState.COMPLETED))
			{
				this.complete.resume();
				this.complete.show();
			}
		}
		else if(data instanceof String)
		{
			System.out.println("DATA: " + String.valueOf(data));
		}
		else if(data instanceof Direction)
		{
			Direction d = (Direction) data;
			System.out.println("DIRECTION: " + d.name());
		}
		else
		{
			System.out.println("OBSERVABLE CALLED");
		}
	}

	@Override
	public void updateUIElement(String key, Object data) 
	{
		if(key.equals("start"))
		{			
			this.level.setPlay();
		}
		else if(key.equals("die"))
		{
			this.reset();
		}
		else if(key.equals("pause"))
		{
			this.level.setPause();
		}
		else if(key.equals("completed"))
		{
			this.game.previousScreen();
		}
		else if(key.equals("PLAYER_LEFT"))
		{
			PlayerController.moveLeft();
		}
		else if(key.equals("PLAYER_RIGHT"))
		{
			PlayerController.moveRight();
		}
		else if(key.equals("PLAYER_UP"))
		{
			PlayerController.moveUp();
		}
		else if(key.equals("PLAYER_DOWN"))
		{
			PlayerController.moveDown();
		}
	}

	@Override
	public void updateScreen(long timeElapsed)
	{
		// Move the player if needed
		// Accelerometer, a revoir	
		if(this.level.getLevelState() == LevelState.PLAY)
		{
			OrientationInput orientation = this.game.getInput().getOrientationInput();
			
			if(orientation.getGameX() > 0.f)
			{
				PlayerController.moveRight();
			}
			else if(orientation.getGameX() < 0.f)
			{
				PlayerController.moveLeft();
			}
			else if(orientation.getGameY() > 0.f)
			{
				PlayerController.moveUp();
			}
			else if(orientation.getGameY() < 0.f)
			{
				PlayerController.moveDown();
			}
		}
			
		// Update the level
		this.level.update();
	}

	@Override
	public void paintScreen(long timeElapsed) 
	{
		this.drawBuffer();
		this.level.draw(this.game.getGraphics());
		this.drawUIElements(this.game.getGraphics());
	}

	@Override
	public void internalDispose() 
	{
		//this.level = null;
	}

	@Override
	protected boolean onBackButtonPressed() 
	{
		if(this.level.getLevelState() == LevelState.PAUSE || this.level.getLevelState() == LevelState.COMPLETED)
		{
			return true;
		}
		else
		{
			this.level.setPause();
			return false;
		}
	}
}
