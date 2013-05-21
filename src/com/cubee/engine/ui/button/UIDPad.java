package com.cubee.engine.ui.button;

import android.graphics.Point;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Input;
import com.cubee.engine.framework.Screen;
import com.cubee.engine.ui.UIElement;

public class UIDPad extends UIElement
{
	private DirectionalButton up, down, right, left;
	
	public UIDPad(Point position, Input input, Screen master)
	{
		super(true, true);
		
		this.left = new DirectionalButton(new Point(position.x - 60, position.y), input, Direction.G_LEFT);
		master.addUIElement(this.left, "PLAYER_LEFT");
				
		this.right = new DirectionalButton(new Point(position.x + 72, position.y), input, Direction.G_RIGHT);
		master.addUIElement(this.right, "PLAYER_RIGHT");
				
		this.up = new DirectionalButton(new Point(position.x, position.y - 58), input, Direction.G_UP);
		master.addUIElement(this.up, "PLAYER_UP");
				
		this.down = new DirectionalButton(new Point(position.x, position.y + 70), input, Direction.G_DOWN);
		master.addUIElement(this.down, "PLAYER_DOWN");
	}
	
	@Override
	public void draw(Graphics graphics) 
	{
		
	}

	@Override
	protected void internalRun() 
	{
		
	}
	
	@Override
	public void internalDraw(Graphics graphics) 
	{
		
	}
}