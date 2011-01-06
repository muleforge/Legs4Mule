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
package org.mule.transport.legstar.tcp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mule.transport.legstar.tcp.i18n.LegstarTcpMessages;
import org.mule.transport.tcp.protocols.DirectProtocol;

import com.legstar.codec.HostCodec;
import com.legstar.csok.client.CicsSocket;

/**
 * This application level protocol is compatible with the LegStar z/OS LSOKBIN module.
 * After the initial connection dialog, the client is expected to send data formatted
 * like so:
 * 
 * Data item      Length Offset Purpose
 * -------------- ------ ------ -----------------
 * Message type      8     0    Type of service
 * String delimiter  1     8    Binary zero 
 * Message           x     9    Variable size
 * 
 * Acceptable values for message types are:
 * LSOKEXEC :A request to execute a program.
 * LSOKDATA :Input data for a request.
 * LSOKUOWC :A unit of work command.
 * LSOKPROB :A probe used to check if server is alive.
 *
 */
public class LegstarTcpProtocol extends DirectProtocol {

    /** Identifies execution requests. TODO use CicsSocket. */
    public static final String EXEC_REQUEST_EC = "LSOKEXEC";

    /** Size of header preceding any reply from host. TODO use CicsSocket. */
    public static final int REPLY_HDR_LEN = 9;

    /** Eye catcher for data messages.  TODO use CicsSocket.*/
    public static final String DATA_MSG_EC = "LSOKDATA";

    /** Reply eye catcher for errors. TODO use CicsSocket. */
    public static final String REPLY_ERROR_MSG_EC = "LSOKERR0";

    /** Maximum size of the host reply for acknowledgements and error
     *  reports. TODO make public. TODO use CicsSocket.*/
    public static final int MAX_PROT_REPLY_LEN = 266;

    /** Localized messages. */
    private static final LegstarTcpMessages I18N = new LegstarTcpMessages();

    /**
     *  {@inheritDoc}
     *  Send a formated request to LSSOKBIN.
     *  */
    protected void writeByteArray(
            final OutputStream os,
            final byte[] data) throws IOException {
        // Write the length and then the data.
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bos.write(CicsSocket.formatMessageType(
                EXEC_REQUEST_EC, HostCodec.HEADER_CODE_PAGE));
        bos.write(data);
        bos.flush();
    }

    /**
     *  {@inheritDoc}
     *  Receive formated response from LSSOKBIN.
     *  <p/>
     *  Transforms any application level errors into IOExceptions.
     *  */
    public Object read(final InputStream is) throws IOException {
        String msgType = recvMessageType(is);

        /* Check if this is a valid reply or an error reply */
        if (DATA_MSG_EC.compareTo(
                msgType.substring(
                        0, DATA_MSG_EC.length())) != 0) {
            recvError(msgType, is);
        }
        return super.read(is);
    }
 
    /**
     * All replies must start with a fixed size message type.
     * @param is an opened input stream on the host
     * @return the header element
     * @throws IOException if header element cannot be recovered
     */
    private String recvMessageType(
            final InputStream  is) throws IOException {
        String msgType = getCharsFromHost(is, REPLY_HDR_LEN);
        if (msgType == null) {
            throw (new IOException(I18N.noResponseFromHostMessage().getMessage()));
        }
        if (msgType.length() < REPLY_HDR_LEN) {
            throw (new IOException(I18N.invalidReplyFromHostMessage().getMessage()));
        }
        return msgType;
    }

    /**
     * When the reply does not present the expected header, it
     * might contain an error report.
     * @param msgType the header received
     * @param is an opened input stream on the host
     * @throws IOException in all cases
     */
    private void recvError(
            final String msgType,
            final InputStream  is) throws IOException {

        if (REPLY_ERROR_MSG_EC.compareTo(
                msgType.substring(
                        0, REPLY_ERROR_MSG_EC.length())) != 0) {
            /* Consider this is a system error message */
            String errString = getCharsFromHost(is, MAX_PROT_REPLY_LEN);
            throw (new IOException(msgType + errString));
        } else {
            /* Get the error message content */
            String errString = getCharsFromHost(is, MAX_PROT_REPLY_LEN);
            throw (new IOException(errString.trim()));
        }
    }
    
    /**
     * Receives character data from host and convert it from  character set.
     * It is assumed the maximum size to receive is small and is unlikely to
     * get chunked.
     * @param is an it stream from a socket connection to a host
     * @param maxSize the largest expected size
     * @return the result string
     * @throws IOException if receiving fails
     */
    private String getCharsFromHost(
            final InputStream  is,
            final int maxSize) throws IOException {
        byte[] buffer = (byte[]) super.read(is, maxSize);
        if (buffer == null) {
            return null;
        }
        return new String(buffer, HostCodec.HEADER_CODE_PAGE);
    }
}
