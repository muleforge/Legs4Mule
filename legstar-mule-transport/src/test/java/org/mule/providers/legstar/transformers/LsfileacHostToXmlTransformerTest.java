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
package org.mule.providers.legstar.transformers;


import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;


/**
 * Test AbstractHostToXmlTransformer class.
 *
 */
public class LsfileacHostToXmlTransformerTest extends AbstractTransformerTestCase {

    /** A sample of a serialized holder.*/
    public static final String LSFILEAC_XML_SAMPLE =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
        + "<LsfileacResponseHolder xmlns=\"http://cixs.test.legstar.com/lsfileac\">"
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
        + "</LsfileacResponseHolder>";
    
    /** {@inheritDoc} */
    public Transformer getTransformer() throws Exception {
        return new LsfileacHostToXmlTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return LSFILEAC_XML_SAMPLE;
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return HostData.toByteArray(
                LsfileacHostToJavaTransformerTest.LSFILEAC_MESSAGE_HOST_DATA);
    }

    /** {@inheritDoc} */
    public boolean compareResults(final Object expected, final Object result) {
        if (result == null || !(result instanceof String)) {
            return false;
        }
        if (!getResultData().equals((String) result)) {
            return false;
        }
        return true;
    }
}
