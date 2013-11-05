import net.musketeer.datasync.Env;
import net.musketeer.datasync.protocol.ProtocolPluginManager;

import org.junit.Test;

/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 6, 2013
 */
public class PluginsTest {
	@Test
	public void test() throws Exception {
		Env.getInstance().init( "/home/bjyfliubing/Test/datasync" );
		ProtocolPluginManager.getInstance().start();
	}

}
