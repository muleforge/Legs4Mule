package org.mule.providers.legstar.model;

/**
 * Exception related to an inconsistent or erroneous model.
 */
public class CixsModelException extends Exception {

    /** Unique serial ID. */
    private static final long serialVersionUID = -8210427970123669600L;

    /**
     * Build Exception from message.
     * @param message exception description
     */
    public CixsModelException(final String message) {
        super(message);
    }

  /**
   * Build Exception from inner exception.
   * @param e the inner exception
   */
    public CixsModelException(final Exception e) {
        super(e);
    }
}

