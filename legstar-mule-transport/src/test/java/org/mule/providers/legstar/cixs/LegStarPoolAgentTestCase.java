package org.mule.providers.legstar.cixs;

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
