package net.musketeer.datasync.protocol.tcp.nio;

import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.musketeer.datasync.protocol.Connection;
import net.musketeer.datasync.protocol.model.ProtocolDefinition;
import net.musketeer.datasync.protocol.tcp.AbstractTcpConnector;

public class TcpClientConnector extends AbstractTcpConnector {

	private static Logger LOG = LoggerFactory.getLogger( TcpClientConnector.class );
	
	private ProtocolDefinition definition;
		
	private SocketChannel channel;
	
	public TcpClientConnector( ProtocolDefinition config ) {
		super( config );
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

	public ProtocolDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(ProtocolDefinition definition) {
		this.definition = definition;
	}

}
