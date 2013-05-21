package com.cubee.engine.lib.thread.exception;

public class TerminatedThreadException extends Exception
{
	private static final long serialVersionUID = -3564590868485042288L;

	public TerminatedThreadException(String message)
	{
		super(message);
	}
}
