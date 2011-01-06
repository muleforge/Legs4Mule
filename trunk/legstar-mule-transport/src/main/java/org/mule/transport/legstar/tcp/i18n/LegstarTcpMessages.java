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
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.tcp.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

/**
 * Indirect references to messages from a bundle.
 */
public class LegstarTcpMessages extends MessageFactory {

    /** Bundle is legstar-tcp-messages.properties. */
    private static final String BUNDLE_PATH = getBundlePath("legstar-tcp");

    /**
     * @return invalid reply from host message
     */
    public Message invalidReplyFromHostMessage() {
        return createMessage(BUNDLE_PATH, 1);
    }

    /**
     * @return no response from host message
     */
    public Message noResponseFromHostMessage() {
        return createMessage(BUNDLE_PATH, 2);
    }

    /**
     * @return unrecognized response from host message
     */
    public Message unrecognizedResponseFromHostMessage() {
        return createMessage(BUNDLE_PATH, 3);
    }

}
