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
