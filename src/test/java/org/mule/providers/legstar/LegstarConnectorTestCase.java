/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.legstar;

import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

public class LegstarConnectorTestCase extends AbstractConnectorTestCase
{

    /** Legstar listening port. */
    private static final int LEGSTAR_PORT = 8083;

    public UMOConnector getConnector() throws Exception {
 
        LegstarConnector c = new LegstarConnector();
        c.setName("Test-Legstar");
        c.initialise();
        return c;
    }

    public String getTestEndpointURI()
    {
        return "legstar:http://localhost:" + Integer.toString(LEGSTAR_PORT);
    }

    public Object getValidMessage() throws Exception
    {
        return new String("mok message").getBytes();
    }


    public void testProperties() throws Exception
    {
        // TODO test setting and retrieving any custom properties on the
        // Connector as necessary
    }

}
