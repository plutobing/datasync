/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.musketeer.datasync.protocol.tcp;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.musketeer.datasync.protocol.ProtocolConfigProcessor;
import net.musketeer.datasync.protocol.config.ProtocolConfig;
import net.musketeer.datasync.protocol.tcp.config.TcpProtocolConfig;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 5, 2013
 */
public class TcpConfigProcessor implements ProtocolConfigProcessor {

private static final String JAXB_CONTEXT;
	
	static {
		final StringBuilder sb = new StringBuilder( 
				ProtocolConfig.class.getPackage().getName() );
		sb.append( ":" ).append( TcpProtocolConfig.class.getPackage().getName() );
		JAXB_CONTEXT = sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.ProtocolConfigProcessor#read(java.io.InputStream)
	 */
	@Override
	public ProtocolConfig read( InputStream is ) throws Exception {
		if ( is == null ) {
			throw new IllegalArgumentException( "The input stream can not be null." );
		}
		final JAXBContext context = JAXBContext.newInstance( JAXB_CONTEXT, 
				this.getClass().getClassLoader() );
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final ProtocolConfig config = ( TcpProtocolConfig ) unmarshaller.unmarshal( is );
		return config;
	}

	/* (non-Javadoc)
	 * @see net.musketeer.datasync.protocol.ProtocolConfigProcessor#write(net.musketeer.datasync.protocol.config.ProtocolConfig, java.io.OutputStream)
	 */
	@Override
	public void write( ProtocolConfig config, OutputStream os )
			throws Exception {
		if ( config == null ) {
			throw new IllegalArgumentException( "The config can not be null." );
		}
		final JAXBContext context = JAXBContext.newInstance( JAXB_CONTEXT, 
				this.getClass().getClassLoader() );
		final Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( config, os );
	}

}
