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
package org.mule.transport.legstar.wmq;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.transport.http.ReleasingInputStream;

import com.legstar.test.coxb.LsfileaeCases;
import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.ObjectFactory;

/**
 * Test a roundtrip on the legstar WMQ transport with mqcih messaging.
 *
 */
public class LegstarWmqMqcihFunctionalTestCase extends FunctionalTestCase {
    
    /**
     * Increase the timeout to allow enough time for debugging.
     */
    public LegstarWmqMqcihFunctionalTestCase() {
        super();
        System.setProperty(PROPERTY_MULE_TEST_TIMEOUT, "600");
    }
    
    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "legstar-wmq-mqcih-test-config-lsfileae.xml";
    }

    /**
     * Perform round trip.
     * @throws Exception if test fails
     */
    public void testSend() throws Exception {        
        FunctionalTestComponent service =
            (FunctionalTestComponent) getComponent("lsfileaeAdapterService");
        assertNotNull(service);

        Map < String, Object > properties = new HashMap < String, Object >();
        properties.put(LegstarWmqConnector.HOST_USERID_PROPERTY, "P390");
        properties.put(LegstarWmqConnector.HOST_PASSWORD_PROPERTY, "STREAM2");
        MuleClient client = new MuleClient();
        MuleMessage result = client.send("lsfileaeClientEndpoint",
                getJavaRequest(), properties);

        /* The HTTP transport sends back a java serialized object */
        assertTrue(null == result.getExceptionPayload());
        assertTrue(result.getPayload() instanceof ReleasingInputStream);
        ObjectInputStream in = new ObjectInputStream(
                (ReleasingInputStream) result.getPayload());
        Dfhcommarea reply = (Dfhcommarea) in.readObject();
        LsfileaeCases.checkJavaObjectReply100(reply);
    }

    /**
     * @return a java request object.
     */
    public static Dfhcommarea getJavaRequest() {
        ObjectFactory of = new ObjectFactory();
        Dfhcommarea dfhcommarea = of.createDfhcommarea();
        dfhcommarea.setComNumber(100L);
        return dfhcommarea;
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
