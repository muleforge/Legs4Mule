/*******************************************************************************
 * Copyright (c) 2009 LegSem.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     LegSem - initial API and implementation
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
