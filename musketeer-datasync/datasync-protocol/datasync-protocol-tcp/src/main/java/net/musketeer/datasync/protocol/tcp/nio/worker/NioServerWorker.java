package net.musketeer.datasync.protocol.tcp.nio.worker;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ThreadPoolExecutor;

import net.musketeer.datasync.protocol.config.ProtocolConfig;
import net.musketeer.datasync.protocol.tcp.nio.NioWorker;
import net.musketeer.datasync.protocol.tcp.nio.event.Handler;
import net.musketeer.datasync.protocol.tcp.nio.event.InStreamHandler;

public class NioServerWorker extends NioWorker {
	
	public NioServerWorker( ThreadPoolExecutor executor,
			ProtocolConfig definition ) {
		super( executor, definition );
	}

	@Override
	public int getSelectionKey() {
		return SelectionKey.OP_READ;
	}

	@Override
	public Handler createHandler( SelectionKey key ) {
		return new InStreamHandler( key );
	}

}
