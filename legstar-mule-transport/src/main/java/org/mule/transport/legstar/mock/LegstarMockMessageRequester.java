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
package org.mule.transport.legstar.mock;

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.transport.AbstractMessageRequester;

/**
 * <code>LegstarMockpMessageRequester</code>.
 */
public class LegstarMockMessageRequester extends AbstractMessageRequester {

    /**
     * @param endpoint the inbound endpoint
     */
    public LegstarMockMessageRequester(final InboundEndpoint endpoint) {
        super(endpoint);
    }

    /**
     * Make a specific request to the underlying transport.
     *
     * @param timeout the maximum time the operation should block before returning.
     *            The call should return immediately if there is data available. If
     *            no data becomes available before the timeout elapses, null will be
     *            returned
     * @return the result of the request wrapped in a MuleMessage object. Null will be
     *         returned if no data was available
     * @throws Exception if the call to the underlying protocol causes an exception
     */
    protected MuleMessage doRequest(final long timeout) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
