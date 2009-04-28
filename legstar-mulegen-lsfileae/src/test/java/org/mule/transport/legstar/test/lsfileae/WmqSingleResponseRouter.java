package org.mule.transport.legstar.test.lsfileae;

import org.mule.api.routing.MessageInfoMapping;
import org.mule.routing.response.SingleResponseRouter;

public class WmqSingleResponseRouter extends SingleResponseRouter {

    protected MessageInfoMapping messageInfoMapping = new WmqMessageInfoMapping();

    @Override
    public MessageInfoMapping getMessageInfoMapping() {
        return messageInfoMapping;
    }

}
