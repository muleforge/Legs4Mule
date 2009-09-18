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
package org.mule.transport.legstar.tcp;


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
public class LegstarTcpMessageDispatcherTestCase extends AbstractMuleTestCase {
    
    /**
     * Check that the factory works.
     * @throws Exception if test fails
     */
    public void testFactoryCreation() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        MessageDispatcher dispatcher = factory.create(getEndpoint());
        assertTrue(null != dispatcher);
    }
    
    /**
     * Test an invalid request.
     * @throws Exception if test fails
     */
    public void testInvalidRequest() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        MessageDispatcher dispatcher = factory.create(getEndpoint());
        dispatcher.initialise();
        try {
            dispatcher.send(getTestEvent(getEndpoint()));
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().startsWith("Mule message body is not a LegStar message."));
        }
    }

    /**
     * Test an actual request.
     * @throws Exception if test fails
     */
    public void testLsfileaeRequest() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        MuleMessage muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
    }
    
    /**
     * Check that a dispatcher is reusable.
     * Without a keepSendSocketOpen property set to true on the connector, the
     * dispatcher will use 2 different sockets for each request.
     * @throws Exception if test fails
     */
    public void testDispatcherReuse() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        MuleMessage muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
        muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
    }

    /**
     * Check that we are able to reuse sockets if needed (keepSendSocketOpen).
     * @throws Exception if test fails
     */
    public void testSocketReuse() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        ((LegstarTcpConnector) endpoint.getConnector()).setKeepSendSocketOpen(true);
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        MuleMessage muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
        muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
    }

    /**
     * Check that if sockets are reused and a timeout occurs on the mainframe (2 seconds),
     * the pooling mechanism will create a new socket.
     * @throws Exception if test fails
     */
    public void testSocketReuseWithTimeout() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        ((LegstarTcpConnector) endpoint.getConnector()).setKeepSendSocketOpen(true);
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        MuleMessage muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
        /* Simulate some processing so that the mainframe transaction times out */
        Thread.sleep(3000L);
        muleReplyMessage = dispatcher.send(
                getTestEvent(getLsfileaeMessage100(), endpoint));
        checkLsfileae100Reply(muleReplyMessage);
    }

   
    /**
     * Test authentication error.
     * @throws Exception if test fails
     */
    public void testAuthenticationError() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        LegstarTcpConnector connector = (LegstarTcpConnector) endpoint.getConnector();
        connector.setHostUserID("TARA");
        connector.setHostPassword("TATA");
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        try {
            dispatcher.send(
                    getTestEvent(getLsfileaeMessage100(), endpoint));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("SEE06908 The USERID is not known to the external security manager.",
                    e.getCause().getMessage());
        }
    }
    
    /**
     * Test non existent program error.
     * @throws Exception if test fails
     */
    public void testNonExistentProgramError() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        try {
            dispatcher.send(
                    getTestEvent(getNonExistentProgramMessage100(), endpoint));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("CICS command=LINK COMMAREA failed, resp=PGMIDERR, resp2=3", e.getCause().getMessage());
        }
    }
    
    /**
     * Test data structure mismatch error.
     * This test generates an ASRA abend and a dump which may cause CICS to stress if done
     * too often. For this reason this is an optional test.
     * @throws Exception if test fails
     */
    public void optionalTestDataStructureMismatchError() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getEndpoint();
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        try {
            dispatcher.send(
                    getTestEvent(getDataStructureMismatchMessage100(), endpoint));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("No response from host. Connection reset or server transaction abend.",
                    e.getCause().getMessage());
        }
    }
    
    /**
     * Test non existent endpoint error.
     * @throws Exception if test fails
     */
    public void testNonExistentEndpointError() throws Exception {
        LegstarTcpMessageDispatcherFactory factory = new LegstarTcpMessageDispatcherFactory();
        OutboundEndpoint endpoint = getNonExistentEndpoint();
        MessageDispatcher dispatcher = factory.create(endpoint);
        dispatcher.initialise();
        try {
            dispatcher.send(
                    getTestEvent(getNonExistentProgramMessage100(), endpoint));
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("Connection refused: connect", e.getCause().getMessage());
        }
    }
    /**
     * @return an outbound endpoint
     * @throws Exception if endpoint cannot be created
     */
    private OutboundEndpoint getEndpoint() throws Exception {
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(
                new URIBuilder("legstar-tcp://mainframe:3011"),
                muleContext);
        OutboundEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
                endpointBuilder);
        LegstarTcpConnector connector = (LegstarTcpConnector) endpoint.getConnector();
        connector.setHostUserID("P390");
        connector.setHostPassword("STREAM2");
        return endpoint;
    }
    
    /**
     * @return a non existent outbound endpoint
     * @throws Exception if endpoint cannot be created
     */
    private OutboundEndpoint getNonExistentEndpoint() throws Exception {
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(
                new URIBuilder("legstar-tcp://mainframe:65101"),
                muleContext);
        OutboundEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(
                endpointBuilder);
        LegstarTcpConnector connector = (LegstarTcpConnector) endpoint.getConnector();
        connector.setHostUserID("P390");
        connector.setHostPassword("STREAM2");
        return endpoint;
    }

    /**
     * Create a message for LSFILEAE customer 100.
     * @return a message ready for transport
     * @throws HeaderPartException if LegStar message cannot be created
     * @throws HostMessageFormatException if message cannot be serialized as a byte array 
     */
    private static byte[] getLsfileaeMessage100() throws HeaderPartException, HostMessageFormatException {
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
    
    /**
     * Create a message for a non existent program.
     * @return a message ready for transport
     * @throws HeaderPartException if LegStar message cannot be created
     * @throws HostMessageFormatException if message cannot be serialized as a byte array 
     */
    private static byte[] getNonExistentProgramMessage100() throws HeaderPartException, HostMessageFormatException {
        Map < String, Object > map = new HashMap < String, Object >();
        map.put(Constants.CICS_PROGRAM_NAME_KEY, "EAELIFSL");
        map.put(Constants.CICS_LENGTH_KEY, "79");
        map.put(Constants.CICS_DATALEN_KEY, "6");
        
        LegStarMessage legstarMessage = new LegStarMessage();
        legstarMessage.setHeaderPart(new LegStarHeaderPart(map, 0));
        legstarMessage.addDataPart(new CommareaPart(
                HostData.toByteArray(LsfileaeCases.getHostBytesHexRequest100())));
        return legstarMessage.toByteArray();

    }
    
    /**
     * Create a message which data does not match the target program input commarea.
     * @return a message ready for transport
     * @throws HeaderPartException if LegStar message cannot be created
     * @throws HostMessageFormatException if message cannot be serialized as a byte array 
     */
    private static byte[] getDataStructureMismatchMessage100() throws HeaderPartException, HostMessageFormatException {
        Map < String, Object > map = new HashMap < String, Object >();
        map.put(Constants.CICS_PROGRAM_NAME_KEY, "TYPESMIX");
        map.put(Constants.CICS_LENGTH_KEY, "79");
        map.put(Constants.CICS_DATALEN_KEY, "6");
        
        LegStarMessage legstarMessage = new LegStarMessage();
        legstarMessage.setHeaderPart(new LegStarHeaderPart(map, 0));
        legstarMessage.addDataPart(new CommareaPart(
                HostData.toByteArray(LsfileaeCases.getHostBytesHexRequest100())));
        return legstarMessage.toByteArray();

    }
    /**
     * Check a reply to a LSFILEAE 100 request.
     * @param muleReplyMessage the reply as a Mule message
     * @throws Exception if check fails
     */
    private static void checkLsfileae100Reply(
            final MuleMessage muleReplyMessage) throws Exception {
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
}
