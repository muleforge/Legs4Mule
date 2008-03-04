/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.legstar.http;

import org.mule.providers.http.HttpMessageAdapter;
import org.mule.umo.MessagingException;

/**
 * <code>LegstarMessageAdapter</code> delegates processing to
 * <code>HttpMessageAdapter</code>.
 */
public class LegstarHttpMessageAdapter extends HttpMessageAdapter {
 
    /** Serial ID.   */
    private static final long serialVersionUID = 5737156381082603182L;

    /**
     * Constructs an adapter that basically wraps a message.
     * @param message the inner message
     * @throws MessagingException if construction fails
     */
    public LegstarHttpMessageAdapter(
            final Object message) throws MessagingException {
        super(message);
    }


}
