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
package org.mule.providers.legstar.http.transformers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.NullPayload;
import org.mule.providers.http.HttpResponse;
import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;

import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarMessage;

/**
 * <code>LegStarMessageToHttpResponse</code> will turn a an architected
 * LegStar message into an http response which payload is binary.
 */
public class LegStarMessageToHttpResponse
extends AbstractObjectToHttpResponseTransformer {

    /** logger used by this class.  */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Construct the transformer. Specify source and return types.
     */
    public LegStarMessageToHttpResponse() {
        super();
        registerSourceType(LegStarMessage.class);
        logger.debug("instantiation");
    }

    /** {@inheritDoc} */
    public final Object transform(
            final Object src,
            final String encoding,
            final UMOEventContext context) throws TransformerException {

        /* This situation arises if the client starts by an HTTP HEAD method. */
        if (src instanceof HttpResponse) {
            return src;
        }

        /* This situation happens when an exception happened. There is normally
         * a 500 http status set by the standard Mule exception mapping 
         * mechanism */
        if (src instanceof NullPayload) {
            return super.transform(null, encoding, context);
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
            return super.transform(requestMessage.toByteArray(), encoding, context);

        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    LegstarMessages.errorFormattingHostData(), this, e);
        }

    }

}
