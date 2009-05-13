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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

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
public class LsfileaeClientTest extends TestCase {

    /** This is part of Mule safe protocol over TCP.*/
    public static final String COOKIE = "You are using SafeProtocol";

    /**
     * Run the target LSFILEAE mainframe program.
     * Client sends a serialized java object and receive one as a reply.
     * This implements the Mule SafeProtocol with an initial cookie and data preceded by length.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        byte[] request = getSerializedJavaRequest();

        Socket socket = new Socket("localhost", 3210);
        socket.setSoTimeout(3000);

        OutputStream socketOut = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(socketOut);
        dos.writeInt(COOKIE.length());
        dos.writeBytes(COOKIE);
        dos.writeInt(request.length);
        dos.write(request);
        dos.flush();

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int cookieLength = dis.readInt();
        assertEquals(COOKIE.length(), cookieLength);
        byte[] cookieBytes = new byte[cookieLength];
        dis.readFully(cookieBytes);
        assertEquals(COOKIE, new String(cookieBytes));
        dis.readInt();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Dfhcommarea reply = (Dfhcommarea) in.readObject();

        socket.close();

        checkJavaObjectReply(reply);

    }

    /**
     * @return a serialized java request object in a byte array.
     * @throws IOException  if serialization fails
     */
    public static byte[] getSerializedJavaRequest() throws IOException {
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
     * Check the values returned from LSFILEAE after they were transformed to Java.
     * @param dfhcommarea the java data object
     */
    public static void checkJavaObjectReply(final Dfhcommarea dfhcommarea) {
        assertEquals(100, dfhcommarea.getComNumber());
        assertEquals("$0100.11", dfhcommarea.getComAmount());
        assertEquals("*********", dfhcommarea.getComComment());
        assertEquals("26 11 81", dfhcommarea.getComDate());
        assertEquals("SURREY, ENGLAND", dfhcommarea.getComPersonal().getComAddress());
        assertEquals("S. D. BORMAN", dfhcommarea.getComPersonal().getComName());
        assertEquals("32156778", dfhcommarea.getComPersonal().getComPhone());
    }
}
