package net.musketeer.datasync.resource;

public interface ResourceFilter {
	
	public < T, R > R doFilter( T t ) throws Exception;

}
