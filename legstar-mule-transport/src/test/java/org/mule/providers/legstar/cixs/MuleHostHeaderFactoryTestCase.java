package org.mule.providers.legstar.cixs;

import java.util.HashMap;
import java.util.Map;

import org.mule.impl.MuleMessage;
import org.mule.umo.UMOMessage;

import junit.framework.TestCase;

public class MuleHostHeaderFactoryTestCase extends TestCase {
    
    public void testHeaderCreation() {
        Map <String, Object> messageProperties = new HashMap <String, Object>();
        messageProperties.put(MuleHostHeaderFactory.L4M_KEY_HOSTREQUESTID, "lsfileae-client");
        messageProperties.put(MuleHostHeaderFactory.L4M_KEY_HOSTUSERID, "P390");
        messageProperties.put(MuleHostHeaderFactory.L4M_KEY_HOSTPASSWORD, "STREAM2");
        messageProperties.put(MuleHostHeaderFactory.L4M_KEY_HOSTTRACEMODE, new Boolean(true));
        messageProperties.put(MuleHostHeaderFactory.L4M_KEY_HOSTCHARSET, "IBM01147");
        messageProperties.put(MuleHostHeaderFactory.L4M_KEY_HOSTENDPOINT, "someMainframe");
        
        UMOMessage umoMessage = new MuleMessage("message", messageProperties);
        MuleHostHeader h = MuleHostHeaderFactory.createHostHeader(umoMessage);
        assertEquals("P390", h.getHostUserID());
        assertEquals("STREAM2", h.getHostPassword());
        assertEquals("lsfileae-client", h.getHostRequestID());
        assertEquals(true, h.getHostTraceMode());
        assertEquals("IBM01147", h.getHostCharset());
        assertEquals("someMainframe", h.getHostEndPoint());
    }

}
