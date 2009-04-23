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
package org.mule.transport.legstar.cixs;

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
