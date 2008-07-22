/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/

package org.mule.providers.legstar.http;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.legstar.http.LegstarHttpConnector;
import org.mule.providers.service.TransportFactory;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpoint;


public class LegstarHttpConnectorFactoryTestCase extends AbstractMuleTestCase
{
    /** Legstar listening port. */
    private static final int LEGSTAR_PORT = 8083;

    public void testCreateFromFactory() throws Exception
    {
        MuleEndpointURI url = new MuleEndpointURI(getEndpointURI());
        UMOEndpoint endpoint = TransportFactory.createEndpoint(url, UMOEndpoint.ENDPOINT_TYPE_RECEIVER);
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof LegstarHttpConnector);
        assertEquals("http://localhost:8083", endpoint.getEndpointURI().getAddress());
    }
    
    public String getEndpointURI() 
    {
    	return "legstar:http://localhost:" + Integer.toString(LEGSTAR_PORT);
    }

}
