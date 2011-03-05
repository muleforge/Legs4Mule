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
package org.mule.transport.legstar.config;




import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.specific.MessageProcessorDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.legstar.cixs.transformer.HostToLegstarExecRequestMuleTransformer;
import org.mule.transport.legstar.cixs.transformer.LegstarExecReplyToHostMuleTransformer;
import org.mule.transport.legstar.http.LegstarHttpConnector;
import org.mule.transport.legstar.http.transformer.HostByteArrayToHttpResponse;
import org.mule.transport.http.HttpConstants;

import com.legstar.host.invoke.model.HostContainer;

/**
 * Registers a Bean Definition Parser for handling <code>&lt;legstar:connector&gt;</code> elements.
 */
public class LegStarNamespaceHandler extends AbstractMuleNamespaceHandler {

    /** {@inheritDoc} */
    public void init() {
        registerStandardTransportEndpoints(
                LegstarHttpConnector.LEGSTARHTTP, URIBuilder.SOCKET_ATTRIBUTES)
                .addAlias("contentType", HttpConstants.HEADER_CONTENT_TYPE);
        
        registerConnectorDefinitionParser(LegstarHttpConnector.class);

        registerBeanDefinitionParser("java-to-host-transformer", new MessageProcessorDefinitionParser());
        registerBeanDefinitionParser("host-to-java-transformer", new MessageProcessorDefinitionParser());

        registerBeanDefinitionParser("xml-to-host-transformer", new MessageProcessorDefinitionParser());
        registerBeanDefinitionParser("host-to-xml-transformer", new MessageProcessorDefinitionParser());

        registerBeanDefinitionParser("host-to-legstar-transformer",
                new MessageProcessorDefinitionParser(HostToLegstarExecRequestMuleTransformer.class));
        registerBeanDefinitionParser("legstar-to-host-transformer",
                new MessageProcessorDefinitionParser(LegstarExecReplyToHostMuleTransformer.class));

        registerBeanDefinitionParser("host-byte-array-to-http-response-transformer",
                new MessageProcessorDefinitionParser(HostByteArrayToHttpResponse.class));
        
        registerMuleBeanDefinitionParser("host-program",
                new ChildDefinitionParser("hostProgram", HostProgram.class));
        registerMuleBeanDefinitionParser("input-container",
                new ChildDefinitionParser("inputContainers", HostContainer.class)).addCollection("inputContainers");
        registerMuleBeanDefinitionParser("output-container",
                new ChildDefinitionParser("outputContainers", HostContainer.class)).addCollection("outputContainers");

    }
}
