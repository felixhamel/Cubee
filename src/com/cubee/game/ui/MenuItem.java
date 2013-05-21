package com.cubee.game.ui;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Input;
import android.graphics.Point;
import com.cubee.engine.ui.Points;
import com.cubee.engine.ui.button.UIButton;
import com.cubee.game.assets.Assets;

public class MenuItem extends UIButton
{
	public MenuItem(String text, Point position, Input input) 
	{
		super(text, position, input);
		this.image = Assets.menuButton;
	}

	@Override
	public void setupPaint() 
	{
		this.paint.setTextSize(40);
	}

	@Override
	public void setupImageProperties() 
	{
		this.normalStatePoints = new Points(new Point(15,28), new Point(510, 158));
		this.hoveredStatePoints = new Points(new Point(15, 167), new Point(510, 297));
	}

	@Override
	public void internalDraw(Graphics graphics)
	{
		
	}
}
