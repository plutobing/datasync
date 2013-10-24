package net.musketeer.datasync.core.resource;

import java.util.LinkedList;
import java.util.List;

public class ResourceFilterChain implements ResourceFilter {

	private List< ResourceFilter > filters = new LinkedList< ResourceFilter >();

	public < R > void doFilter( R r ) throws Exception {
		for ( ResourceFilter filter : filters ) {
			filter.doFilter( r );
		}
	}

}
