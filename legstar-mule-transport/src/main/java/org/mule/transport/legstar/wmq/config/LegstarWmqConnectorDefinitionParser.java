package org.mule.transport.legstar.wmq.config;

import org.mule.transport.jms.config.JmsConnectorDefinitionParser;
import org.mule.transport.legstar.wmq.LegstarWmqConnector;

/**
 * JMS has a separate definition for its connector XSD attributes.
 *
 */
public class LegstarWmqConnectorDefinitionParser extends JmsConnectorDefinitionParser {
    
    /**
     * Constructor.
     */
    public LegstarWmqConnectorDefinitionParser() {
        super(LegstarWmqConnector.class);
    }

}
