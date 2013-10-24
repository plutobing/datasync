package net.musketeer.datasync.protocol.tcp.nio.event;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import net.musketeer.datasync.memqueue.MemQueueProducer;


public class InStreamHandler extends StreamHandler {
		
	private SelectionKey key;
		
	private ByteBuffer lenBuff = ByteBuffer.allocate( 4 );
	
	private ByteBuffer contentBuff;

	private boolean readHeadCompleted = false;
	
	private MemQueueProducer producer = new MemQueueProducer( "original-data-queue" );

	public InStreamHandler( SelectionKey key ) {
		this.key = key;
	}

	public void handle() {
		try {
			SocketChannel channel = ( SocketChannel ) key.channel();
			if ( channel == null || ! channel.isOpen() ) {
				key.cancel();
				return ;
			}
			int i = 0;
			if ( ! readHeadCompleted  ) {
				i = channel.read( lenBuff );
				if ( i == -1 ) {
					key.cancel();
					return ;
				}
				if ( lenBuff.position() < 4 ) {
					return ;
				} else {
					readHeadCompleted = true;
				}
			}
			
			if ( contentBuff == null ) {
				final int len = Integer.parseInt( new String( lenBuff.array() ), 10 );
				contentBuff = ByteBuffer.allocate( len );
			}
			
			i = channel.read( contentBuff );
			if ( i == -1 ) {
				key.cancel();
				return ;
			}
			if ( contentBuff.position() < contentBuff.capacity() ) {
				return ;
			}
			//Push the content to memory queue
			producer.produce( contentBuff.array() );
			key.attach( new OutStreamHandler( key ) );
			key.interestOps( SelectionKey.OP_WRITE );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
