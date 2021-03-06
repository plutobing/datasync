package net.musketeer.datasync.protocol.tcp.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

import net.musketeer.datasync.protocol.config.ProtocolConfig;
import net.musketeer.datasync.protocol.tcp.config.TcpRequestConfig;
import net.musketeer.datasync.protocol.tcp.nio.event.Handler;
import net.musketeer.datasync.protocol.tcp.nio.event.StreamHandler;

public abstract class NioWorker implements Runnable {

	protected Selector selector;
	
	protected ThreadPoolExecutor executor;
	
	protected ProtocolConfig config;
	
//	private ConcurrentLinkedQueue< Session > queue = new ConcurrentLinkedQueue< Session >();

	public NioWorker( ThreadPoolExecutor executor, ProtocolConfig config ) {
		this.executor = executor;
		this.config = config;
	}
	
	public void run() {
		int ops = 0;
		while ( true ) {
			try {
				ops = selector.select();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			
			if ( ops > 0 ) {
				final Iterator< SelectionKey > iter = selector.selectedKeys().iterator();
				
				SelectionKey key = null;
				while ( iter.hasNext() ) {
					key = iter.next();
					dispatch( key );
					iter.remove();
				}
			}
		}
	}
	
	private void dispatch( SelectionKey key ) {
		if ( key == null ) {
			return ;
		}
		StreamHandler handler = ( StreamHandler ) key.attachment();
		
		if ( handler != null ) {
			handler.handle();
		}
	}
	
	public void register( SocketChannel channel ) throws IOException {
		channel.configureBlocking( ( ( TcpRequestConfig ) getConfig().getRequest() ).isBlock() );
		channel.socket().setKeepAlive( ( ( TcpRequestConfig ) getConfig().getRequest() ).isKeepAlive() );
		channel.socket().setReuseAddress( ( ( TcpRequestConfig ) getConfig().getRequest() ).isReuseAddress() );
		channel.socket().setTcpNoDelay( ( ( TcpRequestConfig ) getConfig().getRequest() ).isNoDelay() );
		
		Selector selector;
		
		if ( this.selector == null ) {
			this.selector = selector = Selector.open();
			executor.execute( this );
		} else {
			selector = this.selector;
		}
		SelectionKey key = channel.register( selector, getSelectionKey() );
		key.attach( createHandler( key ) );
	}
	
	public abstract int getSelectionKey();
	
	public abstract Handler createHandler( SelectionKey key );

	public ProtocolConfig getConfig() {
		return config;
	}

	public void setConfig(ProtocolConfig config) {
		this.config = config;
	}

}
