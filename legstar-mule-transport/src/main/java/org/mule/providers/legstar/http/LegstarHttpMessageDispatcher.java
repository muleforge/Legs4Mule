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

import org.apache.commons.httpclient.HttpMethod;
import org.mule.providers.http.HttpClientMessageDispatcher;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.legstar.transformers.AbstractHostEsbTransformer;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.transformer.TransformerException;

/**
 * <code>LegstarMessageDispatcher</code> delegates most of its behavior
 * to <code>HttpClientMessageDispatcher</code>.
 */
public class LegstarHttpMessageDispatcher extends HttpClientMessageDispatcher {

    /**
     * Constructor for a given endpoint.
     * @param endpoint the Mule endpoint
     */
    public LegstarHttpMessageDispatcher(final UMOImmutableEndpoint endpoint) {
        super(endpoint);
    }

    /** 
     * We override this method because we need to inform the pipeline that 
     * this is an execution request and therefore LegStar messaging is needed.
     * {@inheritDoc}
     *  */
    protected UMOMessage doSend(final UMOEvent event) throws Exception {
        event.getMessage().setBooleanProperty(
                AbstractHostEsbTransformer.IS_LEGSTAR_MESSAGING, true);
        return super.doSend(event);
    }

    /** 
     * We override this method because there is no way we can force the
     * http headers that we need.
     * {@inheritDoc}
     *  */
    public final HttpMethod getMethod(final UMOEvent event) throws TransformerException {
        HttpMethod method = super.getMethod(event);

        /* Force the content type expected by the Mainframe */
        method.addRequestHeader(HttpConstants.HEADER_CONTENT_TYPE,
        "application/octet-stream");

        /* TODO these parameters should actually be obtained from an
         * equivalent to legstar-invoker-config using some host endpoint*/
        Boolean hostTraceMode = Boolean.valueOf(event.getMessage().getBooleanProperty(
                AbstractHostEsbTransformer.HOST_TRACE_MODE_PROPERTY, false));
        method.addRequestHeader("CICSTraceMode", hostTraceMode.toString());


        return method;
    }


}


