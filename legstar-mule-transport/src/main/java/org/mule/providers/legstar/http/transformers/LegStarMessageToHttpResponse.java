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
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.NullPayload;
import org.mule.providers.http.HttpResponse;
import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;

import com.legstar.messaging.LegStarMessage;

/**
 * <code>LegStarMessageToHttpResponse</code> will turn a an architected
 * LegStar message into an http response which payload is binary.
 */
public class LegStarMessageToHttpResponse
extends AbstractObjectToHttpResponseTransformer
{

    /** logger used by this class.  */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Construct the transformer. Specify source and return types.
     */
    public LegStarMessageToHttpResponse()
    {
        super();
        registerSourceType(LegStarMessage.class);
        logger.debug("instantiation");
    }

    /** {@inheritDoc} */
    public final Object transform(final Object src, final String encoding, final UMOEventContext context) throws TransformerException
            {

        /* This situation arises if the client starts by an HTTP HEAD method. */
        if (src instanceof HttpResponse)
        {
            return src;
        }

        /* This situation happens when an exception happened. There is normally
         * a 500 http status set by the standard Mule exception mapping 
         * mechanism */
        if (src instanceof NullPayload)
        {
            return super.transform(null, encoding, context);
        }

        /* Since the only source type registered is LegStarMessage, it is safe
         * to cast the 'src' object directly to that type. */
        LegStarMessage requestMessage = (LegStarMessage) src;

        try
        {
            int bytesLength = requestMessage.getHostSize();
            if (bytesLength == 0)
            {
                throw new TransformerException(
                        LegstarMessages.invalidHostDataSize(), this);
            }
            byte[] result = new byte[bytesLength];
            InputStream hostStream = requestMessage.sendToHost();
            int rc;
            int pos = 0;
            while ((rc = hostStream.read(result, pos, bytesLength - pos)) > 0)
            {
                pos += rc;
            }

            return super.transform(result, encoding, context);
        }
        catch (IOException e)
        {
            throw new TransformerException(
                    LegstarMessages.errorFormattingHostData(), this, e);
        }

            }

}
