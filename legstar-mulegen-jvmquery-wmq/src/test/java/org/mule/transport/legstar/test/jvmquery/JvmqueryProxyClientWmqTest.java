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
package org.mule.transport.legstar.test.jvmquery;


import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import com.legstar.coxb.host.HostData;

/**
 * Test the generated ESB.
 *
 */
public class JvmqueryProxyClientWmqTest extends FunctionalTestCase {

    /** Raw mainframe request. */
    public static final String MAINFRAME_REQUEST_DATA =
        /*0 0 0 2 M U L E _ H O M E - - - - - - - - - - - - - - - - - - -*/
        "00000002d4e4d3c56dc8d6d4c540404040404040404040404040404040404040"
        /*  - - - - J A V A _ H O M E - - - - - - - - - - - - - - - - - - -*/
        + "40404040d1c1e5c16dc8d6d4c540404040404040404040404040404040404040"
        /*  - - - - */
        + "40404040";

    /** Expected raw mainframe response sample. */
    public static final String EXPECTED_MAINFRAME_RESPONSE_DATA =
        /* 0 0 0 2 F r a n c e - - - - - - - - - - - - - - - - - - - - - -*/
        "00000002c6998195838540404040404040404040404040404040404040404040"
        /* - - - - € - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
        + "404040409f404040404040404040404040404040404040404040404040404040"
        /*  - - - - D : \ L e g s e m \ L e g s t a r \ j b o s s \ m l i t*/
        + "40404040c47ae0d38587a28594e0d38587a2a38199e0918296a2a2e0949389a3"
        /* t l e \ C : \ P r o g r a m - F i l e s \ J a v a \ j d k 1 . 6*/
        + "a39385e0c37ae0d799968799819440c6899385a2e0d181a581e0918492f14bf6"
        /* . 0 - - v e n d r e d i - 1 0 - o c t o b r e - 2 0 0 8 - 1 4 -*/
        + "4bf04040a58595849985848940f1f0409683a39682998540f2f0f0f840f1f440"
        /* h - 2 8 f r a n ç a i s - - - - - - - - - - - - - - - - - - - -*/
        + "8840f2f886998195488189a24040404040404040404040404040404040404040"
        /* - - - - */
        + "40404040";


    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-proxy-wmq-config-jvmquery-wmq.xml";
    }

    /**
     * Start the ESB and let the mainframe call in.
     * @throws Exception if test fails
     */
    public void testRunFromMainframe() throws Exception {
        Thread.sleep(10000);
    }
    /**
     * Call the esb and check response.
     * @throws Exception if test fails
     */
    public void testRun() throws Exception {
        MuleClient client = new MuleClient();
        client.dispatch(
                "MainframeRequestEndpoint",
                HostData.toByteArray(MAINFRAME_REQUEST_DATA),
                null);
        MuleMessage message = client.request("MainframeReplyEndpoint", 10000);
        assertEquals(EXPECTED_MAINFRAME_RESPONSE_DATA.substring(0, 135),
                HostData.toHexString(message.getPayloadAsBytes()).substring(0, 135));
        
    }

}
