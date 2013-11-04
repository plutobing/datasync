package net.musketeer.datasync.protocol.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import net.musketeer.datasync.protocol.config.ProtocolConfig;
import net.musketeer.datasync.protocol.tcp.AbstractTcpConnector;
import net.musketeer.datasync.protocol.tcp.config.TcpRequestConfig;
import net.musketeer.datasync.protocol.tcp.nio.event.Handler;
import net.musketeer.datasync.protocol.tcp.nio.worker.NioServerWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServerConnector extends AbstractTcpConnector {

	private static Logger LOG = LoggerFactory.getLogger( TcpServerConnector.class );
		
	private ProtocolConfig definition;

	private ServerSocketChannel ssc;
	
	private Selector selector;
		
	private ThreadPoolExecutor pool;
	
	private NioWorker worker;
	
	private ExecutorService starter;
	
	public TcpServerConnector( ProtocolConfig config ) {
		super( config );
	}
	
	protected boolean init() {
		//Create Thread Pool
		this.pool = ( ThreadPoolExecutor ) Executors.newFixedThreadPool( ( ( TcpRequestConfig ) ( getDefinition().getRequest() ) ).getBackLog(), new ThreadFactory() {
			
			private int count = 0;
			
			private ThreadGroup group = new ThreadGroup( "Protocol-Tcp-Server" );
			
			@Override
			public Thread newThread( Runnable r ) {
				return new Thread( group, r, 
						new StringBuffer( group.getName() )
							.append( "-" )
							.append( count++ ).toString() );
			}
			
		} );
		
		boolean completed = false;
		try {
			final URI uri = URI.create( getDefinition().getCommons().getUri() );
			InetSocketAddress address = new InetSocketAddress( uri.getHost(), uri.getPort() );
			this.ssc = ServerSocketChannel.open();
			this.ssc.configureBlocking( ( ( TcpRequestConfig ) getDefinition().getRequest() ).isBlock() );
			ServerSocket serverSocket = this.ssc.socket();
			serverSocket.bind( address, ( ( TcpRequestConfig ) getDefinition().getRequest() ).getBackLog() );
			serverSocket.setReuseAddress( true );
			this.selector = Selector.open();
			this.ssc.register( this.selector, SelectionKey.OP_ACCEPT, new Acceptor() );
			completed = true;
			
			worker = new NioServerWorker( this.pool, definition );
		} catch ( IOException e ) {
			e.printStackTrace();
			completed = false;
		}
		return completed;
	}

	public void run() {

//		Executors.newSingleThreadExecutor().execute( worker );
	}
	
	class SelectorManager implements Runnable {

		public void run() {
			int ops = 0;
			while ( true ) {
				try {
					ops = selector.select();
					
					if ( ops > 0 ) {
						final Set< SelectionKey > selectedKeys = selector.selectedKeys();
						final Iterator< SelectionKey > keysIter = selectedKeys.iterator();
						SelectionKey key = null;
						while ( keysIter.hasNext() ) {
							key = keysIter.next();
							dispatch( key );
							keysIter.remove();
						}
					}
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
		
		private void dispatch( SelectionKey key ) {
			if ( ! key.isValid() ) {
				key.cancel();
			}
			Channel channel = key.channel();
			if ( channel.isOpen() ) {
				Handler r = ( Handler ) key.attachment();
				System.out.println( r.getClass().getName() );
				if ( r != null ) {
					r.handle();
				}
			} else {
				key.cancel();
				System.out.println( "================The socket is closed.====================" );
			}
			
		}
		
	}
	
	class Acceptor implements Handler {

		public void handle() {
			try {
				SocketChannel channel = ssc.accept();
				if ( channel != null ) {
					worker.register( channel );
				}
				selector.wakeup();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}

	}

	public ProtocolConfig getDefinition() {
		return definition;
	}

	@Override
	public < T, R > R received( T t ) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public < T, R > R send( T t ) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws Exception {
		start : {
			if ( isRunning.get() ) {
				LOG.warn( "The instance of Tcp Server has been started." );
				break start;
			}
			starter = Executors.newSingleThreadExecutor( new ThreadFactory() {

				@Override
				public Thread newThread( Runnable r ) {
					return new Thread( r, "Protocol-Tcp-Server-Starter" );
				}
				
			} );
			starter.execute( new SelectorManager() );
		}
	}

	@Override
	public void stop() throws Exception {
		if ( ! isRunning.get() ) {
			LOG.warn( "The instance of Tcp Server has been stopped." );
			return ;
		}
		starter.shutdown();
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
