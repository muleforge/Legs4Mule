package org.mule.transport.legstar;

import org.mule.api.MuleMessage;
import org.mule.transport.legstar.config.HostCredentials;

/**
 * All LegStar connectors must implement this interface.
 *
 */
public interface LegstarConnector {

    /** A mule property that might hold the host password. */
    String HOST_PASSWORD_PROPERTY = "hostPassword";

    /** A mule property that might hold the host user ID. */
    String HOST_USERID_PROPERTY = "hostUserID";
    
    /**
     * Credentials can be set at the connector level or passed as properties
     * in incoming messages.
     * <p/>
     * The dynamic credentials passed in message properties take precedence over
     * any connector property.
     * @param message the incoming mule message
     * @return a set of host credentials
     */
    HostCredentials getHostCredentials(final MuleMessage message);

    /**
     * @return the host user ID for mainframe authentication
     */
    String getHostUserID();

    /**
     * @return the host Password for mainframe authentication
     */
    String getHostPassword();
}
