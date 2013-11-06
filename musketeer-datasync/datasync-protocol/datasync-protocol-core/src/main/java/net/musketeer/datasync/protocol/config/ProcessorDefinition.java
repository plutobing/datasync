/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.protocol.config;

import net.musketeer.datasync.protocol.Processor;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 6, 2013
 */
public abstract class ProcessorDefinition extends AbstractDefinition {

	protected abstract Processor getProcessor();
	
}
