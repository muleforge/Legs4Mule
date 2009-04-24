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
package org.mule.transport.legstar.http.transformer;

import java.io.IOException;

import org.mule.transport.legstar.i18n.LegstarMessages;
import org.mule.transport.legstar.transformer.IObjectToHostTransformer;
import org.mule.transport.NullPayload;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.HttpResponse;
import org.mule.transport.http.transformers.MuleMessageToHttpResponse;
import org.mule.api.transformer.TransformerException;
import org.mule.api.MuleMessage;

import com.legstar.coxb.transform.HostTransformException;

/**
 * <code>AbstractObjectToHttpResponse</code> contains methods that
 * are common to legstar/mule transformers that produce HTTP responses.
 * Such responses are targeted at the mainframe which has special requirements
 * for http headers such as content type and content length.
 */
public abstract class AbstractObjectToHttpResponse extends MuleMessageToHttpResponse
{

    /** When channeled over http, the legstar payload must be binary. */
    private static final String LEGSTAR_HTTP_CONTENT_TYPE =
        "application/octet-stream";

    /** Message labels. */
    private LegstarMessages mLegstarMessages = new LegstarMessages();;


    /** 
     * Overriding this method because <code>MuleMessageToHttpResponse</code> does
     * not allow the content type to be set directly.
     * {@inheritDoc}
     *  */
    @Override
    public final HttpResponse createResponse(
            final Object src,
            final String encoding,
            final MuleMessage msg)
    throws IOException, TransformerException {

        /* Force the content type and content length */
        msg.setStringProperty(HttpConstants.HEADER_CONTENT_TYPE,
                LEGSTAR_HTTP_CONTENT_TYPE);

        HttpResponse response = super.createResponse(src, encoding, msg);

        return response;
    }

    /**
     * @return Message labels
     */
    public LegstarMessages getLegstarMessages() {
        return mLegstarMessages;
    }

    /** {@inheritDoc} */
    public Object transform(
            final MuleMessage muleMessage,
            final String encoding) throws TransformerException {

        try {
            Object src = muleMessage.getPayload();
            
            /* This situation arises if the client starts by an HTTP HEAD method. */
            if (src instanceof HttpResponse) {
                return src;
            }
            
            /* This situation happens when an exception happened. There is normally
            * a 500 http status set by the standard Mule exception mapping 
            * mechanism */
            if (src instanceof NullPayload) {
                return super.transform(muleMessage, encoding);
            }

            /* Use existing transformer to get a host byte array */
            IObjectToHostTransformer transformer = getObjectToHostMuleTransformer();
            byte[] hostBytes = transformer.transform(muleMessage, encoding);
            muleMessage.setPayload(hostBytes);
            
            /* Delegate to parent the encapsulation in an http body */
            return super.transform(muleMessage, encoding);
        } catch (HostTransformException e) {
            throw new TransformerException(
                    getLegstarMessages().hostTransformFailure(), this, e);
        }
    }
    
    /**
     * @return an instance of a transformer
     * @throws HostTransformException if transformer cannot be obtained
     */
    public abstract IObjectToHostTransformer getObjectToHostMuleTransformer(
            ) throws HostTransformException;
}
