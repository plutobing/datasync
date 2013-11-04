package net.musketeer.datasync.memqueue;

import java.io.File;

import javax.xml.bind.JAXBContext;

import net.musketeer.datasync.Env;
import net.musketeer.datasync.memqueue.model.ConfigurationDefinition;
import net.musketeer.datasync.memqueue.model.QueueDefinition;

public class MemoryQueueManager {
	
	private static volatile MemoryQueueManager _instance;

	public static MemoryQueueManager getInstance() {
		if ( _instance == null ) {
			synchronized ( MemoryQueueManager.class ) {
				if ( _instance == null ) {
					_instance = new MemoryQueueManager();
				}
			}
		}
		return _instance;
	}

	public void start() throws Exception {
		try {
			JAXBContext context = JAXBContext
					.newInstance( ConfigurationDefinition.class.getPackage()
							.getName() );

			ConfigurationDefinition configuration = ( ConfigurationDefinition ) context
					.createUnmarshaller().unmarshal( new File( Env.getInstance().getConfPath() 
							+ "memqueue/memqueue.xml" ) );

			if ( configuration != null ) {
				for ( QueueDefinition queue : configuration.getQueues() )
					if ( queue.isPriority() )
						createPriorityQueue( queue.getName(),
								queue.getCapacity() );
					else
						creatQueue( queue.getName(), queue.getCapacity() );
			}
		} catch ( Exception e ) {
			throw e;
		}
	}
	
	public void stop() {
		MemoryQueueRegistry.getInstance().removeAll();
		_instance = null;
	}

	public < T > boolean creatQueue( String name, int capacity ) {
		boolean success = false;
		try {
			MemoryQueue queue = new DefaultMemoryQueue< T >( name, capacity );
			queue.start();
			MemoryQueueRegistry.getInstance().register( name, queue );
			success = true;
		} catch ( QueueExistException e ) {
			e.printStackTrace();
		}
		return success;
	}

	public < T extends Cloneable > boolean createPriorityQueue( String name,
			int capacity ) {
		boolean success = false;
		try {
			MemoryQueue queue = new PriorityMemoryQueue< T >( name, capacity );
			queue.start();
			MemoryQueueRegistry.getInstance().register( name, queue );
			success = true;
		} catch ( QueueExistException e ) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean destroyQueue( String name ) {
		boolean success = false;
		try {
			MemoryQueue queue = MemoryQueueRegistry.getInstance().remove( name );
			queue.stop();
			success = true;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return success;
	}

	public MemoryQueue lookup( String name ) {
		return MemoryQueueRegistry.getInstance().get( name );
	}
}