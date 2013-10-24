package net.musketeer.datasync.protocol.jms.model;

import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.config.RequestConfig;

@XmlRootElement( name = "request.jms" )
public class JmsRequestDefinition extends RequestConfig {

}
