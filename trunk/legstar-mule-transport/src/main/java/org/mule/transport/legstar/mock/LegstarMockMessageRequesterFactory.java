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

import org.mule.transport.AbstractMessageRequesterFactory;
import org.mule.api.MuleException;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.transport.MessageRequester;

/**
 * <code>LegstarMockMessageRequesterFactory</code> returns instances
 * of <code>LegstarMockMessageRequester</code>.
 */

public class LegstarMockMessageRequesterFactory 
extends AbstractMessageRequesterFactory {

    /** {@inheritDoc} */
    public MessageRequester create(final InboundEndpoint endpoint)
            throws MuleException {
        return new LegstarMockMessageRequester(endpoint);
    }

}
