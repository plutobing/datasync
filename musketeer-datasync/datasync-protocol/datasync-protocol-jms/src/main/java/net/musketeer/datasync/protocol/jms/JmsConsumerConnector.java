package net.musketeer.datasync.protocol.jms;

import java.util.ArrayList;
import java.util.List;

import net.musketeer.datasync.protocol.jms.config.JmsCommonsConfig;
import net.musketeer.datasync.protocol.jms.config.JmsRequestConfig;
import net.musketeer.datasync.protocol.jms.domain.JmsConsumerDomain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JmsConsumerConnector extends AbstractJmsConnector {
	
	private static Logger LOG = LoggerFactory.getLogger( JmsConsumerConnector.class );
	
	private List< JmsConsumerDomain > agents;

	public < T, R > R received( T t ) throws Exception {
		return null;
	}

	public < T, R > R send( T t ) throws Exception {
		return null;
	}

	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			LOG.warn( "The instance named [" + getConfig().getName() + "] has been started." );
			return ;
		}
		if ( agents == null || agents.size() == 0 ) {
			final JmsRequestConfig request = ( JmsRequestConfig ) getConfig().getRequest();
			final JmsCommonsConfig commons = ( JmsCommonsConfig ) getConfig().getCommons();
			int sessionCount = request.getSessionCount() == 0 ? 1 : request.getSessionCount();
			agents = new ArrayList< JmsConsumerDomain >( sessionCount );
			for ( int i = 0; i < sessionCount; i++ ) {
				JmsConsumerDomain agent = new JmsConsumerDomain( request.getContextFactory(), 
					request.getProviderUrl(), request.getDestination(), 
					commons.getCharset(), request.getType() );
				try {
					agent.init();
				} catch ( Exception e ) {
					LOG.error( e.getMessage(), e );
					continue ;
				}
				agents.add( agent );
			}
		}
		running.set( true );
		LOG.info( "The instance of JmsConsumer named [" + getConfig().getName() + "] started successfully." );
	}

	public synchronized void stop() throws Exception {
		if ( isShutdown() ) {
			LOG.warn( "The instance named [" + getConfig().getName() + "] has been stopped." );
			return ;
		}
		stopped : {
			if ( agents == null || agents.size() == 0 ) {
				break stopped;
			}
			for ( int i = 0; i < agents.size(); i++ ) {
				JmsConsumerDomain agent = agents.remove( i );
				agent.destroy();
			}
		}
		running.set( false );
		LOG.info( "The instance of JmsConsumer named [" + getConfig().getName() + "] stopped successfully." );
	}

}
