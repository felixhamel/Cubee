package com.cubee.engine.framework.interfaces;

import com.cubee.engine.framework.Audio;
import com.cubee.engine.framework.FileIO;
import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Input;
import com.cubee.engine.framework.Screen;
import com.cubee.engine.framework.View;

public interface GameInterface
{	
	/**
	 * Return the Audio System of the Game Engine.
	 * @return Audio - The Audio system.
	 */
	public Audio getAudio();
	
	/**
	 * Returns the Input system of the Game Engine.
	 * @return Input - The Input System.
	 */
	public Input getInput();
	
	/**
	 * Returns the File Manipulation System of the Game Engine.
	 * @return FileIO - The File I/O System.
	 */
	public FileIO getFileIO();
	
	/**
	 * Returns the Graphics System of the Game Engine.
	 * @return Graphics - The Graphics System.
	 */
	public Graphics getGraphics();
	
	/**
	 * Returns the current Screen that is used by the Game Engine.
	 * @return Screen - The Screen used by the Game Engine.
	 */
	public Screen getCurrentScreen();
	
	public Screen getInitScreen();

	public View getView();

	public void setNextScreen(Screen nextScreen, boolean keepInStack);

	void previousScreen();
}
