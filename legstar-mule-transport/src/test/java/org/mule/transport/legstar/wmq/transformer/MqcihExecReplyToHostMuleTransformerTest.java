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
package org.mule.transport.legstar.wmq.transformer;

import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;

/**
 * Test MqcihToHostMuleTransformer class.
 *
 */
public class MqcihExecReplyToHostMuleTransformerTest extends AbstractTransformerTestCase {

    /** This makes sure there is a single instance of test data. */
    private static byte[] _TestData;
    
    /**
     * Constructor.
     */
    public MqcihExecReplyToHostMuleTransformerTest() {
        super();
        _TestData = HostData.toByteArray(LsfileaeCases.getHostBytesHex());
    }
    
    /** {@inheritDoc} */
    public AbstractMessageAwareTransformer getTransformer() throws Exception {
        MqcihExecReplyToHostMuleTransformer transformer = new MqcihExecReplyToHostMuleTransformer();
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
        return HostData.toByteArray(
                HostToMqcihExecRequestMuleTransformerTest.MQCIH_MESSAGE_HOST_DATA);
    }

}
