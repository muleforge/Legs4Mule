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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;

import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * This page collects parameters needed for Mule to Cixs artifacts generation.
 *
 */
public class Mule2CixsGeneratorWizardPage extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Mule2CixsGeneratorWizardPage";

    /** HTTP adapter to mainframe transport parameters. */
    private CixsAdapterToHostHttpGroup mCixsAdapterToHostHttpGroup;

    /** WMQ adapter to mainframe transport parameters. */
    private CixsAdapterToHostWmqGroup mCixsAdapterToHostWmqGroup;

    /** Keeps a reference on the deployment group container. */
    private Composite mDeploymentGroup = null;

    /**
     * Construct the page.
     * @param selection the current workbench selection
     * @param mappingFile the mapping file
     */
    protected Mule2CixsGeneratorWizardPage(
            final IStructuredSelection selection, final IFile mappingFile) {
        super(PAGE_NAME,
                Messages.mule_to_cixs_wizard_page_title,
                Messages.mule_to_cixs_wizard_page_description,
                selection, mappingFile);
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        super.addWidgetsToDeploymentGroup(container);

        mDeploymentGroup = container;

        createLabel(container, Messages.adapter_to_host_transport_label + ":");
        Composite composite = new Composite(container, SWT.NULL);
        composite.setLayout(new RowLayout());

        mCixsAdapterToHostHttpGroup = new CixsAdapterToHostHttpGroup(this);
        mCixsAdapterToHostHttpGroup.createButton(composite);
        mCixsAdapterToHostHttpGroup.createControls(container);

        mCixsAdapterToHostWmqGroup = new CixsAdapterToHostWmqGroup(this);
        mCixsAdapterToHostWmqGroup.createButton(composite);
        mCixsAdapterToHostWmqGroup.createControls(container);
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);

        getCixsAdapterToHostHttpGroup().initControls();
        getCixsAdapterToHostWmqGroup().initControls();
 
        /* Make sure one of the adapter transports groups is visible */
        SampleConfigurationTransport sampleConfigurationTransport =
            SampleConfigurationTransport.valueOf(getProjectPreferences().get(
                    PreferenceConstants.ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT,
                    getStore().getDefaultString(
                            PreferenceConstants.ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT)));
                
        if (sampleConfigurationTransport == SampleConfigurationTransport.HTTP) {
            getCixsAdapterToHostHttpGroup().getButton().setSelection(true);
            getCixsAdapterToHostWmqGroup().getButton().setSelection(false);
        }
        if (sampleConfigurationTransport == SampleConfigurationTransport.WMQ) {
            getCixsAdapterToHostHttpGroup().getButton().setSelection(false);
            getCixsAdapterToHostWmqGroup().getButton().setSelection(true);
        }

        /* FIXME Have to hardcode the size of the wizard page, otherwise
         * the wmq group appears truncated. */
        getShell().setSize(800, 650);
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        getCixsAdapterToHostHttpGroup().createListeners();
        getCixsAdapterToHostWmqGroup().createListeners();
        
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {
        
        getCixsAdapterToHostHttpGroup().setVisibility();
        getCixsAdapterToHostWmqGroup().setVisibility();

        getShell().layout(new Control[] {mDeploymentGroup});

        if (!super.validateExtendedWidgets()) {
            return false;
        }
        if (getSampleConfigurationTransport() == SampleConfigurationTransport.HTTP
                && !getCixsAdapterToHostHttpGroup().validateControls()) {
            return false;
        }
        if (getSampleConfigurationTransport() == SampleConfigurationTransport.WMQ
                && !getCixsAdapterToHostWmqGroup().validateControls()) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    public void storeExtendedProjectPreferences() {
        getCixsAdapterToHostHttpGroup().storeProjectPreferences();
        getCixsAdapterToHostWmqGroup().storeProjectPreferences();
        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT,
                getSampleConfigurationTransport().toString());
        
    }

    /**
     * @return HTTP adapter to mainframe transport parameters
     */
    public CixsAdapterToHostHttpGroup getCixsAdapterToHostHttpGroup() {
        return mCixsAdapterToHostHttpGroup;
    }

    /**
     * @return WMQ adapter to mainframe transport parameters
     */
    public CixsAdapterToHostWmqGroup getCixsAdapterToHostWmqGroup() {
        return mCixsAdapterToHostWmqGroup;
    }

    /**
     * @return the client transport selected
     */
    public final SampleConfigurationTransport getSampleConfigurationTransport() {
        if (getCixsAdapterToHostHttpGroup().getSelection()) {
            return SampleConfigurationTransport.HTTP;
        }
        if (getCixsAdapterToHostWmqGroup().getSelection()) {
            return SampleConfigurationTransport.WMQ;
        }
        return SampleConfigurationTransport.HTTP;
    }
    
    /**
     * @return sample configuration host messaging.
     */
    public final SampleConfigurationHostMessagingType getSampleConfigurationHostMessagingType() {
        if (getCixsAdapterToHostWmqGroup().getLegstarButton().getSelection()) {
            return SampleConfigurationHostMessagingType.LEGSTAR;
        } else if (getCixsAdapterToHostWmqGroup().getMqcihButton().getSelection()) {
            return SampleConfigurationHostMessagingType.MQCIH;
        }
        return SampleConfigurationHostMessagingType.MQCIH;
    }

}
