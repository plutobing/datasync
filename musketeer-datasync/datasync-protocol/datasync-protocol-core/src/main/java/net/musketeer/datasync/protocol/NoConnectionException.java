package net.musketeer.datasync.protocol;

public class NoConnectionException extends Exception {

	private static final long serialVersionUID = 8032394852033419579L;
	
	public NoConnectionException( String message ) {
		super( message );
	}

}
