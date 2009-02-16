package org.mule.providers.legstar.test.lsfileae;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.mule.extras.client.MuleClient;
import org.mule.providers.legstar.cixs.MuleHostHeaderFactory;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.ObjectFactory;

/**
 * Test the adapter for LSFILEAE in a standalone configuration (using legstar regular transports).
 * This test requires access to an actual mainframe running FILEA sample.
 */
public class LsfileaeStandaloneTest extends FunctionalTestCase {

    /** {@inheritDoc}*/
    protected String getConfigResources() {
        return "mule-adapter-standalone-config-lsfileae.xml";
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
                "tcp://localhost:3213/lsfileae",
                getJavaObjectRequest100(),
                messageProperties);
        assertNotNull(message);
        assertNull(message.getExceptionPayload());
        assertFalse(message.getPayload() == null);
        assertTrue(message.getPayload() instanceof byte[]);
        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream((byte[]) message.getPayload()));
        checkJavaObjectReply100((Dfhcommarea) in.readObject());
        
    }

    /**
     * @return an instance of a valued java object.
     */
    public static Dfhcommarea getJavaObjectRequest100() {
        ObjectFactory of = new ObjectFactory();
        Dfhcommarea dfhcommarea = of.createDfhcommarea();
        dfhcommarea.setComNumber(100L);
        return dfhcommarea;
    }

    /** 
     * Check the values returned from LSFILAE after they were transformed to Java.
     * @param dfhcommarea the java data object
     */
    public static void checkJavaObjectReply100(final Dfhcommarea dfhcommarea) {
        assertEquals(100, dfhcommarea.getComNumber());
        assertEquals("$0100.11", dfhcommarea.getComAmount());
        assertEquals("*********", dfhcommarea.getComComment());
        assertEquals("26 11 81", dfhcommarea.getComDate());
        assertEquals("SURREY, ENGLAND", dfhcommarea.getComPersonal().getComAddress());
        assertEquals("S. D. BORMAN", dfhcommarea.getComPersonal().getComName());
        assertEquals("32156778", dfhcommarea.getComPersonal().getComPhone());
    }
}
