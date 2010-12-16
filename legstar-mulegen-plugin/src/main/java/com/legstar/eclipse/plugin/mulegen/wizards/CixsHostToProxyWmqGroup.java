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

import java.util.Locale;

import com.legstar.cixs.gen.model.options.WmqTransportParameters;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * The WebSphere MQ deployment control group.
 * <p/>
 * Parameters needed for mainframe clients to reach proxies over WebSphere MQ.
 *
 */
public class CixsHostToProxyWmqGroup extends AbstractCixsWmqGroup {

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     * @param genModel the data model
     * @param selected whether this group should initially be selected
     */
    public CixsHostToProxyWmqGroup(
            final AbstractCixsGeneratorWizardPage wizardPage,
            final WmqTransportParameters genModel,
            final boolean selected) {
        super(wizardPage, genModel, selected);
    }

    /**
     * @return a prefix to use for WMQ queue names.
     */
    private String getQueueNamesPrefix() {
        return getWizardPage().getServiceName().toUpperCase(Locale.getDefault());
    }

    @Override
    public String getDefaultWmqConnectionFactory() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_CONNECTION_FACTORY);
    }

    @Override
    public String getDefaultWmqErrorQueue() {
        return getQueueNamesPrefix() + '.'
        + getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_ERROR_QUEUE_SUFFIX);
    }

    @Override
    public String getDefaultWmqJndiContextFactory() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY);
    }

    @Override
    public String getDefaultWmqJndiUrl() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_JNDI_URL);
    }

    @Override
    public String getDefaultWmqReplyQueue() {
        return getQueueNamesPrefix() + '.'
        + getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_REPLY_QUEUE_SUFFIX);
    }

    @Override
    public String getDefaultWmqRequestQueue() {
        return getQueueNamesPrefix() + '.'
        + getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_REQUEST_QUEUE_SUFFIX);
    }

    @Override
    public String getDefaultWmqZosQueueManager() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_ZOS_QUEUE_MANAGER);
    }
}
