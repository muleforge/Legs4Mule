package org.mule.transport.legstar.mock;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.DispatchException;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.DefaultMessageAdapter;
import org.mule.transport.legstar.mock.i18n.LegstarMockMessages;

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
    
    /** Localized messages. */
    private static final LegstarMockMessages I18N = new LegstarMockMessages();
    
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
        Object body = event.transformMessage();
        if (body instanceof byte[]) {
            LegStarMessage legstarRequestMessage = new LegStarMessage();
            legstarRequestMessage.fromByteArray((byte[]) body, 0);
            LegStarRequest legStarRequest = new LegStarRequest(
                    requestMuleMessage.getCorrelationId(), null, legstarRequestMessage);
            getMockConnection().recvResponse(legStarRequest);
            LegStarMessage legstarReplyMessage = legStarRequest.getResponseMessage();
            DefaultMessageAdapter adapter = new DefaultMessageAdapter(
                    legstarReplyMessage.toByteArray());
            return new DefaultMuleMessage(adapter);
        } else {
            throw new DispatchException(I18N.invalidBodyMessage(),
                    requestMuleMessage, event.getEndpoint());  
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

