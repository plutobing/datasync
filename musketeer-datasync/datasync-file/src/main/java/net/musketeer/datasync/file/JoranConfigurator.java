package net.musketeer.datasync.file;

import java.io.File;

import javax.xml.bind.JAXBContext;

import net.musketeer.datasync.Env;
import net.musketeer.datasync.file.joran.ConfigurationDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoranConfigurator {
	private static Logger LOG = LoggerFactory
			.getLogger( JoranConfigurator.class );
	private static volatile JoranConfigurator instance;
	private ConfigurationDefinition configuration;

	public static JoranConfigurator getInstance() {
		if ( instance == null ) {
			synchronized ( JoranConfigurator.class ) {
				if ( instance == null ) {
					instance = new JoranConfigurator();
				}
			}
		}

		return instance;
	}

	public void init() throws Exception {
		LOG.info( "The FILE-CONFIG initializing..." );
		JAXBContext context = JAXBContext
				.newInstance( new Class[] { ConfigurationDefinition.class } );
		this.configuration = ( ( ConfigurationDefinition ) context
				.createUnmarshaller().unmarshal(
						new File( Env.getInstance().getConfPath(),
								"file/configuration.xml" ) ) );
		LOG.info( "The FILE-CONFIG initialized." );
	}

	public ConfigurationDefinition getConfiguration() {
		return this.configuration;
	}

	public static void main( String[] args ) throws Exception {
		getInstance().init();
	}
}