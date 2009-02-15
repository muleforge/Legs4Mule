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

        store.setDefault(PreferenceConstants.TARGET_MULE_CONFIG_FOLDER,
        "conf");
        store.setDefault(PreferenceConstants.COBOL_SAMPLE_FOLDER,
        "cobol");
        store.setDefault(PreferenceConstants.SERVICE_URI,
        "http://localhost:8083");
        store.setDefault(PreferenceConstants.HOST_URI,
        "http://mainframe:4081");
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
