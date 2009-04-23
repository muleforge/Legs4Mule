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
package org.mule.transport.legstar.cixs;

import org.mule.api.MuleMessage;

/**
 * Utility class for generated Mule-LegStar host headers. This allows
 * runtime parameters needed to access a host from being shipped as
 * regular UMO message properties.
 */
public final class MuleHostHeaderFactory {

    /** Host request identifier. */
    public static final String LEGSTAR_HOST_REQUEST_ID = "com.legstar.cixs.host.requestid";

    /** Host endpoint name property key. */
    public static final String LEGSTAR_HOST_ENDPOINT = "com.legstar.cixs.host.endpoint";

    /** Host character set property key. */
    public static final String LEGSTAR_HOST_CHARSET = "com.legstar.cixs.host.charset";

    /** Host user ID property key. */
    public static final String LEGSTAR_HOST_USERID = "com.legstar.cixs.host.userid";

    /** Host password property key. */
    public static final String LEGSTAR_HOST_PASSWORD = "com.legstar.cixs.host.password";

    /** Host trace mode property key. */
    public static final String LEGSTAR_HOST_TRACE_MODE = "com.legstar.cixs.host.tracemode";


    /** Defeats instanciation since this is a utility class.*/
    private MuleHostHeaderFactory() {

    }
    /**
     * Extracts header data from a Mule message properties and create an
     * Host header.
     * @param umoMessage the Mule message
     * @return the new host header
     */
    public static MuleHostHeader createHostHeader(final MuleMessage umoMessage) {
        MuleHostHeader hostHeader = new MuleHostHeader();
        hostHeader.setHostEndPoint(
                (String) umoMessage.getProperty(LEGSTAR_HOST_ENDPOINT));
        hostHeader.setHostCharset(
                (String) umoMessage.getProperty(LEGSTAR_HOST_CHARSET));
        hostHeader.setHostUserID(
                (String) umoMessage.getProperty(LEGSTAR_HOST_USERID));
        hostHeader.setHostPassword(
                (String) umoMessage.getProperty(LEGSTAR_HOST_PASSWORD));
        hostHeader.setTraceMode(
                umoMessage.getBooleanProperty(LEGSTAR_HOST_TRACE_MODE, false));
        hostHeader.setHostRequestID(
                (String) umoMessage.getProperty(LEGSTAR_HOST_REQUEST_ID));
        return hostHeader;
    }
}
