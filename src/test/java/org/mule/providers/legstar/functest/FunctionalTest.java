package org.mule.providers.legstar.functest;

import java.io.ByteArrayInputStream;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostReceiveException;
import com.legstar.messaging.LegStarMessage;
import com.legstar.util.Util;

public class FunctionalTest extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "test-config.xml";
    }
    
    public void testEchoComponent() throws Exception {
        // send the request. The payload simulates a byte array of cics data
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("legstar:http://localhost:8083", getTestData(), null);
        assertNotNull(result);
        assertNull(result.getExceptionPayload());
        assertFalse(result.getPayload() == null);
        assertTrue(getTestData().equals(getResultData(result.getPayloadAsBytes())));
    }

    private LegStarMessage getTestData() throws Exception {
        byte[] hostBytes = Util.toByteArray("d3e2d6d2c8c5c1c4404040404040404000000077000000020000006fc07fc3c9c3e2d6a4a3c39695a38189958599a27f7aad7fd9859793a8c481a3817f6b7fd9859793a8e2a381a3a4a27fbd6b7fc3c9c3e2c38881959585937f7a7fd3e2c6c9d3c5c1c360c3c8c1d5d5c5d37f6b7fc3c9c3e2d7999687998194d58194857f7a7fd3e2c6c9d3c5c1c37fd0d8a48599a8c481a3814040404040404000000004f1f2f3f4d8a48599a8d3899489a340404040404000000002f5f6");
        ByteArrayInputStream hostStream = new ByteArrayInputStream(hostBytes);
        LegStarMessage message = new LegStarMessage();
        message.recvFromHost(hostStream);
        return message;
    }
    
    private LegStarMessage getResultData(byte[] hostBytes) throws HostReceiveException, HeaderPartException {
        ByteArrayInputStream hostStream = new ByteArrayInputStream(hostBytes);
        LegStarMessage message = new LegStarMessage();
        message.recvFromHost(hostStream);
        return message;
    }
}
