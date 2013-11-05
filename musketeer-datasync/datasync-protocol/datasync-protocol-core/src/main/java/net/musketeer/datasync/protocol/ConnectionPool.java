package net.musketeer.datasync.protocol;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import net.musketeer.datasync.protocol.config.ProtocolConfig;

public class ConnectionPool {
	
	private int capacity = 10;
	
	private AtomicInteger currentSize = new AtomicInteger( 0 );
	
	private String connectionClass;
	
	private int maxWaitTime = 5000;
	
	private ProtocolConfig config;
	
	private BlockingQueue< Connection > pool;
	
	public ConnectionPool() {
		super();
	}
	
	public ConnectionPool( int capacity ) {
		super();
		this.capacity = capacity;
		init();
	}
	
	public ConnectionPool( int capacity, String connectionClass, ProtocolConfig config ) {
		super();
		this.capacity = capacity;
		this.connectionClass = connectionClass;
		this.config = config;
		init();
	}
	
	private void init() {
		pool = new LinkedBlockingQueue< Connection >( getCapacity() );
	}
	
	public Connection getConnection() throws Exception {
		Connection connection = pool.poll( getMaxWaitTime(), TimeUnit.MILLISECONDS );
		if ( connection == null ) {
			if ( currentSize.get() < capacity ) {
				try {
					connection = ( Connection ) Class.forName( getConnectionClass() ).newInstance();
				} catch ( Exception e ) {
					throw e;
				}
				pool.add( connection );
				currentSize.incrementAndGet();
			} else {
				throw new NoConnectionException( "Can not get the instance of Connection in the pool." );
			}
		}
		return connection;
	}
	
	public void release( Connection connection ) throws Exception {
		if ( connection == null ) {
			throw new IllegalArgumentException( "The connection can not be null." );
		}
		synchronized ( this ) {
			if ( deep() == capacity ) {
				connection.stop();
				connection = null;
			} else {
				pool.put( connection );
			}
		}
	}
	
	public int deep() {
		return pool.size();
	}
	
	public void reallyRelease() {
		synchronized ( this ) {
			final List< Connection > connections = new LinkedList< Connection >();
			pool.removeAll( connections );
			for ( Connection connection : connections ) {
				try {
					connection.stop();
				} catch ( Exception e ) {
				}
			}
			connections.clear();
		}
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public String getConnectionClass() {
		return connectionClass;
	}

	public void setConnectionClass( String connectionClass ) {
		this.connectionClass = connectionClass;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime( int maxWaitTime ) {
		this.maxWaitTime = maxWaitTime;
	}

	public void setCapacity( int capacity ) {
		this.capacity = capacity;
	}

	/**
	 * @return the config
	 */
	public ProtocolConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig( ProtocolConfig config ) {
		this.config = config;
	}

}
