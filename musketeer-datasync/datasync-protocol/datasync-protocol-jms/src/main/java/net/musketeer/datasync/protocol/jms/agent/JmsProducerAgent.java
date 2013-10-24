package net.musketeer.datasync.protocol.jms.agent;

import java.net.ConnectException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.musketeer.datasync.protocol.jms.AbstractJmsConnector;
import net.musketeer.datasync.protocol.jms.domain.JmsProducerDomain;

public class JmsProducerAgent extends AbstractJmsConnector {
	
	private BlockingQueue< JmsProducerDomain > producers = new LinkedBlockingQueue< JmsProducerDomain >();

	public < T, R > R received( T t ) throws Exception {
		return null;
	}

	public < T, R > R send( T t ) throws Exception {
		final JmsProducerDomain domain = producers.take();
		try {
			domain.send( t );
		} catch ( Exception e ) {
			if ( e instanceof ConnectException ) {
				
			}
		}
		return null;
	}

	public void start() throws Exception {
		start : {
			if ( isStartup() ) {
				break start;
			}
			try {
				for ( int i = 0; i < 10; i++ ) {
					JmsProducerDomain producer = new JmsProducerDomain( "", "", "", "", null );
					producer.init();
					producers.add( producer );
				}
			} catch ( Exception e ) {
				throw e;
			}
			running.set( true );
		}
	}

	public void stop() throws Exception {
		stop : {
			if ( isShutdown() ) {
				break stop;
			}
			try {
				for ( JmsProducerDomain producer : producers ) {
					producer.destroy();
					producers.remove( producer );
				}
			} catch ( Exception e ) {
				throw e;
			}
			running.set( false );
		}
	}

}
