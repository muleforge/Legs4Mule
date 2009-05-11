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
    public static final String COBOL_SAMPLE_FOLDER =
        "com.legstar.eclipse.plugin.mulegen.CixsCobolFolder";

    /** Folder where configuration files should be generated. */
    public static final String TARGET_MULE_CONFIG_FOLDER =
        "com.legstar.eclipse.plugin.mulegen.CixsConfigFolder";

    /** Last UMO component implementation name used for a proxy. */
    public static final String PROXY_LAST_UMO_COMPONENT_IMPLEMENTATION_NAME =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastUmoComponentName";

    /** Last sample configuration transport selected for an adapter. */
    public static final String ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastSampleConfigurationTransport";

    /** Last sample configuration transport selected for a proxy. */
    public static final String PROXY_LAST_SAMPLE_CONFIGURATION_TRANSPORT =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastSampleConfigurationTransport";

    /* --------------------------------------------------------------------- */
    /* Adapter to Mainframe HTTP transport preferences.                      */
    /* --------------------------------------------------------------------- */
    /** HTTP Host exposed by mainframe to adapter. */
    public static final String ADAPTER_TO_HOST_DEFAULT_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultHttpHost";

    /** HTTP Port on which mainframe listens to adapter. */
    public static final String ADAPTER_TO_HOST_DEFAULT_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultHttpPort";

    /** HTTP path on which mainframe listens to adapter. */
    public static final String ADAPTER_TO_HOST_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.HttpPath";

    /** Last HTTP Host exposed to JBoss ESB clients. */
    public static final String ADAPTER_TO_HOST_LAST_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.LastHttpHost";

    /** Last Port on which HTTP listens to JBoss ESB clients. */
    public static final String ADAPTER_TO_HOST_LAST_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.LastHttpPort";

    /** Last for path on which HTTP listens to JBoss ESB clients. */
    public static final String ADAPTER_TO_HOST_LAST_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.LastHttpPath";

    /* --------------------------------------------------------------------- */
    /* Adapter to Mainframe WMQ transport preferences.                       */
    /* --------------------------------------------------------------------- */
    /** URL used to do naming lookups for WMQ resources. */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_JNDI_URL =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqJndiUrl";

    /** Context factory class JBossESB uses to do naming lookups for WMQ resources. */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqJndiContextFactory";

    /** Connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_CONNECTION_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqConnectionFactory";

    /** ZOS WMQ Queue Manager. */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_ZOS_QUEUE_MANAGER =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqZosQueueManager";

    /** ZOS WMQ Queue name receiving requests. */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_REQUEST_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqRequestQueue";

    /** ZOS WMQ Queue name receiving replies. */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_REPLY_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqReplyQueue";

    /** ZOS WMQ Queue name receiving errors. */
    public static final String ADAPTER_TO_HOST_DEFAULT_WMQ_ERROR_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.DefaultWmqErrorQueue";

    /** URL used to do naming lookups for WMQ resources. */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_JNDI_URL =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqJndiUrl";

    /** Context factory class JBossESB uses to do naming lookups for WMQ resources. */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_JNDI_CONTEXT_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqJndiContextFactory";

    /** Connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_CONNECTION_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqConnectionFactory";

    /** ZOS WMQ Queue Manager. */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_ZOS_QUEUE_MANAGER =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqZosQueueManager";

    /** ZOS WMQ Queue name receiving requests. */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_REQUEST_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqRequestQueue";

    /** ZOS WMQ Queue name receiving replies. */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_REPLY_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqReplyQueue";

    /** ZOS WMQ Queue name receiving errors. */
    public static final String ADAPTER_TO_HOST_LAST_WMQ_ERROR_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastWmqErrorQueue";

    /** Selection of host LegStar messaging. */
    public static final String ADAPTER_LAST_LEGSTAR_MESSAGING_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastLegstarMessaging";

    /** Selection of host CICS MQ Bridge messaging. */
    public static final String ADAPTER_LAST_MQCIH_MESSAGING_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.adapter.LastMqcihMessaging";


    /* --------------------------------------------------------------------- */
    /* Mainframe to Proxy HTTP transport preferences.                        */
    /* --------------------------------------------------------------------- */
    /** HTTP Host exposed by Mule to Mainframe clients. */
    public static final String HOST_TO_PROXY_DEFAULT_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultHttpHost";

    /** Port on which HTTP listens to mainframe clients. */
    public static final String HOST_TO_PROXY_DEFAULT_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultHttpPort";

    /** Prefix for path on which HTTP listens to mainframe clients. */
    public static final String HOST_TO_PROXY_HTTP_PATH_TEMPLATE =
        "com.legstar.eclipse.plugin.mulegen.proxy.HttpPath";

    /** Last HTTP Host exposed to Mainframe clients. */
    public static final String HOST_TO_PROXY_LAST_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastHttpHost";

    /** Last Port on which HTTP listens to mainframe clients. */
    public static final String HOST_TO_PROXY_LAST_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastHttpPort";

    /** Last for path on which HTTP listens to mainframe clients. */
    public static final String HOST_TO_PROXY_LAST_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastHttpPath";

    /** Last selection status of the dfhwbcli group. */
    public static final String HOST_TO_PROXY_LAST_DFHWBCLI_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastDfhwbcliButtonSelection";
    
    /** Last selection status of the webapi group. */
    public static final String HOST_TO_PROXY_LAST_WEBAPI_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWebapiButtonSelection";
    
    /** Last selection status of the legstar group. */
    public static final String HOST_TO_PROXY_LAST_LEGSTAR_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastLegstarButtonSelection";
    
    
    /* --------------------------------------------------------------------- */
    /* Mainframe to Proxy WMQ transport preferences.                         */
    /* --------------------------------------------------------------------- */
    /** URL used to do naming lookups for WMQ resources. */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_JNDI_URL =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqJndiUrl";

    /** Context factory class JBossESB uses to do naming lookups for WMQ resources. */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqJndiContextFactory";

    /** Connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_CONNECTION_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqConnectionFactory";

    /** ZOS WMQ Queue Manager. */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_ZOS_QUEUE_MANAGER =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqZosQueueManager";

    /** ZOS WMQ suffix of Queue name receiving requests. */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_REQUEST_QUEUE_SUFFIX =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqRequestQueueSuffix";

    /** ZOS WMQ suffix of Queue name receiving replies. */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_REPLY_QUEUE_SUFFIX =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqReplyQueueSuffix";

    /** ZOS WMQ suffix of Queue name receiving errors. */
    public static final String HOST_TO_PROXY_DEFAULT_WMQ_ERROR_QUEUE_SUFFIX =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultWmqErrorQueueSuffix";

    /** URL used to do naming lookups for WMQ resources. */
    public static final String HOST_TO_PROXY_LAST_WMQ_JNDI_URL =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqJndiUrl";

    /** Context factory class JBossESB uses to do naming lookups for WMQ resources. */
    public static final String HOST_TO_PROXY_LAST_WMQ_JNDI_CONTEXT_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqJndiContextFactory";

    /** Connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    public static final String HOST_TO_PROXY_LAST_WMQ_CONNECTION_FACTORY =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqConnectionFactory";

    /** ZOS WMQ Queue Manager. */
    public static final String HOST_TO_PROXY_LAST_WMQ_ZOS_QUEUE_MANAGER =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqZosQueueManager";

    /** ZOS WMQ Queue name receiving requests. */
    public static final String HOST_TO_PROXY_LAST_WMQ_REQUEST_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqRequestQueue";

    /** ZOS WMQ Queue name receiving replies. */
    public static final String HOST_TO_PROXY_LAST_WMQ_REPLY_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqReplyQueue";

    /** ZOS WMQ Queue name receiving errors. */
    public static final String HOST_TO_PROXY_LAST_WMQ_ERROR_QUEUE =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWmqErrorQueue";

    
    /** Utility class. */
    private PreferenceConstants() {
        
    }
}
