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
package org.mule.transport.legstar.wmq.transformer;

import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.transport.legstar.config.ConfigUtils;
import org.mule.transport.legstar.wmq.LegstarWmqConnector;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transformer.Transformer;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;

/**
 * Test HostToMqcihMuleTransformer class.
 *
 */
public class HostToMqcihExecRequestMuleTransformerTest extends AbstractTransformerTestCase {

    /** Wrapped message serialization. */
    public static final String MQCIH_MESSAGE_HOST_DATA =
        /* MQCIH */
        "c3c9c840"
        + "00000002"
        + "000000b4"
        + "00000000"
        + "00000000"
        + "4040404040404040"
        + "00000006"
        + "00000000"
        + "00000000"
        + "00000000"
        + "00000111"
        + "fffffffe"
        + "00000001"
        + "00000057"
        + "00000000"
        + "00000000"
        + "00000000"
        + "00000000"
        + "0000000000000000"
        + "40404040"
        + "40404040"
        + "E2E3D9C5C1D4F240"
        + "4040404040404040"
        + "4040404040404040"
        + "40404040"
        + "40404040"
        + "40404040"
        + "40404040"
        + "40404040"
        + "40404040"
        + "40404040"
        + "40404040"
        + "4040404040404040"
        + "4040404040404040"
        + "00000000"
        + "00000000"
        + "00000000"
        + "00000000"

        /* CICS Program name*/
        + "d3e2c6c9d3c5c1c5"

        /* Commarea*/
        /*  0 0 0 1 0 0 T O T O                                 L A B A S   S T R E E T                */
        + "f0f0f0f1f0f0e3d6e3d640404040404040404040404040404040d3c1c2c1e240e2e3d9c5c5e34040404040404040"
        /* 8 8 9 9 3 3 1 4 1 0 0 4 5 8     0 0 1 0 0 . 3 5 A   V O I R      */
        + "f8f8f9f9f3f3f1f4f1f0f0f4f5f84040f0f0f1f0f04bf3f5c140e5d6c9d9404040";

    /** This makes sure there is a single instance of test data. */
    private static byte[] _TestData;
    
    /**
     * Constructor.
     */
    public HostToMqcihExecRequestMuleTransformerTest() {
        super();
        _TestData = HostData.toByteArray(LsfileaeCases.getHostBytesHex());
    }

    /** {@inheritDoc} */
    public AbstractMessageTransformer getTransformer() throws Exception {
        HostToMqcihExecRequestMuleTransformer transformer = new HostToMqcihExecRequestMuleTransformer();
        transformer.setHostProgram(ConfigUtils.getHostProgram("lsfileae.properties"));
        transformer.setEndpoint(getEndpoint());
        transformer.setMuleContext(muleContext);
        return transformer;
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return HostData.toByteArray(MQCIH_MESSAGE_HOST_DATA);
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return _TestData;
    }

    /**
     * @return an outbound endpoint
     * @throws Exception if endpoint cannot be created
     */
    private OutboundEndpoint getEndpoint() throws Exception {
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(
                new URIBuilder(getTestEndpointURI(),
                        muleContext));
        OutboundEndpoint endpoint = muleContext.getEndpointFactory().getOutboundEndpoint(
                endpointBuilder);
        LegstarWmqConnector connector = (LegstarWmqConnector) endpoint.getConnector();
        connector.setHostUserID("P390");
        connector.setHostPassword("STREAM2");
        return endpoint;
    }

    /** @return a URI to connect to. */
    public String getTestEndpointURI() {
        return "legstar-wmq://CICS01.BRIDGE.REQUEST.QUEUE"
        + "?jndiInitialFactory=com.sun.jndi.fscontext.RefFSContextFactory"
        + "&jndiProviderUrl=file:///JNDI-Directory"
        + "&connectionFactoryJndiName=ConnectionFactory";
    }

    
}
