package org.mule.transport.legstar.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.security.Credentials;
import org.mule.transport.legstar.tcp.i18n.LegstarTcpMessages;
import org.mule.transport.tcp.TcpSocketFactory;

import com.legstar.codec.HostCodec;
import com.legstar.csok.client.CicsSocket;

/**
 * Deliver new socket connections when needed.
 * <p/>
 * Satisfy the EZASOCKET need for an initial message.
 *
 */
public class LegstarTcpSocketFactory extends TcpSocketFactory {

    /** Reply eye catcher for acknowledgements. TODO use CicsSocket. */
    public static final String REPLY_ACK_MSG_EC = "LSOKACK0";

    /** Maximum size of the host reply for acknowledgements and error
     *  reports. TODO use CicsSocket.*/
    public static final int MAX_PROT_REPLY_LEN = 266;

    /** Processing instructions for UOW commit. TODO use CicsSocket. */
    public static final String UOW_COMMIT = "Commit";

    /** Processing instructions for UOW rollback. TODO use CicsSocket. */
    public static final String UOW_ROLLBACK = "Rollback";

    /** Processing instructions for UOW keep. TODO use CicsSocket. */
    public static final String UOW_KEEP = "Keep";

    /** Connector to which this factory is bound. */
    private LegstarTcpConnector _connector;

    /** Localized messages. */
    private static final LegstarTcpMessages I18N = new LegstarTcpMessages();

    /**
     * logger used by this class.
     */
    private static final Log LOG = LogFactory.getLog(LegstarTcpSocketFactory.class);

    /**
     * Creates a factory and binds it to a protocol.
     * @param connector to bind to this factory
     */
    public LegstarTcpSocketFactory(final LegstarTcpConnector connector) {
        _connector = connector;
    }

    /**
     *  {@inheritDoc}
     *  The socket pool invokes this method when it needs to create a new
     *  socket. We need to override the standard behavior because EZASOCKET
     *  requires an initial client message before it becomes available for
     *  send/receive operations.
     *  
     *   */
    public Object makeObject(final Object key) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("No sockets available from pool. Requested to create a new one.");
        }
        Socket socket = (Socket) super.makeObject(key);
        LegstarTcpSocketKey socketKey = (LegstarTcpSocketKey) key;
        exchangeInitialMessage(socket, socketKey.getCredentials());
        return socket;
    }

    /**
     * {@inheritDoc}
     * When sockets are reused, the server transaction might have timed out or gone stale.
     * Here we probe the server to make sure the socket is usable.
     * */
    public boolean validateObject(
            final Object key, final Object object) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Requested to validate a socket.");
        }
        boolean valid = super.validateObject(key, object);
        if (getConnector().isKeepSendSocketOpen()) {
            try {
                exchangeProbeMessage((Socket) object);
            } catch (Exception e) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * {@inheritDoc}
     * When a socket is returned to the pool in opened state, we need to commit the
     * mainframe transaction (otherwise the transaction would never end).
     */
    public void passivateObject(final Object key, final Object object) throws Exception {
        super.passivateObject(key, object);
        Socket socket = (Socket) object;
        if (!socket.isClosed()) {
            exchangeCommitMessage((Socket) object);
        }
    }

    /**
     * EZASOKET expects an initial message that includes credentials.
     * TODO get authentication parameters from event
     * @param socket the opened connection to EZASOKET
     * @param credentials the host credentials
     * @throws Exception if initial message cannot be exchanged with CICS server
     */
    protected void exchangeInitialMessage(
            final Socket socket,
            final Credentials credentials) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending initial message to socket server.");
        }
        write(socket, 
                CicsSocket.formatCIM(
                        credentials.getUsername(),
                        new String(credentials.getPassword()),
                        "muleSocket",
                        (LOG.isDebugEnabled()) ? true : false,
                                HostCodec.HEADER_CODE_PAGE));
        receiveAck(socket);
    }

    /**
     * Probe the server transaction to make sure it is not stale.
     * @param socket the opened connection to EZASOKET
     * @throws Exception if probe message cannot be exchanged with CICS server
     */
    protected void exchangeProbeMessage(
            final Socket socket) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending probe message to socket server.");
        }
        write(socket,
                CicsSocket.formatProbe(HostCodec.HEADER_CODE_PAGE));
        receiveAck(socket);
    }

    /**
     * Commit the server transaction.
     * @param socket the opened connection to EZASOKET
     * @throws Exception if probe message cannot be exchanged with CICS server
     */
    protected void exchangeCommitMessage(
            final Socket socket) throws Exception {
        exchangeUOWMessage(socket, UOW_COMMIT);
    }

    /**
     * Instruct host on Unit Of Work processing and wait for Ack.
     * @param socket the opened connection to EZASOKET
     * @param command the unit of work command (commit, rollback or keep)
     * @throws Exception if a failure is detected
     */
    private void exchangeUOWMessage(
            final Socket socket,
            final String command) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending "
                    + command + " unit of work command to socket server.");
        }
        write(socket,
                CicsSocket.formatUOW(command, HostCodec.HEADER_CODE_PAGE));
        receiveAck(socket);
    }

    /**
     * Expecting an ACK reply from the socket server.
     * @param socket the opened socket
     * @throws IOException if ACK is not received
     */
    private void receiveAck(final Socket socket) throws IOException {
        byte[] response = read(socket, MAX_PROT_REPLY_LEN);
        if (response == null) {
            throw new IOException(I18N.noResponseFromHostMessage().getMessage());
        }
        String ackString = (new String(response, HostCodec.HEADER_CODE_PAGE)).trim();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Socket server reply is: " + ackString);
        }
        /* If this is not a valid ACK, it could be an error report*/
        if (REPLY_ACK_MSG_EC.compareTo(
                ackString.substring(
                        0, REPLY_ACK_MSG_EC.length())) != 0) {
            /* Sanity check for characters being displayable. We expect
             * the host error reply to start with an error code in
             * uppercase characters. */
            if (Character.getType(ackString.charAt(0))
                    == Character.UPPERCASE_LETTER) {
                throw (new IOException(ackString));
            } else {
                throw (new IOException(
                        I18N.unrecognizedResponseFromHostMessage().getMessage()));
            }
        }
    }

    /**
     * Send a byte array over on a socket.
     * @param socket the opened connection
     * @param data a byte array
     * @throws IOException if send operation fails
     */
    private void write(
            final Socket socket,
            final byte[] data) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(data);
        bos.flush();
    }

    /**
     * Read a predefined number of bytes.
     * @param socket the opened connection
     * @param maxLength the maximum number of bytes to read
     * @return bytes read
     * @throws IOException if read operation fails
     */
    private byte[] read(
            final Socket socket,
            final int maxLength) throws IOException {
        BufferedInputStream bis =
            new BufferedInputStream(socket.getInputStream());
        byte[] buffer = new byte[maxLength];
        bis.read(buffer, 0, maxLength);
        return buffer;
    }

    /**
     * @return the connector to which this factory is bound
     */
    public LegstarTcpConnector getConnector() {
        return _connector;
    }
}
