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

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.service.TransportFactory;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpoint;


/**
 * Test the LegstarHttpConnectorFactory class.
 *
 */
public class LegstarHttpConnectorFactoryTestCase extends AbstractMuleTestCase {

    /** Legstar listening port. */
    private static final int LEGSTAR_PORT = 8083;

    /**
     * Check that connector can be created.
     * @throws Exception if fails
     */
    public void testCreateFromFactory() throws Exception {
        MuleEndpointURI url = new MuleEndpointURI(getEndpointURI());
        UMOEndpoint endpoint = TransportFactory.createEndpoint(url, UMOEndpoint.ENDPOINT_TYPE_RECEIVER);
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof LegstarHttpConnector);
        assertEquals("http://localhost:8083", endpoint.getEndpointURI().getAddress());
    }

    /**
     * @return a legstar scheme URI.
     */
    public String getEndpointURI() {
        return "legstar:http://localhost:" + Integer.toString(LEGSTAR_PORT);
    }

}
