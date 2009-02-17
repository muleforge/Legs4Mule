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
package org.mule.providers.legstar.cixs;

import java.util.HashMap;
import java.util.Map;

import org.mule.impl.MuleMessage;
import org.mule.umo.UMOMessage;

import junit.framework.TestCase;

/**
 * Test MuleHostHeaderFactory.
 *
 */
public class MuleHostHeaderFactoryTestCase extends TestCase {

    /**
     * Test header creation.
     */
    public void testHeaderCreation() {
        Map < String, Object > messageProperties = new HashMap < String, Object >();
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_REQUEST_ID, "lsfileae-client");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_USERID, "P390");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_PASSWORD, "STREAM2");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_TRACE_MODE, new Boolean(true));
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_CHARSET, "IBM01147");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_ENDPOINT, "someMainframe");

        UMOMessage umoMessage = new MuleMessage("message", messageProperties);
        MuleHostHeader h = MuleHostHeaderFactory.createHostHeader(umoMessage);
        assertEquals("P390", h.getHostUserID());
        assertEquals("STREAM2", h.getHostPassword());
        assertEquals("lsfileae-client", h.getHostRequestID());
        assertEquals(true, h.getHostTraceMode());
        assertEquals("IBM01147", h.getHostCharset());
        assertEquals("someMainframe", h.getHostEndPoint());
    }

}
