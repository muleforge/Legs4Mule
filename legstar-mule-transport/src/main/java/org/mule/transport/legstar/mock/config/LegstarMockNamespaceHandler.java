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
package org.mule.transport.legstar.mock.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.transport.legstar.mock.LegstarMockConnector;
import org.mule.endpoint.URIBuilder;

/**
 * Registers a Bean Definition Parser for handling <code>&lt;legstar-mock:connector&gt;</code> elements
 * and supporting endpoint elements.
 */
public class LegstarMockNamespaceHandler extends AbstractMuleNamespaceHandler {

    /** {@inheritDoc} */
    public void init() {
        /* This creates handlers for 'endpoint', 'outbound-endpoint' and 'inbound-endpoint' elements.
           The defaults are sufficient unless you have endpoint styles different from the Mule standard ones
           The URIBuilder as constants for common required attributes, but you can also pass in a user-defined String[].
         */
        registerStandardTransportEndpoints(LegstarMockConnector.LEGSTARMOCK, URIBuilder.PATH_ATTRIBUTES);

        /* This will create the handler for your custom 'connector' element.
         * You will need to add handlers for any other xml elements you define.
         * For more information see: http://www.mulesource.org/display/MULE2USER/Creating+a+Custom+XML+Namespace
        */
        registerConnectorDefinitionParser(LegstarMockConnector.class);
    }
}
