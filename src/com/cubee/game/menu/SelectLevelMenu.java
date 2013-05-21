package com.cubee.game.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.graphics.Color;

import com.cubee.engine.AppStructure;
import com.cubee.engine.exceptions.UnsupportedImageFormat;
import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.framework.Screen;
import com.cubee.engine.framework.interfaces.GraphicsInterface.ImageFormat;

import android.graphics.Point;
import com.cubee.engine.ui.button.Direction;
import com.cubee.engine.ui.button.DirectionalButton;
import com.cubee.game.assets.Assets;
import com.cubee.game.ui.MenuItem;

public class SelectLevelMenu extends Screen
{
	private int selectedLevel;
	private String[] levelFolders = null;
	
	private ArrayList<Integer> levelList = new ArrayList<Integer>();
	private Map<Integer, Image> levels = new HashMap<Integer, Image>();
	
	public SelectLevelMenu(Game game) 
	{
		super(game);
		
		this.initUIElements();
		this.loadLevels();
		
		this.init();
		
		// Set buffer
		this.buffer.drawImage(Assets.logoMini, 50, 50);
	}
	
	private void init()
	{
		// Init
		selectedLevel = 0;
	}
	
	private void initUIElements()
	{
		this.addUIElement(new MenuItem("Select Level", 
				new Point(750, 600), 
				this.game.getInput()),
				"SelectLevel"
				);
		this.addUIElement(new MenuItem("Back", 
				new Point(40, 600), 
				this.game.getInput()),
				"Back"
				);
		this.addUIElement(new DirectionalButton(new Point(200, 300), 
				this.game.getInput(), 
				Direction.LEFT),
				"Left"
				);
		this.addUIElement(new DirectionalButton(new Point(930, 300),  
				this.game.getInput(), 
				Direction.RIGHT),
				"Right"
				);
	}
	
	private void loadLevels()
	{
		// Load all the levels
		try
		{
			String levelFolder = AppStructure.LEVEL_FOLDER.substring(0, AppStructure.LEVEL_FOLDER.length()-1);
			this.levelFolders = this.game.getAssets().list(levelFolder);
			
			for(String level : this.levelFolders)
			{
				// Extract the ID
				Integer levelID = Integer.parseInt(level);
				this.levelList.add(levelID);

				// Load the Preview Image
				System.out.println(levelFolder + "/" + level + "/preview.png");
				Image levelPreview = this.game.getGraphics().newImage(levelFolder + "/" + level + "/preview.png", ImageFormat.RGB565);
				this.levels.put(levelID, levelPreview);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedImageFormat e)
		{
			e.printStackTrace();
		}
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
			this.game.setNextScreen(new CalibrationScreen(this.game, this.levelList.get(this.selectedLevel)), false);
		}
		else if(key.equals("Back"))
		{
			this.game.previousScreen();
		}
		else if(key.equals("Left"))
		{
			//change selected level
			this.previousLevel();
		}
		else if(key.equals("Right"))
		{
			//change selected level
			this.nextLevel();
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
		
		//Print level preview image
		graphics.drawImage(this.levels.get(this.levelList.get(this.selectedLevel)), 425, 250);
		
		// Print the cadre
		graphics.drawImage(Assets.levelSelectionCadre, 390, 215);
		
		// Print the buttons
		this.drawUIElements(graphics);
	}

	@Override
	public void internalDispose() 
	{
		
	}
	
	private void nextLevel()
	{
		if(selectedLevel == this.levelList.size() - 1)
		{
			selectedLevel = 0;
		}
		else
		{
			selectedLevel++;
		}
	}
	private void previousLevel()
	{
		if(selectedLevel == 0)
		{
			selectedLevel = this.levelList.size() - 1;
		}
		else
		{
			selectedLevel--;
		}
	}

	@Override
	protected boolean onBackButtonPressed() 
	{
		return true;
	}
}
