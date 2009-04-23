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
package org.mule.transport.legstar.transformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpVersion;
import org.mule.RequestContext;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.MuleEvent;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.OutputHandler;
import org.mule.transport.http.HttpResponse;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;

/**
 * Test AbstractXmlToHttpResponseTransformer class.
 *
 */
public class LsfileaeXmlToHttpResponseTransformerTest extends AbstractTransformerTestCase {

    /** {@inheritDoc} */
    public Transformer getTransformer() throws Exception {
        Transformer transformer = new LsfileaeXmlToHttpResponseTransformer();
        transformer.initialise();
        return transformer;
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        HttpResponse resultData = new HttpResponse();
        resultData.setStatusLine(HttpVersion.HTTP_1_1, 200, "OK");
        OutputHandler outputHandler = new OutputHandler() {

            public void write(
                    final MuleEvent event,
                    final OutputStream out) throws IOException {
                out.write(HostData.toByteArray(
                        LsfileaeCases.getHostBytesHex()));
            }

        };
        resultData.setBody(outputHandler);
        return resultData;
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return LsfileaeCases.getXml();
    }

    /** {@inheritDoc} */
    public boolean compareResults(final Object expected, final Object result) {
        try {
            if (expected instanceof HttpResponse && result instanceof HttpResponse) {
                HttpResponse httpExpected = (HttpResponse) expected;
                HttpResponse httpResult = (HttpResponse) result;
                if (!httpExpected.getStatusLine().equals(httpResult.getStatusLine())) {
                    return false;
                }
                ByteArrayOutputStream outExpected = new ByteArrayOutputStream();
                httpExpected.getBody().write(RequestContext.getEvent(), outExpected);
                byte[] bodyExpected = outExpected.toByteArray();

                ByteArrayOutputStream outResult = new ByteArrayOutputStream();
                httpResult.getBody().write(RequestContext.getEvent(), outResult);
                byte[] bodyResult = outResult.toByteArray();

                if (!Arrays.equals(bodyExpected, bodyResult)) {
                    return false;
                }
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        } catch (TransformerException e) {
            return false;
        }
    }
}
