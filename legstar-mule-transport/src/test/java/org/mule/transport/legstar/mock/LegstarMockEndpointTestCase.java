/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.mock;

import org.mule.api.endpoint.EndpointURI;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;

/**
 * Test legstar mock endpoints.
 *
 */
public class LegstarMockEndpointTestCase extends AbstractMuleTestCase {

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    /**
     * Create an endpoint and check its properties.
     * @throws Exception if something goes wrong
     */
    public void testValidEndpointURI() throws Exception  {
        EndpointURI endpointUri = new MuleEndpointURI("legstar-mock://localhost");
        endpointUri.initialise();
        assertEquals("legstar-mock", endpointUri.getScheme());
        assertEquals("legstar-mock", endpointUri.getSchemeMetaInfo());
        assertEquals("legstar-mock://localhost", endpointUri.getAddress());
        assertEquals(-1, endpointUri.getPort());
        assertEquals("localhost", endpointUri.getHost());
        assertEquals("legstar-mock://localhost", endpointUri.getAddress());
        assertEquals(0, endpointUri.getParams().size());
    }

}
