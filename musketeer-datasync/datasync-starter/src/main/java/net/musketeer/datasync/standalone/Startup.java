package net.musketeer.datasync.standalone;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.musketeer.datasync.memqueue.MemoryQueueManager;

public class Startup {

	private static Logger LOG = LoggerFactory.getLogger( Startup.class );

	private static ExecutorService single = Executors
			.newSingleThreadExecutor( new ThreadFactory() {

				@Override
				public Thread newThread( Runnable r ) {
					return new Thread( r, "DataSync-Starter-Thread" );
				}

			} );

	public static void main( String... args ) {
		Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() {

			@Override
			public void run() {

			}

		} ) );
		single.execute( new MainRunnable() );
		System.out
				.println( "======================================================" );
		System.out.println( "The framework has been started successfully." );
		System.out
				.println( "======================================================" );

	}
	
	class MainRunnable implements Runnable {

		@Override
		public void run() {
			try {
				MemoryQueueManager.getInstance().start();
				LOG.info( "Memory queue manager has been started." );
				SpringEngine.getInstance().start();
				LOG.info( "Spring Engine has been started." );
				ProtocolManager.getInstance().start();
				LOG.info( "Protocol manager has been started." );
				LOG.info( "Data Sync System started completed." );
			} catch ( Exception e ) {
				LOG.error( e.getMessage(), e );
				return;
			}
		}
		
	}

}
