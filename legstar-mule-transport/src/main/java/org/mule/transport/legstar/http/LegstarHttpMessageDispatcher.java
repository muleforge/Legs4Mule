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

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transport.http.HttpClientMessageDispatcher;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.legstar.cixs.transformer.AbstractHostToExecRequestMuleTransformer;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transformer.TransformerException;

/**
 * <code>LegstarMessageDispatcher</code> delegates most of its behavior
 * to <code>HttpClientMessageDispatcher</code>.
 */
public class LegstarHttpMessageDispatcher extends HttpClientMessageDispatcher {

    /** When channeled over http, the legstar payload must be binary. */
    private static final String LEGSTAR_HTTP_CONTENT_TYPE =
        "application/octet-stream";
    
    /** LegStar mainframe modules recognize this HTTP header for trace mode.*/
    private static final String LEGSTAR_HTTP_HEADER_TRACE_MODE = "CICSTraceMode";

    /** logger used by this class.   */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * Constructor.
     * @param endpoint the endpoint to dispatch to
     */
    public LegstarHttpMessageDispatcher(final OutboundEndpoint endpoint) {
        super(endpoint);
    }

    /** 
     * We override this method because we need to perform LegStar messaging specific
     * transformations and also need to force the http header content type.
     * {@inheritDoc}
     *  */
    public final HttpMethod getMethod(final MuleEvent event) throws TransformerException {
        
        if (_log.isDebugEnabled()) {
            _log.debug("Creating http method for endpoint " + event.getEndpoint());
        }
        
        HttpMethod httpMethod = super.getMethod(event);

        /* Force the content type expected by the Mainframe */
        httpMethod.removeRequestHeader(HttpConstants.HEADER_CONTENT_TYPE);
        httpMethod.addRequestHeader(HttpConstants.HEADER_CONTENT_TYPE,
                LEGSTAR_HTTP_CONTENT_TYPE);
        
        if (isHostTraceOn(event.getMessage()) || _log.isDebugEnabled()) {
            httpMethod.addRequestHeader(LEGSTAR_HTTP_HEADER_TRACE_MODE,
                    "true");
        }

        return httpMethod;
    }
    
    /**
     * @param esbMessage the mule message
     * @return true if the mainframe should trace execution requests
     */
    public boolean isHostTraceOn(final MuleMessage esbMessage) {
        return esbMessage.getBooleanProperty(
                AbstractHostToExecRequestMuleTransformer.LEGSTAR_HOST_TRACE_ON_KEY,
                false);
    }

}


