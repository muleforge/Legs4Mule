package org.mule.transport.legstar.mock;

import org.mule.transport.AbstractMessageDispatcherFactory;
import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;

/**
 * <code>LegstarMockMessageDispatcherFactory</code> creates dispatchers.
 */
public class LegstarMockMessageDispatcherFactory extends AbstractMessageDispatcherFactory {

    /** {@inheritDoc} */
    public MessageDispatcher create(final OutboundEndpoint endpoint) throws MuleException {
        return new LegstarMockMessageDispatcher(endpoint);
    }

}
