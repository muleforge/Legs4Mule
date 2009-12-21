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
package org.mule.transport.legstar.test.lsfileae;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

import junit.framework.TestCase;

import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.ObjectFactory;

/**
 * Test the adapter for the LSFILEAE mainframe program.
 * <p/>
 * This test does not use any of the Mule classes. It assumes the adapter service has
 * been deployed in a live instance of Mule.
 * <p/>
 * Client sends/receive serialized java objects.
 */
public class LsfileaeHttpClientTest extends TestCase {

    /** This is the HTTP URL of the adapter (not the target mainframe).*/
    public static final String LSFILEAE_ADAPTER_URL = "http://megamouss:3280/lsfileae";

    /**
     * Run the target LSFILEAE mainframe program.
     * Client sends a serialized java object and receive one as a reply.
     * This implements the Mule SafeProtocol with an initial cookie and data preceded by length.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        byte[] requestBytes = getSerializedJavaRequest();
        InputStream replyStream = postRequest(requestBytes);
        Dfhcommarea reply = getJavaReply(replyStream);
        checkJavaObjectReply(reply);
    }

    /**
     * Use HTTP client to post binary data and receive a binary reply.
     * @param serializedJavaObject the request data
     * @return the response data as a stream
     * @throws IOException general IO failure
     */
    private InputStream postRequest(
            final byte[] serializedJavaObject) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(LSFILEAE_ADAPTER_URL);
        ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(
                serializedJavaObject, "application/octet-stream");
        postMethod.setRequestEntity(requestEntity);
        if (200 != httpClient.executeMethod(postMethod)) {
            throw new IOException(postMethod.getStatusText()); 
        }
        return postMethod.getResponseBodyAsStream();
    }

    /**
     * @return a serialized java request object in a byte array.
     * @throws IOException  if serialization fails
     */
    private byte[] getSerializedJavaRequest() throws IOException {
        ObjectFactory of = new ObjectFactory();
        Dfhcommarea dfhcommarea = of.createDfhcommarea();
        dfhcommarea.setComNumber(100L);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(dfhcommarea);
        out.close();
        return bos.toByteArray();
    }
    
    /**
     * De-serialize a byte array into a java object.
     * @param replyStream the HTTP reply
     * @return a java object
     * @throws IOException if de-serialization fails
     */
    private Dfhcommarea getJavaReply(final InputStream replyStream) throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(replyStream);
            Dfhcommarea reply = (Dfhcommarea) in.readObject();
            return reply;
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
       
    }

    /** 
     * Check the values returned from LSFILEAE after they were transformed to Java.
     * @param dfhcommarea the java data object
     */
    private void checkJavaObjectReply(final Dfhcommarea dfhcommarea) {
        assertEquals(100, dfhcommarea.getComNumber());
        assertEquals("$0100.11", dfhcommarea.getComAmount());
        assertEquals("*********", dfhcommarea.getComComment());
        assertEquals("26 11 81", dfhcommarea.getComDate());
        assertEquals("SURREY, ENGLAND", dfhcommarea.getComPersonal().getComAddress());
        assertEquals("S. D. BORMAN", dfhcommarea.getComPersonal().getComName());
        assertEquals("32156778", dfhcommarea.getComPersonal().getComPhone());
    }
}
