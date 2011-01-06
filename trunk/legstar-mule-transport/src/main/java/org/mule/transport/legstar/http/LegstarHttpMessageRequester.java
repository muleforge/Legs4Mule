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
package org.mule.transport.legstar.http;

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.transport.http.HttpClientMessageRequester;

/**
 * <code>LegstarHttpMessageRequester</code> delegates most of its behavior
 * to <code>HttpClientMessageRequester</code>.
 */
public class LegstarHttpMessageRequester extends HttpClientMessageRequester {

    /**
     * @param endpoint the inbound endpoint
     */
    public LegstarHttpMessageRequester(final InboundEndpoint endpoint) {
        super(endpoint);
    }

}
