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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.mule.providers.http.HttpResponse;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.coxb.host.HostData;
import com.legstar.messaging.LegStarMessage;

/**
 * Test the LegStarMessageToHttpResponse class.
 *
 */
public class LegStarMessageToHttpResponseTestCase extends AbstractTransformerTestCase {

    /** A LegStarMessage serialization. */
    private static final String LEGSTAR_MESSAGE_HOST_DATA =
      /*L S O K H E A D                        8       1      0 L S O K C O M M A R E A         */
       "d3e2d6d2c8c5c1c44040404040404040000000080000000100000000d3e2d6d2c3d6d4d4c1d9c5c140404040"
      /*      79 0 0 0 1 0 0 T O T O                                 L A B A S   S T R E E T                */
     + "0000004ff0f0f0f1f0f0e3d6e3d640404040404040404040404040404040d3c1c2c1e240e2e3d9c5c5e34040404040404040"
     /* 8 8 9 9 3 3 1 4 1 0 0 4 5 8     0 0 1 0 0 . 3 5 A   V O I R      */
     + "f8f8f9f9f3f3f1f4f1f0f0f4f5f84040f0f0f1f0f04bf3f5c140e5d6c9d9404040";

    /** {@inheritDoc} */
    public Object getTestData() {
        try {
            LegStarMessage message = new LegStarMessage();
            message.fromByteArray(HostData.toByteArray(LEGSTAR_MESSAGE_HOST_DATA), 0);
            return message;
        } catch (Exception ex) {
            return null;
        }
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        HttpResponse response = new HttpResponse();
        response.addHeader(new Header("Content-Type", "application/octet-stream"));
        response.addHeader(new Header("Date", "Thu, 24 Jul 2008 12:44:53 CEST"));
        response.addHeader(new Header("Server", "Mule Core/1.4.4"));
        response.addHeader(new Header("Expires", "Thu, 24 Jul 2008 12:44:53 CEST"));
        response.addHeader(new Header("Content-Length", "127"));

        response.setBody(new ByteArrayInputStream(
                HostData.toByteArray(LEGSTAR_MESSAGE_HOST_DATA)));
        return response;
    }

    /** {@inheritDoc} */
    public UMOTransformer getTransformer()  {
        UMOTransformer t = new LegStarMessageToHttpResponse();
        // Set the correct return class for this roundtrip test
        t.setReturnClass(this.getResultData().getClass());
        return t;
    }

    /** {@inheritDoc} */
    public UMOTransformer getRoundTripTransformer() {
        /** This is not a roundtrippable transformer */
        return null;
    }


    /**
     * Overriding this method here because the default implementation compares objects
     * using their equals method. HttpResponse, as of 1.4.4, does not implement equals and
     * therefore inherits from default Object which is useless.
     * {@inheritDoc}
     * */
    public boolean compareResults(final Object expected, final Object result) {

        /** In the case of http responses we compare content types, length and payload. */
        if (expected instanceof HttpResponse && result instanceof HttpResponse) {
            try {
                HttpResponse responseExpected = (HttpResponse) expected;
                HttpResponse responseResult = (HttpResponse) result;
                String contentTypeExpected = "";
                String contentLengthExpected = "";
                for (Header header : responseExpected.getHeaders()) {
                    if (header.getName().equals("Content-Type")) {
                        contentTypeExpected = header.getValue();
                    }
                    if (header.getName().equals("Content-Length")) {
                        contentLengthExpected = header.getValue();
                    }
                }
                String contentTypeResult = "";
                String contentLengthResult = "";
                for (Header header : responseResult.getHeaders()) {
                    if (header.getName().equals("Content-Type")) {
                        contentTypeResult = header.getValue();
                    }
                    if (header.getName().equals("Content-Length")) {
                        contentLengthResult = header.getValue();
                    }
                }
                if (!compareResults(contentTypeExpected, contentTypeResult)) {
                    return false;
                }
                if (!compareResults(contentLengthExpected, contentLengthResult)) {
                    return false;
                }
                if (!compareResults(responseExpected.getBodyBytes(), responseResult.getBodyBytes())) {
                    return false;
                }
                return true;
            } catch (IOException e) {
                return false;
            }

        }
        
        return super.compareResults(expected, result);
    }
}
