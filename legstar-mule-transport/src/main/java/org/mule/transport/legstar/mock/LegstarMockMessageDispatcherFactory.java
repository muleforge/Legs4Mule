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

import org.mule.transport.AbstractMessageDispatcherFactory;
import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;

/**
 * <code>LegstarMockMessageDispatcherFactory</code> creates dispatchers.
 */
public class LegstarMockMessageDispatcherFactory extends AbstractMessageDispatcherFactory {

    /** {@inheritDoc} */
    public MessageDispatcher create(final OutboundEndpoint endpoint) throws MuleException {
        return new LegstarMockMessageDispatcher(endpoint);
    }

}
