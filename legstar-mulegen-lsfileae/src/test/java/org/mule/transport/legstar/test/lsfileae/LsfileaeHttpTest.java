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

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.api.MuleMessage;

import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.ObjectFactory;

/**
 * Test the adapter for the LSFILEAE mainframe program.
 * <p/>
 * Adapter transport is HTTP. The LegStar mainframe modules for HTTP must
 * be installed on the mainframe:
 * {@link http://www.legsem.com/legstar/legstar-distribution-zos/}.
 * <p/>
 * Client sends/receive serialized java objects.
 */
public class LsfileaeHttpTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-http-config-lsfileae.xml";
    }
    
    /**
     * Run the target LSFILEAE mainframe program.
     * Client sends a serialized java object and receive one as a reply.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(
                "lsfileaeClientEndpoint",
                getJavaObjectRequest100(), null);
        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream((byte[]) message.getPayload()));
        checkJavaObjectReply100((Dfhcommarea) in.readObject());
        
    }

    /**
     * @return an instance of a the java request object.
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
