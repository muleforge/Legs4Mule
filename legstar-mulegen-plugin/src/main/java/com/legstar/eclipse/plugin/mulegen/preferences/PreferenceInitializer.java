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

import org.eclipse.jface.preference.IPreferenceStore;
import org.mule.transport.legstar.model.AntBuildCixs2MuleModel;

import com.legstar.cixs.gen.model.options.WmqTransportParameters;
import com.legstar.codegen.CodeGenUtil;
import com.legstar.eclipse.plugin.cixscom.preferences.AbstractCicxsPreferenceInitializer;
import com.legstar.eclipse.plugin.mulegen.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractCicxsPreferenceInitializer {

    /**
     * {@inheritDoc}
     * @see org.eclipse.core.runtime.preferences.
     * AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        super.initializeDefaultPreferences(store);

        store.setDefault(PreferenceConstants.MULE_INSTALL_FOLDER,
                getDefaultMuleLocation());
        store.setDefault(PreferenceConstants.MULE_APPS_FOLDER,
                getDefaultMuleLocation() + "/apps");

        store.setDefault(PreferenceConstants.TARGET_MULE_CONFIG_FOLDER, "conf");
        store.setDefault(PreferenceConstants.DEFAULT_COBOL_SAMPLE_FOLDER, "cobol");

        /* --------------------------------------------------------------------- */
        /* Adapter to Mainframe HTTP transport preferences.                      */
        /* --------------------------------------------------------------------- */
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_HTTP_HOST,
        "mainframe");
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_HTTP_PORT,
        "4081");
        store.setDefault(PreferenceConstants.ADAPTER_HTTP_PATH,
        "/CICS/CWBA/LSWEBBIN");

        /* --------------------------------------------------------------------- */
        /* Adapter to Mainframe WMQ transport preferences.                       */
        /* --------------------------------------------------------------------- */
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_JNDI_URL,
                WmqTransportParameters.DEFAULT_JNDI_FS_DIRECTORY);
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY,
                WmqTransportParameters.DEFAULT_JNDI_CONTEXT_FACTORY);
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_CONNECTION_FACTORY,
        "ConnectionFactory");
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_ZOS_QUEUE_MANAGER,
        "CSQ1");
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_REQUEST_QUEUE,
        "CICS01.BRIDGE.REQUEST.QUEUE");
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_REPLY_QUEUE,
        "CICS01.BRIDGE.REPLY.QUEUE");
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_WMQ_ERROR_QUEUE,
        "");

        /* --------------------------------------------------------------------- */
        /* Adapter to Mainframe TCP transport preferences.                       */
        /* --------------------------------------------------------------------- */
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_TCP_HOST,
        "mainframe");
        store.setDefault(PreferenceConstants.ADAPTER_DEFAULT_TCP_PORT,
        "3011");

        /* --------------------------------------------------------------------- */
        /* Mainframe to Proxy HTTP transport preferences.                        */
        /* --------------------------------------------------------------------- */
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_HTTP_HOST,
                CodeGenUtil.getLocalIPAddress());
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_HTTP_PORT,
                AntBuildCixs2MuleModel.DEFAULT_HTTP_PORT);
        store.setDefault(PreferenceConstants.PROXY_HTTP_PATH_TEMPLATE,
                AntBuildCixs2MuleModel.DEFAULT_SERVER_PATH_TEMPLATE);

        /* --------------------------------------------------------------------- */
        /* Mainframe to Proxy WMQ transport preferences.                         */
        /* --------------------------------------------------------------------- */
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_JNDI_URL,
                WmqTransportParameters.DEFAULT_JNDI_FS_DIRECTORY);
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY,
                WmqTransportParameters.DEFAULT_JNDI_CONTEXT_FACTORY);
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_CONNECTION_FACTORY,
        "ConnectionFactory");
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_ZOS_QUEUE_MANAGER,
        "CSQ1");
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_REQUEST_QUEUE_SUFFIX,
                WmqTransportParameters.DEFAULT_REQUEST_QUEUE_SUFFIX);
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_REPLY_QUEUE_SUFFIX,
                WmqTransportParameters.DEFAULT_REPLY_QUEUE_SUFFIX);
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_WMQ_ERROR_QUEUE_SUFFIX,
                WmqTransportParameters.DEFAULT_ERROR_QUEUE_SUFFIX);

    }

    /**
     * If Mule is installed on this machine, this will retrieve the
     * installed installation folder.
     * @return the default location
     */
    public String getDefaultMuleLocation() {
        String value = System.getenv("MULE_HOME");
        if (value == null) {
            value = System.getenv("MULE3_HOME");
            if (value == null) {
                return "";
            }
        }
        return value;
    }

}
