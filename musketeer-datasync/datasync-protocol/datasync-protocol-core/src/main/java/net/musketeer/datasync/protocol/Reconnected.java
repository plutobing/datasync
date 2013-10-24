package net.musketeer.datasync.protocol;

public interface Reconnected {

	public void reconnect( Connection connector ) throws Exception;

}
