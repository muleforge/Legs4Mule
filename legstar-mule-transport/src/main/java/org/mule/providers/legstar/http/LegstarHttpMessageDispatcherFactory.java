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
    public final UMOMessageDispatcher create(final UMOImmutableEndpoint endpoint) throws UMOException
    {
        return new LegstarHttpMessageDispatcher(endpoint);
    }

}
