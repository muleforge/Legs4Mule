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
package com.legstar.eclipse.plugin.mulegen.preferences;

/**
 * Constant definitions for plug-in preferences.
 */
public final class PreferenceConstants {

    /** Where Mule is installed. */
    public static final String MULE_INSTALL_FOLDER =
        "muleInstallFolder";

    /** Mule folder where user jar files should be deployed. */
    public static final String MULE_USER_JAR_FOLDER =
        "com.legstar.eclipse.plugin.mulegen.UserJarFolder";

    /** Folder where COBOL Client code should be generated. */
    public static final String DEFAULT_COBOL_SAMPLE_FOLDER =
        "com.legstar.eclipse.plugin.mulegen.CixsCobolFolder";

    /** Folder where configuration files should be generated. */
    public static final String TARGET_MULE_CONFIG_FOLDER =
        "com.legstar.eclipse.plugin.mulegen.CixsConfigFolder";

    /* --------------------------------------------------------------------- */
    /* Adapter to Mainframe HTTP transport preferences.                      */
    /* --------------------------------------------------------------------- */
    /** HTTP Host exposed by mainframe to adapter. */
    public static final String ADAPTER_DEFAULT_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultHttpHost";

    /** HTTP Port on which mainframe listens to adapter. */
    public static final String ADAPTER_DEFAULT_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultHttpPort";

    /** HTTP path on which mainframe listens to adapter. */
    public static final String ADAPTER_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.HttpPath";

    /* --------------------------------------------------------------------- */
    /* Adapter to Mainframe WMQ transport preferences.                       */
    /* --------------------------------------------------------------------- */
    /** URL used to do naming lookups for WMQ resources. */
    public static final String ADAPTER_DEFAULT_WMQ_JNDI_URL =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqJndiUrl";

    /** Context factory class JBossESB uses to do naming lookups for WMQ resources. */
    public static final String ADAPTER_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqJndiContextFactory";

    /** Connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    public static final String ADAPTER_DEFAULT_WMQ_CONNECTION_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqConnectionFactory";

    /** ZOS WMQ Queue Manager. */
    public static final String ADAPTER_DEFAULT_WMQ_ZOS_QUEUE_MANAGER =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqZosQueueManager";

    /** ZOS WMQ Queue name receiving requests. */
    public static final String ADAPTER_DEFAULT_WMQ_REQUEST_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqRequestQueue";

    /** ZOS WMQ Queue name receiving replies. */
    public static final String ADAPTER_DEFAULT_WMQ_REPLY_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqReplyQueue";

    /** ZOS WMQ Queue name receiving errors. */
    public static final String ADAPTER_DEFAULT_WMQ_ERROR_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqErrorQueue";

    /* --------------------------------------------------------------------- */
    /* Adapter to Mainframe TCP transport preferences.                       */
    /* --------------------------------------------------------------------- */
    /** TCP Host exposed by mainframe to adapter. */
    public static final String ADAPTER_DEFAULT_TCP_HOST =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultTcpHost";

    /** TCP Port on which mainframe listens to adapter. */
    public static final String ADAPTER_DEFAULT_TCP_PORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultTcpPort";

    /* --------------------------------------------------------------------- */
    /* Mainframe to Proxy HTTP transport preferences.                        */
    /* --------------------------------------------------------------------- */
    /** HTTP Host exposed by Mule to Mainframe clients. */
    public static final String PROXY_DEFAULT_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultHttpHost";

    /** Port on which HTTP listens to mainframe clients. */
    public static final String PROXY_DEFAULT_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultHttpPort";

    /** Prefix for path on which HTTP listens to mainframe clients. */
    public static final String PROXY_HTTP_PATH_TEMPLATE =
        "com.legstar.eclipse.plugin.mulegen.proxy.HttpPath";

    /* --------------------------------------------------------------------- */
    /* Mainframe to Proxy WMQ transport preferences.                         */
    /* --------------------------------------------------------------------- */
    /** URL used to do naming lookups for WMQ resources. */
    public static final String PROXY_DEFAULT_WMQ_JNDI_URL =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqJndiUrl";

    /** Context factory class JBossESB uses to do naming lookups for WMQ resources. */
    public static final String PROXY_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqJndiContextFactory";

    /** Connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    public static final String PROXY_DEFAULT_WMQ_CONNECTION_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqConnectionFactory";

    /** ZOS WMQ Queue Manager. */
    public static final String PROXY_DEFAULT_WMQ_ZOS_QUEUE_MANAGER =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqZosQueueManager";

    /** ZOS WMQ suffix of Queue name receiving requests. */
    public static final String PROXY_DEFAULT_WMQ_REQUEST_QUEUE_SUFFIX =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqRequestQueueSuffix";

    /** ZOS WMQ suffix of Queue name receiving replies. */
    public static final String PROXY_DEFAULT_WMQ_REPLY_QUEUE_SUFFIX =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqReplyQueueSuffix";

    /** ZOS WMQ suffix of Queue name receiving errors. */
    public static final String PROXY_DEFAULT_WMQ_ERROR_QUEUE_SUFFIX =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqErrorQueueSuffix";

    
    /** Utility class. */
    private PreferenceConstants() {
        
    }
}
