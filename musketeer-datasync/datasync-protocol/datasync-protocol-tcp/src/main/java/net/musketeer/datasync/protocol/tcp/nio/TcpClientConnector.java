package net.musketeer.datasync.protocol.tcp.nio;

import java.nio.channels.SocketChannel;

import net.musketeer.datasync.protocol.config.ProtocolConfig;
import net.musketeer.datasync.protocol.tcp.AbstractTcpConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpClientConnector extends AbstractTcpConnector {

	private static Logger LOG = LoggerFactory.getLogger( TcpClientConnector.class );
	
	private ProtocolConfig definition;
		
	private SocketChannel channel;
	
	public TcpClientConnector() {
	}
	
	@Override
	protected boolean init() {
		return false;
	}

	@Override
	public <T, R> R received(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public < T, R > R send( T t ) throws Exception {
		if ( t == null ) {
			throw new IllegalArgumentException( "The argument can not be null." );
		}
		final byte[] bytes = ( byte[] ) t;
		
		return null;
	}

	@Override
	public void start() throws Exception {
		if ( isStartup() ) {
			return ;
		}
		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public ProtocolConfig getDefinition() {
		return definition;
	}

	public void setDefinition(ProtocolConfig definition) {
		this.definition = definition;
	}

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.Connection#getConfig()
	 */
	@Override
	public ProtocolConfig getConfig() {
		return this.config;
	}

}
