/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.wmq;

import org.mule.api.endpoint.EndpointURI;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;

/**
 * Test legstar WMQ endpoints.
 *
 */
public class LegstarWmqEndpointTestCase extends AbstractMuleTestCase {

    /**
     * Create an endpoint and check its properties.
     * @throws Exception if something goes wrong
     */
    public void testValidEndpointURI() throws Exception {
        EndpointURI endpointUri = new MuleEndpointURI("legstar-wmq://CICS01.BRIDGE.REQUEST.QUEUE");
        endpointUri.initialise();
        assertEquals("legstar-wmq", endpointUri.getScheme());
        assertEquals("legstar-wmq", endpointUri.getSchemeMetaInfo());
        assertEquals("CICS01.BRIDGE.REQUEST.QUEUE", endpointUri.getAddress());
    }

    /**
     * Get user/password from URI.
     * @throws Exception if something goes wrong
     */
    public void testUserPasswordURI() throws Exception {
        EndpointURI endpointUri = new MuleEndpointURI("legstar-wmq://user:password@CICS01.BRIDGE.REQUEST.QUEUE");
        endpointUri.initialise();
        assertEquals("legstar-wmq", endpointUri.getScheme());
        assertEquals("legstar-wmq", endpointUri.getSchemeMetaInfo());
        assertEquals("CICS01.BRIDGE.REQUEST.QUEUE", endpointUri.getAddress());
        assertEquals("user:password", endpointUri.getUserInfo());
        assertEquals("user", endpointUri.getUser());
        assertEquals("password", endpointUri.getPassword());
    }
}
