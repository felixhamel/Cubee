package com.cubee.engine.lib.thread;

import java.lang.Thread.State;
import java.util.Observable;

import com.cubee.engine.lib.thread.exception.TerminatedThreadException;

/**
 * The ThreadObserver is an object that is observed by the ThreadManager.
 * When the thread is dead, he tells the ThreadManager that he can be removed
 * from it. 
 * 
 * @author Felix Hamel
 */
public class ThreadObserver extends Observable implements Runnable
{
	private Thread threadToObserve = null;
	private final int TIME_TO_WAIT = 10;
	
	/**
	 * Constructor, keep the thread and start it is not already done.
	 * @param _thread
	 * @throws TerminatedThreadException 
	 */
	public ThreadObserver(Thread _thread) throws TerminatedThreadException
	{
		// If the thread is not terminated, manage it
		if(_thread.getState() != State.TERMINATED)
		{
			this.threadToObserve = _thread;
			
			// If the thread was not started, start it
			if(!_thread.isAlive())
			{
				_thread.start();
			}
		}
		else
		{
			throw new TerminatedThreadException("Thread was already terminated");
		}
	}
	
	@Override
	public void run() 
	{
		try
		{
			while(this.threadToObserve.isAlive())
			{
				Thread.sleep(this.TIME_TO_WAIT);
			}
			
			this.setChanged();
			this.notifyObservers();
		} 
		catch (InterruptedException e) 
		{}
	}

}
