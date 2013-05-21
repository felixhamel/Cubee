package com.cubee.game.assets;

import com.cubee.engine.AppStructure;
import com.cubee.engine.exceptions.UnsupportedImageFormat;
import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.interfaces.GraphicsInterface.ImageFormat;
import com.google.common.base.Preconditions;

public class LoadingAssetsThread extends Thread
{
	private Game game;
	
	public LoadingAssetsThread(Game game)
	{
		// Validation
		Preconditions.checkNotNull(game, "Game can't be null");
		this.game = game;
	}
	
	public void preload()
	{
		try
		{
			if(this.game.getGraphics() != null)
			{
				Assets.logo = this.game.getGraphics().newImage(
						AppStructure.GFX_FOLDER + "Logo.png",
						ImageFormat.RGB565
				);
				
				Assets.menuBackground = this.game.getGraphics().newImage(
						AppStructure.GFX_FOLDER + "MenuBackground.png",
						ImageFormat.RGB565
				);	
			}
		}
		catch (UnsupportedImageFormat e) 
		{
			e.printStackTrace();
		}
	}
	
	// Load the images
	public void run()
	{
			try 
			{
				if(this.game.getGraphics() != null)
				{
					// Logo
					Assets.logoMini = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "LogoMini.png", 
							ImageFormat.RGB565
					);
					
					// Menu
					Assets.menuCube = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "MenuCube.png", 
							ImageFormat.RGB565
					);
					Assets.menuButton = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "MenuButtons.png", 
							ImageFormat.ARGB4444
					);
					Assets.menuArrows = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "MenuArrows.png",
							ImageFormat.RGB565
					);
					
					Assets.menuUISwitch = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "OnOffSwitch.png", 
							ImageFormat.ARGB4444
					);
					
					Assets.gameArrows = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "GameArrows.png", 
							ImageFormat.RGB565
					);
					
					// Levels
					/*Assets.levelPreviews.add(this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "level1.jpg", 
							ImageFormat.ARGB4444)
					);
					Assets.levelPreviews.add(this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "level2.jpg", 
							ImageFormat.ARGB4444)
					);
					Assets.levelPreviews.add(this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "level3.jpg", 
							ImageFormat.ARGB4444)
					);
					Assets.levelPreviews.add(this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "level4.jpg", 
							ImageFormat.ARGB4444)
					);
					Assets.levelPreviews.add(this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "level5.jpg", 
							ImageFormat.ARGB4444)
					);*/
					Assets.levelSelectionCadre = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "LevelCadre.png", 
							ImageFormat.RGB565
					);
					
					// Player/Level
					Assets.player = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/Player.png", 
							ImageFormat.RGB565
					);
					Assets.caseNormal = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/Case_Normal.png",
							ImageFormat.RGB565
					);
					Assets.caseEnd = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/Case_End.png",
							ImageFormat.RGB565
					);
					Assets.caseStart = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/Case_Start.png",
							ImageFormat.RGB565
					);
					Assets.topBar = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/TopBar.png", 
							ImageFormat.RGB565
					);
					Assets.startPopup = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/StartLevelPopup.png",
							ImageFormat.RGB565
					);
					Assets.diePopup = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/DieLevelPopup.png",
							ImageFormat.RGB565
					);
					Assets.successPopup = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/LevelCompleteted.png",
							ImageFormat.RGB565
					);
					Assets.backPopup = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/BackLevelPopup.png",
							ImageFormat.RGB565
					);
					Assets.pausePopup = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/BackLevelPopup.png",
							ImageFormat.RGB565
					);
					Assets.calibrationPopup = this.game.getGraphics().newImage(
							AppStructure.GFX_FOLDER + "Level/CalibrationPopup.png",
							ImageFormat.RGB565
					);
				}
			} 
			catch (UnsupportedImageFormat e) 
			{
				e.printStackTrace();
			}
		}
}
