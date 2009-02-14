package org.mule.providers.legstar.cixs;

import junit.framework.TestCase;

/**
 * Test the AbstractAdapterCallable class.
 *
 */
public class AbstractAdapterCallableTest extends TestCase {
    
    /**
     * Test instantiation.
     */
    public void testAdapter() {
        AdapterCallableImpl adapterCallableImpl = new AdapterCallableImpl();
        assertEquals("adapterName", adapterCallableImpl.getActionAdapterName());
        assertEquals("legstar-invoker-config.xml", adapterCallableImpl.getLegStarConfigFileName());
        assertTrue(adapterCallableImpl.getRequestID(null).startsWith("adapterName:"));
        
    }

    /**
     * A simplistic implementation of the abstract class being tested.
     */
    public static class AdapterCallableImpl extends AbstractAdapterCallable {

        /**
         * Constructor.
         */
        public AdapterCallableImpl() {
            super("adapterName");
        }

        /** {@inheritDoc}*/
        public Object call(final Object request, final MuleHostHeader hostHeader) {
            return request;
        }

    }
}
