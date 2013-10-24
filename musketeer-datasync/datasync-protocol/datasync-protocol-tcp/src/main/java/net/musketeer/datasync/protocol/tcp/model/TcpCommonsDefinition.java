package net.musketeer.datasync.protocol.tcp.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.model.CommonsDefinition;

@XmlRootElement( name = "commons.tcp" )
public class TcpCommonsDefinition extends CommonsDefinition {
	@XmlAttribute
	private boolean isBlock;

	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock( boolean isBlock ) {
		this.isBlock = isBlock;
	}

}
