package net.musketeer.datasync.protocol.jms;

import java.util.concurrent.atomic.AtomicBoolean;

import net.musketeer.datasync.protocol.Connection;
import net.musketeer.datasync.protocol.ConnectionPool;
import net.musketeer.datasync.protocol.config.ProtocolConfig;

public abstract class AbstractJmsConnector implements Connection {

	protected AtomicBoolean running = new AtomicBoolean( false );
	
	protected ConnectionPool pool = new ConnectionPool();
	
	protected ProtocolConfig config;

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
	
	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.Connection#getConfig()
	 */
	@Override
	public ProtocolConfig getConfig() {
		return this.config;
	}

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.Connection#setConfig(net.musketeer.datasync.protocol.config.ProtocolConfig)
	 */
	@Override
	public void setConfig( ProtocolConfig config ) {
		this.config = config;
	}
}
