package net.musketeer.datasync.protocol.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum( String.class )
public enum SideEnum {
	
	@XmlEnumValue( value = "server" )SERVER,
	
	@XmlEnumValue( value = "client" )CLIENT

}
