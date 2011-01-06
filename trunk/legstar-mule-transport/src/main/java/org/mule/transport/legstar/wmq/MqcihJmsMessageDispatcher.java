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
package org.mule.transport.legstar.wmq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.mule.api.MuleEvent;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.transport.jms.JmsMessageDispatcher;

/**
 * This is an override of the standard JmsMessageDispatcher needed to
 * handle CICS MQ Bridge specific correlation needs.
 * <p/>
 * We assume that interacting with CICS MQ Bridge is always synchronous.
 *
 */
public class MqcihJmsMessageDispatcher extends JmsMessageDispatcher {

    /**
     * Constructor.
     * @param endpoint the JMS endpoint
     */
    public MqcihJmsMessageDispatcher(final OutboundEndpoint endpoint) {
        super(endpoint);
    }
    
    /**
     * {@inheritDoc}
     * The standard JmsMessageDispatcher#createReplyToConsumer will use the message ID
     * as the selector only if JMSMessageID is null. In the case of CICS MQ Bridge,
     * the JMSMessageID is not null but yet, the message ID must be used as the
     * correlator.
     * */
    protected MessageConsumer createReplyToConsumer(
            final Message currentMessage,
            final MuleEvent event,
            final Session session,
            final Destination replyTo,
            final boolean topic) throws JMSException {
        String tempJMSCorrelationID = currentMessage.getJMSCorrelationID();
        currentMessage.setJMSCorrelationID(null);
        MessageConsumer consumer = super.createReplyToConsumer(currentMessage, event, session, replyTo, topic);
        currentMessage.setJMSCorrelationID(tempJMSCorrelationID);
        return consumer;
        
    }
    
}
