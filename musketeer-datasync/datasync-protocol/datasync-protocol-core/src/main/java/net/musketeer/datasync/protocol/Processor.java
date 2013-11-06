/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.protocol;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 6, 2013
 */
public interface Processor {

	public < T, R > R process( T t );

}
