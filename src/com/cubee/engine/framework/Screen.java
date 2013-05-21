package com.cubee.engine.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;

import com.cubee.engine.framework.interfaces.ScreenInterface;
import com.cubee.engine.framework.Game;
import com.cubee.engine.level.Level;
import com.cubee.engine.lib.thread.ThreadManager;
import com.cubee.engine.ui.UIElement;
import com.cubee.engine.ui.animations.fade.FadeIn;
import com.cubee.engine.ui.animations.fade.FadeOut;
import com.cubee.engine.ui.button.UIButton;
import com.cubee.game.assets.Assets;
import com.google.common.base.Preconditions;

//TODO: Ajouter les FadeIn FadeOut
public abstract class Screen implements ScreenInterface, Observer
{
	protected Game game;
	protected boolean isPaused = false;
	protected Paint paint;
	protected Graphics buffer = null;
	
	// Animation
	private Map<String, UIElement> uiElements = new HashMap<String, UIElement>();
	private ThreadManager uiThreads = new ThreadManager();
	public boolean alive = true;
	
	public Screen(Game game)
	{
		// Validation
		Preconditions.checkNotNull(game, "Game can't be null in Screen class");	
		
		this.game = game;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		
		this.buffer = new Graphics(this.game.getAssets(), Bitmap.createBitmap(1280, 736, Config.RGB_565));
	}
	
	public void addUIElement(UIElement element, String key)
	{
		// Validation
		Preconditions.checkNotNull(key, "The Key can't be NULL");
		Preconditions.checkArgument(key.length() > 0, "The Key can't be empty");
		Preconditions.checkArgument(!this.uiElements.containsKey(key), "The Key already exists (" + key + ")");
		Preconditions.checkNotNull(element, "Can't add a NULL UIElement");
		
		// Keep the element
		element.addObserver(this);
		this.uiElements.put(key, element);
		
		// Build a thread of it
		this.uiThreads.add(element);
	}
	
	public void drawUIElements(Graphics graphics)
	{
		for(UIElement element : this.uiElements.values())
		{
			element.draw(graphics);
		}
	}
	
	public UIElement getUIElement(String key)
	{
		if(this.uiElements.containsKey(key))
		{
			return this.uiElements.get(key);
		}
		return null;
	}
	
	@Override
	public void update(Observable observable, Object data)
	{
		// Find the UIElement related to this update
		if(observable instanceof UIElement && !(observable instanceof Level))
		{
			for(Entry<String, UIElement> entry : this.uiElements.entrySet())
			{
				if(entry.getValue() == observable)
				{
					System.out.println("UpdateUIElement : " + String.valueOf(data));
					this.updateUIElement(entry.getKey(), data);
				}
			}
		}
		else
		{
			System.out.println("UpdateObservable : " + String.valueOf(data));
			this.updateObservable(data);
		}
	}
	
	@Override
	public abstract void updateObservable(Object data);

	@Override
	public abstract void updateUIElement(String key, Object data);
	
	@Override
	public abstract void updateScreen(long timeElapsed);
	
	@Override
	public abstract void paintScreen(long timeElapsed);
	
	public void drawBuffer()
	{
		this.game.getGraphics().drawBitmap(this.buffer.getFramebuffer());
	}
	
	@Override
	public void dispose()
	{
		this.buffer.getFramebuffer().recycle();
		this.buffer = null;
		this.alive = false;
		this.internalDispose();
	}
	
	public abstract void internalDispose();

	@Override
	public void resume() 
	{
		for(UIElement element : this.uiElements.values())
		{
			element.resume();
		}
	}

	@Override
	public void pause() 
	{
		for(UIElement element : this.uiElements.values())
		{
			element.pause();
		}
	}
	
	protected abstract boolean onBackButtonPressed();
}
