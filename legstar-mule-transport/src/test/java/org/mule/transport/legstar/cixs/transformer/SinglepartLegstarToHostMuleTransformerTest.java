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

import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;

/**
 * Test HostToLegstarMuleTransformer class.
 *
 */
public class SinglepartLegstarToHostMuleTransformerTest extends AbstractTransformerTestCase {

    /** A LegStarMessage serialization. */
    private static final String LSFILEAE_MESSAGE_HOST_DATA =
        /*L S O K H E A D                 (Message part ID)*/
        "d3e2d6d2c8c5c1c44040404040404040"
        /*       78                       (Message part content length)*/
        + "0000004e"
        /*        1                       (Header part number of parts)*/
        + "00000001"
        /*       70                       (Header part JSON string length)*/
        + "00000046"
        /*  { " C I C S D a t a L e n g t h " : " 7 9 " , " C I C S L e n g t h " : " 7 9 " ,  */
        + "c07fc3c9c3e2c481a381d3859587a3887f7a7ff7f97f6b7fc3c9c3e2d3859587a3887f7a7ff7f97f6b"
        /*  " C I C S P r o g r a m N a m e " : " L S F I L E A E " } L S O K C O M M A R E A        */
        + "7fc3c9c3e2d7999687998194d58194857f7a7fd3e2c6c9d3c5c1c57fd0d3e2d6d2c3d6d4d4c1d9c5c140404040"
        /*       79 0 0 0 1 0 0 T O T O                                 L A B A S   S T R E E T                */
        + "0000004ff0f0f0f1f0f0e3d6e3d640404040404040404040404040404040d3c1c2c1e240e2e3d9c5c5e34040404040404040"
        /* 8 8 9 9 3 3 1 4 1 0 0 4 5 8     0 0 1 0 0 . 3 5 A   V O I R      */
        + "f8f8f9f9f3f3f1f4f1f0f0f4f5f84040f0f0f1f0f04bf3f5c140e5d6c9d9404040";

    /** This makes sure there is a single instance of test data. */
    private static byte[] _TestData;
    
    /**
     * Constructor.
     */
    public SinglepartLegstarToHostMuleTransformerTest() {
        super();
        _TestData = HostData.toByteArray(LsfileaeCases.getHostBytesHex());
    }
    
    /** {@inheritDoc} */
    public AbstractMessageAwareTransformer getTransformer() throws Exception {
        LegstarToHostMuleTransformer transformer = new LegstarToHostMuleTransformer();
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
        return HostData.toByteArray(LSFILEAE_MESSAGE_HOST_DATA);
    }

}
