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
package org.mule.transport.legstar.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transport.http.HttpClientMessageDispatcher;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.transformers.ObjectToHttpClientMethodRequest;
import org.mule.transport.legstar.cixs.MuleHostHeaderFactory;
import org.mule.transport.legstar.cixs.transformer.HostToLegstarMuleTransformer;
import org.mule.transport.legstar.cixs.transformer.LegstarToHostMuleTransformer;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;

/**
 * <code>LegstarMessageDispatcher</code> delegates most of its behavior
 * to <code>HttpClientMessageDispatcher</code>.
 */
public class LegstarHttpMessageDispatcher extends HttpClientMessageDispatcher {

    /** When channeled over http, the legstar payload must be binary. */
    private static final String LEGSTAR_HTTP_CONTENT_TYPE =
        "application/octet-stream";
    
    /** Attribute set at the legstar-mule endpoint level. Gives the mainframe program attributes. */
    private static final String LEGSTAR_MULE_PROGRAM_PROP_FILE_NAME = "programPropFileName";
    
    /** Attribute set at the legstar-mule endpoint level. Gives the host trace mode. */
    private static final String LEGSTAR_MULE_HOST_TRACE_ON = "hostTraceOn";

    /** LegStar mainframe modules recognize this HTTP header for trace mode.*/
    private static final String LEGSTAR_HTTP_HEADER_TRACE_MODE = "CICSTraceMode";

    /** List of transformers to apply systematically before sending.
     * The last transformer in the list must produce an apache http method. */
    private final List < Transformer > _preSendTransformers = new ArrayList < Transformer >();

    /** List of transformers to apply systematically on return from sending. */
    private final List < Transformer > _postSendTransformers = new ArrayList < Transformer >();

    /** True if the host needs to trace execution requests. */
    private final boolean _isHostTraceOn;
    
    /** logger used by this class.   */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * Constructor for a given endpoint.
     * When the endpoint is configured with target mainframe program attributes, we
     * need to wrap the host data into a formatted LegStar message and then unwrap
     * upon return from executing the mainframe program.
     * @param endpoint the Mule endpoint
     */
    public LegstarHttpMessageDispatcher(final OutboundEndpoint endpoint) {
        super(endpoint);

        String programPropFileName = (String) endpoint.getProperty(LEGSTAR_MULE_PROGRAM_PROP_FILE_NAME);
        if (programPropFileName != null && programPropFileName.length() > 0) {
            HostToLegstarMuleTransformer toLegstarTransformer = new HostToLegstarMuleTransformer();
            toLegstarTransformer.setProgramPropFileName(programPropFileName);
            _preSendTransformers.add(toLegstarTransformer);
            _postSendTransformers.add(new LegstarToHostMuleTransformer());
        }

        ObjectToHttpClientMethodRequest toHttpClientMethodRequest = new ObjectToHttpClientMethodRequest();
        toHttpClientMethodRequest.setMuleContext(endpoint.getMuleContext());
        _preSendTransformers.add(toHttpClientMethodRequest);
 
        Object prop = endpoint.getProperty(LEGSTAR_MULE_HOST_TRACE_ON);
        if (prop != null) {
            _isHostTraceOn = Boolean.parseBoolean((String) prop);
        } else {
            _isHostTraceOn = false;
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void doInitialise() throws InitialisationException {
        super.doInitialise();
        for (Transformer transformer : _preSendTransformers) {
            transformer.initialise();
        }
        for (Transformer transformer : _postSendTransformers) {
            transformer.initialise();
        }
    }

    /** 
     * Overriding this method in order to apply post send transformers.
     * {@inheritDoc}
     *  */
    protected MuleMessage doSend(final MuleEvent event) throws Exception {
        MuleMessage esbMessage = super.doSend(event);
        esbMessage.applyTransformers(_postSendTransformers);
        return esbMessage;
    }

    /** 
     * We override this method because we need to perform LegStar messaging specific
     * transformations and also need to force the http header content type.
     * {@inheritDoc}
     *  */
    public final HttpMethod getMethod(final MuleEvent event) throws TransformerException {
        
        if (_log.isDebugEnabled()) {
            _log.debug("Creating http method for endpoint " + event.getEndpoint());
        }
        
        MuleMessage msg = event.getMessage();
        setPropertyFromEndpoint(event, msg, HttpConnector.HTTP_CUSTOM_HEADERS_MAP_PROPERTY);
        
        HttpMethod httpMethod;
        Object body = event.transformMessage();

        if (body instanceof HttpMethod) {
            httpMethod = (HttpMethod) body;
        } else {
            msg.applyTransformers(_preSendTransformers);
            httpMethod = (HttpMethod) msg.getPayload();
        }
        
        httpMethod.setFollowRedirects(((LegstarHttpConnector) getConnector()).isFollowRedirects());

        /* Force the content type expected by the Mainframe */
        httpMethod.addRequestHeader(HttpConstants.HEADER_CONTENT_TYPE,
                LEGSTAR_HTTP_CONTENT_TYPE);
        
        if (isHostTraceOn(msg)) {
            httpMethod.addRequestHeader(LEGSTAR_HTTP_HEADER_TRACE_MODE,
                    "true");
        }

        return httpMethod;
    }
    
    /**
     * @param esbMessage the mule message
     * @return true if the mainframe should trace execution requests
     */
    public boolean isHostTraceOn(final MuleMessage esbMessage) {
        return esbMessage.getBooleanProperty(
                MuleHostHeaderFactory.LEGSTAR_HOST_TRACE_ON, _isHostTraceOn);
    }

}


