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

import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.legstar.i18n.LegstarMessages;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.HttpResponse;
import org.mule.transport.http.transformers.MuleMessageToHttpResponse;
import org.mule.api.transformer.TransformerException;
import org.mule.api.MuleMessage;

/**
 * <code>HostByteArrayToHttpResponse</code> takes a byte array containing
 * mainframe data and prepares it to be sent as an HTTP binary body.
 */
public class HostByteArrayToHttpResponse extends MuleMessageToHttpResponse {

    /** When channeled over http, the legstar payload must be binary. */
    private static final String LEGSTAR_HTTP_CONTENT_TYPE = "application/octet-stream";

    /** Message labels. */
    private LegstarMessages mI18NMessages = new LegstarMessages();

    /**
     * Source is a byte array and response is an HTTP response.
     */
    public HostByteArrayToHttpResponse() {
        registerSourceType(byte[].class);
        setReturnDataType(DataTypeFactory.create(HttpResponse.class));
    }

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
    	msg.setInboundProperty(HttpConstants.HEADER_CONTENT_TYPE,
    			LEGSTAR_HTTP_CONTENT_TYPE);
        HttpResponse response = super.createResponse(src, encoding, msg);
        return response;
    }

    /**
     * @return Message labels
     */
    public LegstarMessages getI18NMessages() {
        return mI18NMessages;
    }

}
