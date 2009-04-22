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

import org.mule.DefaultMuleMessage;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.MuleMessage;
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

    
    /** This makes sure there is a single instance of test data. */
    public static final Object TEST_DATA = createTestData();
    
    /** {@inheritDoc} */
    public AbstractMessageAwareTransformer getTransformer() throws Exception {
        return new LsfileacJavaToHostTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return HostData.toByteArray(
                LsfileacHostToJavaTransformerTest.LSFILEAC_MESSAGE_HOST_DATA);
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return TEST_DATA;
    }

    /** {@inheritDoc} */
    public static Object createTestData() {
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

    /**
     *  {@inheritDoc}
     * We override this method in order to set the message property that
     * commands legstar messaging and program properties.
     *      */
    public void testTransform() throws Exception  {
        MuleMessage muleMessage = new DefaultMuleMessage(getTestData());
        muleMessage.setBooleanProperty(
                AbstractHostJavaMuleTransformer.IS_LEGSTAR_MESSAGING, true);
        muleMessage.setStringProperty(
                AbstractJavaToHostMuleTransformer.PROGRAM_PROP_FILE_NAME,
                "lsfileac.properties");
        
        Object result = this.getTransformer().transform(muleMessage, "");
        assertNotNull(result);

        Object expectedResult = this.getResultData();
        assertNotNull(expectedResult);

        assertTrue(this.compareResults(expectedResult, result));
    }

}
