package org.mule.providers.legstar.test.lsfileae;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.mule.extras.client.MuleClient;
import org.mule.providers.legstar.cixs.MuleHostHeaderFactory;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import com.legstar.test.coxb.LsfileaeCases;
import com.legstar.test.coxb.lsfileae.Dfhcommarea;

/**
 * Test the adapter for LSFILEAE in a bridge configuration (using legstar-mule HTTP transport).
 * This test requires access to an actual mainframe running FILEA sample.
 */
public class LsfileaeBridgeTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-bridge-config-lsfileae.xml";
    }
    
    /**
     * Run the target LSFILEAE mainframe program.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        /* Visually check that the mainframe received these headers */
        Map < String, Object > messageProperties = new HashMap < String, Object >();
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_REQUEST_ID, "legstar-mule");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_USERID, "MYUSER");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_PASSWORD, "MYPASS");
        messageProperties.put(MuleHostHeaderFactory.LEGSTAR_HOST_TRACE_MODE, new Boolean(true));
        MuleClient client = new MuleClient();
        UMOMessage message = client.send(
                "tcp://localhost:3213",
                LsfileaeCases.getJavaObjectRequest100(),
                messageProperties);
        assertNotNull(message);
        assertNull(message.getExceptionPayload());
        assertFalse(message.getPayload() == null);
        assertTrue(message.getPayload() instanceof byte[]);
        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream((byte[]) message.getPayload()));
        LsfileaeCases.checkJavaObjectReply100((Dfhcommarea) in.readObject());
        
    }

}
