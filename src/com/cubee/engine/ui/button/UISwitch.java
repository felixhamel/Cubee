package com.cubee.engine.ui.button;

import android.graphics.Point;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Input;
import com.cubee.engine.ui.Points;
import com.cubee.game.assets.Assets;

public class UISwitch extends UIButton{

	private boolean switchOn;
	
	public UISwitch(Point position, Input input, boolean state) {
		super("", position, input);
		System.out.println("Switch Created " + state);
		switchOn = state;
		this.touch = input.getTouch();
		this.setupImageProperties();
		this.image = Assets.menuUISwitch;
	}

	@Override
	public void setupPaint() {
		// TODO Auto-generated method stub
		this.paint.setTextSize(40);
	}

	@Override
	public void setupImageProperties() {
		// TODO Auto-generated method stub
		if(switchOn)
		{
			this.normalStatePoints = new Points(new Point(14,12), new Point(211,68));
			this.hoveredStatePoints = new Points(new Point(14,12), new Point(211,68));
		}
		else
		{
			this.normalStatePoints = new Points(new Point(14,90), new Point(211,146));
			this.hoveredStatePoints = new Points(new Point(14,90), new Point(211,146));
		}
	}
	
	public void switchState()
	{
		switchOn = !switchOn;
		this.setupImageProperties();
	}
	
	public boolean getSwitchValue()
	{
		return switchOn;
	}
	
	@Override
	protected void internalRun()
	{
		for(int i = 0; i < 2; i++)
		{
			if(this.touch.getTouchX(i) >= this.position.x && this.touch.getTouchX(i) <= (this.width + this.position.x))
			{
				if(this.touch.getTouchY(i) >= this.position.y && this.touch.getTouchY(i) <= (this.height + this.position.y))
				{
					if(this.state != UIButtonState.HOVERED)
					{
						this.fingerTouch = i;
						this.changeState(UIButtonState.HOVERED);
					}
				}
				else
				{
					this.reset();
				}
			}
			else if(i == this.fingerTouch && this.state != UIButtonState.NORMAL)
			{
				if(this.touch.isTouchDown(this.fingerTouch))
				{	
					this.reset();
				}
				else
				{
					this.setChanged();
					this.notifyObservers();
					this.reset();
				}
			}
		}
	}

	@Override
	public void internalDraw(Graphics graphics) {
		// TODO Auto-generated method stub
		
	}
}

