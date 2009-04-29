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
package org.mule.transport.legstar.model;

import java.util.Map;

import com.legstar.codegen.CodeGenMakeException;

/**
 * Set of parameters needed for Websphere MQ transport.
 */
public class WmqTransportParameters {

    /** The default jndi file system directory relative to mule install folder. */
    public static final String DEFAULT_JNDI_FS_DIRECTORY = "file:///JNDI-Directory";

    /** The URL used to do naming lookups for WMQ resources. */
    private String mJndiUrl;
    
    /** The default context factory class used to do naming lookups for WMQ resources. */
    public static final String DEFAULT_JNDI_CONTEXT_FACTORY = "com.sun.jndi.fscontext.RefFSContextFactory";

    /** The context factory class used to do naming lookups for WMQ resources. */
    private String mJndiContextFactory = DEFAULT_JNDI_CONTEXT_FACTORY;
    
    /** The connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    private String mConnectionFactory;
    
    /** The ZOS WMQ Manager. */
    private String mZosQueueManager;

    /** The default suffix for WMQ Queue name receiving requests.*/
    public static final String DEFAULT_REQUEST_QUEUE_SUFFIX = "REQUEST.QUEUE";

    /** The WMQ Queue name receiving requests.*/
    private String mRequestQueue;
    
    /** The default suffix for WMQ Queue name receiving replies.*/
    public static final String DEFAULT_REPLY_QUEUE_SUFFIX = "REPLY.QUEUE";

    /** The WMQ Queue name receiving replies.*/
    private String mReplyQueue;
    
    /** The default suffix for WMQ Queue name receiving errors.*/
    public static final String DEFAULT_ERROR_QUEUE_SUFFIX = "ERROR.QUEUE";

    /** The WMQ Queue name receiving errors.*/
    private String mErrorQueue;
    
    /**
     * WMQ parameters are expected by templates to come from a parameters map.
     * @param parameters a parameters map to which wmq parameters must be added
     */
    public void add(final Map < String, Object > parameters) {
        parameters.put("wmqJndiUrl", getJndiUrl());
        parameters.put("wmqJndiContextFactory", getJndiContextFactory());
        parameters.put("wmqConnectionFactory", getConnectionFactory());
        parameters.put("wmqZosQueueManager", getZosQueueManager());
        parameters.put("wmqRequestQueue", getRequestQueue());
        parameters.put("wmqReplyQueue", getReplyQueue());
        if (getErrorQueue() != null && getErrorQueue().length() > 0) {
            parameters.put("wmqErrorQueue", getErrorQueue());
        }
    }
    
    /**
     * Check that parameters are set correctly.
     * @throws CodeGenMakeException if parameters are missing or wrong
     */
    public void check() throws CodeGenMakeException {
        if (getJndiUrl() == null || getJndiUrl().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify a Websphere MQ JNDI URL");
        }
        if (getJndiContextFactory() == null || getJndiContextFactory().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify a Websphere MQ JNDI context factory");
        }
        if (getConnectionFactory() == null || getConnectionFactory().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify a Websphere MQ JNDI connection factory");
        }
        if (getZosQueueManager() == null || getZosQueueManager().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify a Websphere MQ ZOS queue Manager");
        }
        if (getRequestQueue() == null || getRequestQueue().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify a Websphere MQ Target request queue");
        }
        if (getReplyQueue() == null || getReplyQueue().length() == 0) {
            throw new CodeGenMakeException(
            "You must specify a Websphere MQ Target reply queue");
        }
        
    }
    
    /**
     * @return the URL used to do naming lookups for WMQ resources
     */
    public String getJndiUrl() {
        return mJndiUrl;
    }

    /**
     * @param jndiUrl the URL used to do naming lookups for WMQ resources
     */
    public void setJndiUrl(final String jndiUrl) {
        mJndiUrl = jndiUrl;
    }

    /**
     * @return the context factory class the JBossESB will use
     *  to do naming lookups for WMQ resources
     */
    public String getJndiContextFactory() {
        return mJndiContextFactory;
    }

    /**
     * @param jndiContextFactory the context factory class the JBossESB will use
     *  to do naming lookups for WMQ resources
     */
    public void setJndiContextFactory(final String jndiContextFactory) {
        mJndiContextFactory = jndiContextFactory;
    }

    /**
     * @return the connection-factory used to lookup queues/topics in a naming directory (JNDI)
     */
    public String getConnectionFactory() {
        return mConnectionFactory;
    }

    /**
     * @param connectionFactory the connection-factory used to lookup queues/topics in a naming directory (JNDI)
     */
    public void setConnectionFactory(final String connectionFactory) {
        mConnectionFactory = connectionFactory;
    }

    /**
     * @return the WMQ Queue name receiving requests
     */
    public String getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * @param requestQueue the WMQ Queue name receiving requests
     */
    public void setRequestQueue(final String requestQueue) {
        mRequestQueue = requestQueue;
    }

    /**
     * @return the WMQ Queue name receiving replies
     */
    public String getReplyQueue() {
        return mReplyQueue;
    }

    /**
     * @param replyQueue the WMQ Queue name receiving replies
     */
    public void setReplyQueue(final String replyQueue) {
        mReplyQueue = replyQueue;
    }

    /**
     * @return the WMQ Queue name receiving errors
     */
    public String getErrorQueue() {
        return mErrorQueue;
    }

    /**
     * @param errorQueue the WMQ Queue name receiving errors
     */
    public void setErrorQueue(final String errorQueue) {
        mErrorQueue = errorQueue;
    }

    /**
     * @return the ZOS WMQ Manager
     */
    public String getZosQueueManager() {
        return mZosQueueManager;
    }

    /**
     * @param zosQueueManager the ZOS WMQ Manager
     */
    public void setZosQueueManager(final String zosQueueManager) {
        mZosQueueManager = zosQueueManager;
    }


}
