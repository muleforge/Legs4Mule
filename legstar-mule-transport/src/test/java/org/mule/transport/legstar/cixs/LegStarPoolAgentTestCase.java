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
 * Test LegStarPoolAgent class.
 *
 */
public class LegStarPoolAgentTestCase extends TestCase {
    
    /**
     * Test loading configuration.
     * @throws Exception if load fails
     */
    public void testLoadConfig() throws Exception {
        LegStarPoolAgent poolAgent = new LegStarPoolAgent();
        poolAgent.initialise();
        poolAgent.start();
        poolAgent.stop();
        poolAgent.dispose();
    }

}
