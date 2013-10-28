package net.musketeer.datasync.protocol.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "protocol")
public abstract class ProtocolConfig extends AbstractDefinition {
	@XmlAttribute
	protected String id;
	@XmlAttribute
	protected ModeEnum mode;
	@XmlAttribute
	protected SideEnum side;
	@XmlAttribute
	protected IODirection ioDirection;
	@XmlElementRef
	protected RequestConfig request;
	@XmlElementRef
	protected ResponseConfig response;
	@XmlElementRef
	protected CommonsConfig commons;

	public RequestConfig getRequest() {
		return request;
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public ModeEnum getMode() {
		return mode;
	}

	public void setMode( ModeEnum mode ) {
		this.mode = mode;
	}

	public SideEnum getSide() {
		return side;
	}

	public void setSide( SideEnum side ) {
		this.side = side;
	}

	public IODirection getIoDirection() {
		return ioDirection;
	}

	public void setIoDirection( IODirection ioDirection ) {
		this.ioDirection = ioDirection;
	}

	public void setRequest( RequestConfig request ) {
		this.request = request;
	}

	public ResponseConfig getResponse() {
		return response;
	}

	public void setResponse( ResponseConfig response ) {
		this.response = response;
	}

	public CommonsConfig getCommons() {
		return commons;
	}

	public void setCommons( CommonsConfig commons ) {
		this.commons = commons;
	}

}
