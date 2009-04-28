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

import java.util.HashMap;
import java.util.Map;

import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileacCases;

/**
 * Test AbstractHostToJavaTransformer class.
 *
 */
public class LsfileacHostToJavaTransformerTest extends AbstractTransformerTestCase {

    /** {@inheritDoc} */
    public Transformer getTransformer() throws Exception {
        return new LsfileacHostToJavaTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        LsfileacHolder holder = new LsfileacHolder();
        holder.setReplyData(null);
        holder.setReplyStatus(null);
        return holder;
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        Map < String, byte[]> testData = new HashMap < String, byte[]>();
        testData.put("ReplyData",
                HostData.toByteArray(LsfileacCases.getHostBytesHexReplyData()));
        testData.put("ReplyStatus",
                HostData.toByteArray(LsfileacCases.getHostBytesHexReplyStatus()));
        return testData;
    }

    /** {@inheritDoc} */
    public boolean compareResults(final Object expected, final Object result) {
        if (result == null || !(result instanceof LsfileacHolder)) {
            return false;
        }
        LsfileacHolder holder = (LsfileacHolder) result;
        LsfileacCases.checkJavaObjectReplyData(holder.getReplyData());
        LsfileacCases.checkJavaObjectReplyStatus(holder.getReplyStatus());
        return true;
    }
    
}
