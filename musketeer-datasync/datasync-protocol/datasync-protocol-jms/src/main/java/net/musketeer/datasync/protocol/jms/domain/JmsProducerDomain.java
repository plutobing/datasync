package net.musketeer.datasync.protocol.jms.domain;

import javax.jms.BytesMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import net.musketeer.datasync.protocol.jms.NoSuchTypeException;
import net.musketeer.datasync.protocol.jms.TypeEnum;

public class JmsProducerDomain extends JmsDomain {
		
	public JmsProducerDomain( String contextFactory, String providerUrl,
			String destination, String charset, TypeEnum type ) {
		super( contextFactory, providerUrl, destination, charset, type );
	}

	private MessageProducer producer;

	@Override
	protected void init( Session session ) throws Exception {
		if ( getType().equals( TypeEnum.QUEUE ) ) {
			this.producer = session.createProducer( session.createQueue( getDestination() ) );
		} else if ( getType().equals( TypeEnum.TOPIC ) ) {
			this.producer = session.createProducer( session.createTopic( getDestination() ) ); 
		} else {
			throw new NoSuchTypeException( "No such type defined [" + getType() + "]" );
		}
	}

	public < T > void send( T t ) throws Exception {
		if ( t != null && t instanceof byte[] ) {
			final BytesMessage message = super.createBytesMessage();
			message.writeBytes( ( byte[] ) t );
			this.producer.send( message );
		}
	}
	
	public void destroy() throws Exception {
		this.producer.close();
		super.destroy();
	}

}
