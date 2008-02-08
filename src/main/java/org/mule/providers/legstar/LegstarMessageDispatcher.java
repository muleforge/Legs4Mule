/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.legstar;

import org.mule.providers.http.HttpClientMessageDispatcher;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

/**
 * <code>LegstarMessageDispatcher</code> delegates most of its behavior
 * to <code>HttpClientMessageDispatcher</code>.
 */
public class LegstarMessageDispatcher extends HttpClientMessageDispatcher {

    /**
     * Constructor for a given endpoint.
     * @param endpoint the Mule endpoint
     */
    public LegstarMessageDispatcher(final UMOImmutableEndpoint endpoint) {
        super(endpoint);
    }

}


