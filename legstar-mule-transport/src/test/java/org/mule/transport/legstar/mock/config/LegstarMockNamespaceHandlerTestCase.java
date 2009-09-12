/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.legstar.mock.config;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.legstar.mock.LegstarMockConnector;

/**
 * Test for LegstarmockNamespaceHandler.
 */
public class LegstarMockNamespaceHandlerTestCase extends FunctionalTestCase {
    
    /** {@inheritDoc} */
    protected String getConfigResources() {
        return "legstar-mock-namespace-config.xml";
    }

    /**
     * Creates a connector from a spring configuration.
     * @throws Exception if creation fails
     */
    public void testLegstarMockConfig() throws Exception {
        LegstarMockConnector c = 
            (LegstarMockConnector) muleContext.getRegistry().lookupConnector("legstarMockConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

        //TODO Assert specific properties are configured correctly


    }
}
