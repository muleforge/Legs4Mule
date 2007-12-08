/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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

