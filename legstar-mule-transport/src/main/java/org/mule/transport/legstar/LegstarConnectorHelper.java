package org.mule.transport.legstar;

import org.mule.api.MuleMessage;
import org.mule.transport.legstar.config.HostCredentials;

/**
 * Provides common utility methods for connectors.
 *
 */
public final class LegstarConnectorHelper {
    
    /**
     * Utility class.
     */
    private LegstarConnectorHelper() {
        
    }

    /**
     * Credentials can be set at the connector level or passed as properties
     * in incoming messages.
     * <p/>
     * The dynamic credentials passed in message properties take precedence over
     * any connector property.
     * @param message the incoming mule message
     * @param connector the connector used to send that message
     * @return a set of host credentials
     */
    public static HostCredentials getHostCredentials(
            final LegstarConnector connector,
            final MuleMessage message) {
        String hostUserID = (String) message.getProperty(
                LegstarConnector.HOST_USERID_PROPERTY);
        if (hostUserID == null) {
            hostUserID = connector.getHostUserID();
        }
        String hostPassword = (String) message.getProperty(
                LegstarConnector.HOST_PASSWORD_PROPERTY);
        if (hostPassword == null) {
            hostPassword = connector.getHostPassword();
        }
        return new HostCredentials(hostUserID, hostPassword.toCharArray());
    }
}
