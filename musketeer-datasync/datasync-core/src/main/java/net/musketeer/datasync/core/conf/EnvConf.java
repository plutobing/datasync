package net.musketeer.datasync.core.conf;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import net.musketeer.datasync.core.resource.impl.AbstractFileFilter;
import net.musketeer.datasync.core.resource.impl.FileResourceScanner;

public class EnvConf {

	private static volatile EnvConf _instance;
	
	private static final String HOME_PATH;
	
	private static final String CONF_PATH;
	
	private static final String LIB_PATH;
	
	private static final String LOGS_PATH;
	
	static {
		String baseHome = System.getProperty( "base.home" );;
		if ( baseHome == null || "".equals( baseHome ) ) {
			final URL url = EnvConf.class.getProtectionDomain().getCodeSource().getLocation();
			try {
				final File location = new File( url.toURI() );
				baseHome = location.getParentFile().getAbsolutePath();
			} catch ( URISyntaxException e ) {
				e.printStackTrace();
			}
		}
		HOME_PATH = baseHome;
		CONF_PATH = new StringBuffer( HOME_PATH ).append( "/conf/" ).toString();
		LIB_PATH = new StringBuffer( HOME_PATH ).append( "/lib/" ).toString();
		LOGS_PATH = new StringBuffer( HOME_PATH ).append( "/logs/" ).toString();
	}
	
	private EnvConf() {
	}
	
	public static EnvConf getInstance() {
		if ( _instance == null ) {
			synchronized ( EnvConf.class ) {
				if ( _instance == null ) {
					_instance = new EnvConf();
				}
			}
		}
		return _instance;
	}
	
	public URL[] getLib() {
		final File libDir = new File( LIB_PATH );
		
		final AbstractFileFilter< URL[] > filter = new AbstractFileFilter< URL[] >() {
			
			private List< URL > urls = new LinkedList< URL >();

			protected void filter( File file ) {
				if ( file.getName().endsWith( ".jar" ) ) {
					try {
						urls.add( file.toURI().toURL() );
					} catch ( MalformedURLException e ) {
						e.printStackTrace();
					}
				}
			}

			public URL[] getResult() {
				final URL[] urlArray = new URL[ urls.size() ];
				urls.toArray( urlArray );
				return urlArray;
			}
			
		};
		final FileResourceScanner scanner = new FileResourceScanner();
		scanner.scan( libDir, filter );
		return filter.getResult();
	}
	
	public String getHomePath() {
		return HOME_PATH;
	}

	public String getConfPath() {
		return CONF_PATH;
	}

	public String getLibPath() {
		return LIB_PATH;
	}

	public String getLogsPath() {
		return LOGS_PATH;
	}

}
