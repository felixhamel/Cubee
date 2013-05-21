package com.cubee.engine.framework.interfaces;

import com.cubee.engine.exceptions.UnsupportedImageFormat;
import com.cubee.engine.framework.Image;
import android.graphics.Point;

import android.graphics.Paint;

public interface GraphicsInterface 
{
	/**
	 * All the Image format available to the user.
	 */
	public static enum ImageFormat {
        ARGB8888, 
        ARGB4444, 
        RGB565
    }

	/**
	 * Create a new Image from a file.
	 * @param fileName - The name of the Image.
	 * @param format - The format of the Image.
	 * @return Image - The Image created from the File.
	 * @throws UnsupportedImageFormat 
	 */
    public Image newImage(String fileName, ImageFormat format) throws UnsupportedImageFormat;

    /**
     * Erase all the Screen with a specific color. (Fill all the Screen with a color).
     * @param color - The color that will fill the screen.
     */
    public void clearScreen(int color);

    /**
     * Draw a line on the Screen.
     * @param x1 - The X position of the line.
     * @param y1 - The Y position of the line.
     * @param x2 - The end X position of the line.
     * @param y2 - The end Y position of the line.
     * @param color - The color of the line.
     */
    public void drawLine(int x1, int y1, int x2, int y2, int color);
	public void drawLine(Point start, Point end, int color);

    /**
     * Draw a rectangle into the Screen.
     * @param x - The X position of the Rectangle.
     * @param y - The Y position of the Rectangle.
     * @param width - The width of the Rectangle.
     * @param height - The height of the Rectangle.
     * @param color - The color of the Rectangle.
     */
    public void drawRect(int x, int y, int width, int height, int color, boolean fill); 
	public void drawRect(Point position, int width, int height, int color, boolean fill);

    /**
     * Draw an Image to the Screen.
     * @param image - The Image to draw on the Screen.
     * @param x - The X position of the Image.
     * @param y - The Y position of the Image.
     * @param srcX - The X position on the Image to display (0 = all the width).
     * @param srcY - The Y position on the Image to display (0 = all the height).
     * @param srcWidth - The width of the Image to display.
     * @param srcHeight - The height of the Image to display.
     */
    public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
	public void drawScaledImage(Image image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight);

    /**
     * Draw an Image to the Screen
     * @param image - The Image to draw on the Screen.
     * @param x - The X position of the Image.
     * @param y - The Y position of the Image.
     */
    public void drawImage(Image image, int x, int y);

    /**
     * Draw some Text on the Screen.
     * @param text - The Text to print on the Screen.
     * @param x - The X position of the Text.
     * @param y - The Y position of the Text.
     * @param paint - The Paint.
     */
    void drawString(String text, int x, int y, Paint paint);

    /**
     * Get the Width of the Graphic space.
     * @return
     */
    public int getWidth();

    /**
     * Get the Height of the Graphic space.
     * @return
     */
    public int getHeight();

    /**
     * Draw a color to the Screen.
     * @param a - The Alpha (0-255)
     * @param r - Red (0-255)
     * @param g - Green (0-255)
     * @param b - Blue (0-255)
     */
    public void drawARGB(int a, int r, int g, int b);
}