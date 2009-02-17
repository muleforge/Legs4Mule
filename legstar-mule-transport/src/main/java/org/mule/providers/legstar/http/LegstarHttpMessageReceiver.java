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
package org.mule.providers.legstar.http;

import org.mule.providers.http.HttpMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

/**
 * <code>LegstarMessageReceiver</code> delegates all processing
 * to <code>HttpMessageReceiver</code>.
 */
public class LegstarHttpMessageReceiver extends HttpMessageReceiver {

    /**
     * Constructs a message receiver for a component.
     * @param connector the Mule connector
     * @param component the Mule component 
     * @param endpoint the Mule endpoint
     * @throws InitialisationException if construction fails
     */
    public LegstarHttpMessageReceiver(
            final UMOConnector connector,
            final UMOComponent component,
            final UMOEndpoint endpoint)
    throws InitialisationException {
        super(connector, component, endpoint);
    }

}

