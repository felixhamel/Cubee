package com.cubee.engine.ui;

import com.cubee.engine.framework.Graphics;

public abstract class UIPopup extends UIElement
{

	public UIPopup()
	{
		super(false, false);
	}

	@Override
	protected abstract void internalRun();

	@Override
	public abstract void internalDraw(Graphics graphics);

}
