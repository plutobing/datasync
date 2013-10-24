package net.musketeer.datasync.protocol.jms.model;

import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.config.ProtocolConfig;

@XmlRootElement( name = "protocol.jms" )
public class JmsProtocolDefinition extends ProtocolConfig {
}
