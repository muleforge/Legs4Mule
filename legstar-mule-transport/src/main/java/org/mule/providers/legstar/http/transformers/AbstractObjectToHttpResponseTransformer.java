/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/

package org.mule.providers.legstar.http.transformers;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.http.HttpResponse;
import org.mule.providers.http.transformers.UMOMessageToHttpResponse;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;

/**
 * <code>AbstractObjectToHttpResponseTransformer</code> contains methods that
 * are common to legstar/mule transformers that produce HTTP responses.
 * Such responses are targeted at the mainframe which has special requirements
 * for http headers such as content type and content length.
 */
public class AbstractObjectToHttpResponseTransformer extends UMOMessageToHttpResponse
{

    /** When channeled over http, the legstar payload must be binary. */
    private static final String LEGSTAR_HTTP_CONTENT_TYPE =
        "binary/octet-stream";


    /** 
     * Overriding this method because <code>UMOMessageToHttpResponse</code> does
     * not allow the content length to be set directly.
     * {@inheritDoc} */
    public final HttpResponse createResponse(
            final Object src,
            final String encoding,
            final UMOEventContext context)
    throws IOException, TransformerException
    {

        UMOMessage msg = context.getMessage();
        /* Force the content type and content length */
        msg.setStringProperty(HttpConstants.HEADER_CONTENT_TYPE,
                LEGSTAR_HTTP_CONTENT_TYPE);

        HttpResponse response = super.createResponse(src, encoding, context);

        /* We make the assumption that the source has already been 
         * transformed into a byte array.
         * TODO consider case where the Mule component raises an exception
         * what should we send to the host?  */
        if (src != null && src instanceof byte[])
        {
            Header header = new Header(
                    HttpConstants.HEADER_CONTENT_LENGTH,
                    Integer.toString(((byte[]) src).length));
            response.addHeader(header);
        }

        return response;
    }

}
