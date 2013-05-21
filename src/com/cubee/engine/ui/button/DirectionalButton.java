package com.cubee.engine.ui.button;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Input;
import android.graphics.Point;
import com.cubee.engine.ui.Points;
import com.cubee.game.assets.Assets;


public class DirectionalButton extends UIButton
{
	private Direction dir;
	public DirectionalButton(Point position, Input input, Direction direction) 
	{
		super("", position, input);
		dir = direction;
		if(dir == Direction.LEFT || dir == Direction.RIGHT || Direction.DOWN == dir || Direction.UP == dir)
		{
			this.image = Assets.menuArrows;
		}
		else
		{
			this.image = Assets.gameArrows;
		}
		this.setupImageProperties();
		this.changeState(UIButtonState.NORMAL);
	}

	@Override
	public void setupPaint() 
	{
		
	}

	@Override
	public void setupImageProperties() 
	{
		if(dir == Direction.LEFT)
		{
			this.normalStatePoints = new Points(new Point(165,196), new Point(300,361));
			this.hoveredStatePoints = new Points(new Point(160,9), new Point(283, 174));
		}
		else if(dir == Direction.RIGHT)
		{
			this.normalStatePoints = new Points(new Point(17,196), new Point(137,361));
			this.hoveredStatePoints = new Points(new Point(17,9), new Point(137, 174));
		}
		else if(dir == Direction.G_LEFT)
		{
			this.normalStatePoints = new Points(new Point(23,243), new Point(77,310));
			this.hoveredStatePoints = new Points(new Point(114,243), new Point(168,310));
		}
		else if(dir == Direction.G_RIGHT)
		{
			this.normalStatePoints = new Points(new Point(23,87), new Point(77,154));
			this.hoveredStatePoints = new Points(new Point(114,87), new Point(168,154));
		}
		else if(dir == Direction.G_UP)
		{
			this.normalStatePoints = new Points(new Point(16,12), new Point(83,65));
			this.hoveredStatePoints = new Points(new Point(108,12), new Point(175,65));
		}
		else if(dir == Direction.G_DOWN)
		{
			this.normalStatePoints = new Points(new Point(16,170), new Point(83,225));
			this.hoveredStatePoints = new Points(new Point(108,170), new Point(175,225));
		}
		else
		{
			this.normalStatePoints = new Points(new Point(0,100), new Point(0,100));
			this.hoveredStatePoints = new Points(new Point(0,100), new Point(0,100));
		}
		
	}

	@Override
	public void internalDraw(Graphics graphics) 
	{
		
	}

}
