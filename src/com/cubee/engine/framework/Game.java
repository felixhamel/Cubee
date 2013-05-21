package com.cubee.engine.framework;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.cubee.engine.framework.includes.screen.ScreenStack;
import com.cubee.engine.framework.interfaces.GameInterface;
import android.graphics.Point;

import com.cubee.engine.ui.animations.fade.FadeIn;
import com.cubee.engine.ui.animations.fade.FadeOut;

public abstract class Game extends Activity implements GameInterface
{	
	// Stack
	ScreenStack screenStack = new ScreenStack();
	
	// Elements
	protected Audio audio = null;
	protected FileIO fileIO = null;
	protected Graphics graphics = null;
	protected Input input = null;
	protected Screen screen = null;
	protected View view = null;
	//protected WakeLock wakeLock;
	private boolean lock = false;
	public Point size = new Point();
	
	private SensorManager sensorManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Setup Video
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.requestWindowFeature(MODE_MULTI_PROCESS);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Full Screen
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		
		this.size = this.getWindowSize();
		
		// Setup Framebuffer
		Bitmap frameBuffer = Bitmap.createBitmap(
			(int) this.size.x,
			(int) this.size.y,
			Config.RGB_565
		);
		
		// Setup Accelerometer
		 this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		// Setup Framework
		this.view = new View(this, frameBuffer);		
		this.graphics = new Graphics(this.getAssets(), frameBuffer);
		this.fileIO = new FileIO(this);
		this.screen = this.getInitScreen();
		this.input = new Input(this.view, 1.f, 1.f);
		this.setContentView(this.view);
		this.view.setLayerType(View.LAYER_TYPE_HARDWARE, null); // Hardware accelerated
		
		// Setup the sensors
		this.sensorManager.registerListener(
				this.input.getAccelerometerSensor(), 
				this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME
		);
		this.sensorManager.registerListener(
				this.input.getGeomagneticSensor(), 
				this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 
				SensorManager.SENSOR_DELAY_GAME
		);
		
		// Setup the WakeLock
		/*PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
	    this.wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "CubeeEngine");*/
	}
	
	public Point getWindowSize()
	{
		if(this.size.x == 0 && this.size.y == 0)
		{
			Display display = this.getWindowManager().getDefaultDisplay();
			display.getSize(this.size);
		}
		return this.size;
	}

	@Override
	public Audio getAudio() 
	{
		return null;
	}

	@Override
	public FileIO getFileIO() 
	{
		return this.fileIO;
	}
	
	@Override
	public Input getInput()
	{
		return this.input;
	}
	
	public void setScreen(Screen screen)
	{
		this.screen = screen;
	}
	
	@Override
	public void setNextScreen(Screen nextScreen, boolean keepInStack)
	{
		if(!this.lock)
		{
			this.lock = true;
			
			this.nextScreenStackElement(nextScreen, keepInStack);
			
			this.lock = false;
		}
	}

	@Override
	public void previousScreen()
	{
		if(!this.lock)
		{
			this.onBackPressed();
		}
	}

	@Override
	public Screen getCurrentScreen() 
	{
		return this.screen;
	}
	
	@Override
	public Graphics getGraphics() 
	{
		return this.graphics;
	}
	
	@Override
	public View getView()
	{
		return this.view;
	}
	
	@Override
	public synchronized void onBackPressed()
	{
		if(!this.lock)
		{
			// Check with the screen if it's ok to back
			if(this.screen.onBackButtonPressed())
			{
				this.lock = true;
				this.previousScreenStackElement();
				this.lock = false;
			}
		}
	}
	
	private synchronized void nextScreenStackElement(Screen nextScreen, boolean keepInStack)
	{
		// Pause
		this.screen.pause();
		this.view.pause();

		// Keep the present screen in a stack
		if(keepInStack)
		{
			this.screenStack.add(this.screen);
		}
		
		this.drawFadeOutAnimation();
		
		this.screen = nextScreen;
		
		this.drawFadeInAnimation();
		
		try
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		// Resume
		this.view.resume();
		this.screen.resume();
	}
	
	private synchronized void previousScreenStackElement()
	{
		if(!this.screenStack.isEmpty())
		{
			// Pause
			this.view.pause();
			this.screen.pause();
			
			// Pop the next screen
			Screen nextScreen = (Screen) this.screenStack.pop();
			
			this.drawFadeOutAnimation();
			
			this.screen = nextScreen;
			
			this.drawFadeInAnimation();
					
			// Resume
			this.screen.resume();
			this.view.resume();
		}
		else
		{
			this.screen.pause();
			this.view.pause();
			this.drawFadeOutAnimation();
			System.exit(0);
		}
	}
	
	private void drawFadeInAnimation()
	{
		FadeIn fade = new FadeIn(this);
		fade.start();
		while(!fade.isFinished())
		{
			this.screen.paintScreen(10);
			fade.nextFrame();
			fade.drawOnScreen();
			this.view.drawFameBuffer(new Rect());
			
			try 
			{
				Thread.sleep(15);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void drawFadeOutAnimation()
	{		
		FadeOut fade = new FadeOut(this);
		fade.start();
		while(!fade.isFinished())
		{
			this.screen.paintScreen(10);
			fade.nextFrame();
			fade.drawOnScreen();
			this.view.drawFameBuffer(new Rect());
			
			try 
			{
				Thread.sleep(15);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
