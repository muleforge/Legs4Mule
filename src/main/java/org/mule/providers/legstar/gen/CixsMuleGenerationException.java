/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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

