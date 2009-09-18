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
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.tcp;

import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.endpoint.URIBuilder;
import org.mule.tck.AbstractMuleTestCase;

/**
 * Test legstar tcp endpoints.
 *
 */
public class LegstarTcpEndpointTestCase extends AbstractMuleTestCase {

    /**
     * Create an endpoint and check its properties.
     * @throws Exception if something goes wrong
     */
    public void testValidEndpointURI() throws Exception {
        EndpointURI endpointUri = new MuleEndpointURI("legstar-tcp://localhost:1234");
        endpointUri.initialise();
        assertEquals("legstar-tcp", endpointUri.getScheme());
        assertEquals("legstar-tcp", endpointUri.getSchemeMetaInfo());
        assertEquals("legstar-tcp://localhost:1234", endpointUri.getAddress());
        assertEquals(1234, endpointUri.getPort());
        assertEquals("localhost", endpointUri.getHost());
        assertEquals(0, endpointUri.getParams().size());
    }
    
    /**
     * Check the endpoint properties when used for outbound requests.
     * @throws Exception if test fails
     */
    public void testEndpoint() throws Exception {
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(
                new URIBuilder("legstar-tcp://mainframe:3011"),
                muleContext);
        OutboundEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
                endpointBuilder);
        assertTrue(endpoint.isSynchronous());
    }

}
