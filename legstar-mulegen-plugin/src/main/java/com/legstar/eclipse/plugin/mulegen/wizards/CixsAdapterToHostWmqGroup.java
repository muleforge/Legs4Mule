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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * The WebSphere MQ deployment control group.
 * <p/>
 * Parameters needed by adapter to reach the mainframe over WebSphere MQ.
 *
 */
public class CixsAdapterToHostWmqGroup extends AbstractCixsWmqGroup {

    /** Selection of LegStar sample host messaging type.*/
    private Button mLegstarButton = null;

    /** Selection of CICS MQ Bridge sample host messaging type.*/
    private Button mMqcihButton = null;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     */
    public CixsAdapterToHostWmqGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        
        super.createControls(composite);

        AbstractWizardPage.createLabel(getGroup(), Messages.sample_adapter_host_messaging_type_label + ':');
        Composite buttonsComposite = new Composite(getGroup(), SWT.NULL);
        buttonsComposite.setLayout(new RowLayout());

        mMqcihButton = new Button(buttonsComposite, SWT.RADIO);
        mMqcihButton.setText("CICS MQ Bridge");

        mLegstarButton = new Button(buttonsComposite, SWT.RADIO);
        mLegstarButton.setText("LegStar");
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {

        setWmqJndiUrl(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_JNDI_URL,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_JNDI_URL)));

        setWmqJndiContextFactory(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_JNDI_CONTEXT_FACTORY,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY)));

        setWmqConnectionFactory(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_CONNECTION_FACTORY,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_CONNECTION_FACTORY)));

        setWmqZosQueueManager(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_ZOS_QUEUE_MANAGER,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_ZOS_QUEUE_MANAGER)));

        setWmqRequestQueue(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_REQUEST_QUEUE,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_REQUEST_QUEUE)));

        setWmqReplyQueue(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_REPLY_QUEUE,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_REPLY_QUEUE)));

        setWmqErrorQueue(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_ERROR_QUEUE,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_WMQ_ERROR_QUEUE)));

        getMqcihButton().setSelection(getProjectPreferences().getBoolean(
                PreferenceConstants.ADAPTER_LAST_MQCIH_MESSAGING_BUTTON_SELECTION, true));

        getLegstarButton().setSelection(getProjectPreferences().getBoolean(
                PreferenceConstants.ADAPTER_LAST_LEGSTAR_MESSAGING_BUTTON_SELECTION, false));

    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {
        super.createExtendedListeners();
        mLegstarButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mMqcihButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {

        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_JNDI_URL,
                getWmqJndiUrl());
        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_JNDI_CONTEXT_FACTORY,
                getWmqJndiContextFactory());
        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_CONNECTION_FACTORY,
                getWmqConnectionFactory());
        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_ZOS_QUEUE_MANAGER,
                getWmqZosQueueManager());
        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_REQUEST_QUEUE,
                getWmqRequestQueue());
        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_REPLY_QUEUE,
                getWmqReplyQueue());
        getProjectPreferences().put(PreferenceConstants.ADAPTER_TO_HOST_LAST_WMQ_ERROR_QUEUE,
                getWmqErrorQueue());

        getProjectPreferences().putBoolean(
                PreferenceConstants.ADAPTER_LAST_LEGSTAR_MESSAGING_BUTTON_SELECTION,
                getLegstarButton().getSelection());
        getProjectPreferences().putBoolean(
                PreferenceConstants.ADAPTER_LAST_MQCIH_MESSAGING_BUTTON_SELECTION,
                getMqcihButton().getSelection());
    }

    /**
     * @return the selection of LegStar sample host messaging type
     */
    public Button getLegstarButton() {
        return mLegstarButton;
    }

    /**
     * @return the selection of CICS MQ Bridge sample host messaging type
     */
    public Button getMqcihButton() {
        return mMqcihButton;
    }

}
