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
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;

import com.legstar.cixs.gen.model.options.WmqTransportParameters;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsWmqGroup;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * The WebSphere MQ deployment control group.
 * <p/>
 * Parameters needed by adapter to reach the mainframe over WebSphere MQ.
 *
 */
public class CixsAdapterWmqGroup extends AbstractCixsWmqGroup {

    /** Selection of LegStar sample host messaging type.*/
    private Button _legstarButton = null;

    /** Selection of CICS MQ Bridge sample host messaging type.*/
    private Button _mqcihButton = null;
    
    /** Host messaging type over MQ chosen.*/
    private SampleConfigurationHostMessagingType _sampleConfigurationHostMessagingType;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     * @param genModel the data model
     * @param sampleConfigurationHostMessagingType initialHost messaging type over MQ chosen
     * @param selected whether this group should initially be selected
     */
    public CixsAdapterWmqGroup(
            final AbstractCixsGeneratorWizardPage wizardPage,
            final WmqTransportParameters genModel,
            final SampleConfigurationHostMessagingType sampleConfigurationHostMessagingType,
            final boolean selected) {
        super(wizardPage, genModel, selected, Messages.adapter_wmq_transport_group_label);
        _sampleConfigurationHostMessagingType = sampleConfigurationHostMessagingType;
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        super.createControls(composite);

        AbstractWizardPage.createLabel(getGroup(), Messages.sample_adapter_host_messaging_type_label + ':');
        Composite buttonsComposite = new Composite(getGroup(), SWT.NULL);
        buttonsComposite.setLayout(new RowLayout());

        _mqcihButton = new Button(buttonsComposite, SWT.RADIO);
        _mqcihButton.setText("CICS MQ Bridge");

        _legstarButton = new Button(buttonsComposite, SWT.RADIO);
        _legstarButton.setText("LegStar");
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {
        super.initExtendedControls();

        getLegstarButton().setSelection(
                _sampleConfigurationHostMessagingType == SampleConfigurationHostMessagingType.LEGSTAR);
        getMqcihButton().setSelection(
                _sampleConfigurationHostMessagingType == SampleConfigurationHostMessagingType.MQCIH);

    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {
        super.createExtendedListeners();

        _legstarButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _mqcihButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void updateGenModelExtended() {
        super.updateGenModelExtended();

        if (getLegstarButton().getSelection()) {
            _sampleConfigurationHostMessagingType = SampleConfigurationHostMessagingType.LEGSTAR;
        }
        if (getMqcihButton().getSelection()) {
            _sampleConfigurationHostMessagingType = SampleConfigurationHostMessagingType.MQCIH;
        }
    }
    
    /**
     * @return the selection of LegStar sample host messaging type
     */
    public Button getLegstarButton() {
        return _legstarButton;
    }

    /**
     * @return the selection of CICS MQ Bridge sample host messaging type
     */
    public Button getMqcihButton() {
        return _mqcihButton;
    }

    @Override
    public String getDefaultWmqConnectionFactory() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_CONNECTION_FACTORY);
    }

    @Override
    public String getDefaultWmqErrorQueue() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_ERROR_QUEUE);
    }

    @Override
    public String getDefaultWmqJndiContextFactory() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY);
    }

    @Override
    public String getDefaultWmqJndiUrl() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_JNDI_URL);
    }

    @Override
    public String getDefaultWmqReplyQueue() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_REPLY_QUEUE);
    }

    @Override
    public String getDefaultWmqRequestQueue() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_REQUEST_QUEUE);
    }

    @Override
    public String getDefaultWmqZosQueueManager() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_WMQ_ZOS_QUEUE_MANAGER);
    }

}
