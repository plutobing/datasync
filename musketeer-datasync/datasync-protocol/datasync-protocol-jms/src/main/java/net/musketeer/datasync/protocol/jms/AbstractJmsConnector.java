package net.musketeer.datasync.protocol.jms;

import java.util.concurrent.atomic.AtomicBoolean;

import net.musketeer.datasync.protocol.Connection;
import net.musketeer.datasync.protocol.ConnectionPool;

public abstract class AbstractJmsConnector implements Connection {

	protected AtomicBoolean running = new AtomicBoolean( false );
	
	protected ConnectionPool pool = new ConnectionPool();

	public boolean isShutdown() {
		return ! running.get();
	}

	public boolean isStartup() {
		return running.get();
	}

	public ConnectionPool getPool() {
		return pool;
	}

	public void setPool( ConnectionPool pool ) {
		this.pool = pool;
	}

}
