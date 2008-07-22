/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/

package org.mule.providers.legstar.transformers;

import java.io.ByteArrayInputStream;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.messaging.LegStarMessage;
import com.legstar.util.Util;


public class LegstarTransformersTestCase extends AbstractTransformerTestCase
{

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getTestData()
     */
    public Object getTestData()
    {
        try {
            byte[] hostBytes = Util.toByteArray("d3e2d6d2c8c5c1c4404040404040404000000077000000020000006fc07fc3c9c3e2d6a4a3c39695a38189958599a27f7aad7fd9859793a8c481a3817f6b7fd9859793a8e2a381a3a4a27fbd6b7fc3c9c3e2c38881959585937f7a7fd3e2c6c9d3c5c1c360c3c8c1d5d5c5d37f6b7fc3c9c3e2d7999687998194d58194857f7a7fd3e2c6c9d3c5c1c37fd0d8a48599a8c481a3814040404040404000000004f1f2f3f4d8a48599a8d3899489a340404040404000000002f5f6");
            ByteArrayInputStream hostStream = new ByteArrayInputStream(hostBytes);
            LegStarMessage message = new LegStarMessage();
            message.recvFromHost(hostStream);
            return message;
        }
        catch (Exception ex) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getResultData()
     */
    public Object getResultData()
    {
        byte[] hostBytes = Util.toByteArray("d3e2d6d2c8c5c1c4404040404040404000000077000000020000006fc07fc3c9c3e2d6a4a3c39695a38189958599a27f7aad7fd9859793a8c481a3817f6b7fd9859793a8e2a381a3a4a27fbd6b7fc3c9c3e2c38881959585937f7a7fd3e2c6c9d3c5c1c360c3c8c1d5d5c5d37f6b7fc3c9c3e2d7999687998194d58194857f7a7fd3e2c6c9d3c5c1c37fd0d8a48599a8c481a3814040404040404000000004f1f2f3f4d8a48599a8d3899489a340404040404000000002f5f6");
        return hostBytes;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getTransformers()
     */
    public UMOTransformer getTransformer()
    {
        UMOTransformer t = new LegStarMessageToByteArray();
        // Set the correct return class for this roundtrip test
        t.setReturnClass(this.getResultData().getClass());
        return t;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getRoundTripTransformer()
     */
    public UMOTransformer getRoundTripTransformer()
    {
        UMOTransformer t = new ByteArrayToLegStarMessage();
        // Set the correct return class for this roundtrip test
        t.setReturnClass(this.getTestData().getClass());
        return t;
    }

}
