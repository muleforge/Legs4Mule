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

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.mule.module.client.MuleClient;
import org.mule.transport.legstar.cixs.MuleHostHeaderFactory;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.NullPayload;
import org.mule.api.MuleMessage;

import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.ObjectFactory;

/**
 * Test the adapter for LSFILEAE in a bridge configuration (using legstar-mule HTTP transport).
 * This test requires access to an actual mainframe running FILEA sample.
 */
public class LsfileaeWmqTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-wmq-config-lsfileae.xml";
    }
    
    /**
     * Run the target LSFILEAE mainframe program.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        /* Visually check that the mainframe received these headers */
        Map < String, Object > messageProperties = new HashMap < String, Object >();
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_REQUEST_ID, "legstar-mule");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_USERID, "MYUSER");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_PASSWORD, "MYPASS");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_TRACE_ON, new Boolean(true));
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(
                "tcp://localhost:3213",
                getJavaObjectRequest100(),
                messageProperties);
        assertNotNull(message);
        assertFalse(message.getPayload() == null);
        assertFalse(message.getExceptionPayload() != null);
        assertFalse(message.getPayload() instanceof NullPayload);
        assertTrue(message.getPayload() instanceof byte[]);
        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream((byte[]) message.getPayload()));
        checkJavaObjectReply100((Dfhcommarea) in.readObject());
        
    }

    /**
     * @return an instance of a valued java object.
     */
    public static Dfhcommarea getJavaObjectRequest100() {
        ObjectFactory of = new ObjectFactory();
        Dfhcommarea dfhcommarea = of.createDfhcommarea();
        dfhcommarea.setComNumber(100L);
        return dfhcommarea;
    }

    /** 
     * Check the values returned from LSFILAE after they were transformed to Java.
     * @param dfhcommarea the java data object
     */
    public static void checkJavaObjectReply100(final Dfhcommarea dfhcommarea) {
        assertEquals(100, dfhcommarea.getComNumber());
        assertEquals("$0100.11", dfhcommarea.getComAmount());
        assertEquals("*********", dfhcommarea.getComComment());
        assertEquals("26 11 81", dfhcommarea.getComDate());
        assertEquals("SURREY, ENGLAND", dfhcommarea.getComPersonal().getComAddress());
        assertEquals("S. D. BORMAN", dfhcommarea.getComPersonal().getComName());
        assertEquals("32156778", dfhcommarea.getComPersonal().getComPhone());
    }
}
