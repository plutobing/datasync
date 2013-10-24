package net.musketeer.datasync.protocol.tcp.model;

import javax.xml.bind.annotation.XmlRootElement;

import net.musketeer.datasync.protocol.config.ProtocolConfig;

@XmlRootElement( name = "protocol.tcp" )
public class TcpProtocolDefinition extends ProtocolConfig {
}