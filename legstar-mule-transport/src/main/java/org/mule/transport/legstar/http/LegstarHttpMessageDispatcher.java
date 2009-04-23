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
import org.mule.transport.http.HttpClientMessageDispatcher;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.legstar.transformer.AbstractHostJavaMuleTransformer;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transformer.TransformerException;

/**
 * <code>LegstarMessageDispatcher</code> delegates most of its behavior
 * to <code>HttpClientMessageDispatcher</code>.
 */
public class LegstarHttpMessageDispatcher extends HttpClientMessageDispatcher {

    /**
     * Constructor for a given endpoint.
     * @param endpoint the Mule endpoint
     */
    public LegstarHttpMessageDispatcher(final OutboundEndpoint endpoint) {
        super(endpoint);
    }

    /** 
     * We override this method because we need to inform the pipeline that 
     * this is an execution request and therefore LegStar messaging is needed.
     * {@inheritDoc}
     *  */
    protected MuleMessage doSend(final MuleEvent event) throws Exception {
        event.getMessage().setBooleanProperty(
                AbstractHostJavaMuleTransformer.IS_LEGSTAR_MESSAGING, true);
        return super.doSend(event);
    }

    /** 
     * We override this method because there is no way we can force the
     * http headers that we need.
     * {@inheritDoc}
     *  */
    public final HttpMethod getMethod(final MuleEvent event) throws TransformerException {
        HttpMethod method = super.getMethod(event);

        /* Force the content type expected by the Mainframe */
        method.addRequestHeader(HttpConstants.HEADER_CONTENT_TYPE,
        "application/octet-stream");

        return method;
    }


}


