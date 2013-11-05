/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.business;

import net.musketeer.datasync.component.GenericActivator;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 5, 2013
 */
public class Activator extends GenericActivator {

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.component.GenericActivator#start()
	 */
	@Override
	protected void start() throws Exception {
		SpringEngine.getInstance().start();
	}

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.component.GenericActivator#stop()
	 */
	@Override
	protected void stop() throws Exception {
		
	}

}
