/*
 * $Id: NamespaceHandler.vm 10621 2008-01-30 12:15:16Z dirk.olmes $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.legstar.tcp.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.transport.legstar.tcp.LegstarTcpConnector;
import org.mule.endpoint.URIBuilder;


/**
 * Registers a Bean Definition Parser for handling <code>&lt;legstartcp:connector&gt;</code> elements
 * and supporting endpoint elements.
 */
public class LegstarTcpNamespaceHandler extends AbstractMuleNamespaceHandler {

    /** {@inheritDoc} */
    public void init() {
        registerStandardTransportEndpoints(LegstarTcpConnector.LEGSTARTCP, URIBuilder.PATH_ATTRIBUTES);
        registerConnectorDefinitionParser(LegstarTcpConnector.class);
    }
}
