package net.musketeer.datasync.business.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import net.musketeer.datasync.core.DataModel;
import net.musketeer.datasync.memqueue.MemQueueConsumer;
import net.musketeer.datasync.memqueue.MemQueueProducer;

/**
 * 原始数据处理服务类
 *
 * <p></p>
 * @author liubing
 */
public class OriginalDataProcessService implements Runnable {
	
	/**
	 * 类-队列名称映射
	 */
	private Map< String, String > queues;
	
	private Map< String, MemQueueProducer > producers = new HashMap< String, MemQueueProducer >( 16 );
	
	private String consumeQueue;
	
	private ExecutorService single = Executors.newSingleThreadExecutor( new ThreadFactory() {

		@Override
		public Thread newThread( Runnable r ) {
			return new Thread( r, "OriginalDataProcess-Thread" );
		}
		
	} );
	
	public OriginalDataProcessService( String consumeQueue ) {
		this.consumeQueue = consumeQueue;
		init();
	}
	
	private void init() {
		single.execute( this );
	}

	@Override
	public void run() {
		final MemQueueConsumer consumer = new MemQueueConsumer( getConsumeQueue() );
		while ( true ) {
			final byte[] message = consumer.consume();
			final DataModel dm = JSON.parseObject( 
					message, DataModel.class, Feature.AllowComment );
			final String targetQueue = queues.get( dm.getName() );
			if ( producers.containsKey( targetQueue ) ) {
				producers.get( targetQueue ).produce( dm.getValue() );
			} else {
				final MemQueueProducer producer = new MemQueueProducer( targetQueue );
				producer.produce( dm.getValue() );
				producers.put( targetQueue, producer );
			}
		}
	}

	public String getConsumeQueue() {
		return consumeQueue;
	}

	public void setConsumeQueue( String consumeQueue ) {
		this.consumeQueue = consumeQueue;
	}

	public Map< String, String > getQueues() {
		return queues;
	}

	public void setQueues( Map< String, String > queues ) {
		this.queues = queues;
	}

}
