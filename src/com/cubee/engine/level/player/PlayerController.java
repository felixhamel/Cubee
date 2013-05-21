package com.cubee.engine.level.player;

import java.util.Observable;

public class PlayerController extends Observable
{
	private static final PlayerController instance = new PlayerController();
	
	public static PlayerController getInstance()
	{
		return instance;
	}
	
	public static void moveUp()
	{
		instance.setChanged();
		instance.notifyObservers(PlayerMovement.UP);
	}
	
	public static void moveDown()
	{
		instance.setChanged();
		instance.notifyObservers(PlayerMovement.DOWN);
	}
	
	public static void moveLeft()
	{
		instance.setChanged();
		instance.notifyObservers(PlayerMovement.LEFT);
	}
	
	public static void moveRight()
	{
		instance.setChanged();
		instance.notifyObservers(PlayerMovement.RIGHT);
	}
}
