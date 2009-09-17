/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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
 * <code>LegstarWmqMessageDispatcher</code>  sends requests to the mainframe over WMQ.
 */
public class LegstarWmqMessageDispatcher extends JmsMessageDispatcher {

    /**
     * Constructor.
     * @param endpoint the JMS endpoint
     */
    public LegstarWmqMessageDispatcher(final OutboundEndpoint endpoint) {
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
        String messageFormat = currentMessage.getStringProperty("JMS_IBM_Format");
        if (messageFormat != null && messageFormat.equals("MQCICS  ")) {
            String tempJMSCorrelationID = currentMessage.getJMSCorrelationID();
            currentMessage.setJMSCorrelationID(null);
            MessageConsumer consumer = super.createReplyToConsumer(currentMessage, event, session, replyTo, topic);
            currentMessage.setJMSCorrelationID(tempJMSCorrelationID);
            return consumer;
        } else {
            return super.createReplyToConsumer(currentMessage, event, session, replyTo, topic);
        }

    }
}

