package net.musketeer.datasync.protocol.tcp.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.model.RequestDefinition;

@XmlRootElement( name = "request.tcp" )
public class TcpRequestDefinition extends RequestDefinition {
	@XmlAttribute
	private int backLog;

	public int getBackLog() {
		return backLog;
	}

	public void setBackLog( int backLog ) {
		this.backLog = backLog;
	}

}
