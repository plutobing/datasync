package net.musketeer.datasync.protocol;

import net.musketeer.datasync.protocol.config.ProtocolConfig;

/**
 * 
 * @author pluto.bing.liu
 *
 */
public interface Connection {
		
	public boolean isShutdown();
	
	public boolean isStartup();
	
	public < T, R > R received( T t ) throws Exception;
	
	public < T, R > R send( T t ) throws Exception;
	
	public void start() throws Exception;
	
	public void stop() throws Exception;
	
	public ProtocolConfig getConfig();
	
}
