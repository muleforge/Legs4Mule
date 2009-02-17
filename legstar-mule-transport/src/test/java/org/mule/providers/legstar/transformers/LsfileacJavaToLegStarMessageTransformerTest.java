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

import java.util.HashMap;

import org.mule.impl.RequestContext;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.UMOEvent;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.coxb.host.HostData;
import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.coxb.transform.HostTransformException;
import com.legstar.test.coxb.LsfileacCases;
import com.legstar.test.coxb.lsfileac.ReplyData;
import com.legstar.test.coxb.lsfileac.ReplyStatus;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusTransformers;

/**
 * Test AbstractJavaToHostTransformer class.
 *
 */
public class LsfileacJavaToLegStarMessageTransformerTest extends AbstractTransformerTestCase {

    /**
     * A simplistic implementation of the abstract class being tested.
     *
     */
    public static class LsfileacJavaToHostTransformer extends AbstractJavaToHostEsbTransformer {

        /**
         * Pass binding transformers for lsfileac multi part payload.
         */
        public LsfileacJavaToHostTransformer() {
            super(new HashMap < String, AbstractTransformers >());
            getBindingTransformersMap().put("ReplyData", new ReplyDataTransformers());
            getBindingTransformersMap().put("ReplyStatus", new ReplyStatusTransformers());
            registerSourceType(LsfileacHolder.class);
        }

        /** {@inheritDoc} */
        public Object getObjectFromHolder(
                final Object holderObject,
                final String partID) throws TransformerException {
            
            if (partID.equals("ReplyData")) {
                return ((LsfileacHolder) holderObject).getReplyData();
            } else if (partID.equals("ReplyStatus")) {
                return ((LsfileacHolder) holderObject).getReplyStatus();
            }
            return null;
        }
    }

    /** {@inheritDoc} */
    protected void doSetUp() throws Exception {
        /* Override to set message properties */
        UMOEvent event = getTestEvent("test");
        event.getMessage().setBooleanProperty(
                AbstractHostEsbTransformer.IS_LEGSTAR_MESSAGING, true);
        event.getMessage().setStringProperty(
                AbstractJavaToHostEsbTransformer.PROGRAM_PROP_FILE_NAME,
                "lsfileac.properties");
        RequestContext.setEvent(event);
    }

    /** {@inheritDoc} */
    public UMOTransformer getTransformer() throws Exception {
        return new LsfileacJavaToHostTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return HostData.toByteArray(
                LsfileacLegStarMessageToJavaTransformerTest.LSFILEAC_MESSAGE_HOST_DATA);
    }

    /** {@inheritDoc} */
    public UMOTransformer getRoundTripTransformer() throws Exception {
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

    /**
     * A Holder class that aggregates 2 classes.
     *
     */
    public static class LsfileacHolder {
        
        /** Inner ReplyData JAXB-bound object. */
        private ReplyData replyData;
 
        /** Inner ReplyStatus JAXB-bound object. */
        private ReplyStatus replyStatus;
        /**
         * Get the inner ReplyData JAXB-bound object.
         * @return JAXB-bound object
         */
        public final ReplyData getReplyData() {
            return replyData;
        }

        /**
         * Set the inner ReplyData JAXB-bound object.
         * @param value JAXB-bound object
         */
        public final void setReplyData(
            final ReplyData value) {
                replyData = value;
        }
        /**
         * Get the inner ReplyStatus JAXB-bound object.
         * @return JAXB-bound object
         */
        public final ReplyStatus getReplyStatus() {
            return replyStatus;
        }

        /**
         * Set the inner ReplyStatus JAXB-bound object.
         * @param value JAXB-bound object
         */
        public final void setReplyStatus(
            final ReplyStatus value) {
                replyStatus = value;
        }
    }

}
