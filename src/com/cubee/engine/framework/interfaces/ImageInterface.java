package com.cubee.engine.framework.interfaces;

import com.cubee.engine.framework.interfaces.GraphicsInterface.ImageFormat;

public interface ImageInterface 
{
	/**
	 * Return the Width of the Image.
	 * @return int - Width
	 */
	public int getWidth();
	
	/**
	 * Return the Height of the Image.
	 * @return int - Height
	 */
    public int getHeight();
    
    /**
     * Return the Format of the Image.
     * @return ImageFormat - The format of the page.
     */
    public ImageFormat getFormat();
        
    /**
     * Delete all
     */
    public void dispose();
}
