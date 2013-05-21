package com.cubee.engine.level;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.cubee.game.assets.Assets;

public class Case 
{
	public enum CaseState
	{
		NORMAL, // Default
		HOLE,
		START,
		END,
	}
	
	private static Image image = Assets.caseNormal;
	private CaseState state = CaseState.NORMAL;
	
	public Case(CaseState state)
	{
		this.changeState(state);
	}
	
	public void changeState(CaseState state)
	{
		switch(state)
		{
			case NORMAL :
				this.state = CaseState.NORMAL;
			break;
			case HOLE :
				this.state = CaseState.HOLE;		
			break;
			case START :
				this.state = CaseState.START;
			break;	
			case END :
				this.state = CaseState.END;
			break;
		}
	}
	
	public CaseState getCaseState()
	{
		return this.state;
	}
	
	public void disappear()
	{
		if(this.state == CaseState.NORMAL)
		{
			this.changeState(CaseState.HOLE);
		}
	}
}
