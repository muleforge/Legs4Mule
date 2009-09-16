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

import java.util.HashMap;
import java.util.Map;

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.legstar.tcp.LegstarTcpConnector;
import org.mule.api.MuleMessage;

/**
 * Test the adapter for the LSFILEAE mainframe program.
 * <p/>
 * Adapter transport is TCP. The LegStar mainframe modules for TCP must
 * be installed on the mainframe:
 * {@link tcp://www.legsem.com/legstar/legstar-distribution-zos/}.
 * <p/>
 * Client sends/receive XML.
 */
public class LsfileaeTcpXmlTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-config-lsfileae-tcp-tcp-xml-legstar.xml";
    }
    
    /**
     * Run the target LSFILEAE mainframe program.
     * Client sends an XML string and receive one as a reply.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        MuleClient client = new MuleClient();
        Map < String, Object > properties = new HashMap < String, Object >();
        properties.put(LegstarTcpConnector.HOST_USERID_PROPERTY, "P390");
        properties.put(LegstarTcpConnector.HOST_PASSWORD_PROPERTY, "STREAM2");
        MuleMessage message = client.send(
                "lsfileaeClientEndpoint",
                getXmlRequest(),
                properties);
        checkXmlReply(new String(message.getPayloadAsBytes()));
        
    }

    /**
     * @return an XML serialization of the request
     */
    public static String getXmlRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<Dfhcommarea xmlns=\"http://legstar.com/test/coxb/lsfileae\">"
        + "<ComNumber>100</ComNumber>"
        + "</Dfhcommarea>";
    }

    /** 
     * Check the values returned from LSFILEAE after they were transformed to XML.
     * @param replyXml the XML reply
     */
    public static void checkXmlReply(final String replyXml) {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Dfhcommarea xmlns=\"http://legstar.com/test/coxb/lsfileae\">"
                + "<ComNumber>100</ComNumber>"
                + "<ComPersonal>"
                + "<ComName>S. D. BORMAN</ComName>"
                + "<ComAddress>SURREY, ENGLAND</ComAddress>"
                + "<ComPhone>32156778</ComPhone>"
                + "</ComPersonal>"
                + "<ComDate>26 11 81</ComDate>"
                + "<ComAmount>$0100.11</ComAmount>"
                + "<ComComment>*********</ComComment>"
                + "</Dfhcommarea>",
                replyXml);
    }
}
