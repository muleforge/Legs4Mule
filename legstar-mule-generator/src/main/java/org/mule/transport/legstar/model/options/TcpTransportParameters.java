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

import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;

/**
 * Set of parameters needed for TCP transport.
 */
public class TcpTransportParameters {

    /** The URL scheme. */
    private String mScheme = "tcp";

    /** The host on which the HTTP server listens. */
    private String mHost =  CodeGenUtil.getLocalIPAddress();

    /** The default port number on which the HTTP server listens. */
    public static final int DEFAULT_PORT = 3060;

    /** The port number on which the HTTP server listens. */
    private int mPort = DEFAULT_PORT;

    /** User ID for basic authentication. */
    private String mUserId = "";

    /** Password for basic authentication. */
    private String mPassword = "";

    /**
     * TCP parameters are expected by templates to come from a parameters map.
     * @param parameters a parameters map to which tcp parameters must be added
     */
    public void add(final Map < String, Object > parameters) {
        parameters.put("tcpHost", getHost());
        parameters.put("tcpPort", Integer.toString(getPort()));
        parameters.put("tcpUserId", getUserId());
        parameters.put("tcpPassword", getPassword());
        parameters.put("tcpURL", getUrl());
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
        return mScheme;
    }

    /**
     * @return the host on which the HTTP server listens
     */
    public String getHost() {
        return mHost;
    }

    /**
     * @param host the host on which the HTTP server listens
     */
    public void setHost(final String host) {
        mHost = host;
    }

    /**
     * @return the port number on which the HTTP server listens
     */
    public int getPort() {
        return mPort;
    }

    /**
     * @param port the port number on which the HTTP server listens
     */
    public void setPort(final int port) {
        mPort = port;
    }

    /**
     * @return the user ID for basic authentication
     */
    public String getUserId() {
        return mUserId;
    }

    /**
     * @param userId the user ID for basic authentication to set
     */
    public void setUserId(final String userId) {
        mUserId = userId;
    }

    /**
     * @return the password for basic authentication
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * @param password the password for basic authentication to set
     */
    public void setPassword(final String password) {
        mPassword = password;
    }

}
