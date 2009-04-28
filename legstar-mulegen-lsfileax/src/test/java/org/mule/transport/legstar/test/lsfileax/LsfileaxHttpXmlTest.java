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
package org.mule.transport.legstar.test.lsfileax;

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.api.MuleMessage;

/**
 * Test the adapter for the LSFILEAC/LSFILEAE mainframe programs.
 * <p/>
 * Adapter transport is HTTP. The LegStar mainframe modules for HTTP must
 * be installed on the mainframe:
 * {@link http://www.legsem.com/legstar/legstar-distribution-zos/}.
 * <p/>
 * Client sends/receive XML.
 */
public class LsfileaxHttpXmlTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-http-config-xml-lsfileax.xml";
    }
    
    /**
     * Run the target LSFILEAC mainframe program.
     * Client sends an XML string and receive one as a reply.
     * @throws Exception if test fails
     */
    public void testLsfileac() throws Exception {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(
                "lsfileacClientEndpoint",
                getXmlLsfileacRequest(),
                null);
        checkXmlLsfileacReply(new String(message.getPayloadAsBytes()));
        
    }

    /**
     * Run the target LSFILEAE mainframe program.
     * Client sends an XML string and receive one as a reply.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send(
                "lsfileaeClientEndpoint",
                getXmlLsfileaeRequest(),
                null);
        checkXmlLsfileaeReply(new String(message.getPayloadAsBytes()));
        
    }

    /**
     * @return an XML serialization of the request
     */
    public static String getXmlLsfileaeRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<Dfhcommarea xmlns=\"http://legstar.com/test/coxb/lsfileae\">"
        + "<ComNumber>100</ComNumber>"
        + "</Dfhcommarea>";
    }

    /** 
     * Check the values returned from LSFILEAE after they were transformed to XML.
     * @param replyXml the XML reply
     */
    public static void checkXmlLsfileaeReply(final String replyXml) {
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
    /**
     * @return an XML serialization of the request
     */
    public static String getXmlLsfileacRequest() {
        return
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
        + "<LsfileacRequestHolder xmlns=\"http://cixs.test.legstar.com/lsfileax\">"
        + "<QueryData xmlns=\"http://legstar.com/test/coxb/lsfileac\">"
        + "<QueryName>S*</QueryName>"
        + "<QueryAddress>*</QueryAddress>"
        + "<QueryPhone>*</QueryPhone>"
        + "</QueryData>"
        + "<QueryLimit xmlns=\"http://legstar.com/test/coxb/lsfileac\">"
        + "<MaxElapseTime>5000</MaxElapseTime>"
        + "<MaxItemsRead>100</MaxItemsRead>"
        + "</QueryLimit>"
        + "</LsfileacRequestHolder>";
    }

    /** 
     * Check the values returned from LSFILEAC after they were transformed to XML.
     * @param replyXml the XML reply
     */
    public static void checkXmlLsfileacReply(final String replyXml) {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
                + "<LsfileacResponseHolder xmlns=\"http://cixs.test.legstar.com/lsfileax\">"
                + "<ReplyData xmlns=\"http://legstar.com/test/coxb/lsfileac\">"
                + "<ReplyItemscount>5</ReplyItemscount>"
                + "<ReplyItem>"
                + "<ReplyNumber>100</ReplyNumber>"
                + "<ReplyPersonal>"
                + "<ReplyName>S. D. BORMAN</ReplyName>"
                + "<ReplyAddress>SURREY, ENGLAND</ReplyAddress>"
                + "<ReplyPhone>32156778</ReplyPhone>"
                + "</ReplyPersonal>"
                + "<ReplyDate>26 11 81</ReplyDate>"
                + "<ReplyAmount>$0100.11</ReplyAmount>"
                + "<ReplyComment>*********</ReplyComment>"
                + "</ReplyItem>"
                + "<ReplyItem>"
                + "<ReplyNumber>762</ReplyNumber>"
                + "<ReplyPersonal>"
                + "<ReplyName>SUSAN MALAIKA</ReplyName>"
                + "<ReplyAddress>SAN JOSE,CALIFORNIA</ReplyAddress>"
                + "<ReplyPhone>22312121</ReplyPhone>"
                + "</ReplyPersonal>"
                + "<ReplyDate>01 06 74</ReplyDate>"
                + "<ReplyAmount>$0000.00</ReplyAmount>"
                + "<ReplyComment>*********</ReplyComment>"
                + "</ReplyItem>"
                + "<ReplyItem>"
                + "<ReplyNumber>6016</ReplyNumber>"
                + "<ReplyPersonal>"
                + "<ReplyName>SIR MICHAEL ROBERTS</ReplyName>"
                + "<ReplyAddress>NEW DELHI, INDIA</ReplyAddress>"
                + "<ReplyPhone>70331211</ReplyPhone>"
                + "</ReplyPersonal>"
                + "<ReplyDate>21 05 74</ReplyDate>"
                + "<ReplyAmount>$0009.88</ReplyAmount>"
                + "<ReplyComment>*********</ReplyComment>"
                + "</ReplyItem>"
                + "<ReplyItem>"
                + "<ReplyNumber>200000</ReplyNumber>"
                + "<ReplyPersonal>"
                + "<ReplyName>S. P. RUSSELL</ReplyName>"
                + "<ReplyAddress>GLASGOW,  SCOTLAND</ReplyAddress>"
                + "<ReplyPhone>63738290</ReplyPhone>"
                + "</ReplyPersonal>"
                + "<ReplyDate>26 11 81</ReplyDate>"
                + "<ReplyAmount>$0020.00</ReplyAmount>"
                + "<ReplyComment>*********</ReplyComment>"
                + "</ReplyItem>"
                + "<ReplyItem>"
                + "<ReplyNumber>555555</ReplyNumber>"
                + "<ReplyPersonal>"
                + "<ReplyName>S.J. LAZENBY</ReplyName>"
                + "<ReplyAddress>KINGSTON, N.Y.</ReplyAddress>"
                + "<ReplyPhone>39944420</ReplyPhone>"
                + "</ReplyPersonal>"
                + "<ReplyDate>26 11 81</ReplyDate>"
                + "<ReplyAmount>$0005.00</ReplyAmount>"
                + "<ReplyComment>*********</ReplyComment>"
                + "</ReplyItem>"
                + "</ReplyData>"
                + "<ReplyStatus xmlns=\"http://legstar.com/test/coxb/lsfileac\">"
                + "<ReplyType>0</ReplyType>"
                + "<SearchDuration>00:00:00</SearchDuration>"
                + "<TotalItemsRead>43</TotalItemsRead>"
                + "<ReplyResp>0</ReplyResp>"
                + "<ReplyResp2>0</ReplyResp2>"
                + "<ReplyMessage/>"
                + "</ReplyStatus>"
                + "</LsfileacResponseHolder>",
                replyXml);
    }
}
