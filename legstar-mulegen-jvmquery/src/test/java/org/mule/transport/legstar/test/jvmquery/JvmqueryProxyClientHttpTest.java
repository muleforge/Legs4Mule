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


import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.mule.tck.FunctionalTestCase;

import com.legstar.coxb.host.HostData;


/**
 * Test the generated proxy.
 * <p/>
 * This test simulates a mainframe HTTP request/response to a LegStar proxy.
 *
 */
public class JvmqueryProxyClientHttpTest extends FunctionalTestCase {

    /** Target proxy URL.*/
    public static final String JVMQUERY_PROXY_URL = "http://localhost:8083/legstar/services/jvmquery";

    /** Raw mainframe request in hex. */
    public static final String MAINFRAME_REQUEST_DATA =
        /*0 0 0 2 M U L E _ H O M E - - - - - - - - - - - - - - - - - - -*/
        "00000002d4e4d3c56dc8d6d4c540404040404040404040404040404040404040"
        /*  - - - - J A V A _ H O M E - - - - - - - - - - - - - - - - - - -*/
        + "40404040d1c1e5c16dc8d6d4c540404040404040404040404040404040404040"
        /*  - - - - */
        + "40404040";

    /** Expected raw mainframe response in hex. */
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
        return "mule-proxy-config-jvmquery-http.xml";
    }

    /**
     * Call the esb and check response.
     * @throws Exception if test fails
     */
    public void testRun() throws Exception {
        String response = invoke(MAINFRAME_REQUEST_DATA,
                EXPECTED_MAINFRAME_RESPONSE_DATA.length() / 2);
        assertEquals(EXPECTED_MAINFRAME_RESPONSE_DATA.substring(0, 131),
                response.substring(0, 131));
    }

    /**
     * Invoke the proxy deployed at the specified URL.
     * @param mainframeRequestData a hex string of the raw mainframe data
     * @param expectedResponseSize the size in bytes of the expected response
     * @return a hex string of the mainframe response data
     * @throws Exception if invoke fails
     */
    public String invoke(
            final String mainframeRequestData,
            final int expectedResponseSize) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(JVMQUERY_PROXY_URL);
        ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(
                HostData.toByteArray(mainframeRequestData), "application/octet-stream");
        postMethod.setRequestEntity(requestEntity);
        if (200 != httpClient.executeMethod(postMethod)) {
           throw new IOException(postMethod.getStatusText()); 
        }
        if (expectedResponseSize != postMethod.getResponseContentLength()) {
            throw new IOException("Content length returned does not match");
        }
        return HostData.toHexString(postMethod.getResponseBody());
    }
    
}
