package net.musketeer.datasync.protocol.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum( String.class )
public enum ModeEnum {

	@XmlEnumValue( value = "async" ) ASYNC,
	
	@XmlEnumValue( value = "sync" ) SYNC
	
}
