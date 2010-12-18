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


import com.legstar.cixs.gen.model.options.HttpTransportParameters;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsHttpGroup;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * The HTTP transport (Adapter to Mainframe) control group.
 * <p/>
 * Parameters needed by adapter to reach the mainframe over HTTP.
 * <p/>
 *
 */
public class CixsAdapterHttpGroup extends AbstractCixsHttpGroup {

    /**
     * Construct this control group attaching it to a wizard page.
     * 
     * @param wizardPage
     *            the parent wizard page
     * @param genModel
     *            the data model
     * @param selected
     *            whether this group should initially be selected
     */
    public CixsAdapterHttpGroup(
            final AbstractCixsGeneratorWizardPage wizardPage,
            final HttpTransportParameters genModel,
            final boolean selected) {
        super(wizardPage, genModel, selected, Messages.adapter_to_host_http_transport_group_label);
    }
    
    /**
     * @return a default value
     */
    public String getDefaultHttpHost() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_HTTP_HOST);
    }

    /**
     * @return a default value
     */
    public int getDefaultHttpPort() {
        return Integer.parseInt(getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_HTTP_PORT));
    }

    /**
     * @return a default value
     */
    public String getDefaultHttpPassword() {
        return "";
    }

    /**
     * @return a default value
     */
    public String getDefaultHttpPath() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_HTTP_PATH);
    }

    /**
     * @return a default value
     */
    public String getDefaultHttpUserId() {
        return "";
    }


}
