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
     */
    public CixsHostToProxyWmqGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {

        setWmqJndiUrl(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_JNDI_URL,
                getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_JNDI_URL)));

        setWmqJndiContextFactory(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_JNDI_CONTEXT_FACTORY,
                getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY)));

        setWmqConnectionFactory(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_CONNECTION_FACTORY,
                getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_CONNECTION_FACTORY)));

        setWmqZosQueueManager(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_ZOS_QUEUE_MANAGER,
                getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_ZOS_QUEUE_MANAGER)));

        setWmqRequestQueue(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_REQUEST_QUEUE,
                getQueueNamesPrefix() + '.'
                + getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_REQUEST_QUEUE_SUFFIX)));

        setWmqReplyQueue(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_REPLY_QUEUE,
                getQueueNamesPrefix() + '.'
                + getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_REPLY_QUEUE_SUFFIX)));

        setWmqErrorQueue(getProjectPreferences().get(
                PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_ERROR_QUEUE,
                getQueueNamesPrefix() + '.'
                + getWizardPage().getStore().getString(
                        PreferenceConstants.HOST_TO_PROXY_DEFAULT_WMQ_ERROR_QUEUE_SUFFIX)));


    }

    /**
     * @return a prefix to use for WMQ queue names.
     */
    private String getQueueNamesPrefix() {
        return getWizardPage().getServiceName().toUpperCase(Locale.getDefault());
    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {

        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_JNDI_URL,
                getWmqJndiUrl());
        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_JNDI_CONTEXT_FACTORY,
                getWmqJndiContextFactory());
        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_CONNECTION_FACTORY,
                getWmqConnectionFactory());
        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_ZOS_QUEUE_MANAGER,
                getWmqZosQueueManager());
        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_REQUEST_QUEUE,
                getWmqRequestQueue());
        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_REPLY_QUEUE,
                getWmqReplyQueue());
        getProjectPreferences().put(PreferenceConstants.HOST_TO_PROXY_LAST_WMQ_ERROR_QUEUE,
                getWmqErrorQueue());

    }

}
