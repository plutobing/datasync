package net.musketeer.datasync.memqueue;

public abstract class AbstractProcessor implements Processor {
	
	protected MemoryQueue queue;

	protected AbstractProcessor( MemoryQueue queue ) {
		this.queue = queue;
	}
}
