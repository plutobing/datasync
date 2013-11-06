/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.protocol.tcp.config;

import net.musketeer.datasync.protocol.Processor;
import net.musketeer.datasync.protocol.config.IoStrategyConfig;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 6, 2013
 */
public class TcpIoStrategyConfig extends IoStrategyConfig {

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.config.ProcessorDefinition#getProcessor()
	 */
	@Override
	protected Processor getProcessor() {
		return null;
	}

}
