package org.mule.transport.legstar.mock;


import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;
import org.mule.tck.AbstractMuleTestCase;

import com.legstar.config.Constants;
import com.legstar.coxb.host.HostData;
import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarHeaderPart;
import com.legstar.messaging.LegStarMessage;
import com.legstar.test.coxb.LsfileaeCases;


/**
 * Test the LegstarMockMessageDispatcher class.
 *
 */
public class LegstarMockMessageDispatcherTestCase extends AbstractMuleTestCase {
    
    /**
     * Check that the factory works.
     * @throws Exception if test fails
     */
    public void testFactoryCreation() throws Exception {
        LegstarMockMessageDispatcherFactory factory = new LegstarMockMessageDispatcherFactory();
        MessageDispatcher dispatcher = factory.create(getEndpoint());
        assertTrue(null != dispatcher);
    }
    
    /**
     * Test an actual request.
     * @throws Exception if test fails
     */
    public void testRequest() throws Exception {
        LegstarMockMessageDispatcherFactory factory = new LegstarMockMessageDispatcherFactory();
        MessageDispatcher dispatcher = factory.create(getEndpoint());
        dispatcher.initialise();
        try {
            dispatcher.send(getTestEvent(getEndpoint()));
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().startsWith("Mule message body is not a LegStar message."));
        }
        MuleMessage muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), getEndpoint()));
        assertTrue(muleReplyMessage != null);
        assertTrue(muleReplyMessage.getPayload() instanceof byte[]);
        byte[] replyPayload = (byte[]) muleReplyMessage.getPayload();
        assertTrue(LegStarMessage.isLegStarMessage(replyPayload));
        LegStarMessage replyLegStarMessage = new LegStarMessage();
        replyLegStarMessage.fromByteArray(replyPayload, 0);
        assertEquals(1, replyLegStarMessage.getHeaderPart().getDataPartsNumber());
        assertEquals(LsfileaeCases.getHostBytesHexReply100(),
                HostData.toHexString(replyLegStarMessage.getDataParts().get(0).getContent()));
        
    }

    /**
     * @return an outbound endpoint
     * @throws Exception if endpoint cannot be created
     */
    public OutboundEndpoint getEndpoint() throws Exception {
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(
                new URIBuilder("legstar-mock://localhost"),
                muleContext);
        return muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
                endpointBuilder);
    }
    
    /**
     * Create a message for LSFILEAE customer 100.
     * @return a message ready for transport
     * @throws HeaderPartException if LegStar message cannot be created
     * @throws HostMessageFormatException if message cannot be serialized as a byte array 
     */
    public static byte[] getLsfileaeMessage100() throws HeaderPartException, HostMessageFormatException {
        Map < String, Object > map = new HashMap < String, Object >();
        map.put(Constants.CICS_PROGRAM_NAME_KEY, "LSFILEAE");
        map.put(Constants.CICS_LENGTH_KEY, "79");
        map.put(Constants.CICS_DATALEN_KEY, "6");
        
        LegStarMessage legstarMessage = new LegStarMessage();
        legstarMessage.setHeaderPart(new LegStarHeaderPart(map, 0));
        legstarMessage.addDataPart(new CommareaPart(
                HostData.toByteArray(LsfileaeCases.getHostBytesHexRequest100())));
        return legstarMessage.toByteArray();

    }
}
