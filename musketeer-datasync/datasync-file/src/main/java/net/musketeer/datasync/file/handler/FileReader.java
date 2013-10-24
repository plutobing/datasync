package net.musketeer.datasync.file.handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.musketeer.datasync.memqueue.MemQueueProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件读取类
 * <p></p>
 * @author liubing
 * Date 2013-10-22
 */
public class FileReader implements Runnable {
	
	private static Logger LOG = LoggerFactory.getLogger( FileReader.class );
	
	private MemQueueProducer producer = new MemQueueProducer( "original-data-queue" );

	private String filePath;
		
	public FileReader( String filePath ) throws Exception {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		final File file = new File( filePath );
		if ( ! file.exists() ) {
			return ;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream( file );
			read( fis );
		} catch ( FileNotFoundException e ) {
			LOG.error( e.getMessage(), e );
		} catch ( IOException e ) {
			LOG.error( e.getMessage(), e );
		} finally {
			if ( fis != null ) {
				try {
					fis.close();
				} catch ( IOException e ) {
				}
				fis = null;
			}
		}
	}
	
	private void read( InputStream is ) throws IOException {
		final ByteArrayOutputStream readBuff = new ByteArrayOutputStream();
		int n = 0;
		boolean readHeader = true;
		int length = 0;
		final int init_read_len = 4;
		byte[] tempBytes = new byte[ init_read_len ];
		while ( ( n = is.read( tempBytes ) ) != -1 ) {
			if ( n == 0 ) {
				continue ;
			}
			readBuff.write( tempBytes, 0, n );
			if ( readBuff.size() < init_read_len ) {
				continue ;
			} else if ( readHeader ) {
				final byte[] currentBytes = readBuff.toByteArray();
				length = Integer.parseInt( new String( currentBytes, 0, 4 ), 10 );
				readHeader = false;
				tempBytes = new byte[ length ];
			} else {
				if ( ( readBuff.size() - init_read_len ) < length ) {
					continue ;
				}
				final byte[] message = readBuff.toByteArray();
				LOG.info( "Current receive message is " + new String( message ) );
				producer.produce( cutOut( message, length ) );
				readBuff.reset();
				readHeader = true;
				tempBytes = new byte[ init_read_len ];
			}
		}
	}
	
	private byte[] cutOut( byte[] bytes, int len ) {
		final byte[] dstBytes = new byte[ len ];
		System.arraycopy( bytes, 4, dstBytes, 0, len );
		return dstBytes;
	}

}
