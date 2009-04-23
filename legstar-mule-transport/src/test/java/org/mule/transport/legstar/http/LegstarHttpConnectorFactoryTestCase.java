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

import org.mule.api.endpoint.EndpointURI;
import org.mule.api.transport.Connector;
import org.mule.transport.service.TransportFactory;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.endpoint.MuleEndpointURI;


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
        EndpointURI url = new MuleEndpointURI(getEndpointURI());
        Connector connector = TransportFactory.createConnector(url, null);
        assertNotNull(connector);
        assertTrue(connector instanceof LegstarHttpConnector);
        assertEquals("legstar", connector.getProtocol());
    }

    /**
     * @return a legstar scheme URI.
     */
    public String getEndpointURI() {
        return "legstar:http://localhost:" + Integer.toString(LEGSTAR_PORT);
    }

}
