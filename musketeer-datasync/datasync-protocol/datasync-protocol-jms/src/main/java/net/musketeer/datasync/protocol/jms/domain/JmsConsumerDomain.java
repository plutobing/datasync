package net.musketeer.datasync.protocol.jms.domain;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import net.musketeer.datasync.memqueue.MemQueueProducer;
import net.musketeer.datasync.protocol.jms.NoSuchTypeException;
import net.musketeer.datasync.protocol.jms.config.TypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsConsumerDomain extends JmsDomain {
	
	private static Logger LOG = LoggerFactory.getLogger( JmsConsumerDomain.class );

	public JmsConsumerDomain( String contextFactory, String providerUrl,
			String destination, String charset, TypeEnum type ) {
		super( contextFactory, providerUrl, destination, charset, type );
	}

	private MessageConsumer consumer;

	@Override
	protected void init( Session session ) throws Exception {
		if ( getType().equals( TypeEnum.QUEUE ) ) {
			this.consumer = session.createConsumer( session.createQueue( getDestination() ) );
		} else if ( getType().equals( TypeEnum.TOPIC ) ) {
			this.consumer = session.createConsumer( session.createTopic( getDestination() ) ); 
		} else {
			throw new NoSuchTypeException( "No such type defined [" + getType() + "]" );
		}
		this.consumer.setMessageListener( new MessageListener() {
			
			private MemQueueProducer producer = new MemQueueProducer( "jms_consumer" );

			public void onMessage( Message message ) {
				if ( message instanceof BytesMessage ) {
					try {
						final byte[] bytes = new byte[ ( int ) ( ( BytesMessage ) message ).getBodyLength() ];
						( ( BytesMessage ) message ).readBytes( bytes );
						if ( LOG.isDebugEnabled() ) {
							LOG.debug( "Current received message is [\n" + new String( bytes ) + "\n]." );
						}
						producer.produce( bytes );
					} catch ( JMSException e ) {
						e.printStackTrace();
					}
				}
			}
			
		} );
	}

}
