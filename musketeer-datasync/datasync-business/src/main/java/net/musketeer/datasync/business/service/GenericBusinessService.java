package net.musketeer.datasync.business.service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import net.musketeer.datasync.memqueue.MemQueueConsumer;

public class GenericBusinessService implements Runnable {
		
	protected static final int DEFAULT_POOL_CAPACITY = 10;
	
	protected ExecutorService single = Executors.newSingleThreadExecutor();
	
	protected ThreadPoolExecutor pool;
	
	protected String queueName;
	
	protected Handler handler;
	
	public GenericBusinessService() {
	}
	
	public GenericBusinessService( int pool_capacity, String queueName ) {
		if ( pool_capacity == 0 ) {
			pool_capacity = DEFAULT_POOL_CAPACITY;
		}
		pool = ( ThreadPoolExecutor ) Executors.newFixedThreadPool( pool_capacity, new ThreadFactory() {
			
			private int count;
			
			private ThreadGroup group = new ThreadGroup( this.getClass().getSimpleName() + "Pool" );

			@Override
			public Thread newThread( Runnable r ) {
				return new Thread( group, r, group.getName().concat( "-" ).concat( String.valueOf( count ) ) );
			}
			
		} );
		this.queueName = queueName;
		init();
	}
	
	private void init() {
		single.execute( this );
	}
	
	@Override
	public void run() {
		MemQueueConsumer consumer = new MemQueueConsumer( getQueueName() );
		while ( true ) {
			final Map< String, Object > pm = consumer.consume();
			pool.execute( new Runnable() {

				@Override
				public void run() {
					handler.handle( pm );
				}
				
			} );
		}
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setHandler( Handler handler ) {
		this.handler = handler;
	}
	
}
