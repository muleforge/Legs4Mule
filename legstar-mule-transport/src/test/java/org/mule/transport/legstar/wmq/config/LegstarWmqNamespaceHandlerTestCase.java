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
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.legstar.wmq.config;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.legstar.wmq.LegstarWmqConnector;

/**
 * Test for LegstarWmqNamespaceHandlerTestCase.
 */
public class LegstarWmqNamespaceHandlerTestCase extends FunctionalTestCase {

    /** {@inheritDoc} */
    protected String getConfigResources() {
        return "legstar-wmq-namespace-config.xml";
    }

    /**
     * Creates a connector from a spring configuration.
     * @throws Exception if creation fails
     */
    public void testLegstarWmqConfig() throws Exception {
        LegstarWmqConnector c = (LegstarWmqConnector) muleContext.getRegistry().lookupConnector("legstarWmqConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

        //TODO Assert specific properties are configured correctly


    }
}
