/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.protocol.jms.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.config.RequestConfig;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 5, 2013
 */
@XmlRootElement( name = "request" )
public class JmsRequestConfig extends RequestConfig {
	@XmlAttribute
	private String contextFactory;
	@XmlAttribute
	private String destination;
	@XmlAttribute
	private TypeEnum type;
	@XmlAttribute
	private int sessionCount = 1;

	/**
	 * @return the contextFactory
	 */
	public String getContextFactory() {
		return contextFactory;
	}
	/**
	 * @param contextFactory the contextFactory to set
	 */
	public void setContextFactory( String contextFactory ) {
		this.contextFactory = contextFactory;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination( String destination ) {
		this.destination = destination;
	}
	/**
	 * @return the type
	 */
	public TypeEnum getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType( TypeEnum type ) {
		this.type = type;
	}
	/**
	 * @return the sessionCount
	 */
	public int getSessionCount() {
		return sessionCount;
	}
	/**
	 * @param sessionCount the sessionCount to set
	 */
	public void setSessionCount( int sessionCount ) {
		this.sessionCount = sessionCount;
	}

}
