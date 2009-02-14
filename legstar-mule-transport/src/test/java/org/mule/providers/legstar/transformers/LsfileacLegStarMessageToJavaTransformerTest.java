package org.mule.providers.legstar.transformers;

import java.util.HashMap;
import java.util.Map;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.coxb.host.HostData;
import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.test.coxb.LsfileacCases;
import com.legstar.test.coxb.lsfileac.ReplyData;
import com.legstar.test.coxb.lsfileac.ReplyStatus;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusTransformers;

/**
 * Test AbstractHostToJavaTransformer class.
 *
 */
public class LsfileacLegStarMessageToJavaTransformerTest extends AbstractTransformerTestCase {

    /** A sample LegStarMessage content.*/
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


    /**
     * A simplistic implementation of the abstract class being tested.
     *
     */
    public static class LsfileacHostToJavaTransformer extends AbstractHostToJavaEsbTransformer {

        /**
         * Pass binding transformers for lsfileac multi part holder.
         */
        public LsfileacHostToJavaTransformer() {
            super(new HashMap < String, AbstractTransformers >());
            getBindingTransformersMap().put("ReplyData", new ReplyDataTransformers());
            getBindingTransformersMap().put("ReplyStatus", new ReplyStatusTransformers());
            setReturnClass(LsfileacHolder.class);
        }

        /** {@inheritDoc} */
        public Object createHolder(
                final Map < String, Object > transformedParts) throws TransformerException {
            LsfileacHolder holder = new LsfileacHolder();
            holder.setReplyData((ReplyData) transformedParts.get("ReplyData"));
            holder.setReplyStatus((ReplyStatus) transformedParts.get("ReplyStatus"));
            return holder;
        }
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

    /** {@inheritDoc} */
    public UMOTransformer getTransformer() throws Exception {
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
    public UMOTransformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return HostData.toByteArray(LSFILEAC_MESSAGE_HOST_DATA);
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
