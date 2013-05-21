package com.cubee.engine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class View extends SurfaceView implements Runnable
{
	private Game game;
	private Bitmap framebuffer;
	private Thread renderThread = null;
	private SurfaceHolder holder;
	private boolean running = true;
	private boolean alive = true;
	
	public View(Game game, Bitmap framebuffer) 
	{
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = this.getHolder();
		
		this.setFitsSystemWindows(true);
		
		this.renderThread = new Thread(this);
		this.renderThread.start();
	}
	
	@Override
	public void run() 
	{
		Looper.prepare();
		
		Rect dstRect = new Rect();        
        long deltaTime = 0;
        long startTime = System.nanoTime();
        
        while(this.alive) 
        {  
        	while(this.running)
        	{        		
        		if(!holder.getSurface().isValid())
                {	
        			continue;
                }
        		
	            deltaTime = (System.nanoTime() - startTime) / 1000;
	            startTime = System.nanoTime();
	                        
	            if (deltaTime > 100)
	            {
	                deltaTime = 100;
	            }
	
	            // Update the screen
	            this.game.getCurrentScreen().updateScreen(deltaTime);
	            this.game.getCurrentScreen().paintScreen(deltaTime);
	            
	            this.drawFameBuffer(dstRect);
        	}
        }
	}
	
	public synchronized void drawFameBuffer(Rect dstRect)
	{
		if(holder.getSurface().isValid())
        {
			Canvas canvas = this.holder.lockCanvas();
	        canvas.getClipBounds(dstRect);
	        canvas.drawBitmap(this.framebuffer, null, dstRect, null);
	        
	        this.holder.unlockCanvasAndPost(canvas);
        }
	}
	
	public void resume()
	{
		this.running = true;
	}
	
	public void pause()
	{
		this.running = false;
	}
}