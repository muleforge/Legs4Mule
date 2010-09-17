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

package org.mule.transport.legstar.wmq;

import org.mule.transport.jms.JmsConnector;
import org.mule.transport.legstar.LegstarConnector;
import org.mule.transport.legstar.LegstarConnectorHelper;
import org.mule.transport.legstar.config.HostCredentials;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;

/**
 * <code>LegstarWmqConnector</code> is the LegStar Websphere MQ connector.
 * <p/>
 * This is a simple extension of JmsConnector.
 */
public class LegstarWmqConnector extends JmsConnector implements LegstarConnector {

    /**
     * @param context the Mule context
     */
    public LegstarWmqConnector(MuleContext context) {
		super(context);
	}

	/** This constant defines the main transport protocol identifier. */
    public static final String LEGSTARWMQ = "legstar-wmq";

    /** Host user ID. */
    private String _hostUserID = "";
    
    /** Host password. */
    private String _hostPassword = "";

    /** {@inheritDoc} */
    public String getProtocol() {
        return LEGSTARWMQ;
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
     * This must be the same as the parent {@link JmsConnector#getUsername()}.
     * @param userID the host user ID to set
     */
    public void setHostUserID(final String userID) {
        _hostUserID = userID;
        super.setUsername(userID);
    }

    /**
     * This must be the same as the parent {@link JmsConnector#getPassword()}.
     * @param password the host Password to set
     */
    public void setHostPassword(final String password) {
        _hostPassword = password;
        setPassword(password);
    }
    
    /** {@inheritDoc} */
    public void setUsername(final String username) {
        _hostUserID = username;
        super.setUsername(username);
    }

    /** {@inheritDoc} */
    public void setPassword(final String password) {
        _hostPassword = password;
        super.setPassword(password);
    }
}
