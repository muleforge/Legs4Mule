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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.http.HttpConnector;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOMessageReceiver;

/**
 * <code>LegstarConnector</code> is essentially and <code>HttpConnector</code>
 * with transformers to handle to special LegStar messaging to wrap
 * mainframe data. The LegStar support for HTTP must be installed on the
 * mainframe (see http://www.legsem.com/legstar/legstar-chttprt).
 * TODO in this form, LegstarConnector does not have the capability to
 * support any other transports than HTTP.
 */
public class LegstarHttpConnector extends HttpConnector
{

    /** Name of property holding the mainframe character set. */
    public static final String HOST_CHARSET_PROPERTY = "hostCharset";

    /** Name of Property holding jaxb qualified class name. */
    public static final String JAXB_QUAL_CLASS_NAME = "jaxbClassName";

    /** Name of Property holding cobol binding qualified class name. */
    public static final String COXB_QUAL_CLASS_NAME = "coxbClassName";

    /** Name of Property holding target Mainframe program properties file name. */
    public static final String PROGRAM_PROP_FILE_NAME = "programPropFileName";

    /**
     * No-Args constructor.
     */
    public LegstarHttpConnector()
    {
        registerProtocols();
    }

    /** logger used by this class.   */
    private final Log logger = LogFactory.getLog(getClass());

    /** {@inheritDoc} */
    public final void doInitialise() throws InitialisationException
    {
        super.doInitialise();
        logger.debug("doInitialise");
    }

    /**
     * Used to register the legtstar:http
     * as a valid protocol combination. "legstar" is the scheme
     * meta info and http is the protocol.
     */
    public final void registerProtocols()
    {
        List < String > schemes = new ArrayList < String > ();
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
    public final String getProtocol()
    {
        return "legstar";
    }

    /** 
     * Because UMOMessageReceiver getTargetReceiver does a lookup with
     * a key like legstar://localhost:8083 instead of http://localhost:8083
     * we override the standard method.
     * {@inheritDoc}
     *  */
    public final UMOMessageReceiver lookupReceiver(final String key)
    {
        if (key != null)
        {
            UMOMessageReceiver receiver = (UMOMessageReceiver) receivers.get(key);
            if (receiver == null)
            {
                return (UMOMessageReceiver) receivers.get(
                        key.replace(getProtocol(), "http"));
            }
            return receiver;
        }
        else
        {
            throw new IllegalArgumentException("Receiver key must not be null");
        }
    }

}

