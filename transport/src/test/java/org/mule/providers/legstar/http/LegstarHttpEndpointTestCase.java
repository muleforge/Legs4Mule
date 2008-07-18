/*
 * \$Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.legstar.http;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpointURI;


public class LegstarHttpEndpointTestCase extends AbstractMuleTestCase
{

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    public void testValidEndpointURI() throws Exception
    {
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
