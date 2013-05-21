package com.cubee.engine.lib.thread;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.cubee.engine.lib.thread.exception.TerminatedThreadException;

/**
 * The thread manager is used as a container for multiple threads that needs to be kept 
 * somewhere. When the thread is dead (has finished his job), he tells the manager to remove
 * it from the list.
 * 
 * @author Felix Hamel
 */
public class ThreadManager implements Observer
{
	private List<ThreadObserver> observers = null;
	
	/**
	 * Constructor, prepare the list
	 */
	public ThreadManager()
	{
		this.observers = new ArrayList<ThreadObserver>();
		this.observers.clear();
	}
	
	// -------------------------------------------------------
	// Manage the threads
	// -------------------------------------------------------
	
	/**
	 * Add a new Thread in the list (Automatically started)
	 * @param _thread - The thread to add to the list
	 */
	public synchronized void add(Thread _thread)
	{
		if(_thread.getState() != State.TERMINATED)
		{
			try
			{
				// Observable runnable
				ThreadObserver observer = new ThreadObserver(_thread);
				observer.addObserver(this);
				this.observers.add(observer);
				
				// Launch the thread
				Thread thread = new Thread(observer);
				thread.start();
			}
			catch(TerminatedThreadException e)
			{
				System.out.println("Couldn't add the tread, was already terminated before adding it to the list");
			}
		}
	}
	
	/**
	 * Create a thread with the Runnable, start it and add it to the list
	 * @param _runnable - The runnable to add to the list
	 */
	public void add(Runnable _runnable)
	{
		Thread thread = new Thread(_runnable);
		this.add(thread);
	}
	
	/**
	 * Remove a thread from the list with his Observable
	 * @param _obs
	 */
	private synchronized void removeThread(Observable _obs)
	{
		this.observers.remove(_obs);
	}
	
	// -------------------------------------------------------
	// Action
	// -------------------------------------------------------
	
	/**
	 * When a thread has finished his job, he calls this methods to aware the
	 * Manager that he can be removed from the list.
	 * <br><b>Do not use this methods unless you really needs to.</b>
	 */
	@Override
	public void update(Observable _observedThread, Object _id) 
	{
		this.removeThread(_observedThread);
	}
	
	// -------------------------------------------------------
	// Get informations about the Manager
	// -------------------------------------------------------
	
	/**
	 * Get the information if the ThreadManager is empty of not.
	 * @return boolean - True if the list is Empty, False otherwise.
	 */
	public final boolean isEmpty()
	{
		return this.observers.isEmpty();
	}

	/**
	 * Get the number of threads that are running in the Manager.
	 * @return int - The number of running threads in the Manager.
	 */
	public final int getNbThreadRunning() 
	{
		return this.observers.size();
	}
}
