package org.mule.providers.legstar.gen;

/**
 * Exception related to failures during code generation.
 */
public class CixsMuleGenerationException extends Exception {

    /** Unique serial ID. */
    private static final long serialVersionUID = 6490029804547733908L;

    /**
     * Build Exception from message.
     * @param message exception description
     */
    public CixsMuleGenerationException(final String message) {
        super(message);
    }

  /**
   * Build Exception from inner exception.
   * @param e the inner exception
   */
    public CixsMuleGenerationException(final Exception e) {
        super(e);
    }
}

