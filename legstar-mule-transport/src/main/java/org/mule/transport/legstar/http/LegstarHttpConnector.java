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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transport.http.HttpConnector;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transport.MessageReceiver;

/**
 * <code>LegstarConnector</code> is essentially and <code>HttpConnector</code>
 * with transformers to handle to special LegStar messaging to wrap
 * mainframe data. The LegStar support for HTTP must be installed on the
 * mainframe (see http://www.legsem.com/legstar/legstar-chttprt).
 * TODO in this form, LegstarConnector does not have the capability to
 * support any other transports than HTTP.
 */
public class LegstarHttpConnector extends HttpConnector {
    
    /** Protocol supported.*/
    public static final String PROTOCOL = "legstar";

    /**
     * No-Args constructor.
     */
    public LegstarHttpConnector() {
        registerProtocols();
    }

    /** logger used by this class.   */
    private final Log _log = LogFactory.getLog(getClass());

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
        List < String > schemes = new ArrayList < String >();
        schemes.add("http");
        schemes.add("https");

        for (Iterator < String > iterator = schemes.iterator(); iterator.hasNext();)
        {
            String s = (String) iterator.next();
            registerSupportedProtocol(s);
        }
        registerSupportedProtocolWithoutPrefix("legstar:http");
        registerSupportedProtocolWithoutPrefix("legstar:https");
    }

    /** {@inheritDoc} */
    public final String getProtocol() {
        return PROTOCOL;
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
                if (receiver == null) {
                    receiver = (MessageReceiver) receivers.get(
                            key.replace(getProtocol() + ':', "https:"));
                }
            }
            return receiver;
        } else {
            throw new IllegalArgumentException("Receiver key must not be null");
        }
    }

}

