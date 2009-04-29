package org.mule.transport.legstar.config;



import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.specific.TransformerDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.legstar.cixs.transformer.HostToLegstarMuleTransformer;
import org.mule.transport.legstar.cixs.transformer.LegstarToHostMuleTransformer;
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
                new TransformerDefinitionParser(HostToLegstarMuleTransformer.class));
        registerBeanDefinitionParser("legstar-to-host-transformer",
                new TransformerDefinitionParser(LegstarToHostMuleTransformer.class));

        registerBeanDefinitionParser("host-byte-array-to-http-response-transformer",
                new TransformerDefinitionParser(HostByteArrayToHttpResponse.class));
    }
}
