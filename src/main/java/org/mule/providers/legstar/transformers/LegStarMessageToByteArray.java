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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.http.HttpConstants;
import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;

import com.legstar.messaging.LegStarMessage;

/**
 * <code>LegStarMessageToByteArray</code> will turn a an architected
 * LegStar message into a byte array.
 */
public class LegStarMessageToByteArray extends AbstractEventAwareTransformer {

    /** logger used by this class.  */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Construct the transformer. Specify source and return types.
     */
    public LegStarMessageToByteArray() {
        registerSourceType(LegStarMessage.class);
        setReturnClass(byte[].class);
        logger.debug("instantiation");
    }

    /** {@inheritDoc} */
	public Object transform(
	        final Object src,
	        final String encoding,
	        final UMOEventContext context) throws TransformerException {
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
            
            /* The next step is likely to be UMOMessageToHttpResponse. Some options*/
            UMOMessage msg = context.getMessage();
            
            /* Force the content type and content length */
            msg.setStringProperty(HttpConstants.HEADER_CONTENT_TYPE,
                    "binary/octet-stream");
            /* This is not propagated by UMOMessageToHttpResponse
             * TODO open an issue with Mule.  */
            msg.setStringProperty(HttpConstants.HEADER_CONTENT_LENGTH,
                    Integer.toString(bytesLength));
            
            return result;
        } catch (IOException e) {
            throw new TransformerException(
                    LegstarMessages.errorFormattingHostData(), this, e);
        }

    }

}
