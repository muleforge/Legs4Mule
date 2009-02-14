/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/

package org.mule.providers.legstar.http;

import com.mockobjects.dynamic.Mock;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;


/**
 * Test the LegstarHttpMessageReceiver class.
 * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
 */
public class LegstarHttpMessageReceiverTestCase extends AbstractMessageReceiverTestCase {

    /** Legstar listening port. */
    private static final int LEGSTAR_PORT = 11958;

    /** {@inheritDoc} */
    public UMOMessageReceiver getMessageReceiver() throws Exception {
        Mock mockComponent = new Mock(UMOComponent.class);
        Mock mockDescriptor = new Mock(UMODescriptor.class);
        mockComponent.expectAndReturn("getDescriptor", mockDescriptor.proxy());
        mockDescriptor.expectAndReturn("getResponseTransformer", null);
        return new LegstarHttpMessageReceiver(endpoint.getConnector(), (UMOComponent) mockComponent.proxy(), endpoint);
    }

    /** {@inheritDoc} */
    public UMOEndpoint getEndpoint() throws Exception {
        return new MuleEndpoint("legstar:http://localhost:" + Integer.toString(LEGSTAR_PORT), true);
    }

}
