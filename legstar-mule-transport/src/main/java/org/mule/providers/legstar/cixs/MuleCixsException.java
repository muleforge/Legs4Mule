/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.cixs;

/**
 * Exception related to a Mule-LegStar error.
 */
public class MuleCixsException extends Exception {

    /** Unique serial ID. */
    private static final long serialVersionUID = 2330171403837091360L;

    /**
     * Construct an exception from an error message.
     * @param message error message text
     */
    public MuleCixsException(final String message) {
        super(message);
    }

    /**
     * Construct an exception from an exception.
     * @param e the root exception
     */
    public MuleCixsException(final Exception e) {
        super(e);
    }

}
