package org.mule.providers.legstar.cixs;

/**
 * Exception related to a Mule-LegStar error.
 */
public class MuleCixsException extends Exception {

    /** Unique serial ID. */
    private static final long serialVersionUID = 2330171403837091360L;

    public MuleCixsException(String message) {
		super(message);
	}
	
	public MuleCixsException(Exception e) {
		super(e);
	}

}
