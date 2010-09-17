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
package org.mule.transport.legstar.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.mule.api.MuleMessage;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.api.transport.PropertyScope;
import org.mule.transport.AbstractMuleMessageFactoryTestCase;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.HttpRequest;
import org.mule.transport.http.RequestLine;

import com.legstar.config.Constants;
import com.legstar.coxb.host.HostData;
import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarHeaderPart;
import com.legstar.messaging.LegStarMessage;
import com.legstar.test.coxb.LsfileaeCases;

/**
 * Test the LegstarHttpMessageFactory class.
 * For general guidelines on writing transports see
 * http://www.mulesoft.org/display/MULE2USER/Creating+Transports
 */
public class LegstarHttpMessageFactoryTestCase extends AbstractMuleMessageFactoryTestCase {

    private static final Header[] HEADERS = new Header[] { new Header("foo-header", "foo-value") };
    private static final String REQUEST_LINE = "POST /CICS/CWBA/LSWEBBIN HTTP/1.1";

    /** {@inheritDoc} */
	@Override
	protected MuleMessageFactory doCreateMuleMessageFactory() {
		return new LegstarHttpMuleMessageFactory(muleContext);
	}

    /** {@inheritDoc} */
	@Override
	protected Object getValidTransportMessage() throws Exception {
        InputStream body = new ByteArrayInputStream(getLsfileaeMessage100());
        RequestLine requestLine = RequestLine.parseLine(REQUEST_LINE);
        HttpRequest request = new HttpRequest(requestLine, HEADERS, body, encoding);
        return request;
	}

    @Override
    protected Object getUnsupportedTransportMessage()
    {
        return "this is not a valid transport message for LegstarHttpMuleMessageFactory";
    }

    @Override
    public void testValidPayload() throws Exception
    {
        MuleMessageFactory factory = createMuleMessageFactory();
        
        Object payload = getValidTransportMessage();
        MuleMessage message = factory.create(payload, encoding);
        assertNotNull(message);
        byte[] bytePayload = (byte[]) message.getPayloadAsBytes();
        assertTrue(Arrays.equals(getLsfileaeMessage100(), bytePayload));
        assertEquals(HttpConstants.METHOD_POST, message.getOutboundProperty(HttpConnector.HTTP_METHOD_PROPERTY));
        assertEquals("foo-value", message.getProperty("foo-header", PropertyScope.INBOUND));
    }

    /**
     * Create a message for LSFILEAE customer 100.
     * @return a message ready for transport
     * @throws HeaderPartException if LegStar message cannot be created
     * @throws HostMessageFormatException if message cannot be serialized as a byte array 
     */
    private static byte[] getLsfileaeMessage100() throws HeaderPartException, HostMessageFormatException {
        Map < String, Object > map = new HashMap < String, Object >();
        map.put(Constants.CICS_PROGRAM_NAME_KEY, "LSFILEAE");
        map.put(Constants.CICS_LENGTH_KEY, "79");
        map.put(Constants.CICS_DATALEN_KEY, "6");
        
        LegStarMessage legstarMessage = new LegStarMessage();
        legstarMessage.setHeaderPart(new LegStarHeaderPart(map, 0));
        legstarMessage.addDataPart(new CommareaPart(
                HostData.toByteArray(LsfileaeCases.getHostBytesHexRequest100())));
        return legstarMessage.toByteArray();

    }

}
