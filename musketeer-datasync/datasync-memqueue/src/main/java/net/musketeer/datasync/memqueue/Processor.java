package net.musketeer.datasync.memqueue;

public abstract interface Processor {
	
	public abstract < T > boolean offer( T paramT ) throws Exception;

	public abstract < T > T take() throws Exception;
	
}