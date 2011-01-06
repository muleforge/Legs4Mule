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
