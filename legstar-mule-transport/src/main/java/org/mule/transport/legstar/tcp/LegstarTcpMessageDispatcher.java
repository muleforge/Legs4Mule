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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.retry.RetryContext;
import org.mule.api.transport.DispatchException;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.transport.DefaultMuleMessageFactory;
import org.mule.transport.legstar.i18n.LegstarMessages;
import org.mule.transport.tcp.TcpInputStream;
import org.mule.transport.tcp.TcpMessageDispatcher;

/**
 * <code>LegstarTcpMessageDispatcher</code> sends requests to the mainframe over sockets.
 */
public class LegstarTcpMessageDispatcher extends TcpMessageDispatcher {

    /** Localized common messages. */
    private static final LegstarMessages I18N_COMMON = new LegstarMessages();

    /**
     * Constructor.
     * @param endpoint the endpoint to dispatch to
     */
    public LegstarTcpMessageDispatcher(final OutboundEndpoint endpoint) {
        super(endpoint);
    }

    /**
     *  {@inheritDoc}
     *  We don't support asynchronous calls.
     *   */
    public void doDispatch(final MuleEvent event) throws Exception {
        throw new UnsupportedOperationException("doDispatch");
    }

    /**
     *  {@inheritDoc}
     *  We duplicate parts of TcpDispatcher#doSend because the socket pool key
     *  must contain the host credentials.
     *   
     *   */
    public synchronized MuleMessage doSend(final MuleEvent event) throws Exception {
        MuleMessage requestMuleMessage = event.getMessage();
        Object body = requestMuleMessage.getPayload();
        if (body instanceof byte[]) {
            Socket socket = getConnector().getSocket(event);
            dispatchToSocket(socket, event);

            try  {
                if (returnResponse(event, true)) {
                    try {
                        Object legstarReplyMessage = receiveFromSocket(socket, event);
                        if (legstarReplyMessage == null) {
                            return null;
                        }

                        if (legstarReplyMessage instanceof MuleMessage) {
                            return (MuleMessage) legstarReplyMessage;
                        }

                        MuleMessageFactory messageFactory =
                        	new DefaultMuleMessageFactory(getConnector().getMuleContext());
                        return messageFactory.create(legstarReplyMessage,
                        		getEndpoint().getEncoding());
                    } catch (SocketTimeoutException e) {
                        // we don't necessarily expect to receive a response here
                        logger.info("Socket timed out normally while doing a synchronous receive on endpointUri: "
                                + event.getEndpoint().getEndpointURI());
                        return null;
                    }
                } else {
                    return null;
                }
            } finally {
                if (!returnResponse(event, true)) {
                	getConnector().releaseSocket(socket, event);
                }
            }
        } else {
            throw new DispatchException(I18N_COMMON.invalidBodyMessage(),
            		event, this);  
        }
    }

    /**
     * Borrowed from {@link TcpMessageDispatcher#receiveFromSocket(Socket, int, ImmutableEndpoint)}.
     * We need to call our own implementation of connector.releaseSocket which takes the
     * event as parameter because credentials might be dynamic.
     * @param socket the opened socket
     * @param event the triggering event
     * @return the data received from the mainframe
     * @throws IOException if receiving fails
     */
    protected static Object receiveFromSocket(
            final Socket socket,
            final MuleEvent event) throws IOException {
        ImmutableEndpoint endpoint = event.getEndpoint();
        int timeout = event.getTimeout();
        final LegstarTcpConnector connector = (LegstarTcpConnector) endpoint.getConnector();
        DataInputStream underlyingIs = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        TcpInputStream tis = new TcpInputStream(underlyingIs)
        {
            @Override
            public void close() throws IOException {
                try {
                    connector.releaseSocket(socket, event);
                } catch (IOException e) {
                    throw e;
                } catch (Exception e) {
                    IOException e2 = new IOException();
                    e2.initCause(e);
                    throw e2;
                }
            }

        };

        if (timeout >= 0) {
            socket.setSoTimeout(timeout);
        }

        try {
            return connector.getTcpProtocol().read(tis);
        } finally {
            if (!tis.isStreaming()) {
                tis.close();
            }
        }
    }

    /**
     * A duplicate of TcpMessageDispatcher#dispatchToSocket that is private.
     * @param socket the opened socket
     * @param event the triggering event
     * @throws Exception if dispatch fails
     */
    private void dispatchToSocket(
            final Socket socket, final MuleEvent event) throws Exception {
        Object payload = event.getMessage().getPayload();
        write(socket, payload);
    }

    /**
     * A duplicate of TcpMessageDispatcher#write that is private.
     * @param socket the opened socket
     * @param data something to send
     * @throws IOException if write fails
     */
    private void write(final Socket socket, final Object data) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        getConnector().getTcpProtocol().write(bos, data);
        bos.flush();
    }

    /** 
     * {@inheritDoc}
     * Overriding {@link TcpMessageDispatcher#validateConnection(RetryContext)} because
     * we can't connect to the mainframe without an actual Mule event. 
     * */
    public RetryContext validateConnection(final RetryContext retryContext) {
        retryContext.setOk();
        return retryContext;
    }

    /**
     * @return the legstar TCP connector
     */
    public LegstarTcpConnector getConnector() {
        return (LegstarTcpConnector) super.getConnector();
    }

}

