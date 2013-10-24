package net.musketeer.datasync.file;

import java.io.File;

import javax.xml.bind.JAXBContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.musketeer.datasync.core.conf.EnvConf;
import net.musketeer.datasync.file.joran.ConfigurationDefinition;

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
						new File( EnvConf.getInstance().getConfPath(),
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