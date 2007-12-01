package org.mule.providers.legstar.gen.util;

/**
 * Exception related to syntax errors or execution errors using cixsmake.
 */
public class CixsMakeException extends Exception {

    /** Unique serial ID. */
    private static final long serialVersionUID = 6490029804547733908L;

    /**
     * Build Exception from message.
     * @param message exception description
     */
    public CixsMakeException(final String message) {
        super(message);
    }

  /**
   * Build Exception from inner exception.
   * @param e the inner exception
   */
    public CixsMakeException(final Exception e) {
        super(e);
    }
}

