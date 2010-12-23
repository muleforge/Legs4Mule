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
package org.mule.transport.legstar.model.options;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;
import com.legstar.codegen.models.AbstractPropertiesModel;

/**
 * Set of parameters needed for TCP transport.
 */
public class TcpTransportParameters extends AbstractPropertiesModel {

    /* ====================================================================== */
    /* Following are this class default values.                             = */
    /* ====================================================================== */

    /** The default port number on which the HTTP server listens. */
    public static final int DEFAULT_TCP_PORT = 3060;

    /** The default scheme to use for URLs. */
    public static final String DEFAULT_TCP_SCHEME = "tcp";

    /* ====================================================================== */
    /* Following are this class fields that are persistent.                 = */
    /* ====================================================================== */

    /** The URL scheme. */
    private String _tcpScheme = DEFAULT_TCP_SCHEME;

    /** The host on which the HTTP server listens. */
    private String _tcpHost;

    /** The port number on which the HTTP server listens. */
    private int _tcpPort = DEFAULT_TCP_PORT;

    /** User ID for basic authentication. */
    private String _tcpUserId = "";

    /** Password for basic authentication. */
    private String _tcpPassword = "";

    /* ====================================================================== */
    /* Following are key identifiers for this model persistence. = */
    /* ====================================================================== */
    /** TCP host. */
    public static final String TCP_HOST = "tcpHost";

    /** TCP port. */
    public static final String TCP_PORT = "tcpPort";

    /** TCP user id. */
    public static final String TCP_USERID = "tcpUserId";

    /** TCP password. */
    public static final String TCP_PASSWORD = "tcpPassword";

    /** TCP URL. */
    public static final String TCP_URL = "tcpURL";

    
    /**
     * Default constructor.
     */
    public TcpTransportParameters() {
        _tcpHost =  CodeGenUtil.getLocalIPAddress();
    }

    /**
     * Construct from properties.
     * @param props a set of properties
     */
    public TcpTransportParameters(final Properties props) {
        super(props);
        setHost(getString(props, TCP_HOST, CodeGenUtil.getLocalIPAddress()));
        setPort(getInt(props, TCP_PORT, DEFAULT_TCP_PORT));
        setUserId(getString(props, TCP_USERID, ""));
        setPassword(getString(props, TCP_PASSWORD, ""));
    }
    /**
     * TCP parameters are expected by templates to come from a parameters map.
     * @param parameters a parameters map to which tcp parameters must be added
     */
    public void add(final Map < String, Object > parameters) {
        parameters.put(TCP_HOST, getHost());
        parameters.put(TCP_PORT, Integer.toString(getPort()));
        parameters.put(TCP_USERID, getUserId());
        parameters.put(TCP_PASSWORD, getPassword());
        parameters.put(TCP_URL, getUrl());
    }

    /**
     * Check that parameters are set correctly.
     * @throws CodeGenMakeException if parameters are missing or wrong
     */
    public void check() throws CodeGenMakeException {
        if (getHost() == null || getHost().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify an TCP host");
        }
        checkTcpURI(getUrl());
    }

    /**
     * Checks that a URI is valid with TCP scheme.
     * @param tcpUri the URI to check
     * @throws CodeGenMakeException if URI has wrong syntax
     */
    public static void checkTcpURI(
            final String tcpUri) throws CodeGenMakeException {
        try {
            if (tcpUri == null || tcpUri.length() == 0) {
                throw new CodeGenMakeException(
                "You must specify a valid URI");
            }
            URI uri = new URI(tcpUri);
            if (uri.getScheme() == null
                    || uri.getScheme().compareToIgnoreCase("tcp") != 0) {
                throw new CodeGenMakeException(
                        "URI " + uri + " must have tcp scheme");
            }
        } catch (URISyntaxException e) {
            throw new CodeGenMakeException(e);
        }

    }
    /**
     * @return a String representation of the TCP URL
     */
    public String getUrl() {
        return getScheme() + "://" + getHost() + ":" + getPort();
    }

    /**
     * @return the scheme to use with TCP
     */
    public String getScheme() {
        return _tcpScheme;
    }

    /**
     * @return the host on which the HTTP server listens
     */
    public String getHost() {
        return _tcpHost;
    }

    /**
     * @param host the host on which the HTTP server listens
     */
    public void setHost(final String host) {
        _tcpHost = host;
    }

    /**
     * @return the port number on which the HTTP server listens
     */
    public int getPort() {
        return _tcpPort;
    }

    /**
     * @param port the port number on which the HTTP server listens
     */
    public void setPort(final int port) {
        _tcpPort = port;
    }

    /**
     * @return the user ID for basic authentication
     */
    public String getUserId() {
        return _tcpUserId;
    }

    /**
     * @param userId the user ID for basic authentication to set
     */
    public void setUserId(final String userId) {
        _tcpUserId = userId;
    }

    /**
     * @return the password for basic authentication
     */
    public String getPassword() {
        return _tcpPassword;
    }

    /**
     * @param password the password for basic authentication to set
     */
    public void setPassword(final String password) {
        _tcpPassword = password;
    }

    /**
     * @return a properties file holding the values of this object fields
     */
    public Properties toProperties() {
        Properties props = super.toProperties();
        putString(props, TCP_HOST, getHost());
        putInt(props, TCP_PORT, getPort());
        putString(props, TCP_USERID, getUserId());
        putString(props, TCP_PASSWORD, getPassword());
        return props;
    }
}
