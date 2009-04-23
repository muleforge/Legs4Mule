package org.mule.transport.legstar.transformer;

import com.legstar.test.coxb.lsfileac.ReplyData;
import com.legstar.test.coxb.lsfileac.ReplyStatus;

/**
 * A Holder class that aggregates 2 classes.
 *
 */
public class LsfileacHolder {

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

