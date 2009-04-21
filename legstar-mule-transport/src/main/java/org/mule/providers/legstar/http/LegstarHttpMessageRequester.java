package org.mule.providers.legstar.http;

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.transport.http.HttpClientMessageRequester;

/**
 * <code>LegstarHttpMessageRequester</code> delegates most of its behavior
 * to <code>HttpClientMessageRequester</code>.
 */
public class LegstarHttpMessageRequester extends HttpClientMessageRequester {

    /**
     * @param endpoint the inbound endpoint
     */
    public LegstarHttpMessageRequester(final InboundEndpoint endpoint) {
        super(endpoint);
    }

}
