/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.legstar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.http.HttpConnector;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOMessageReceiver;

/**
 * <code>LegstarConnector</code> is essentially and <code>HttpConnector</code>
 * with transformers to handle to special LegStar wrappering of
 * host data.
 */
public class LegstarConnector extends HttpConnector {
    
    /**
     * No-Args constructor. Used to register the legtstar:http
     * as a valid protocol combination.
     */
    public LegstarConnector() {
        registerSupportedProtocol(getProtocol() + ':' + "http");
    }

    /** logger used by this class.   */
    private final Log logger = LogFactory.getLog(getClass());
    
    /** {@inheritDoc} */
    public void doInitialise() throws InitialisationException {
        super.doInitialise();
        logger.debug("doInitialise");
    }

    /** {@inheritDoc} */
    public String getProtocol() {
        return "legstar";
    }

    /** 
     * Because UMOMessageReceiver getTargetReceiver does a lookup with
     * a key like legstar://localhost:8083 instead of legstar://localhost:8083
     * we override the standard method.
     * {@inheritDoc}
     *  */
    public UMOMessageReceiver lookupReceiver(final String key) {
        if (key != null) {
            UMOMessageReceiver receiver = (UMOMessageReceiver) receivers.get(key);
            if (receiver == null) {
                return (UMOMessageReceiver) receivers.get(
                        key.replace(getProtocol(), "http"));
            }
            return receiver;
        } else {
            throw new IllegalArgumentException("Receiver key must not be null");
        }
    }

}

