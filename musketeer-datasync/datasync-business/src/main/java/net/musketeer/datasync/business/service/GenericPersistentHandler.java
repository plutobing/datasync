package net.musketeer.datasync.business.service;

import java.util.Map;

import net.musketeer.datasync.business.dao.GenericDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericPersistentHandler implements Handler {

	private static Logger LOG = LoggerFactory.getLogger( GenericPersistentHandler.class );

	private GenericDao dao;

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > void handle( T t ) {
		if ( ! ( t instanceof Map ) ) {
			throw new IllegalArgumentException( "The argument must be an instance of java.util.Map" );
		}
		final Map< String, Object > pm = ( Map< String, Object > ) t;
		try {
			getDao().save( pm );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
		}
	}

	public GenericDao getDao() {
		return dao;
	}

	public void setDao( GenericDao dao ) {
		this.dao = dao;
	}

}
