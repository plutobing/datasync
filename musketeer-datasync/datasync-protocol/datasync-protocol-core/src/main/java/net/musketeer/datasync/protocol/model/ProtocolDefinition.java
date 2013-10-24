package net.musketeer.datasync.protocol.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "protocol")
public abstract class ProtocolDefinition extends AbstractDefinition {
	@XmlAttribute
	protected String id;
	@XmlAttribute
	protected ModeEnum mode;
	@XmlAttribute
	protected SideEnum side;
	@XmlAttribute
	protected IODirection ioDirection;
	@XmlElementRef
	protected RequestDefinition request;
	@XmlElementRef
	protected ResponseDefinition response;
	@XmlElementRef
	protected CommonsDefinition commons;

	public RequestDefinition getRequest() {
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

	public void setRequest( RequestDefinition request ) {
		this.request = request;
	}

	public ResponseDefinition getResponse() {
		return response;
	}

	public void setResponse( ResponseDefinition response ) {
		this.response = response;
	}

	public CommonsDefinition getCommons() {
		return commons;
	}

	public void setCommons( CommonsDefinition commons ) {
		this.commons = commons;
	}

}
