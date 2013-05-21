package com.cubee.engine.framework;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;

import com.cubee.engine.exceptions.UnsupportedImageFormat;
import com.cubee.engine.framework.interfaces.GraphicsInterface;
import android.graphics.Point;
import com.google.common.base.Preconditions;

public class Graphics implements GraphicsInterface 
{
	private AssetManager assets = null;
	private Bitmap frameBuffer = null;
	private Canvas canvas = null;
	private Paint paint;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
	
	public Graphics(AssetManager assets, Bitmap framebuffer)
	{
		// Validation
		Preconditions.checkNotNull(assets);
		Preconditions.checkNotNull(framebuffer);
		
		this.assets = assets;
		this.frameBuffer = framebuffer;
		this.canvas = new Canvas(this.frameBuffer);
		this.paint = new Paint();
		this.paint.setAntiAlias(true);		
	}
	
	@Override
    public Image newImage(String fileName, ImageFormat format) throws UnsupportedImageFormat 
	{
		if(this.assets == null)
		{
			System.out.println("ASSETS MANAGER NULL !");
		}
		else
		{
			//System.out.println("ASSETS MANAGER NOT NULL !");
		}
		
		// Validation
		Preconditions.checkNotNull(fileName, "The name can't be null");
		Preconditions.checkNotNull(format, "The ImageFormat can't be null");
		Preconditions.checkArgument(fileName.length() > 0, "The filename can't be empty");
		
		// Validate the image
		Config imageConfig = null;
		switch(format)
		{
			case RGB565 :
				imageConfig = Config.RGB_565;
			break;
			case ARGB4444 :
				imageConfig = Config.ARGB_4444;
			break;
			case ARGB8888 : // We should avoid at a maximum to use this because of high memory use
				imageConfig = Config.ARGB_8888;
			break;
		}
		
		// Setup the options
		Options imageOptions = new Options();
		imageOptions.inPreferredConfig = imageConfig;
		
		// Prepare the image
        InputStream imageInputStream = null;
        Bitmap imageBitmap = null;
        
        try
        {
        	// Validate the Image
        	imageInputStream = this.assets.open(fileName);
        	imageBitmap		 = BitmapFactory.decodeStream(imageInputStream, null, imageOptions);
        	
        	if(imageBitmap == null)
        	{
        		throw new RuntimeException("Can't load the image from the file '" + fileName + "'");
        	}
        }
        catch(IOException e)
        {
        	throw new RuntimeException("Can't load the image from the file '" + fileName + "'");
        }
        finally 
        {
        	// Close the inputStream
        	if(imageInputStream != null)
        	{
        		try 
        		{
					imageInputStream.close();
				} 
        		catch (IOException e) 
        		{
					e.printStackTrace();
				}
        	}
        }

        // Return the right image from the image format
        switch(imageBitmap.getConfig())
        {
        	case RGB_565 :
        		return new Image(imageBitmap, ImageFormat.RGB565);
        	case ARGB_4444 :
        		return new Image(imageBitmap, ImageFormat.ARGB4444);
        	case ARGB_8888 :
        		return new Image(imageBitmap, ImageFormat.ARGB8888);
        	case ALPHA_8 :
        		throw new UnsupportedImageFormat("This program don't support the ALPHA_8 image format");
        }
		return null;
    }

    @Override
    public void clearScreen(int color) 
    {
    	// Validation
    	//Preconditions.checkArgument(color >= 0, "Can't color with a color negative number");
    	Preconditions.checkArgument(color <= 16777215, "The color number is higher than the max (16777215 or 0xFFFFFF)");
    	
    	// Extract the color
    	int red 	= ((color & 0xff0000) >> 16);
    	int green 	= ((color & 0xff00) >> 8);
    	int blue 	= (color & 0xff);
    	
    	// Draw the color on the screen
    	this.canvas.drawRGB(red, green, blue);
    }


    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) 
    {
    	// Validation
    	//Preconditions.checkArgument(color >= 0, "Can't color with a color negative number (" + color + ")");
    	Preconditions.checkArgument(color <= 16777215, "The color number is higher than the max (16777215 or 0xFFFFFF)");
    	
    	// Change the color of the line
        this.paint.setColor(color);
    	
    	// Draw the line
        canvas.drawLine(x, y, x2, y2, paint);
    }
    
    @Override
    public void drawLine(Point start, Point end, int color)
    {
    	this.drawLine(start.x, start.y, end.x, end.y, color);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color, boolean fill) 
    {
    	// Validation
    	//Preconditions.checkArgument(color >= 0, "Can't color with a color negative number");
    	Preconditions.checkArgument(color <= 16777215, "The color number is higher than the max (16777215 or 0xFFFFFF)");
    	Preconditions.checkArgument(width >= 0, "The width of the rectangle can't be lower than 0");
    	Preconditions.checkArgument(height >= 0, "The height of the rectangle can't be lower than 0");
    	
    	// Change the color
        this.paint.setColor(color);
        
        // Fill of not the rectangle
        if(fill)
        {
        	this.paint.setStyle(Style.FILL);
        }
        else
        {
        	this.paint.setStyle(Style.STROKE);
        }
        
        // Draw the rectangle
        this.canvas.drawRect(x, y, x + width - 1, y + height - 1, this.paint);
    }
    
    @Override
    public void drawRect(Point position, int width, int height, int color, boolean fill)
    {
    	this.drawRect(position.x, position.y, width, height, color, fill);
    }
    
    @Override
    public void drawARGB(int alpha, int red, int green, int blue) 
    {
    	// Fix the Alpha Min & Max
    	if(alpha > 255)
    	{
    		alpha = 255;
    	}
    	else if(alpha < 0)
    	{
    		alpha = 0;
    	}
    	
    	// Validation
    	Preconditions.checkArgument(alpha >= 0 && alpha <= 255);
    	Preconditions.checkArgument(red >= 0 && red <= 255);
    	Preconditions.checkArgument(green >= 0 && green <= 255);
    	Preconditions.checkArgument(blue >= 0 && blue <= 255);
    	
    	// Set the style
        this.paint.setStyle(Style.FILL);
        
        // Set the color
        this.canvas.drawARGB(alpha, red, green, blue);
    }
    
    @Override
    public void drawString(String text, int x, int y, Paint paint)
    {
    	// Validation
    	Preconditions.checkNotNull(text, "The text to draw can't be null");
    	Preconditions.checkArgument(text.length() > 0, "Can't draw an empty text");
    	Preconditions.checkNotNull(paint, "The painter can't be null");
    	
    	// Draw the text
        this.canvas.drawText(text, x, y, paint);
    }
    
    public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) 
    {
    	// Validation
    	Preconditions.checkNotNull(image, "Can't draw an empty image");
    	
        this.srcRect.left = srcX;
        this.srcRect.top = srcY;
        this.srcRect.right = srcX + srcWidth;
        this.srcRect.bottom = srcY + srcHeight;
        
        this.dstRect.left = x;
        this.dstRect.top = y;
        this.dstRect.right = x + srcWidth;
        this.dstRect.bottom = y + srcHeight;

        // Draw the image
        this.canvas.drawBitmap(((Image)image).bitmap, this.srcRect, this.dstRect, null);
    }
    
    @Override
    public void drawImage(Image image, int x, int y) 
    {
    	// Validation
    	Preconditions.checkNotNull(image, "Can't draw an empty image");
    	
    	// Draw the image
        this.canvas.drawBitmap(((Image)image).bitmap, x, y, null);
    }
    
    @Override
    public void drawScaledImage(Image image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight)
    {
    	// Validation
    	Preconditions.checkNotNull(image, "Can't draw an empty image");
        
    	this.srcRect.left = srcX;
        this.srcRect.top = srcY;
        this.srcRect.right = srcX + srcWidth;
        this.srcRect.bottom = srcY + srcHeight;
        
        this.dstRect.left = x;
        this.dstRect.top = y;
        this.dstRect.right = x + width;
        this.dstRect.bottom = y + height;
        
        // Draw the image
        this.canvas.drawBitmap(((Image) image).bitmap, srcRect, dstRect, null);
        
    }
    
    public void drawBitmap(Bitmap bitmap)
    {
    	// Validation
    	Preconditions.checkNotNull(bitmap, "The Bitmap can't be null");
    	this.canvas.drawBitmap(bitmap, 0, 0, this.paint);
    }
   
    @Override
    public int getWidth() 
    {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() 
    {
        return frameBuffer.getHeight();
    }
    
    public Bitmap getFramebuffer()
    {
    	return this.frameBuffer;
    }
}
