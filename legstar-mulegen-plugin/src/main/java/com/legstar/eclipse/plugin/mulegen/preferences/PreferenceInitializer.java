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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.mule.transport.legstar.model.AntBuildCixs2MuleModel;

import com.legstar.codegen.CodeGenUtil;
import com.legstar.eclipse.plugin.mulegen.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * {@inheritDoc}
     * @see org.eclipse.core.runtime.preferences.
     * AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        store.setDefault(PreferenceConstants.MULE_INSTALL_FOLDER,
                getDefaultMuleLocation());
        store.setDefault(PreferenceConstants.MULE_USER_JAR_FOLDER,
                getDefaultMuleLocation() + "/lib/user");

        store.setDefault(PreferenceConstants.TARGET_MULE_CONFIG_FOLDER, "conf");
        store.setDefault(PreferenceConstants.COBOL_SAMPLE_FOLDER, "cobol");

        store.setDefault(PreferenceConstants.PROXY_DEFAULT_HTTP_HOST,
                CodeGenUtil.getLocalIPAddress());
        store.setDefault(PreferenceConstants.PROXY_DEFAULT_HTTP_PORT,
                AntBuildCixs2MuleModel.DEFAULT_HTTP_PORT);
        store.setDefault(PreferenceConstants.PROXY_HTTP_PATH_TEMPLATE,
                AntBuildCixs2MuleModel.DEFAULT_SERVER_PATH_TEMPLATE);

        store.setDefault(PreferenceConstants.ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST,
        "mainframe");

        store.setDefault(PreferenceConstants.ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT,
        "4081");

        store.setDefault(PreferenceConstants.ADAPTER_TO_MAINFRAME_HTTP_PATH,
        "/CICS/CWBA/LSWEBBIN");
    }

    /**
     * If Mule is installed on this machine, this will retrieve the
     * installed installation folder.
     * @return the default location
     */
    public String getDefaultMuleLocation() {
        String value = System.getenv("MULE_HOME");
        if (value == null) {
            return "";
        }
        return value;
    }

}
