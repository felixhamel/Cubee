package com.cubee.engine.framework.interfaces;

public interface ScreenInterface 
{
	public void updateScreen(long timeElapsed);
	public void paintScreen(long timeElapsed);
	public void resume();
	public void pause();
	public void dispose();
	public void updateUIElement(String key, Object data);
	public void updateObservable(Object data);
}
