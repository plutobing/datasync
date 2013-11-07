package net.musketeer.datasync.protocol.tcp;

import java.util.concurrent.atomic.AtomicBoolean;

import net.musketeer.datasync.protocol.Connection;
import net.musketeer.datasync.protocol.config.ProtocolConfig;
import net.musketeer.datasync.protocol.tcp.config.TcpProtocolConfig;

public abstract class AbstractTcpConnector implements Connection {
	
	protected AtomicBoolean isRunning = new AtomicBoolean( true );
	
	protected TcpProtocolConfig config;
	
	public AbstractTcpConnector() {
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

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.Connection#setConfig(net.musketeer.datasync.protocol.config.ProtocolConfig)
	 */
	@Override
	public void setConfig( ProtocolConfig config ) {
		this.config = ( TcpProtocolConfig ) config;
		if ( ! init() ) {
			throw new RuntimeException( "The server initialized error." );
		}
	}
	
}
