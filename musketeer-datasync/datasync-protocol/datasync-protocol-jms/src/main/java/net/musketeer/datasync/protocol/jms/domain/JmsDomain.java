package net.musketeer.datasync.protocol.jms.domain;

import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import net.musketeer.datasync.protocol.jms.TypeEnum;

public abstract class JmsDomain {
	
	protected String contextFactory;
	
	protected String providerUrl;
	
	protected String destination;
	
	protected String charset;
	
	protected TypeEnum type;
	
	private Context context;
	
	private Connection connection;

	private Session session;

	public JmsDomain( String contextFactory, String providerUrl,
			String destination, String charset, TypeEnum type ) {
		super();
		this.contextFactory = contextFactory;
		this.providerUrl = providerUrl;
		this.destination = destination;
		this.charset = charset;
		this.type = type;
	}
	
	public void init() throws Exception {
		final Properties env = new Properties();
		env.put( Context.INITIAL_CONTEXT_FACTORY, getContextFactory() );
		env.put( Context.PROVIDER_URL, getProviderUrl() );
		
		this.context = new InitialContext( env );
		this.connection = ( ( ConnectionFactory ) this.context.lookup( "ConnectionFactory" ) ).createConnection();
		connection.start();
		this.session = this.connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
		
		init( session );
	}

	protected abstract void init( Session session ) throws Exception;
	
	public void destroy() throws Exception {
		this.session.close();
		this.connection.close();
		this.context.close();
	}

	public String getContextFactory() {
		return contextFactory;
	}

	public void setContextFactory( String contextFactory ) {
		this.contextFactory = contextFactory;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl( String providerUrl ) {
		this.providerUrl = providerUrl;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination( String destination ) {
		this.destination = destination;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset( String charset ) {
		this.charset = charset;
	}

	public TypeEnum getType() {
		return type;
	}

	public void setType( TypeEnum type ) {
		this.type = type;
	}

	protected StreamMessage createStreamMessage() throws JMSException {
		return this.session.createStreamMessage();
	}
	
	protected TextMessage createTextMessage() throws JMSException {
		return this.session.createTextMessage();
	}
	
	protected MapMessage createMapMessage() throws JMSException {
		return this.session.createMapMessage();
	}
	
	protected ObjectMessage createObjectMessage() throws Exception {
		return this.session.createObjectMessage();
	}

	protected Message createMessage() throws JMSException {
		return this.session.createMessage();
	}
	
	protected BytesMessage createBytesMessage() throws JMSException {
		return this.session.createBytesMessage();
	}
	
}
