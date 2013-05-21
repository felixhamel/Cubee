package com.cubee.engine.lib.thread;

import java.util.LinkedList;
import java.util.Queue;

import com.cubee.engine.lib.thread.exception.TerminatedThreadException;

public class ThreadPool extends Thread
{
	private int nbMaxThreadSimultaneous = 10;
	private int nbActiveThreads = 0;
	private Thread[] activeThreads = null;
	private Queue<Thread> waitingThreads = new LinkedList<Thread>();
		
	/**
	 * Constructor, set the number of thread that can be active at the same time.
	 * @param _nbMaxThreadSimltaneous - The number of thread that can be active at the same time.
	 */
	public ThreadPool(final int _nbMaxThreadSimltaneous)
	{
		if(_nbMaxThreadSimltaneous > 0)
		{
			this.nbMaxThreadSimultaneous = _nbMaxThreadSimltaneous;
			this.activeThreads = new Thread[this.nbMaxThreadSimultaneous];
			this.start();
		}
	}
	
	public void addThread(Thread _thread) throws TerminatedThreadException
	{
		if(_thread.getState() != State.TERMINATED)
		{
			
		}
		else
		{
			throw new TerminatedThreadException("The thread was already terminated before adding it");
		}
	}
	
	private synchronized void put(Thread _thread)
	{
		for(int i = 0; i < this.nbMaxThreadSimultaneous; i++)
		{
			if(this.activeThreads[i] == null)
			{
				this.activeThreads[i] = _thread;
				this.activeThreads[i].start();
				this.nbActiveThreads++;
				return;
			}
		}
		
		this.waitingThreads.add(_thread);
	}
	
	public int getNbThreadRunning() 
	{
		return this.nbActiveThreads;
	}
	
	public final int getNbActiveThreadSimultaneously()
	{
		return this.nbMaxThreadSimultaneous;
	}
	
	public synchronized final boolean isEmpty()
	{
		return (this.nbActiveThreads == 0);
	}
	
	public void run()
	{
		while(true)
		{
			if(this.nbActiveThreads > 0)
			{
				this.checkThreads();
				
				try 
				{
					Thread.sleep(50);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private synchronized void checkThreads()
	{
		for(int i = 0; i < this.nbMaxThreadSimultaneous; i++)
		{
			if(this.activeThreads[i] != null)
			{
				if(!this.activeThreads[i].isAlive())
				{
					if(this.waitingThreads.size() > 0)
					{
						this.activeThreads[i] = this.waitingThreads.poll();
						this.activeThreads[i].start();
					}
					else
					{
						this.activeThreads[i] = null;
						this.nbActiveThreads--;
					}
				}
			}
		}
	}
}
