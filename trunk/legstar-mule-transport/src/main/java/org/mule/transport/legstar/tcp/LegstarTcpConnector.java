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
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.tcp;

import java.net.Socket;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.mule.transport.legstar.LegstarConnector;
import org.mule.transport.legstar.LegstarConnectorHelper;
import org.mule.transport.legstar.config.HostCredentials;
import org.mule.transport.tcp.TcpConnector;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;

/**
 * <code>LegstarTcpConnector</code> is a mainframe connector over sockets.
 * <p/>
 * This code inherits and borrows from TcpConnector. It allows sockets to be
 * reused with the restriction that each socket is associated with a set of
 * mainframe credentials in addition to the endpoint.
 */
public class LegstarTcpConnector extends TcpConnector implements LegstarConnector {

    /** This constant defines the main transport protocol identifier. */
    public static final String LEGSTARTCP = "legstar-tcp";
    
    /** Host user ID. */
    private String _hostUserID = "";
    
    /** Host password. */
    private String _hostPassword = "";

    /** A pool of sockets mapped to endpoint/credentials. */
    private GenericKeyedObjectPool _socketsPool = new GenericKeyedObjectPool();

    /**
     * Overriding the TCP connector in order to substitute the socket
     * factory with our own. We also have our own protocol.
     * @param context the Mule context.
     */
    public LegstarTcpConnector(MuleContext context) {
        super(context);
        setSocketFactory(new LegstarTcpSocketFactory(this));
        setTcpProtocol(new LegstarTcpProtocol());
    }


    /** {@inheritDoc} */
    public void doInitialise() throws InitialisationException {
        _socketsPool.setFactory(getSocketFactory());
        _socketsPool.setTestOnBorrow(true);
        // Testing is expensive in our case because we send a probe to the mainframe
        _socketsPool.setTestOnReturn(false);
        //There should only be one pooled instance per socket (key)
        _socketsPool.setMaxActive(1);
        _socketsPool.setWhenExhaustedAction(GenericKeyedObjectPool.WHEN_EXHAUSTED_BLOCK);
    }

    /** {@inheritDoc} */
    public void doDisconnect() throws Exception {
        _socketsPool.clear();
        super.doDisconnect();
    }

    /** {@inheritDoc} */
    public void doDispose() {
        try {
            _socketsPool.close();
        } catch (Exception e) {
            logger.warn("Failed to close dispatcher socket pool: " + e.getMessage());
        }
        super.doDispose();
    }

    /** {@inheritDoc} */
    public String getProtocol() {
        return LEGSTARTCP;
    }

    /** {@inheritDoc} */
    public boolean isSyncEnabled(final String protocol) {
        if (protocol.equals(getProtocol())) {
            return true;
        }
        return false;
    }

    /**
     * Lookup a socket in the list of dispatcher sockets but don't create a new
     * socket.
     * <p/>
     * This code is almost identical to TcpConnector#getSocket but there are no other way
     * we can substitute our own socket key.
     * @param event the mule event that triggered the need for a socket
     * @return a socket
     * @throws Exception if socket cannot be retrieved
     */
    protected Socket getSocket(final MuleEvent event) throws Exception {
        ImmutableEndpoint endpoint = event.getEndpoint();
        HostCredentials hostCredentials = getHostCredentials(event.getMessage());
        LegstarTcpSocketKey socketKey = new LegstarTcpSocketKey(endpoint, hostCredentials);
        if (logger.isDebugEnabled()) {
            logger.debug("borrowing socket for " + socketKey + "/" + socketKey.hashCode());
        }
        Socket socket = (Socket) _socketsPool.borrowObject(socketKey);
        if (logger.isDebugEnabled()) {
            logger.debug("borrowed socket, "
                    + (socket.isClosed() ? "closed" : "open") 
                    + "; debt " + _socketsPool.getNumActive());
        }
        return socket;
    }

    /**
     * Return the socket to the pool.
     * @param socket socket to return
     * @param event triggering event
     * @throws Exception if unable to return
     */
    protected void releaseSocket(
            final Socket socket,
            final MuleEvent event) throws Exception {
        ImmutableEndpoint endpoint = event.getEndpoint();
        HostCredentials hostCredentials = getHostCredentials(event.getMessage());
        LegstarTcpSocketKey socketKey = new LegstarTcpSocketKey(endpoint, hostCredentials);
        _socketsPool.returnObject(socketKey, socket);
        if (logger.isDebugEnabled()) {
            logger.debug("returning socket for " + socketKey + "/" + socketKey.hashCode());
            logger.debug("returned socket; debt " + _socketsPool.getNumActive());
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

}
