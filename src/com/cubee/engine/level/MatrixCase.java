package com.cubee.engine.level;

import android.graphics.Point;

import com.cubee.engine.level.Case.CaseState;
import com.google.common.base.Preconditions;

public class MatrixCase 
{
	private Case[][] matrix = null;
	private int sizeX = 0;
	private int sizeY = 0;
	private boolean valid = false;
	
	public MatrixCase(final int sizeX, final int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.matrix = new Case[sizeX][sizeY];
	}
	
	public void createMatrix(int[][] readedLevel) throws Exception
	{
		// Validate the size of the level
		//System.out.println("X: " + readedLevel.length + " && Y: " + readedLevel[0].length);
		if(readedLevel.length == this.sizeX && readedLevel[0].length == this.sizeY)
		{
			for(int y = 0; y < this.sizeY; y++)
			{
				for(int x = 0; x < this.sizeX; x++)
				{
					if(readedLevel[x][y] == 1)
					{
						this.set(new Case(CaseState.NORMAL), x, y);
					}
					else if(readedLevel[x][y] == 2)
					{
						this.set(new Case(CaseState.START), x, y);
					}
					else if(readedLevel[x][y] == 3)
					{
						this.set(new Case(CaseState.END), x, y);
					}
					else
					{
						this.set(new Case(CaseState.HOLE), x, y);
					}
				}
			}
			this.valid = true;
		}
		else
		{
			throw new Exception("Invalid matrix size given (X:" + readedLevel.length + " Y:" + readedLevel[0].length + ")");
		}
	}
	
	public boolean validate()
	{
		if(this.valid)
		{
			// Search for a Start/End case + at least 1 normal case
			boolean startFound = false;
			boolean endFound = false;
			boolean atLeastOnNormalCase = false;
			
			for(int y = 0; y < this.sizeY; y++)
			{
				for(int x = 0; x < this.sizeX; x++)
				{
					if(this.get(x,y).getCaseState() == CaseState.START && !startFound)
					{
						startFound = true;
					}
					else if(this.get(x,y).getCaseState() == CaseState.END && !endFound)
					{
						endFound = true;
					}
					else if(!atLeastOnNormalCase)
					{
						if(this.get(x,y).getCaseState() == CaseState.NORMAL)
						{
							atLeastOnNormalCase = true;
						}
					}
				}
			}
			
			if(startFound && endFound && atLeastOnNormalCase)
			{
				return true;
			}
			else
			{
				System.out.println("START: " + startFound);
				System.out.println("END: " + endFound);
				System.out.println("NORMAL: " + atLeastOnNormalCase);
				return false;
			}
		}
		else
		{
			return this.valid;
		}
	}
	
	public Case get(int x, int y)
	{
		// Validation
		Preconditions.checkArgument(x >= 0 && x < this.sizeX, "Invalid X position");
		Preconditions.checkArgument(y >= 0 && y < this.sizeY, "Invalid Y position");
		
		return this.matrix[x][y];
	}
	
	public void set(Case caseLevel, int x, int y)
	{
		// Validation
		Preconditions.checkArgument(x >= 0 && x < this.sizeX, "Invalid X position");
		Preconditions.checkArgument(y >= 0 && y < this.sizeY, "Invalid Y position");
		Preconditions.checkNotNull(caseLevel, "Can't set a NULL case");
		
		this.matrix[x][y] = caseLevel;
	}
	
	public int getSizeX()
	{
		return this.sizeX;
	}
	
	public int getSizeY()
	{
		return this.sizeY;
	}
}
