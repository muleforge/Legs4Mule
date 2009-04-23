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
package org.mule.transport.legstar.functest;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.transport.http.HttpConstants;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;

import java.util.HashMap;
import java.util.Map;

/**
 * Test a roundtrip on the legstar http transport.
 *
 */
public class LegstarFunctionalTest extends FunctionalTestCase {
    
    /**
     * Increase the timeout to allow enough time for debugging.
     */
    public LegstarFunctionalTest() {
        super();
        System.setProperty(PROPERTY_MULE_TEST_TIMEOUT, "600");
    }
    
    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "legstar-functional-test.xml";
    }

    /**
     * Perform round trip.
     * @throws Exception if test fails
     */
    public void testSend() throws Exception {        
        FunctionalTestComponent testComponent =
            (FunctionalTestComponent) getComponent("testComponent");
        assertNotNull(testComponent);

        /* Simulate a call coming from a mainframe */
        MuleClient client = new MuleClient();
        Map < String, String > props = new HashMap < String, String >();
        props.put(HttpConstants.HEADER_CONTENT_TYPE, "application/octet-stream");
        MuleMessage result = client.send("clientEndpoint",
                HostData.toByteArray(LsfileaeCases.getHostBytesHexReply100()), props);
        assertEquals(LsfileaeCases.getHostBytesHexReply100(),
                HostData.toHexString(result.getPayloadAsBytes()));
    }
}
