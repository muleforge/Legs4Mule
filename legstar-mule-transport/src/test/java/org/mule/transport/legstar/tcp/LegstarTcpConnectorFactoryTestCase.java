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
 * $Id: ConnectorFactoryTestCase.vm 11967 2008-06-05 20:32:19Z dfeist $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.tcp;

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.tck.AbstractMuleTestCase;


/**
 * Tests the creation of the LegstarTcpConnector instance by Mule.
 *
 */
public class LegstarTcpConnectorFactoryTestCase extends AbstractMuleTestCase {

    /**
     * Simulates the way Mule will create the connector.
     * @throws Exception if creation fails
     */
    public void testCreateFromFactory() throws Exception {
        InboundEndpoint endpoint = muleContext.getEndpointFactory()
                .getInboundEndpoint(getEndpointURI());
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof LegstarTcpConnector);
        assertEquals(getEndpointURI(), endpoint.getEndpointURI().getAddress());
    }

    /**
     * @return a sample URI for the mock transport
     */
    public String getEndpointURI() {
        return "legstar-tcp://localhost:1234";
    }

}
