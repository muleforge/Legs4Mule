/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/

package org.mule.providers.legstar.http;

import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

/**
 * Test the LegstarHttpConnector class.
 *
 */
public class LegstarHttpConnectorTestCase extends AbstractConnectorTestCase {

    /** Legstar listening port. */
    private static final int LEGSTAR_PORT = 8083;

    /** An instance to the connector. */
    private LegstarHttpConnector mConnector;

    /** {@inheritDoc} */
    public UMOConnector getConnector() {
        return mConnector;
    }

    /** {@inheritDoc} */
    public String getTestEndpointURI() {
        return "legstar:http://localhost:" + Integer.toString(LEGSTAR_PORT);
    }

    /** {@inheritDoc} */
    public Object getValidMessage() throws Exception {
        return new String("mok message").getBytes();
    }


    /** {@inheritDoc} */
    public UMOConnector createConnector() throws Exception {
        mConnector = new LegstarHttpConnector();
        mConnector.setName("Test-Legstar");
        mConnector.initialise();
        return mConnector;
    }

}
