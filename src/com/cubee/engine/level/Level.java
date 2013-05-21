package com.cubee.engine.level;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.cubee.engine.AppStructure;
import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.level.Case.CaseState;
import com.cubee.engine.level.player.Player;
import com.cubee.engine.plugins.Chronometer;
import com.cubee.engine.ui.UIElement;
import com.cubee.game.assets.Assets;

public class Level extends UIElement
{
	// State
	private LevelState levelState = LevelState.LOADING;
	private int levelID = 0;
	
	// Top Bar
	private Image topBar = Assets.topBar;
	private final int TOPBAR_HEIGHT = 99; // Do not get the Height directly from it
	private final int TOPBAR_WIDTH = topBar.getWidth();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
	
	// Cases
	private MatrixCase matrix = null;
	private final int CASE_SIZE_X = Assets.caseNormal.getWidth();
	private final int CASE_SIZE_Y = Assets.caseNormal.getHeight();
	private final int MATRIX_SIZE_X = 20;
	private final int MATRIX_SIZE_Y = 10;
	
	// Player
	private Player player = null;
	private final int PLAYER_SIZE_X = Assets.player.getWidth();
	private final int PLAYER_SIZE_Y = Assets.player.getHeight();
	private Point actualPosition = new Point();
	
	// Elements
	private Chronometer chronometer = new Chronometer(false);
	private Game game = null;
	private Point startPoint = null;
	private Point endPoint = null;
	private Paint paint = new Paint();
	private Paint debugPaint = new Paint();
	
	public Level(int levelID, Game game) throws Exception
	{
		this.levelID = levelID;
		this.game = game;
		
		// Matrix
		this.matrix = new MatrixCase(this.MATRIX_SIZE_X, this.MATRIX_SIZE_Y);
		
		// Paint
		this.paint.setTextSize(32);
		this.paint.setAntiAlias(true);
		this.paint.setColor(Color.BLACK);
		
		this.debugPaint.setTextSize(16);
		this.debugPaint.setAntiAlias(true);
		this.debugPaint.setColor(Color.BLACK);
		
		// Check if the level exists
		if(!this.checkIfLevelExists())
		{
			throw new Exception("Level#" + this.levelID + " do not exists");
		}
		
		// Load the level
		if(!this.loadLevel())
		{
			throw new Exception("Can't load level");
		}
		
		// Setup player
		this.player = new Player(
				new Point(
						(this.PLAYER_SIZE_X * this.startPoint.x),
						(this.PLAYER_SIZE_Y * this.startPoint.y) + this.TOPBAR_HEIGHT
				),
				this
		);
		this.player.stop();
	}
	
	public LevelState getLevelState()
	{
		return this.levelState;
	}
	
	/**
	 * Check if the level exists
	 * @return
	 */
	private boolean checkIfLevelExists()
	{
		try
		{
			InputStream inLevel = this.game.getAssets().open(
					AppStructure.LEVEL_FOLDER + this.levelID + "/" + AppStructure.LEVEL_FILENAME);
			return true;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Load the level from the file and create the matrix from it
	 * @return
	 */
	private boolean loadLevel()
	{
		// Load the level in a matrix of int
		int[][] levelMatrix = new int[this.MATRIX_SIZE_X][this.MATRIX_SIZE_Y];
		
		try 
		{
			InputStream inLevel = this.game.getAssets().open(
					AppStructure.LEVEL_FOLDER + this.levelID + "/" + AppStructure.LEVEL_FILENAME);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inLevel));
			String line = "";
			
			// Read the level
			for(int y = 0; y < this.MATRIX_SIZE_Y; y++)
			{
				line = reader.readLine();
				for(int x = 0; x < this.MATRIX_SIZE_X; x++)
				{
					int readedCase = Integer.parseInt(line.substring(x,x+1));
					System.out.println("SUBSTRING: x:" + x + " | y:" + y + " | readed:" + readedCase);
					levelMatrix[x][y] = readedCase;
					
					if(readedCase == 2)
					{
						if(this.startPoint == null)
						{
							this.startPoint = new Point(x,y);
						}
						else
						{
							throw new Exception("Can't have more than 1 start point");
						}
					}
					if(readedCase == 3)
					{
						if(this.endPoint == null)
						{
							this.endPoint = new Point(x,y);
						}
						else
						{
							throw new Exception("Can't have more than 1 end point");
						}
					}
				}
			}
			
			this.matrix.createMatrix(levelMatrix);
			return true;
		} 
		catch (NumberFormatException e)
		{
			System.out.println("Wrong char in level file #" + this.levelID);
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public void update()
	{
		this.internalRun();
	}
	
	public void checkPlayerPosition()
	{
		Point pPosition = this.player.getRealPosition();
		int x = (pPosition.x / this.CASE_SIZE_X);
		int y = (pPosition.y - this.TOPBAR_HEIGHT) / this.CASE_SIZE_Y;
		
		//System.out.println("PLAYER-> X:" + x + " Y:" + y + " STATE:" + this.matrix.get(x,y).getCaseState().name());
		if((x < 0 || y < 0) || (x > this.MATRIX_SIZE_X || y > this.MATRIX_SIZE_Y))
		{
			this.player.stop();
			this.setDie();
		}
		else if(this.matrix.get(x, y).getCaseState() == CaseState.HOLE && !this.player.isMoving())
		{
			System.out.println("STOP PLAYER");
			this.player.stop();
			this.setDie();
		}
		else if(this.matrix.get(x, y).getCaseState() == CaseState.END && !this.player.isMoving())
		{
			System.out.println("Level completed!");
			this.player.stop();
			this.setCompleted();
		}
	}
	
	public void setStart()
	{
		this.levelState = LevelState.START;
		this.setChanged();
		this.notifyObservers(this.levelState);
	}
	
	public void setPlay()
	{
		this.levelState = LevelState.PLAY;
		this.player.resume();
		
		if(!this.chronometer.isStarted())
		{
			this.chronometer.reset();
			this.chronometer.start();
		}
		else if(this.chronometer.isPaused())
		{
			this.chronometer.resume();
		}
		
		this.setChanged();
		this.notifyObservers(this.levelState);
	}
	
	public void setCompleted()
	{
		this.levelState = LevelState.COMPLETED;
		this.chronometer.stop();
		this.setChanged();
		this.notifyObservers(this.levelState);
	}
	
	public void setDie()
	{
		this.player.stop();
		this.chronometer.stop();
		this.levelState = LevelState.DIE;
		this.setChanged();
		this.notifyObservers(this.levelState);
	}
	
	public void setPause()
	{
		if(this.levelState == LevelState.PAUSE)
		{
			// Resume
			this.setPlay();
		}
		else
		{
			// Pause
			this.levelState = LevelState.PAUSE;
			this.player.stop();
			this.chronometer.pause();
			this.setChanged();
			this.notifyObservers(this.levelState);
		}
	}

	@Override
	public void internalDraw(Graphics graphics) 
	{
		for(int y = 0; y < this.MATRIX_SIZE_Y; y++)
		{
			for(int x = 0; x < this.MATRIX_SIZE_X; x++)
			{
				int posX = this.CASE_SIZE_X * x;
				int posY = (this.CASE_SIZE_Y * y) + this.TOPBAR_HEIGHT;
				
				switch(this.matrix.get(x, y).getCaseState())
				{
					case NORMAL :
						graphics.drawImage(Assets.caseNormal, posX, posY);
					break;
					case END :
						graphics.drawImage(Assets.caseEnd, posX, posY);
					break;
					case START :
						graphics.drawImage(Assets.caseStart, posX, posY);
					break;
				}
			}
		}
		
		this.game.getGraphics().drawImage(this.topBar, 0, 0);
		this.player.draw(this.game.getGraphics());
		
		/*this.game.getGraphics().drawString("X:" + this.game.getInput().getOrientationInput().getGameX(), 5, 20, this.debugPaint);
		this.game.getGraphics().drawString("Y:" + this.game.getInput().getOrientationInput().getGameY(), 5, 40, this.debugPaint);
		this.game.getGraphics().drawString("AX:" + this.game.getInput().getAccelerometerSensor().getX(), 50, 20, this.debugPaint);
		this.game.getGraphics().drawString("AY:" + this.game.getInput().getAccelerometerSensor().getY(), 50, 40, this.debugPaint);
		this.game.getGraphics().drawString("PX:" + this.player.getRealPosition().x, 5, 60, this.debugPaint);
		this.game.getGraphics().drawString("PY:" + this.player.getRealPosition().y, 5, 80, this.debugPaint);*/
		
		this.game.getGraphics().drawString(
			"Time: " +
			this.dateFormat.format(new Date(this.chronometer.getMilliseconds())), 
			35, 
			56, 
			this.paint
		);
	}

	@Override
	protected synchronized void internalRun() 
	{
		/*if(this.levelState == levelState.PLAY)
		{
			this.checkPlayerPosition();
		}*/
	}
	
	public String getTime()
	{
		return this.dateFormat.format(new Date(this.chronometer.getMilliseconds()));
	}
}
