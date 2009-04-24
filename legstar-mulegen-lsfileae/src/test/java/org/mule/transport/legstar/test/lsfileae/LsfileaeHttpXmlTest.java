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
import org.mule.transport.legstar.cixs.MuleHostHeaderFactory;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.NullPayload;
import org.mule.api.MuleMessage;

/**
 * Test the adapter for LSFILEAE in a bridge configuration (using legstar-mule HTTP transport).
 * This test requires access to an actual mainframe running FILEA sample.
 */
public class LsfileaeHttpXmlTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-http-config-xml-lsfileae.xml";
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
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_TRACE_MODE, new Boolean(true));
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(
                "tcp://localhost:3213",
                getXmlRequest100(),
                messageProperties);
        assertNotNull(message);
        assertFalse(message.getExceptionPayload() != null);
        Object res = message.getPayload();
        assertFalse(res == null);
        assertFalse(res instanceof NullPayload);
        if (res instanceof byte[]) {
            checkXmlReply100(new String((byte[]) res));
        } else {
            fail();
        }
        
    }

    /**
     * @return an XML serialization of the object
     */
    public static String getXmlRequest100() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<Dfhcommarea xmlns=\"http://legstar.com/test/coxb/lsfileae\">"
        + "<ComNumber>100</ComNumber>"
        + "</Dfhcommarea>";
    }

    /** 
     * Check the values returned from LSFILAE after they were transformed to XML.
     * @param dfhcommareaXml the XML reply
     */
    public static void checkXmlReply100(final String dfhcommareaXml) {
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
                dfhcommareaXml);
    }
}
