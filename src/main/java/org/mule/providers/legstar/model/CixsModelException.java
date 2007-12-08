/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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

