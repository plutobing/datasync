package net.musketeer.datasync.protocol.tcp.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.config.RequestConfig;

@XmlRootElement( name = "request.tcp" )
public class TcpRequestDefinition extends RequestConfig {
	@XmlAttribute
	private int backLog;

	public int getBackLog() {
		return backLog;
	}

	public void setBackLog( int backLog ) {
		this.backLog = backLog;
	}

}
