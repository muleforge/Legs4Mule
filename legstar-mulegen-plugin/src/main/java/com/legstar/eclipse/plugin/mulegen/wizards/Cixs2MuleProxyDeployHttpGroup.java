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
package com.legstar.eclipse.plugin.mulegen.wizards;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsProxyDeployHttpGroup;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * Inherits the behavior of a group of controls for HTTP client parameters.
 * <p/>
 * The target is to manage the preferences at the local level rather than cixcom.
 * This is needed because the preferences should not be shared between execution
 * environments.
 */
public class Cixs2MuleProxyDeployHttpGroup extends AbstractCixsProxyDeployHttpGroup {

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     */
    public Cixs2MuleProxyDeployHttpGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }
    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {

        setHttpHost(getProjectPreferences().get(PreferenceConstants.PROXY_LAST_HTTP_HOST,
                getWizardPage().getStore().getString(PreferenceConstants.PROXY_DEFAULT_HTTP_HOST)));
        
        setHttpPort(getProjectPreferences().get(PreferenceConstants.PROXY_LAST_HTTP_PORT,
                getWizardPage().getStore().getString(PreferenceConstants.PROXY_DEFAULT_HTTP_PORT)));

        setHttpPath(getProjectPreferences().get(PreferenceConstants.PROXY_LAST_HTTP_PATH,
                getWizardPage().getStore().getString(
                        PreferenceConstants.PROXY_HTTP_PATH_TEMPLATE).replace(
                                "${service.name}", getWizardPage().getServiceName())));
        
        getDfhwbcliButton().setSelection(getProjectPreferences().getBoolean(
                PreferenceConstants.PROXY_LAST_DFHWBCLI_BUTTON_SELECTION, true));
        getWebapiButton().setSelection(getProjectPreferences().getBoolean(
                PreferenceConstants.PROXY_LAST_WEBAPI_BUTTON_SELECTION, false));
        getLegstarButton().setSelection(getProjectPreferences().getBoolean(
                PreferenceConstants.PROXY_LAST_LEGSTAR_BUTTON_SELECTION, false));
    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {

        getProjectPreferences().put(PreferenceConstants.PROXY_LAST_HTTP_HOST, getHttpHost());
        getProjectPreferences().put(PreferenceConstants.PROXY_LAST_HTTP_PORT, getHttpPort());
        getProjectPreferences().put(PreferenceConstants.PROXY_LAST_HTTP_PATH, getHttpPath());

        getProjectPreferences().putBoolean(
                PreferenceConstants.PROXY_LAST_DFHWBCLI_BUTTON_SELECTION,
                getDfhwbcliButton().getSelection());
        getProjectPreferences().putBoolean(
                PreferenceConstants.PROXY_LAST_WEBAPI_BUTTON_SELECTION,
                getWebapiButton().getSelection());
        getProjectPreferences().putBoolean(
                PreferenceConstants.PROXY_LAST_LEGSTAR_BUTTON_SELECTION,
                getLegstarButton().getSelection());
    }

}
