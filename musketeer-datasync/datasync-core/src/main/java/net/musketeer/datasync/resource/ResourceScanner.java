package net.musketeer.datasync.resource;

public interface ResourceScanner {
	
	public < R > void scan( R resource, ResourceFilter filter );

}
