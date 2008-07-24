package org.mule.providers.legstar.cixs;

import junit.framework.TestCase;

public class LegStarPoolAgentTestCase extends TestCase {
    
    public void testLoadConfig() throws Exception {
        LegStarPoolAgent poolAgent = new LegStarPoolAgent();
        poolAgent.initialise();
        poolAgent.start();
        poolAgent.stop();
        poolAgent.dispose();
    }

}
