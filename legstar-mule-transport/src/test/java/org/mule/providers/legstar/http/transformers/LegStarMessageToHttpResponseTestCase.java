package org.mule.providers.legstar.http.transformers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.mule.providers.http.HttpResponse;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.messaging.LegStarMessage;
import com.legstar.util.Util;

public class LegStarMessageToHttpResponseTestCase extends AbstractTransformerTestCase {

    public Object getTestData()
    {
        try {
            byte[] hostBytes = getTestDataPayload();
            ByteArrayInputStream hostStream = new ByteArrayInputStream(hostBytes);
            LegStarMessage message = new LegStarMessage();
            message.recvFromHost(hostStream);
            return message;
        }
        catch (Exception ex) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getResultData()
     */
    public Object getResultData()
    {
        HttpResponse response = new HttpResponse();
        response.addHeader(new Header("Content-Type", "binary/octet-stream"));
        response.addHeader(new Header("Date", "Thu, 24 Jul 2008 12:44:53 CEST"));
        response.addHeader(new Header("Server", "Mule Core/1.4.4"));
        response.addHeader(new Header("Expires", "Thu, 24 Jul 2008 12:44:53 CEST"));
        response.addHeader(new Header("Content-Length", "185"));

        response.setBody(new ByteArrayInputStream(getTestDataPayload()));
        return response;
    }

    private byte[] getTestDataPayload() {
        return Util.toByteArray("d3e2d6d2c8c5c1c4404040404040404000000077000000020000006fc07fc3c9c3e2d6a4a3c39695a38189958599a27f7aad7fd9859793a8c481a3817f6b7fd9859793a8e2a381a3a4a27fbd6b7fc3c9c3e2c38881959585937f7a7fd3e2c6c9d3c5c1c360c3c8c1d5d5c5d37f6b7fc3c9c3e2d7999687998194d58194857f7a7fd3e2c6c9d3c5c1c37fd0d8a48599a8c481a3814040404040404000000004f1f2f3f4d8a48599a8d3899489a340404040404000000002f5f6");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getTransformers()
     */
    public UMOTransformer getTransformer()
    {
        UMOTransformer t = new LegStarMessageToHttpResponse();
        // Set the correct return class for this roundtrip test
        t.setReturnClass(this.getResultData().getClass());
        return t;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getRoundTripTransformer()
     */
    public UMOTransformer getRoundTripTransformer()
    {
        /** This is not a roundtrippable transformer */
        return null;
    }


    /**
     * Overriding this method here because the default implementation compares objects
     * using their equals method. HttpResponse, as of 1.4.4, does not implement equals and
     * therefore inherits from default Object which is useless.*/
    public boolean compareResults(Object expected, Object result)
    {

        /** In the case of http responses we compare content types, length and payload. */
        if (expected instanceof HttpResponse && result instanceof HttpResponse) {
            try {
                HttpResponse responseExpected = (HttpResponse) expected;
                HttpResponse responseResult = (HttpResponse) result;
                String contentTypeExpected = "";
                String contentLengthExpected = "";
                for(Header header : responseExpected.getHeaders()) {
                    if (header.getName().equals("Content-Type")) {
                        contentTypeExpected = header.getValue();
                    }
                    if (header.getName().equals("Content-Length")) {
                        contentTypeExpected = header.getValue();
                    }
                }
                String contentTypeResult = "";
                String contentLengthResult = "";
                for(Header header : responseResult.getHeaders()) {
                    if (header.getName().equals("Content-Type")) {
                        contentTypeResult = header.getValue();
                    }
                    if (header.getName().equals("Content-Length")) {
                        contentTypeResult = header.getValue();
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
