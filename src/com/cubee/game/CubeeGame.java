package com.cubee.game;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import com.cubee.engine.framework.Game;
import com.cubee.engine.framework.Screen;
import com.cubee.game.menu.LoadingScreen;

public class CubeeGame extends Game
{
	public CubeeGame()
	{
	}
	
	@Override
	public Screen getInitScreen() 
	{
		return new LoadingScreen(this);
	}
}
