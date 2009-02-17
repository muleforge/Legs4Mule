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

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpointURI;


/**
 * Test LegStar Endpoints.
 * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
 *
 */
public class LegstarHttpEndpointTestCase extends AbstractMuleTestCase {

 
    /**
     * Check that LegStar endpoints are valid Mule endpoints.
     * @throws Exception if test fails
     */
    public void testValidEndpointURI() throws Exception  {
        UMOEndpointURI url = new MuleEndpointURI("legstar:http://localhost:7856");
        assertEquals("http", url.getScheme());
        assertEquals("legstar", url.getSchemeMetaInfo());
        assertEquals("http://localhost:7856", url.getAddress());
        assertEquals(7856, url.getPort());
        assertEquals("localhost", url.getHost());
        assertEquals("http://localhost:7856", url.getAddress());
        assertEquals(0, url.getParams().size());
    }

}
