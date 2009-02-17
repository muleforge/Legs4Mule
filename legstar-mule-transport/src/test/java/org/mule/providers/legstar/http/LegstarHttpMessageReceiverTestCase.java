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
