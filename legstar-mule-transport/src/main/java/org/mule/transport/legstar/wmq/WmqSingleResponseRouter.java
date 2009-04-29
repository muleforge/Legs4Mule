package org.mule.transport.legstar.wmq;

import org.mule.api.routing.MessageInfoMapping;
import org.mule.routing.response.SingleResponseRouter;

/**
 * Overriding SingleResponseRouter in order to substitute a MessageInfoMapping
 * that generates WMQ compliant IDs.
 *
 */
public class WmqSingleResponseRouter extends SingleResponseRouter {

    /**
     * A simple WMQ compatible ID mapper.
     */
    protected MessageInfoMapping messageInfoMapping = new WmqMessageInfoMapping();

    /** {@inheritDoc} */
    @Override
    public MessageInfoMapping getMessageInfoMapping() {
        return messageInfoMapping;
    }

}
