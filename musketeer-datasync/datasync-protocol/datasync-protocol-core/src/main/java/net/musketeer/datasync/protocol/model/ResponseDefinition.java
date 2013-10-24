package net.musketeer.datasync.protocol.model;

import javax.xml.bind.annotation.XmlType;

/**
 * 应答配置抽象基类
 * <p>
 * 所有协议的应答配置都继承该类
 * </p>
 * @author pluto.bing.liu
 *
 */
@XmlType( name = "response" )
public abstract class ResponseDefinition extends AbstractAttributeDefinition {

}
