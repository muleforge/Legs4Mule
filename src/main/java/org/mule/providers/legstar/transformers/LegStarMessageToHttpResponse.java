/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.legstar.transformers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.http.HttpResponse;
import org.mule.providers.http.transformers.UMOMessageToHttpResponse;
import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;

import com.legstar.messaging.LegStarMessage;

/**
 * <code>LegStarMessageToByteArray</code> will turn a an architected
 * LegStar message into an http response which payloads is binary.
 */
public class LegStarMessageToHttpResponse extends UMOMessageToHttpResponse {

    /** logger used by this class.  */
    private final Log logger = LogFactory.getLog(getClass());
    
    /** When channeled over http, the legstar payload must be binary. */
    private static final String LEGSTAR_HTTP_CONTENT_TYPE =
        "binary/octet-stream";

    /**
     * Construct the transformer. Specify source and return types.
     */
    public LegStarMessageToHttpResponse() {
        super();
        registerSourceType(LegStarMessage.class);
        logger.debug("instantiation");
    }

    /** {@inheritDoc} */
	public Object transform(
	        final Object src,
	        final String encoding,
	        final UMOEventContext context) throws TransformerException {
	    
	    /* This situation arises if the client starts by an HTTP HEAD method. */
	    if (src instanceof HttpResponse) {
	        return src;
	    }
	    
		/* Since the only source type registered is LegStarMessage, it is safe
		 * to cast the 'src' object directly to that type. */
	    LegStarMessage requestMessage = (LegStarMessage) src;
	    
        try {
            int bytesLength = requestMessage.getHostSize();
            if (bytesLength == 0) {
                throw new TransformerException(
                        LegstarMessages.invalidHostDataSize(), this);
            }
            byte[] result = new byte[bytesLength];
            InputStream hostStream = requestMessage.sendToHost();
            int rc;
            int pos = 0;
            while ((rc = hostStream.read(result, pos, bytesLength - pos)) > 0) {
                pos += rc;
            }
            
            return super.transform(result, encoding, context);
        } catch (IOException e) {
            throw new TransformerException(
                    LegstarMessages.errorFormattingHostData(), this, e);
        }

    }
	
    /** 
     * Overriding this method because <code>UMOMessageToHttpResponse</code> does
     * not allow the content length to be set directly.
     * {@inheritDoc} */
	protected HttpResponse createResponse(
            final Object src,
            final String encoding,
            final UMOEventContext context)
            throws IOException, TransformerException {

        UMOMessage msg = context.getMessage();
        /* Force the content type and content length */
        msg.setStringProperty(HttpConstants.HEADER_CONTENT_TYPE,
                LEGSTAR_HTTP_CONTENT_TYPE);

        HttpResponse response = super.createResponse(src, encoding, context);
        
        /* We make the assumption that the source has already been 
         * transformed into a byte array.
         * TODO consider case where the Mule component raises an exception
         * what should we send to the host?  */
        Header header = new Header(
                HttpConstants.HEADER_CONTENT_LENGTH,
                Integer.toString(((byte[]) src).length));
        response.addHeader(header);

        return response;
    }

}
