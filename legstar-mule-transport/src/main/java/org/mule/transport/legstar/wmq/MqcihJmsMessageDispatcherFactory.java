package org.mule.transport.legstar.wmq;

import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.transport.AbstractMessageDispatcherFactory;

/**
 * <code>MqcihJmsMessageDispatcherFactory</code> returns instances
 * of <code>MqcihJmsMessageDispatcher</code>.
 */
public class MqcihJmsMessageDispatcherFactory extends AbstractMessageDispatcherFactory {

    /** {@inheritDoc} */
    public final MessageDispatcher create(final OutboundEndpoint endpoint) throws MuleException
    {
        return new MqcihJmsMessageDispatcher(endpoint);
    }
}
