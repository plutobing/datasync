package net.musketeer.datasync.core.resource;

public interface ResourceFilter {
	
	public < R > void doFilter( R r ) throws Exception;

}
