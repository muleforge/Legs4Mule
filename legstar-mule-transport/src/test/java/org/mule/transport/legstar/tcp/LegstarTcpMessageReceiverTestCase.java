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
/*
 * $Id: MessageReceiverTestCase.vm 11967 2008-06-05 20:32:19Z dfeist $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.tcp;

import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.AbstractMessageReceiverTestCase;

import com.mockobjects.dynamic.Mock;


/**
 * Test a TCP receiver.
 *
 */
public class LegstarTcpMessageReceiverTestCase extends AbstractMessageReceiverTestCase {

    /** {@inheritDoc} */
    public MessageReceiver getMessageReceiver() throws Exception {
        Mock mockService = new Mock(Service.class);
        mockService.expectAndReturn("getResponseTransformer", null);
        mockService.expectAndReturn("getResponseRouter", null);
        return new LegstarTcpMessageReceiver(endpoint.getConnector(), (Service) mockService.proxy(), endpoint);
    }

    /** {@inheritDoc} */
    public InboundEndpoint getEndpoint() throws Exception {
        EndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(
                new URIBuilder("legstar-tcp://localhost:1234",
                        muleContext));
        return muleContext.getEndpointFactory().getInboundEndpoint(
                endpointBuilder);
    }

}
