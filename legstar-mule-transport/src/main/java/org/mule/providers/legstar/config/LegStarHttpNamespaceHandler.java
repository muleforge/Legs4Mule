package org.mule.providers.legstar.config;



import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.endpoint.URIBuilder;
import org.mule.providers.legstar.http.LegstarHttpConnector;
import org.mule.transport.http.HttpConstants;

/**
 * Registers a Bean Definition Parser for handling <code>&lt;legstar:connector&gt;</code> elements.
 */
public class LegStarHttpNamespaceHandler extends AbstractMuleNamespaceHandler {

    /** {@inheritDoc} */
    public void init() {
        registerStandardTransportEndpoints(
                LegstarHttpConnector.PROTOCOL + ":http", URIBuilder.SOCKET_ATTRIBUTES)
                .addAlias("contentType", HttpConstants.HEADER_CONTENT_TYPE);
        
        registerConnectorDefinitionParser(LegstarHttpConnector.class);
    }
}
