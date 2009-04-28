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
package org.mule.transport.legstar.test.lsfileal;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.api.MuleMessage;

import com.legstar.test.coxb.LsfilealCases;
import com.legstar.test.coxb.lsfileal.ReplyData;
import com.legstar.test.coxb.lsfileal.RequestParms;

/**
 * Test the adapter for the LSFILEAL mainframe program.
 * <p/>
 * Adapter transport is HTTP. The LegStar mainframe modules for HTTP must
 * be installed on the mainframe:
 * {@link http://www.legsem.com/legstar/legstar-distribution-zos/}.
 * <p/>
 * Client sends/receive serialized java objects.
 */
public class LsfilealHttpTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-http-config-lsfileal.xml";
    }
    
    /**
     * Run the target LSFILEAL mainframe program.
     * Client sends a serialized java object and receive one as a reply.
     * @throws Exception if test fails
     */
    public void testLsfileal() throws Exception {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(
                "lsfilealClientEndpoint",
                getJavaObjectRequest(), null);
        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream((byte[]) message.getPayload()));
        checkJavaObjectReply((ReplyData) in.readObject());
        
    }

    /**
     * @return an instance of a the java request object.
     */
    public static RequestParms getJavaObjectRequest() {
        return LsfilealCases.getJavaObjectRequestSStar();
    }

    /** 
     * Check the values returned from LSFILEAL after they were transformed to Java.
     * @param replyObject the java data object
     */
    public static void checkJavaObjectReply(final ReplyData replyObject) {
        LsfilealCases.checkJavaObjectReeplySStar(replyObject);
    }
}
