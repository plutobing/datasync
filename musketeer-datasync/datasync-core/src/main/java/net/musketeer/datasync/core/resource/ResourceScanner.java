package net.musketeer.datasync.core.resource;

public interface ResourceScanner {
	
	public < R > void scan( R resource, ResourceFilter filter );

}
