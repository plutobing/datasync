package net.musketeer.datasync.protocol.tcp.nio.event;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public class OutStreamHandler extends StreamHandler {
	
	private SelectionKey key;
	
	public OutStreamHandler( SelectionKey key ) {
		this.key = key;
	}

	public void handle() {
		byte[] bytes = "bbbbbbbbb".getBytes();
		ByteBuffer writeBuff = ByteBuffer.allocate( bytes.length );
		writeBuff.put( bytes );
		writeBuff.flip();
		try {
			( ( SocketChannel ) key.channel() ).write( writeBuff );
			key.attach( new InStreamHandler( key ) );
			key.interestOps( SelectionKey.OP_READ );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
