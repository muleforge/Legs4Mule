/*
 * $Id: NamespaceHandler.vm 10621 2008-01-30 12:15:16Z dirk.olmes $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.legstar.wmq.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.processors.CheckExclusiveAttributes;
import org.mule.config.spring.parsers.specific.TransformerDefinitionParser;
import org.mule.transport.jms.config.JmsNamespaceHandler;
import org.mule.transport.legstar.wmq.LegstarWmqConnector;
import org.mule.transport.legstar.wmq.transformer.HostToMqcihExecRequestMuleTransformer;
import org.mule.transport.legstar.wmq.transformer.MqcihExecReplyToHostMuleTransformer;
import org.mule.endpoint.URIBuilder;

/**
 * Registers a Bean Definition Parser for handling <code>&lt;legstar-wmq:connector&gt;</code> elements
 * and supporting endpoint elements.
 */
public class LegstarWmqNamespaceHandler extends AbstractMuleNamespaceHandler {

    /** {@inheritDoc} */
    public void init() {
        registerStandardTransportEndpoints(LegstarWmqConnector.LEGSTARWMQ, URIBuilder.PATH_ATTRIBUTES);
        registerMuleBeanDefinitionParser("connector", new LegstarWmqConnectorDefinitionParser()).addAlias(
                JmsNamespaceHandler.NUMBER_OF_CONSUMERS_ATTRIBUTE,
                JmsNamespaceHandler.NUMBER_OF_CONSUMERS_PROPERTY).registerPreProcessor(
                new CheckExclusiveAttributes(new String[][]{
                    new String[]{JmsNamespaceHandler.NUMBER_OF_CONCURRENT_TRANSACTED_RECEIVERS_ATTRIBUTE},
                    new String[]{JmsNamespaceHandler.NUMBER_OF_CONSUMERS_ATTRIBUTE}}));
        registerBeanDefinitionParser("host-to-mqcih-transformer",
                new TransformerDefinitionParser(HostToMqcihExecRequestMuleTransformer.class));
        registerBeanDefinitionParser("mqcih-to-host-transformer",
                new TransformerDefinitionParser(MqcihExecReplyToHostMuleTransformer.class));

    }
}
