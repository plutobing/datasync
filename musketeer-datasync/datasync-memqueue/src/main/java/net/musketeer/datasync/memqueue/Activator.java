/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.memqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.musketeer.datasync.component.GenericActivator;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 5, 2013
 */
public class Activator extends GenericActivator {
	
	private static Logger LOG = LoggerFactory.getLogger( Activator.class );

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.component.GenericActivator#start()
	 */
	@Override
	protected void start() throws Exception {
		MemoryQueueManager.getInstance().start();
		LOG.debug( "The component named [MemoryQueue] has been started." );
	}

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.component.GenericActivator#stop()
	 */
	@Override
	protected void stop() throws Exception {
		MemoryQueueManager.getInstance().stop();
	}

}
