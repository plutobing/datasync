package net.musketeer.datasync.protocol.jms;

import net.musketeer.datasync.protocol.jms.config.JmsCommonsConfig;
import net.musketeer.datasync.protocol.jms.config.JmsRequestConfig;
import net.musketeer.datasync.protocol.jms.domain.JmsProducerDomain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsProducerConnector extends AbstractJmsConnector {
	
	private static Logger LOG = LoggerFactory.getLogger( JmsProducerConnector.class );
	
	private JmsProducerDomain proxy;
	
	public JmsProducerConnector() {
	}

	public < T, R > R received( T t ) throws Exception {
		return null;
	}

	public < T, R > R send( T t ) throws Exception {
		proxy.send( t );
		return null;
	}

	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			LOG.warn( "The instance named [" + getConfig().getName() + "] has been started." );
			return ;
		}
		if ( proxy == null ) {
			final JmsRequestConfig request = ( JmsRequestConfig ) getConfig().getRequest();
			final JmsCommonsConfig commons = ( JmsCommonsConfig ) getConfig().getCommons();
			proxy = new JmsProducerDomain( request.getContextFactory(), 
					request.getProviderUrl(), request.getDestination(), 
					commons.getCharset(), request.getType() );
		}
		proxy.init();
		running.set( true );
		LOG.info( "The instance of JmsProviderConnector named [" + getConfig().getName() + "] started successfully." );
	}

	public synchronized void stop() throws Exception {
		if ( isShutdown() ) {
			LOG.warn( "The instance named [" + getConfig().getName() + "] has been stopped." );
			return ;
		}
		stopped : {
			if ( proxy == null ) {
				break stopped;
			}
			proxy.destroy();
		}
		running.set( false );
		LOG.info( "The instance of JmsProviderConnector named [" + getConfig().getName() + "] stopped successfully." );
	}

}
