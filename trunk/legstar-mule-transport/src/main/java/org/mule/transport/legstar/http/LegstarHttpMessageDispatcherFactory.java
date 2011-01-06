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
package org.mule.transport.legstar.http;

import org.mule.transport.AbstractMessageDispatcherFactory;
import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;

/**
 * <code>LegstarMessageDispatcherFactory</code> returns instances
 * of <code>LegstarMessageDispatcher</code>.
 */

public class LegstarHttpMessageDispatcherFactory 
extends AbstractMessageDispatcherFactory {

    /** {@inheritDoc} */
    public final MessageDispatcher create(final OutboundEndpoint endpoint) throws MuleException
    {
        return new LegstarHttpMessageDispatcher(endpoint);
    }

}
