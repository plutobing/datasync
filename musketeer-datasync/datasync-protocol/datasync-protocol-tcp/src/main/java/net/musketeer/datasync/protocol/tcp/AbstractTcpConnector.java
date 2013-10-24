package net.musketeer.datasync.protocol.tcp;

import java.util.concurrent.atomic.AtomicBoolean;

import net.musketeer.datasync.protocol.Connection;
import net.musketeer.datasync.protocol.config.ProtocolConfig;

public abstract class AbstractTcpConnector implements Connection {
	
	protected AtomicBoolean isRunning = new AtomicBoolean( true );
	
	protected ProtocolConfig config;
	
	public AbstractTcpConnector( ProtocolConfig config ) {
		this.config = config;
		if ( ! init() ) {
			throw new RuntimeException( "The server initialized error." );
		}
	}
	
	protected abstract boolean init();
	
	@Override
	public boolean isShutdown() {
		return ! isRunning.get();
	}

	@Override
	public boolean isStartup() {
		return isRunning.get();
	}

}
