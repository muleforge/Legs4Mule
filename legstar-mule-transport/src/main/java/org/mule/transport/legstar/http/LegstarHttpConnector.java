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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transport.http.HttpConnector;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transport.MessageReceiver;

/**
 * <code>LegstarConnector</code> is essentially and <code>HttpConnector</code>
 * with transformers to handle to special LegStar messaging to wrap
 * mainframe data. The LegStar support for HTTP must be installed on the
 * mainframe @see http://www.legsem.com/legstar/legstar-chttprt.
 */
public class LegstarHttpConnector extends HttpConnector {
    
    /** Protocol internal name. Will not appear in URIs. */
    public static final String INTERNAL_PROTOCOL = "legstar";

    /** Protocol external name. Will appear in URIs. */
    public static final String EXTERNAL_PROTOCOL = "legstar:http";

    /** logger used by this class.   */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * No-Args constructor.
     */
    public LegstarHttpConnector() {
        registerProtocols();
    }

    /** {@inheritDoc} */
    public final void doInitialise() throws InitialisationException {
        super.doInitialise();
        _log.debug("doInitialise");
    }

    /**
     * Used to register the legtstar:http
     * as a valid protocol combination. "legstar" is the scheme
     * meta info and http is the protocol.
     */
    public final void registerProtocols() {
        registerSupportedProtocolWithoutPrefix(EXTERNAL_PROTOCOL);
    }

    /** {@inheritDoc} */
    public final String getProtocol() {
        return INTERNAL_PROTOCOL;
    }

    /** 
     * Because MessageReceiver getTargetReceiver does a lookup with
     * a key like legstar://localhost:8083 instead of http://localhost:8083
     * we override the standard method.
     * {@inheritDoc}
     *  */
    public final MessageReceiver lookupReceiver(final String key) {
        if (key != null) {
            MessageReceiver receiver = (MessageReceiver) receivers.get(key);
            if (receiver == null) {
                receiver = (MessageReceiver) receivers.get(
                        key.replace(getProtocol() + ':', "http:"));
            }
            return receiver;
        } else {
            throw new IllegalArgumentException("Receiver key must not be null");
        }
    }

}

