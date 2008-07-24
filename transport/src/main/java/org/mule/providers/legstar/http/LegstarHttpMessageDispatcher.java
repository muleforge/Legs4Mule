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

import org.apache.commons.httpclient.HttpMethod;
import org.mule.providers.http.HttpClientMessageDispatcher;
import org.mule.providers.http.HttpConstants;
import org.mule.umo.UMOEvent;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.transformer.TransformerException;

/**
 * <code>LegstarMessageDispatcher</code> delegates most of its behavior
 * to <code>HttpClientMessageDispatcher</code>.
 */
public class LegstarHttpMessageDispatcher extends HttpClientMessageDispatcher
{

    /**
     * Constructor for a given endpoint.
     * @param endpoint the Mule endpoint
     */
    public LegstarHttpMessageDispatcher(final UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
    }

    /** 
     * We override this method because there is no way we can force the
     * http headers that we need.
     * {@inheritDoc}
     *  */
    public final HttpMethod getMethod(final UMOEvent event) throws TransformerException
    {
        HttpMethod method = super.getMethod(event);

        /* Force the content type expected by the Mainframe */
        method.addRequestHeader(HttpConstants.HEADER_CONTENT_TYPE,
        "binary/octet-stream");

        /* TODO these parameters should actually be obtained from an
         * equivalent to legstar-invoker-config using some host endpoint*/
        /* For debugging purposes, this forces the Mainframe to log this
         * request */
        method.addRequestHeader("CICSTraceMode", "true");

        /* Forces the path assuming it is a Cics Web Services listening.
         * TODO again this should be externalized in a configuration file
         * */
        method.setPath("/CICS/CWBA/LSWEBBIN");

        /* This is not propagated by UMOMessageToHttpResponse
         * TODO open an issue with Mule.  */
//      method.addRequestHeader(HttpConstants.HEADER_CONTENT_LENGTH,
//      Integer.toString(bytesLength));

        return method;
    }


}


