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

    /** {@inheritDoc} */
    public Connector createConnector() throws Exception {
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


}
