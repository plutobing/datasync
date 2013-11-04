/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.protocol.jms.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 4, 2013
 */
@XmlEnum( String.class )
public enum TypeEnum {
	@XmlEnumValue( value = "queue" )
	QUEUE,
	@XmlEnumValue( value = "topic" )
	TOPIC
	
}
