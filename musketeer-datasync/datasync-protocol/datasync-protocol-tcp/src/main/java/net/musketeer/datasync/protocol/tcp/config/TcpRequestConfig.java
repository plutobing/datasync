package net.musketeer.datasync.protocol.tcp.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.config.RequestConfig;

@XmlRootElement( name = "request" )
public class TcpRequestConfig extends RequestConfig {
	@XmlAttribute
	private boolean isBlock;
	@XmlAttribute
	private boolean keepAlive;
	@XmlAttribute
	private boolean reuseAddress;
	@XmlAttribute
	private boolean noDelay;
	@XmlAttribute
	private int backLog = 2000;
	
	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock( boolean isBlock ) {
		this.isBlock = isBlock;
	}

	/**
	 * @return the keepAlive
	 */
	public boolean isKeepAlive() {
		return keepAlive;
	}

	/**
	 * @param keepAlive the keepAlive to set
	 */
	public void setKeepAlive( boolean keepAlive ) {
		this.keepAlive = keepAlive;
	}

	/**
	 * @return the reuseAddress
	 */
	public boolean isReuseAddress() {
		return reuseAddress;
	}

	/**
	 * @param reuseAddress the reuseAddress to set
	 */
	public void setReuseAddress( boolean reuseAddress ) {
		this.reuseAddress = reuseAddress;
	}

	/**
	 * @return the noDelay
	 */
	public boolean isNoDelay() {
		return noDelay;
	}

	/**
	 * @param noDelay the noDelay to set
	 */
	public void setNoDelay( boolean noDelay ) {
		this.noDelay = noDelay;
	}

	public int getBackLog() {
		return backLog;
	}

	public void setBackLog( int backLog ) {
		this.backLog = backLog;
	}

}
