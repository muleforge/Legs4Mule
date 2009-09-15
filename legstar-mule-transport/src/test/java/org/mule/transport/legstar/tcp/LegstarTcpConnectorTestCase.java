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

import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnectorTestCase;

/**
 * Direct test of the tcp connector.
 *
 */
public class LegstarTcpConnectorTestCase extends AbstractConnectorTestCase {

    /** {@inheritDoc} */
    public Connector createConnector() throws Exception {
        LegstarTcpConnector c = new LegstarTcpConnector();
        c.setName("Test");
        return c;
    }

    /** {@inheritDoc} */
    public String getTestEndpointURI() {
        return "legstar-tcp://localhost:1234";
    }

    /** {@inheritDoc} */
    public Object getValidMessage() throws Exception {
        return new String("mok message").getBytes();
    }


    /**
     * test setting and retrieving any custom properties.
     * @throws Exception if test fails.
     */
    public void testProperties() throws Exception {
        assertTrue(getConnector().isSyncEnabled("legstar-tcp"));
    }

    /** {@inheritDoc} */
    public void testConnectorMessageRequesterFactory() throws Exception {
    }

}
