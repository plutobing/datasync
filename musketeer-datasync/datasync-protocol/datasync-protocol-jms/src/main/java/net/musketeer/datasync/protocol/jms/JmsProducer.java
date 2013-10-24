package net.musketeer.datasync.protocol.jms;

import net.musketeer.datasync.protocol.Connection;

public class JmsProducer extends AbstractJmsConnector {
	
	private Connection agent;
	
	public JmsProducer() {
	}

	public < T, R > R received( T t ) throws Exception {
		return agent.received( t );
	}

	public < T, R > R send( T t ) throws Exception {
		return agent.send( t );
	}

	public synchronized void start() throws Exception {
		agent.start();
	}

	public synchronized void stop() throws Exception {
		agent.stop();
	}

}
