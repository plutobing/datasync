package net.musketeer.datasync.business.service;

public interface Handler {
	
	public < T > void handle( T t );

}
