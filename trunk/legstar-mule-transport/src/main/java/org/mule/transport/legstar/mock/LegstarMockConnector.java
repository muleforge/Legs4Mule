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
package org.mule.transport.legstar.mock;

import org.mule.transport.AbstractConnector;
import org.mule.transport.legstar.LegstarConnector;
import org.mule.transport.legstar.LegstarConnectorHelper;
import org.mule.transport.legstar.config.HostCredentials;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.InitialisationException;

/**
 * This connector simulates mainframe access for adapters. 
 */
public class LegstarMockConnector extends AbstractConnector implements LegstarConnector {

    /**
     * @param context the Mule context.
     */
	public LegstarMockConnector(MuleContext context) {
		super(context);
	}

	/** This constant defines the main transport protocol identifier. */
    public static final String LEGSTARMOCK = "legstar-mock";
    
    /** Host user ID. */
    private String _hostUserID = "";
    
    /** Host password. */
    private String _hostPassword = "";

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    /* IMPLEMENTATION NOTE: All configuaration for the transport should be set
       on the Connector object, this is the object that gets configured in
       MuleXml */

    /** {@inheritDoc} */
    public void doInitialise() throws InitialisationException  {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Is called once all bean properties have been
           set on the connector and can be used to validate and initialise the
           connectors state. */
    }

    /** {@inheritDoc} */
    public void doConnect() throws Exception {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Makes a connection to the underlying
           resource. When connections are managed at the receiver/dispatcher
           level, this method may do nothing */
    }

    /** {@inheritDoc} */
    public void doDisconnect() throws Exception  {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Disconnects any connections made in the
           connect method If the connect method did not do anything then this
           method shouldn't do anything either. */
    }

    /** {@inheritDoc} */
    public void doStart() throws MuleException {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: If there is a single server instance or
           connection associated with the connector i.e.  AxisServer or a Jms
           Connection or Jdbc Connection, this method should put the resource
           in a started state here. */
    }

    /** {@inheritDoc} */
    public void doStop() throws MuleException {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Should put any associated resources into a
           stopped state. Mule will automatically call the stop() method. */
    }

    /** {@inheritDoc} */
    public void doDispose() {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Should clean up any open resources associated
           with the connector. */
    }

    /** {@inheritDoc} */
    public String getProtocol() {
        return LEGSTARMOCK;
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
