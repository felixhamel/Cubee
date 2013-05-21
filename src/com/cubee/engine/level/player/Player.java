package com.cubee.engine.level.player;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Point;

import com.cubee.engine.framework.Graphics;
import com.cubee.engine.framework.Image;
import com.cubee.engine.level.Level;
import com.cubee.game.assets.Assets;
import com.google.common.base.Preconditions;

public class Player implements Observer
{
	private Image image = Assets.player;
	private Point position = null;
	private float speed = 4;
	
	private PlayerController controller = null;
	
	private PlayerMovement movement = PlayerMovement.NONE;
	private PlayerState state = PlayerState.NONE;
	
	private Level level = null;
	
	// Speed
	private final float MAX_SPEED = 4.f;
	private final float MIN_SPEED = 0.f;
	
	// Animation
	private float movementToDo = 0;
	private final float MOVEMENT_PER_ANIMATION = 64;
	
	public Player(Point position, Level level)
	{
		// Validation
		Preconditions.checkNotNull(position);
		
		this.position = position;
		
		this.controller = PlayerController.getInstance();
		this.controller.addObserver(this);
		
		this.level = level;
	}
	
	public boolean isMoving()
	{
		return (this.state == PlayerState.MOVEMENT);
	}
	
	public void stop()
	{
		this.movement = PlayerMovement.NONE;
		this.speed = this.MIN_SPEED;
	}
	
	public void resume()
	{
		this.speed = this.MAX_SPEED;
	}
	
	public void move(PlayerMovement movement)
	{
		if(this.state == PlayerState.NONE && movement != PlayerMovement.NONE)
		{
			this.state = PlayerState.MOVEMENT;
			this.movementToDo = this.MOVEMENT_PER_ANIMATION;
			this.movement = movement;
		}
	}
	
	public Point getCenterPoint()
	{
		Point center = new Point();
		center.x = this.position.x + (image.getWidth() / 2);
		center.y = this.position.y + (image.getHeight() / 2);
		return center;
	}
	
	public Point getRealPosition()
	{
		return this.position;
	}
	
	public int getWidth()
	{
		return this.image.getWidth();
	}
	
	public int getHeight()
	{
		return this.image.getHeight();
	}
	
	public void draw(Graphics graphics)
	{
		if(this.state == PlayerState.MOVEMENT && this.movement != PlayerMovement.NONE)
		{
			if(this.movementToDo > 0)
			{
				switch(this.movement)
				{
					case UP :
						this.position.y -= this.speed;
					break;
					case DOWN :
						this.position.y += this.speed;
					break;
					case LEFT :
						this.position.x -= this.speed;
					break;
					case RIGHT :
						this.position.x += this.speed;
					break;
				}
				this.movementToDo -= this.speed;
			}
			else
			{
				this.state = PlayerState.NONE;
				this.level.checkPlayerPosition();
				
				try 
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		// Draw the Player
		graphics.drawImage(image, this.position.x, this.position.y);
	}

	@Override
	public void update(Observable observable, Object data) 
	{
		if(data instanceof PlayerMovement)
		{
			this.move((PlayerMovement) data);
		}
		
	}
	
}
