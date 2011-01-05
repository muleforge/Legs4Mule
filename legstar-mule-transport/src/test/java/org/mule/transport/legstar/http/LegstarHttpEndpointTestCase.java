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

import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.api.endpoint.EndpointURI;


/**
 * Test LegStar Endpoints.
 * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
 *
 */
public class LegstarHttpEndpointTestCase extends AbstractMuleTestCase {

 
    /**
     * Check that LegStar endpoints are valid Mule endpoints.
     * @throws Exception if test fails
     */
    public void testValidEndpointURI() throws Exception  {
        EndpointURI endpointUri = new MuleEndpointURI("legstar://localhost:7856", muleContext);
        endpointUri.initialise();
        assertEquals("legstar", endpointUri.getScheme());
        assertEquals("legstar://localhost:7856", endpointUri.getAddress());
        assertEquals(7856, endpointUri.getPort());
        assertEquals("localhost", endpointUri.getHost());
        assertEquals(0, endpointUri.getParams().size());
    }

    /**
     * Check that LegStar endpoints are valid Mule endpoints.
     * @throws Exception if test fails
     */
    public void testOldstyleEndpointURI() throws Exception  {
        EndpointURI endpointUri = new MuleEndpointURI("legstar:http://localhost:7856", muleContext);
        endpointUri.initialise();
        assertEquals("legstar", endpointUri.getSchemeMetaInfo());
        assertEquals("http", endpointUri.getScheme());
        assertEquals("http://localhost:7856", endpointUri.getAddress());
        assertEquals(7856, endpointUri.getPort());
        assertEquals("localhost", endpointUri.getHost());
        assertEquals(0, endpointUri.getParams().size());
    }
}
