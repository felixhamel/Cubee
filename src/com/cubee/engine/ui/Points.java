package com.cubee.engine.ui;

import android.graphics.Point;

import com.google.common.base.Preconditions;

public class Points 
{
	public Point start;
	public Point end;
	
	public Points(Point start, Point end)
	{
		// Validation
		Preconditions.checkNotNull(start, "The Start Point can't be null");
		Preconditions.checkNotNull(end, "The End Point can't be null");
		
		this.start = start;
		this.end   = end;
	}
}
