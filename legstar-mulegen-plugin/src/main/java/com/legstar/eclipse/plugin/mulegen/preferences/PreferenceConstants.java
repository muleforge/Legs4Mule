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

    /** HTTP Host exposed by Mule to Mainframe clients. */
    public static final String PROXY_DEFAULT_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultHttpHost";

    /** Port on which HTTP listens to mainframe clients. */
    public static final String PROXY_DEFAULT_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.proxy.DefaultHttpPort";

    /** Prefix for path on which HTTP listens to mainframe clients. */
    public static final String PROXY_HTTP_PATH_TEMPLATE =
        "com.legstar.eclipse.plugin.mulegen.proxy.HttpPath";

    /** Mainframe URI exposed to Mule clients. */
    public static final String HOST_URI =
        "com.legstar.eclipse.plugin.mulegen.CixsHostURI";

    /** Folder where configuration files should be generated. */
    public static final String TARGET_MULE_CONFIG_FOLDER =
        "com.legstar.eclipse.plugin.mulegen.CixsConfigFolder";

    /** Last UMO component implementation name used for a proxy. */
    public static final String PROXY_LAST_UMO_COMPONENT_IMPLEMENTATION_NAME =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastUmoComponentName";

    /** Last HTTP Host exposed to Mainframe clients. */
    public static final String PROXY_LAST_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastHttpHost";

    /** Last Port on which HTTP listens to mainframe clients. */
    public static final String PROXY_LAST_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastHttpPort";

    /** Last for path on which HTTP listens to mainframe clients. */
    public static final String PROXY_LAST_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastHttpPath";

    /** Last selection status of the dfhwbcli group. */
    public static final String PROXY_LAST_DFHWBCLI_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastDfhwbcliButtonSelection";
    
    /** Last selection status of the webapi group. */
    public static final String PROXY_LAST_WEBAPI_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastWebapiButtonSelection";
    
    /** Last selection status of the legstar group. */
    public static final String PROXY_LAST_LEGSTAR_BUTTON_SELECTION =
        "com.legstar.eclipse.plugin.mulegen.proxy.LastLegstarButtonSelection";
    
    /* --------------------------------------------------------------------- */
    /* Adapter to Mainframe HTTP transport preferences.                      */
    /* --------------------------------------------------------------------- */
    /** HTTP Host exposed by mainframe to adapter. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultHttpHost";

    /** HTTP Port on which mainframe listens to adapter. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.DefaultHttpPort";

    /** HTTP path on which mainframe listens to adapter. */
    public static final String ADAPTER_TO_MAINFRAME_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.HttpPath";

    /** Last HTTP Host exposed to JBoss ESB clients. */
    public static final String ADAPTER_TO_MAINFRAME_LAST_HTTP_HOST =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.LastHttpHost";

    /** Last Port on which HTTP listens to JBoss ESB clients. */
    public static final String ADAPTER_TO_MAINFRAME_LAST_HTTP_PORT =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.LastHttpPort";

    /** Last for path on which HTTP listens to JBoss ESB clients. */
    public static final String ADAPTER_TO_MAINFRAME_LAST_HTTP_PATH =
        "com.legstar.eclipse.plugin.mulegen.adapter.to.host.LastHttpPath";

    /** Utility class. */
    private PreferenceConstants() {
        
    }
}
