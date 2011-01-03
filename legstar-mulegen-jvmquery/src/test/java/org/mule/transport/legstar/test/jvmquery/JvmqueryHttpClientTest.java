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

import junit.framework.TestCase;

import com.legstar.coxb.host.HostData;

/**
 * Test the proxy for the Jvmquery POJO.
 * <p/>
 * This test does not use any of the Mule classes. It assumes the proxy service has
 * been deployed in a live instance of Mule.
 * <p/>
 * Client sends/receive byte arrays of mainframe data.
 */
public class JvmqueryHttpClientTest extends TestCase {

    /** This is the HTTP URL of the proxy.*/
    public static final String JVMQUERY_PROXY_URL = "http://localhost:8083/legstar/services/jvmquery";

    /** Raw mainframe request in hex. */
    public static final String MAINFRAME_REQUEST_DATA =
        /*0 0 0 2 L E G S T A R _ H O M E - - - - - - - - - - - - - - - - - -*/
        "00000002d3c5c7e2e3c1d96dc8d6d4c5404040404040404040404040404040404040"
        /*  - - - - J A V A _ H O M E - - - - - - - - - - - - - - - - - - -*/
        + "40404040d1c1e5c16dc8d6d4c540404040404040404040404040404040404040"
        /*  - - - - */
        + "40404040";

    /**
     * Run the target Jvmquery POJO.
     * Client sends a byte array and receive one as a reply.
     * @throws Exception if test fails
     */
    public void testJvmquery() throws Exception {
        String replyHex = postRequest(
        		HostData.toByteArray(MAINFRAME_REQUEST_DATA));
        assertEquals(392, replyHex.length());
    }

    /**
     * Use HTTP client to post binary data and receive a binary reply.
     * @param hostByteArray the request data
     * @return the response data as an hexadecimal string
     * @throws IOException general IO failure
     */
    private String postRequest(
            final byte[] hostByteArray) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(JVMQUERY_PROXY_URL);
        ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(
                hostByteArray, "application/octet-stream");
        postMethod.setRequestEntity(requestEntity);
        if (200 != httpClient.executeMethod(postMethod)) {
            throw new IOException(postMethod.getStatusText()); 
        }
        return HostData.toHexString(postMethod.getResponseBody());
    }

}
