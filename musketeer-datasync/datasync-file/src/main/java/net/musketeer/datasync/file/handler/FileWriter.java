package net.musketeer.datasync.file.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 文件写入了类
 *
 * <p></p>
 * @author liubing
 * Date 2013-10-22
 */
public class FileWriter {
	
	private AtomicLong index = new AtomicLong( System.currentTimeMillis() );
	
	private Object lock = new Object();
	
	private String filePath;
	
	private File file;
	
	private FileOutputStream outputStream;
	
	public FileWriter( String filePath ) throws Exception {
		this.filePath = filePath;
		init();
	}

	private void init() throws Exception {
		this.file = new File( filePath );
		if ( !( this.file.getParentFile().exists() ) )
			this.file.getParentFile().mkdirs();

		if ( !( this.file.exists() ) )
			try {
				this.file.createNewFile();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		try {
			this.outputStream = new FileOutputStream( this.file, true );
		} catch ( FileNotFoundException e ) {
			throw e;
		}
	}

	public void write( byte[] message ) throws IOException {
		synchronized ( this.lock ) {
			long curCapacity = this.file.length();
			long totalCapacity = curCapacity + message.length;
			if ( totalCapacity > 1048576L ) {
				// ByteBuffer curMsgBuffer = ByteBuffer.wrap(message, 0,
				// (int)(1048576L - curCapacity));
				// safeWrite(getOutputStream(), curMsgBuffer);
				renameFileByCopy( getFile().getAbsolutePath(), getFile()
						.getAbsolutePath() + "." + this.index.getAndAdd( 1 ) );
				// int otherCount = (int)(1048576L - curCapacity);
				// if (otherCount > 0) {
				// byte[] otherBytes = new byte[message.length - otherCount];
				// System.arraycopy(message, otherCount, otherBytes, 0,
				// otherBytes.length);
				// message = otherBytes;
				// }
			}
			safeWrite( getOutputStream(), ByteBuffer.wrap( message ) );
		}
	}

	protected void renameFileByCopy( String src, String dst )
			throws IOException {
		int n;
		if ( src.equals( dst ) )
			throw new RuntimeException( "Can not copy the same name file." );

		closeOutputStream();
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(
				src ) );
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream( dst, true ) );
		byte[] readBuffer = new byte[ 16384 ];

		while ( ( n = bis.read( readBuffer ) ) != -1 )
			bos.write( readBuffer, 0, n );

		bos.close();
		bos = null;
		bis.close();
		bis = null;
		File srcFile = new File( src );
		if ( !( srcFile.delete() ) )
			throw new RuntimeException( "Can not delete file named " + src );

		setOutputStream( new FileOutputStream( srcFile ) );
	}

	protected void safeWrite( OutputStream output, ByteBuffer buffer )
			throws IOException {
		FileOutputStream fileOutput = ( FileOutputStream ) output;
		FileChannel outChannel = fileOutput.getChannel();
		FileLock fileLock = outChannel.lock();
		long position = outChannel.position();
		long size = outChannel.size();
		if ( size != position )
			outChannel.position( size );

		outChannel.write( buffer );
		fileLock.release();
	}

	protected void closeOutputStream() {
		if ( getOutputStream() != null ) {
			try {
				getOutputStream().close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			setOutputStream( null );
		}
	}

	File getFile() {
		return this.file;
	}

	void setFile( File file ) {
		this.file = file;
	}

	FileOutputStream getOutputStream() {
		return this.outputStream;
	}

	void setOutputStream( FileOutputStream outputStream ) {
		this.outputStream = outputStream;
	}
}