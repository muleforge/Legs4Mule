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
package org.mule.transport.legstar.config;

import java.util.Arrays;

import org.mule.api.security.Credentials;
import org.mule.util.ArrayUtils;

/**
 * Represents RACF-style credentials.
 *
 */
public class HostCredentials implements Credentials {

    /** Host user ID. */
    private final String _username;

    /** Host password.*/
    private final char[] _password;

    /**
     * Creates a credential object.
     * @param username the host user ID
     * @param password the host password
     */
    public HostCredentials(final String username, final char[] password) {
        _username = username;
        _password = ArrayUtils.clone(password);
    }

    /** {@inheritDoc} */
    public char[] getPassword() {
        return _password.clone();
    }

    /** {@inheritDoc} */
    public Object getRoles() {
        return null;
    }

    /** {@inheritDoc} */
    public String getUsername() {
        return _username;
    }

    /**
     * @return a concatenation of user password HTTP style
     */
    public String getUserInfo() {
        return (_username == null) ? null 
                : ((_password == null) ? _username
                        : _username + ':' + new String(_password)); 
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object obj) {
        if (obj instanceof HostCredentials) {
            HostCredentials objCredentials = (HostCredentials) obj;
            return isEquals(_username, objCredentials.getUsername())
            && isEquals(_password, objCredentials.getPassword());
        } else {
            return false;
        }
    }

    /**
     * Compares two strings even if they are null.
     * @param str1 first string
     * @param str2 second string
     * @return true if both strings are null or both are equal
     */
    private static boolean isEquals(final String str1, final String str2) {
        if (str1 == null) {
            return ((str2 == null) ? true : false);
        } else {
            return ((str2 == null) ? false : str1.equals(str2));
        }
    }

    /**
     * Compares two character arrays even if they are null.
     * @param str1 first character array
     * @param str2 second character array
     * @return true if both character arrays are null or both are equal
     */
    private static boolean isEquals(final char[] str1, final char[] str2) {
        return ArrayUtils.isEquals(str1, str2);
    }
    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + (_username == null ? 0 : _username.hashCode());
        hash = hash * 31 + Arrays.hashCode(_password);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "username:" + getUsername() + ", password:********";
    }
}
