/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.cixs;

import org.mule.umo.UMOMessage;

/**
 * Utility class for generated Mule-LegStar host headers. This allows
 * runtime parameters needed to access a host from being shipped as
 * regular UMO message properties.
 */
public final class MuleHostHeaderFactory
{

    /** Host request identifier. */
    public static final String L4M_KEY_HOSTREQUESTID = "LegStarRequestID";

    /** Host endpoint name property key. */
    public static final String L4M_KEY_HOSTENDPOINT = "LegStarEndPoint";

    /** Host character set property key. */
    public static final String L4M_KEY_HOSTCHARSET = "LegStarCharset";

    /** Host user ID property key. */
    public static final String L4M_KEY_HOSTUSERID = "LegStarUserID";

    /** Host password property key. */
    public static final String L4M_KEY_HOSTPASSWORD = "LegStarPassword";

    /** Host trace mode property key. */
    public static final String L4M_KEY_HOSTTRACEMODE = "LegStarTraceMode";


    /** Defeats instanciation since this is a utility class.*/
    private MuleHostHeaderFactory()
    {

    }
    /**
     * Extracts header data from a Mule message properties and create an
     * Host header.
     * @param umoMessage the Mule message
     * @return the new host header
     */
    public static MuleHostHeader createHostHeader(final UMOMessage umoMessage)
    {
        MuleHostHeader hostHeader = new MuleHostHeader();
        hostHeader.setHostEndPoint(
                (String) umoMessage.getProperty(L4M_KEY_HOSTENDPOINT));
        hostHeader.setHostCharset(
                (String) umoMessage.getProperty(L4M_KEY_HOSTCHARSET));
        hostHeader.setHostUserID(
                (String) umoMessage.getProperty(L4M_KEY_HOSTUSERID));
        hostHeader.setHostPassword(
                (String) umoMessage.getProperty(L4M_KEY_HOSTPASSWORD));
        hostHeader.setTraceMode(
                umoMessage.getBooleanProperty(L4M_KEY_HOSTTRACEMODE, false));
        hostHeader.setHostRequestID(
                (String) umoMessage.getProperty(L4M_KEY_HOSTREQUESTID));
        return hostHeader;
    }
}
