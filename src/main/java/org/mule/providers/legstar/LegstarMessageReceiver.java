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

import org.mule.providers.http.HttpMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

/**
 * <code>LegstarMessageReceiver</code> delegates all processing
 * to <code>HttpMessageReceiver</code>.
 */
public class LegstarMessageReceiver extends HttpMessageReceiver {

    /**
     * Constructs a message receiver for a component.
     * @param connector the Mule connector
     * @param component the Mule component 
     * @param endpoint the Mule endpoint
     * @throws InitialisationException if construction fails
     */
    public LegstarMessageReceiver(
            final UMOConnector connector,
            final UMOComponent component,
            final UMOEndpoint endpoint)
            throws InitialisationException {
        super(connector, component, endpoint);
    }

}

