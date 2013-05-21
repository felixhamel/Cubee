package com.cubee.engine.framework;

import android.graphics.Bitmap;

import com.cubee.engine.framework.interfaces.GraphicsInterface.ImageFormat;
import com.cubee.engine.framework.interfaces.ImageInterface;
import com.google.common.base.Preconditions;

public class Image implements ImageInterface 
{
	public Bitmap bitmap;
	ImageFormat format;
	
	public Image(Bitmap bitmap, ImageFormat imageFormat)
	{
		// Validation
		Preconditions.checkNotNull(bitmap, "The Bitmap can't be null");
		Preconditions.checkNotNull(imageFormat, "The ImageFormat can't be null");
		
		this.bitmap = bitmap;
		this.format = imageFormat;
	}

	@Override
	public int getWidth() 
	{
		return this.bitmap.getWidth();
	}

	@Override
	public int getHeight() 
	{
		return this.bitmap.getHeight();
	}

	@Override
	public final ImageFormat getFormat() 
	{
		return this.format;
	}

	@Override
	public void dispose() 
	{
		this.bitmap.recycle();
	}

}
