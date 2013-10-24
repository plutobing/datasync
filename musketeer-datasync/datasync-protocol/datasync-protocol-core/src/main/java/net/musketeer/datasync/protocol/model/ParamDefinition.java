package net.musketeer.datasync.protocol.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "param" )
public class ParamDefinition extends AbstractDefinition {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String value;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

}
