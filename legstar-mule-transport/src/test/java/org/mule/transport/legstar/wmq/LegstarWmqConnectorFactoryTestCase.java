/*
 * $Id: ConnectorFactoryTestCase.vm 11967 2008-06-05 20:32:19Z dfeist $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.wmq;

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.tck.AbstractMuleTestCase;


/**
 * Tests the creation of the LegstarTcpConnector instance by Mule.
 *
 */
public class LegstarWmqConnectorFactoryTestCase extends AbstractMuleTestCase {

    /**
     * Simulates the way Mule will create the connector.
     * @throws Exception if creation fails
     */
    public void testCreateFromFactory() throws Exception {
        InboundEndpoint endpoint = muleContext.getRegistry()
                .lookupEndpointFactory().getInboundEndpoint(getEndpointURI());
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof LegstarWmqConnector);
        assertEquals("CICS01.BRIDGE.REQUEST.QUEUE", endpoint.getEndpointURI().getAddress());
    }

    /** {@inheritDoc} */
    public String getEndpointURI() {
        return "legstar-wmq://CICS01.BRIDGE.REQUEST.QUEUE"
        + "?jndiInitialFactory=com.sun.jndi.fscontext.RefFSContextFactory"
        + "&jndiProviderUrl=file:///JNDI-Directory"
        + "&connectionFactoryJndiName=ConnectionFactory";
    }

}
