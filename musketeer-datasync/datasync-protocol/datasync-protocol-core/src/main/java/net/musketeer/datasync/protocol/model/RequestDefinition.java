package net.musketeer.datasync.protocol.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * 请求配置抽象基类
 * <p>
 * 所有不同协议的请求配置都继承该类
 * </p>
 * @author pluto.bing.liu
 *
 */
@XmlType( name = "request" )
public abstract class RequestDefinition extends AbstractAttributeDefinition {
	
	protected List< ParamDefinition > params = new LinkedList< ParamDefinition >();

	public List< ParamDefinition > getParams() {
		return params;
	}

	public void setParams( List< ParamDefinition > params ) {
		this.params = params;
	}

}
