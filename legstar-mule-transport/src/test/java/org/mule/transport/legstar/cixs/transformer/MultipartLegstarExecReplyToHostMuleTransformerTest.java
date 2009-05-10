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
package org.mule.transport.legstar.cixs.transformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileacCases;

/**
 * Test HostToLegstarMuleTransformer class.
 *
 */
public class MultipartLegstarExecReplyToHostMuleTransformerTest extends AbstractTransformerTestCase {

    /** A sample LegStarMessage content for multi part target program.*/
    public static final String LSFILEAC_MESSAGE_HOST_DATA =
        /*L S O K H E A D                 (Message part ID)*/
        "d3e2d6d2c8c5c1c44040404040404040"
        /*      119                       (Message part content length)*/
        + "00000077"
        /*        2                       (Header part number of parts)*/
        + "00000002"
        /*      111                       (Header part JSON string length)*/
        + "0000006f"
        /* { " C I C S O u t C o n t a i n e r s " : [ " R e p l y D a t a " , " R e p l y S t a t u s " ] , */
        + "c07fc3c9c3e2d6a4a3c39695a38189958599a27f7aad7fd9859793a8c481a3817f6b7fd9859793a8e2a381a3a4a27fbd6b"
        /* " C I C S C h a n n e l " : " L S F I L E A C - C H A N N E L " , */
        + "7fc3c9c3e2c38881959585937f7a7fd3e2c6c9d3c5c1c360c3c8c1d5d5c5d37f6b"
        /* " C I C S P r o g r a m N a m e " : " L S F I L E A C " } */
        + "7fc3c9c3e2d7999687998194d58194857f7a7fd3e2c6c9d3c5c1c37fd0"
 
        /* R e p l y D a t a              (Message part ID)*/
        + "d9859793a8c481a38140404040404040"
        /*        ?                       (Message part content length)*/
        + integerToString(LsfileacCases.getHostBytesHexReplyData().length() / 2)
        + LsfileacCases.getHostBytesHexReplyData()
        
        /* R e p l y S t a t u s           (Message part ID)*/
        + "d9859793a8e2a381a3a4a24040404040"
        /*         2                       (Message part content length)*/
        + integerToString(LsfileacCases.getHostBytesHexReplyStatus().length() / 2)
        + LsfileacCases.getHostBytesHexReplyStatus();


    /** This makes sure there is a single instance of test data. */
    private static Map < String, byte[]> _TestData;
    
    /**
     * Constructor.
     */
    public MultipartLegstarExecReplyToHostMuleTransformerTest() {
        super();
        _TestData = new HashMap < String, byte[]>();
        _TestData.put("ReplyData",
                HostData.toByteArray(LsfileacCases.getHostBytesHexReplyData()));
        _TestData.put("ReplyStatus",
                HostData.toByteArray(LsfileacCases.getHostBytesHexReplyStatus()));
    }
    
    /** {@inheritDoc} */
    public AbstractMessageAwareTransformer getTransformer() throws Exception {
        LegstarExecReplyToHostMuleTransformer transformer = new LegstarExecReplyToHostMuleTransformer();
        return transformer;
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return _TestData;
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return HostData.toByteArray(LSFILEAC_MESSAGE_HOST_DATA);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public boolean compareResults(final Object expected, final Object result) {
        if (result == null || !(result instanceof Map)) {
            return false;
        }
        Map < String, byte[] > expectedMap = (Map < String, byte[] >) expected;
        Map < String, byte[] > resultMap = (Map < String, byte[] >) result;
        
        if (!Arrays.equals(expectedMap.get("ReplyData"),
                resultMap.get("ReplyData"))) {
            return false;
        }
        if (!Arrays.equals(expectedMap.get("ReplyStatus"),
                resultMap.get("ReplyStatus"))) {
            return false;
        }
        
        return true;
    }
    /**
     * @param i an integer to convert
     * @return a String with the hex representation on 4 bytes of an integer.
     */
    private static String integerToString(final int i) {
        String fullz = "00000000";
        String raw = Integer.toHexString(i);
        if (raw.length() > 8) {
            return raw.substring(0, 8);
        } else if (raw.length() < 8) {
            return fullz.substring(0, 8 - raw.length()) + raw;
        } else {
            return raw;
        }
        
    }
}
