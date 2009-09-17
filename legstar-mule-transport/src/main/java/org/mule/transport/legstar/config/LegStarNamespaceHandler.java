package org.mule.transport.legstar.config;




import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.specific.TransformerDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.legstar.cixs.transformer.HostToLegstarExecRequestMuleTransformer;
import org.mule.transport.legstar.cixs.transformer.LegstarExecReplyToHostMuleTransformer;
import org.mule.transport.legstar.http.LegstarHttpConnector;
import org.mule.transport.legstar.http.transformer.HostByteArrayToHttpResponse;
import org.mule.transport.http.HttpConstants;

/**
 * Registers a Bean Definition Parser for handling <code>&lt;legstar:connector&gt;</code> elements.
 */
public class LegStarNamespaceHandler extends AbstractMuleNamespaceHandler {

    /** {@inheritDoc} */
    public void init() {
        registerStandardTransportEndpoints(
                LegstarHttpConnector.EXTERNAL_PROTOCOL, URIBuilder.SOCKET_ATTRIBUTES)
                .addAlias("contentType", HttpConstants.HEADER_CONTENT_TYPE);
        
        registerConnectorDefinitionParser(LegstarHttpConnector.class);

        registerBeanDefinitionParser("java-to-host-transformer", new TransformerDefinitionParser());
        registerBeanDefinitionParser("host-to-java-transformer", new TransformerDefinitionParser());

        registerBeanDefinitionParser("xml-to-host-transformer", new TransformerDefinitionParser());
        registerBeanDefinitionParser("host-to-xml-transformer", new TransformerDefinitionParser());

        registerBeanDefinitionParser("host-to-legstar-transformer",
                new TransformerDefinitionParser(HostToLegstarExecRequestMuleTransformer.class));
        registerBeanDefinitionParser("legstar-to-host-transformer",
                new TransformerDefinitionParser(LegstarExecReplyToHostMuleTransformer.class));

        registerBeanDefinitionParser("host-byte-array-to-http-response-transformer",
                new TransformerDefinitionParser(HostByteArrayToHttpResponse.class));
        
        registerMuleBeanDefinitionParser("host-program",
                new ChildDefinitionParser("hostProgram", HostProgram.class));
        registerMuleBeanDefinitionParser("input-container",
                new ChildDefinitionParser("inputContainers", HostContainer.class)).addCollection("inputContainers");
        registerMuleBeanDefinitionParser("output-container",
                new ChildDefinitionParser("outputContainers", HostContainer.class)).addCollection("outputContainers");

    }
}
