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

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.legstar.LegstarConnector;
import org.mule.transport.legstar.LegstarConnectorHelper;
import org.mule.transport.legstar.config.HostCredentials;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.MessageReceiver;

/**
 * <code>LegstarConnector</code> is essentially and <code>HttpConnector</code>
 * with transformers to handle to special LegStar messaging to wrap
 * mainframe data. The LegStar support for HTTP must be installed on the
 * mainframe @see http://www.legsem.com/legstar/legstar-chttprt.
 */
public class LegstarHttpConnector extends HttpConnector implements LegstarConnector {
    
    /** Protocol internal name. Will not appear in URIs. */
    public static final String INTERNAL_PROTOCOL = "legstar";

    /** Protocol external name. Will appear in URIs. */
    public static final String EXTERNAL_PROTOCOL = "legstar:http";

    /** Host user ID. */
    private String _hostUserID = "";
    
    /** Host password. */
    private String _hostPassword = "";

    /** logger used by this class.   */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * No-Args constructor.
     */
    public LegstarHttpConnector() {
        registerProtocols();
    }

    /**
     * Used to register the legtstar:http
     * as a valid protocol combination. "legstar" is the scheme
     * meta info and http is the protocol.
     */
    public final void registerProtocols() {
        registerSupportedProtocolWithoutPrefix(EXTERNAL_PROTOCOL);
    }

    /** {@inheritDoc} */
    public final String getProtocol() {
        return INTERNAL_PROTOCOL;
    }

    /** 
     * Because MessageReceiver getTargetReceiver does a lookup with
     * a key like legstar://localhost:8083 instead of http://localhost:8083
     * we override the standard method.
     * {@inheritDoc}
     *  */
    public final MessageReceiver lookupReceiver(final String key) {
        if (key != null) {
            MessageReceiver receiver = (MessageReceiver) receivers.get(key);
            if (receiver == null) {
                receiver = (MessageReceiver) receivers.get(
                        key.replace(getProtocol() + ':', "http:"));
            }
            return receiver;
        } else {
            throw new IllegalArgumentException("Receiver key must not be null");
        }
    }

    /** {@inheritDoc} */
    public HostCredentials getHostCredentials(final MuleMessage message) {
        return LegstarConnectorHelper.getHostCredentials(this, message);
    }

    /**
     * @return the host user ID
     */
    public String getHostUserID() {
        return _hostUserID;
    }

    /**
     * @return the host Password
     */
    public String getHostPassword() {
        return _hostPassword;
    }

    /**
     * @param userID the host user ID to set
     */
    public void setHostUserID(final String userID) {
        _hostUserID = userID;
    }

    /**
     * @param password the host Password to set
     */
    public void setHostPassword(final String password) {
        _hostPassword = password;
    }

    /** 
     * {@inheritDoc}
     * We override this method from HttpConnector in order to provide basic
     * authentication using the host credentials setup at the connector level
     * or passed as message properties. We do that only if authentication was not setup
     * any other way.
     * 
     * */
    protected void setupClientAuthorization(
            final MuleEvent event,
            final HttpMethod httpMethod,
            final HttpClient client,
            final ImmutableEndpoint endpoint)
            throws UnsupportedEncodingException {
        /* give HttpConnector a chance to setup security*/
        super.setupClientAuthorization(event, httpMethod, client, endpoint);
        HostCredentials hostCredentials = getHostCredentials(event.getMessage());
        if (httpMethod.getRequestHeader(HttpConstants.HEADER_AUTHORIZATION) == null
                && hostCredentials.getUserInfo() != null) {
            if (_log.isDebugEnabled()) {
                _log.debug("adding security header " + hostCredentials.toString());
            }
            StringBuffer header = new StringBuffer(128);
            header.append("Basic ");
            header.append(new String(Base64.encodeBase64(
                    hostCredentials.getUserInfo().getBytes(
                endpoint.getEncoding()))));
            httpMethod.addRequestHeader(HttpConstants.HEADER_AUTHORIZATION, header.toString());
        }
    }
    

}

