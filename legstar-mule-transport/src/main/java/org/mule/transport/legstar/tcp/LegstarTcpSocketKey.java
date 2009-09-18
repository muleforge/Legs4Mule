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

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.security.Credentials;
import org.mule.transport.tcp.TcpSocketKey;

/**
 * Socket connections to mainframes are associated with security credentials.
 * We don't want two different users to share the same socket.
 * <p/>
 * Here we override the tcp socket key to make it unique for a set of credentials.
 *
 */
public class LegstarTcpSocketKey extends TcpSocketKey {

    /** Set of credentials associated with this socket key.*/
    private Credentials _credentials;

    /**
     * Construct a socket key.
     * @param endpoint the target endpoint
     * @param credentials the associated credentials
     */
    public LegstarTcpSocketKey(
            final ImmutableEndpoint endpoint, final Credentials credentials) {
        super(endpoint);
        _credentials = credentials;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof LegstarTcpSocketKey) {
            Credentials objCredentials = ((LegstarTcpSocketKey) obj).getCredentials(); 
            if (_credentials == null) {
                return (objCredentials == null) ? true : false;
            } else {
                return (objCredentials == null) ? false : _credentials.equals(objCredentials);
            }
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + super.hashCode();
        hash = hash * 31 + (_credentials == null ? 0 : _credentials.hashCode());
        return hash;
    }

    /**
     * @return the set of credentials associated with this socket key
     */
    public Credentials getCredentials() {
        return _credentials;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return super.toString() + ", credentials:[" + getCredentials().toString() + "]";
    }
}
