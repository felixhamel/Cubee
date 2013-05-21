/**
 * Taken from : http://silveiraneto.net/2008/03/15/simple-java-chronometer/ 
 */

package com.cubee.engine.plugins;

public final class Chronometer
{
	private long startTime = 0;
	private long endTime = 0;
	
	private boolean paused = false;
	private long startPauseTime = 0;
	private long endPauseTime = 0;
 
	/**
	 * Default constructor
	 */
	public Chronometer()
	{
		this(false);
	}
	
	/**
	 * Constructor
	 * @param startChrono - If True, start the Chronometer
	 */
	public Chronometer(boolean startChrono)
	{
		if(startChrono)
		{
			this.start();
		}
	}
	
	/**
	 * Start the Chronometer
	 */
    public void start()
    {
        this.startTime = System.currentTimeMillis();
    }
    
    /**
     * Reset the chronometer
     */
    public void reset()
    {
    	this.start();
    	this.endTime = 0;
    }
 
    /**
     * Stop the Chronometer
     */
    public void stop()
    {
        this.endTime = System.currentTimeMillis();
        
        if(this.paused)
        {
        	this.paused = false;
        	this.endTime -= (this.endPauseTime - this.startPauseTime);
        }
    }
    
    public void pause()
    {
    	this.paused = true;
    	this.startPauseTime = System.currentTimeMillis();
    }
    
    public void resume()
    {
    	if(this.paused)
    	{
    		this.paused = false;
	    	this.endPauseTime = System.currentTimeMillis();
	    	this.startTime += (this.endPauseTime - this.startPauseTime);
    	}
    }
    
    public boolean isPaused()
    {
    	return this.paused;
    }
    
    public boolean isStarted()
    {
    	return !(this.startTime == 0);
    }
 
    @Deprecated
    public long getTime()
    {
    	return this.getMilliseconds();
    }
 
    public long getMilliseconds()
    {
    	if(this.paused)
    	{
    		return (this.startPauseTime  - this.startTime);
    	}
    	else if(this.startTime == (long) 0 && this.endTime == (long) 0)
    	{
    		return 0L;
    	}
    	else if(this.endTime == (long) 0)
    	{
    		return (System.currentTimeMillis() - this.startTime);
    	}
    	else
    	{
    		return (this.endTime - this.startTime);
    	}
    }
 
    public double getSeconds()
    {
    	return (this.getMilliseconds() / 1000.0);
    }
 
    public double getMinutes()
    {
    	return (this.getSeconds() / 60.0);
    }
 
    public double getHours()
    {
    	return (this.getMinutes() / 60.0);
    }
    
    public double getDays()
    {
    	return (this.getHours() / 24.0);
    }
    
    public long getStartTime()
    {
    	return this.startTime;
    }
    
    public long getEndTime()
    {
    	return this.endTime;
    }
}