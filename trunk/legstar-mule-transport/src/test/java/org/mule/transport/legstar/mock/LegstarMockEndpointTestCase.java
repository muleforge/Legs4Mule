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

package org.mule.transport.legstar.mock;

import org.mule.api.endpoint.EndpointURI;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;

/**
 * Test legstar mock endpoints.
 *
 */
public class LegstarMockEndpointTestCase extends AbstractMuleTestCase {

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    /**
     * Create an endpoint and check its properties.
     * @throws Exception if something goes wrong
     */
    public void testValidEndpointURI() throws Exception  {
        EndpointURI endpointUri = new MuleEndpointURI("legstar-mock://localhost", muleContext);
        endpointUri.initialise();
        assertEquals("legstar-mock", endpointUri.getScheme());
        assertEquals("legstar-mock", endpointUri.getSchemeMetaInfo());
        assertEquals("legstar-mock://localhost", endpointUri.getAddress());
        assertEquals(-1, endpointUri.getPort());
        assertEquals("localhost", endpointUri.getHost());
        assertEquals(0, endpointUri.getParams().size());
    }

}
