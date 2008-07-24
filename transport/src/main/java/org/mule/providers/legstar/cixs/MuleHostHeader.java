/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.cixs;

import java.io.Serializable;

import com.legstar.messaging.LegStarAddress;

/**
 * Configuration parameters that a client can send as a header element.
 */

public class MuleHostHeader implements Serializable
{

    /** Unique serial ID. */
    private static final long serialVersionUID = 2175774875242240562L;

    /** The host endpoint identifier. */
    private String mHostEndPoint;

    /** The host character set. */
    private String mHostCharset;

    /** User ID used for host authentication/impersonation. */
    private String mHostUserID;

    /** Password used for authentication. */
    private String mHostPassword;

    /** Whether the host should trace this request. */
    private boolean mHostTraceMode = false;

    /** An identifier for this request (used for tracability). */
    private String mHostRequestID;

    /**
     * Create a messaging Address from this host header data.
     * If no endpoint is explicitly specified, no address can be created.
     * All defaults from default configuration will apply.
     * @return the new host Address
     */
    public final LegStarAddress getAddress()
    {
        if (mHostEndPoint == null)
        {
            return null;
        }
        LegStarAddress address = new LegStarAddress(mHostEndPoint);
        address.setHostCharset(mHostCharset);
        address.setHostUserID(mHostUserID);
        address.setHostPassword(mHostPassword);
        address.setHostTraceMode(mHostTraceMode);
        return address;
    }


    /** Gets the user ID used for host authentication/impersonation.
     * @return host user ID
     */
    public final String getHostUserID()
    {
        return mHostUserID;
    }

    /**
     * Sets the user ID used for host authentication/impersonation.
     * @param userID host user ID to set
     */
    public final void setHostUserID(final String userID)
    {
        this.mHostUserID = userID;
    }

    /** 
     * Gets the password used for authentication.
     * @return host user ID
     */
    public final String getHostPassword()
    {
        return mHostPassword;
    }

    /**
     * Sets the password used for authentication.
     * @param password host user ID to set
     */
    public final void setHostPassword(final String password)
    {
        this.mHostPassword = password;
    }

    /**
     * Gets the target host identifier.
     * @return the target host identifier
     */
    public final String getHostEndPoint()
    {
        return mHostEndPoint;
    }

    /**
     * Sets the target host identifier.
     * @param endPoint target host identifier
     */
    public final void setHostEndPoint(final String endPoint)
    {
        this.mHostEndPoint = endPoint;
    }

    /**
     * Gets the target host character set.
     * @return the target host character set
     */
    public final String getHostCharset()
    {
        return mHostCharset;
    }

    /**
     * Sets the target host character set.
     * @param charset target host character set
     */
    public final void setHostCharset(final String charset)
    {
        this.mHostCharset = charset;
    }

    /**
     * Gets the host trace mode.
     * @return the host trace mode
     */
    public final boolean getHostTraceMode()
    {
        return mHostTraceMode;
    }

    /**
     * Sets the host trace mode.
     * @param traceMode the host trace mode
     */
    public final void setTraceMode(final boolean traceMode)
    {
        this.mHostTraceMode = traceMode;
    }

    /**
     * Gets the identifier for this request.
     * @return the request identifier
     */
    public final String getHostRequestID()
    {
        return mHostRequestID;
    }

    /**
     * Sets the identifier for this request.
     * @param requestID the identifier for this request
     */
    public final void setHostRequestID(final String requestID)
    {
        this.mHostRequestID = requestID;
    }

}

