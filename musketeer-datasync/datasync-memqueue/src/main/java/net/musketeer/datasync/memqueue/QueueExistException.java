package net.musketeer.datasync.memqueue;

public class QueueExistException extends Exception {
	private static final long serialVersionUID = 1670567146661105386L;

	public QueueExistException( String message ) {
		super( message );
	}
}