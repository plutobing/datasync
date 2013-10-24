package net.musketeer.datasync.protocol.tcp;

import java.util.concurrent.atomic.AtomicBoolean;

import net.musketeer.datasync.protocol.Connection;
import net.musketeer.datasync.protocol.model.ProtocolDefinition;

public abstract class AbstractTcpConnector implements Connection {
	
	protected AtomicBoolean isRunning = new AtomicBoolean( true );
	
	protected ProtocolDefinition config;
	
	public AbstractTcpConnector( ProtocolDefinition config ) {
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
