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

import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnectorTestCase;

/**
 * Direct test of the mock connector.
 *
 */
public class LegstarMockConnectorTestCase extends AbstractConnectorTestCase {

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    /** {@inheritDoc} */
    public Connector createConnector() throws Exception {
        /* IMPLEMENTATION NOTE: Create and initialise an instance of your
           connector here. Do not actually call the connect method. */

        LegstarMockConnector c = new LegstarMockConnector();
        c.setName("Test");
        return c;
    }

    /** {@inheritDoc} */
    public String getTestEndpointURI() {
        return "legstar-mock://localhost";
    }

    /** {@inheritDoc} */
    public Object getValidMessage() throws Exception {
        return new String("mok message").getBytes();
    }


    /** {@inheritDoc} */
    public void testProperties() throws Exception {
        // TODO test setting and retrieving any custom properties on the
        // Connector as necessary
    }

}
