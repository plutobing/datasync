package net.musketeer.datasync.protocol.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 通用配置类
 * @author pluto.bing.liu
 *
 */
@XmlType
public abstract class CommonsConfig extends AbstractDefinition {
	
	@XmlAttribute
	protected String charset;

	public String getCharset() {
		return charset;
	}

	public void setCharset( String charset ) {
		this.charset = charset;
	}
	
}
