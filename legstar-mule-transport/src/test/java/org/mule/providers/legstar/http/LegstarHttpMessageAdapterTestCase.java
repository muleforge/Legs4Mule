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

import org.mule.transport.AbstractMessageAdapterTestCase;
import org.mule.api.transport.MessageAdapter;
import org.mule.api.MessagingException;

/**
 * Test the LegstarHttpMessageAdapter class.
 * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
 */
public class LegstarHttpMessageAdapterTestCase extends AbstractMessageAdapterTestCase {

    /** {@inheritDoc} */
    public Object getValidMessage() throws Exception {
        return new String("mok message").getBytes();
    }

    /** {@inheritDoc} */
    public MessageAdapter createAdapter(final Object payload) throws MessagingException {
        return new LegstarHttpMessageAdapter(payload);
    }

}
