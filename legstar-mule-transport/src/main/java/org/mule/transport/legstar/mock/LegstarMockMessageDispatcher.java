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
package org.mule.transport.legstar.mock;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.DispatchException;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.DefaultMuleMessageFactory;
import org.mule.transport.legstar.i18n.LegstarMessages;

import com.legstar.messaging.LegStarConnection;
import com.legstar.messaging.LegStarMessage;
import com.legstar.messaging.LegStarRequest;
import com.legstar.mock.client.MockConnectionFactory;

/**
 * <code>LegstarMockMessageDispatcher</code> simulates dispatching request
 * to the mainframe and receiving responses.
 */
public class LegstarMockMessageDispatcher extends AbstractMessageDispatcher {

    /** A simulated mainframe connection. */
    private LegStarConnection _mockConnection;
    
    /** Localized common messages. */
    private static final LegstarMessages I18N_COMMON = new LegstarMessages();
    
    /**
     * Constructor.
     * @param endpoint the endpoint to dispatch to
     */
    public LegstarMockMessageDispatcher(final OutboundEndpoint endpoint) {
        super(endpoint);
    }

    /** {@inheritDoc} */
    public void doConnect() throws Exception {
        MockConnectionFactory mockConnectionFactory = new MockConnectionFactory();
        _mockConnection = mockConnectionFactory.createConnection("connectionID", null, null);
    }

    /** {@inheritDoc} */
    public void doDisconnect() throws Exception {
    }

    /** {@inheritDoc} */
    public void doDispatch(final MuleEvent event) throws Exception {
        throw new UnsupportedOperationException("doDispatch");
    }

    /**
     *  {@inheritDoc}
     *  The MuleMessage body is assumed to be a LegStarMessage serialized in
     *  a host byte array (in host character set).
     *  
     *   */
    public MuleMessage doSend(final MuleEvent event) throws Exception {
        MuleMessage requestMuleMessage = event.getMessage();
        Object body = requestMuleMessage.getPayload();
        if (body instanceof byte[]) {
            LegStarMessage legstarRequestMessage = new LegStarMessage();
            legstarRequestMessage.fromByteArray((byte[]) body, 0);
            LegStarRequest legStarRequest = new LegStarRequest(
                    requestMuleMessage.getCorrelationId(), null, legstarRequestMessage);
            getMockConnection().recvResponse(legStarRequest);
            LegStarMessage legstarReplyMessage = legStarRequest.getResponseMessage();
            MuleMessageFactory messageFactory =
            	new DefaultMuleMessageFactory(getConnector().getMuleContext());
            return messageFactory.create(legstarReplyMessage.toByteArray(),
            		getEndpoint().getEncoding());
        } else {
            throw new DispatchException(I18N_COMMON.invalidBodyMessage(),
            		event, this);  
        }
    }

    /** {@inheritDoc} */
    public void doDispose() {
    }

    /**
     * @return the mock connection
     */
    public LegStarConnection getMockConnection() {
        return _mockConnection;
    }

}

