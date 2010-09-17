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
package org.mule.transport.legstar.transformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.coxb.transform.HostTransformException;
import com.legstar.test.coxb.LsfileacCases;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusTransformers;

/**
 * Test AbstractJavaToHostTransformer class.
 *
 */
public class LsfileacJavaToHostTransformerTest extends AbstractTransformerTestCase {

    
    /** {@inheritDoc} */
    public AbstractMessageTransformer getTransformer() throws Exception {
    	LsfileacJavaToHostTransformer transformer = new LsfileacJavaToHostTransformer();
    	transformer.setMuleContext(muleContext);
        return transformer;
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        Map < String, byte[]> testData = new HashMap < String, byte[]>();
        testData.put("ReplyData",
                HostData.toByteArray(LsfileacCases.getHostBytesHexReplyData()));
        testData.put("ReplyStatus",
                HostData.toByteArray(LsfileacCases.getHostBytesHexReplyStatus()));
        return testData;
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        LsfileacHolder holder = new LsfileacHolder();
        try {
            holder.setReplyData(new ReplyDataTransformers().toJava(
                    HostData.toByteArray(LsfileacCases.getHostBytesHexReplyData())));
            holder.setReplyStatus(new ReplyStatusTransformers().toJava(
                    HostData.toByteArray(LsfileacCases.getHostBytesHexReplyStatus())));
        } catch (HostTransformException e) {
            fail(e.getMessage());
        }
        return holder;
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

}
