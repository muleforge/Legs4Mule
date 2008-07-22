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

import org.mule.providers.AbstractMessageDispatcherFactory;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;

/**
 * <code>LegstarMessageDispatcherFactory</code> returns instances
 * of <code>LegstarMessageDispatcher</code>.
 */

public class LegstarHttpMessageDispatcherFactory 
            extends AbstractMessageDispatcherFactory {

    /** {@inheritDoc} */
    public UMOMessageDispatcher create(
            final UMOImmutableEndpoint endpoint) throws UMOException {
        return new LegstarHttpMessageDispatcher(endpoint);
    }

}
