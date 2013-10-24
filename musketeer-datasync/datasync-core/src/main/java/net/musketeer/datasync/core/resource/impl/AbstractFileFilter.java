package net.musketeer.datasync.core.resource.impl;

import java.io.File;

import net.musketeer.datasync.core.resource.ResourceFilter;

public abstract class AbstractFileFilter< T > implements ResourceFilter {

	public < R > void doFilter( R r ) throws Exception {
		if ( ! ( r instanceof File ) ) {
			throw new IllegalArgumentException( "The argument must be an instance of java.io.File." );
		}
		final File file = ( File ) r;
		filter( file );
	}
	
	protected abstract void filter( File file );
	
	public abstract T getResult();

}
