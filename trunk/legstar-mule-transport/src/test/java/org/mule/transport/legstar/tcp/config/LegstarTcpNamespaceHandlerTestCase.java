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
package org.mule.transport.legstar.tcp.config;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.legstar.tcp.LegstarTcpConnector;

/**
 * Test for LegstarTcpNamespaceHandlerTestCase.
 */
public class LegstarTcpNamespaceHandlerTestCase extends FunctionalTestCase {

    /** {@inheritDoc} */
    protected String getConfigResources() {
        return "legstar-tcp-namespace-config.xml";
    }

    /**
     * Creates a connector from a spring configuration.
     * @throws Exception if creation fails
     */
    public void testLegstarTcpConfig() throws Exception {
        LegstarTcpConnector c = (LegstarTcpConnector) muleContext.getRegistry().lookupConnector("legstarTcpConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
}
