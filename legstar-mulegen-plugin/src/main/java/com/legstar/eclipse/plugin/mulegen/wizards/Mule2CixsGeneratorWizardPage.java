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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * This page collects parameters needed for Mule to Cixs artifacts generation.
 *
 */
public class Mule2CixsGeneratorWizardPage extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Mule2CixsGeneratorWizardPage";

    /** Each supported transport is an entry in this map.*/
    private Map < SampleConfigurationTransport, AbstractCixsControlsGroup > _transportGroups =
        new LinkedHashMap < SampleConfigurationTransport, AbstractCixsControlsGroup >();

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
        
        /* Add supported transport groups. */
        _transportGroups.put(SampleConfigurationTransport.HTTP, new CixsAdapterToHostHttpGroup(this));
        _transportGroups.put(SampleConfigurationTransport.WMQ, new CixsAdapterToHostWmqGroup(this));
        _transportGroups.put(SampleConfigurationTransport.TCP, new CixsAdapterToHostTcpGroup(this));
        _transportGroups.put(SampleConfigurationTransport.MOCK, new CixsAdapterToHostMockGroup(this));
        
        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.createButton(composite);
            controlsGroup.createControls(container);
        }

    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);

        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.initControls();
        }

        /* Make sure one of the adapter transports groups is visible */
        SampleConfigurationTransport sampleConfigurationTransport =
            SampleConfigurationTransport.valueOf(getProjectPreferences().get(
                    PreferenceConstants.ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT,
                    getStore().getDefaultString(
                            PreferenceConstants.ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT)));

        for (Map.Entry < SampleConfigurationTransport, AbstractCixsControlsGroup > entry 
                : _transportGroups.entrySet()) {
            if (entry.getKey() == sampleConfigurationTransport) {
                entry.getValue().getButton().setSelection(true);
            } else {
                entry.getValue().getButton().setSelection(false);
            }
        }

        /* FIXME Have to hardcode the size of the wizard page, otherwise
         * the wmq group appears truncated. */
        getShell().setSize(800, 650);
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.createListeners();
        }
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {

        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.setVisibility();
        }

        getShell().layout(new Control[] {mDeploymentGroup});

        if (!super.validateExtendedWidgets()) {
            return false;
        }
        for (Map.Entry < SampleConfigurationTransport, AbstractCixsControlsGroup > entry 
                : _transportGroups.entrySet()) {
            if (entry.getKey() == getSampleConfigurationTransport()) {
                if (!entry.getValue().validateControls()) {
                    return false;
                }
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    public void storeExtendedProjectPreferences() {
        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.storeProjectPreferences();
        }
        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_LAST_SAMPLE_CONFIGURATION_TRANSPORT,
                getSampleConfigurationTransport().toString());

    }

    /**
     * @return the client transport selected
     */
    public final SampleConfigurationTransport getSampleConfigurationTransport() {
        for (Map.Entry < SampleConfigurationTransport, AbstractCixsControlsGroup > entry 
                : _transportGroups.entrySet()) {
            if (entry.getValue().getSelection()) {
                return entry.getKey();
            }
        }
        return SampleConfigurationTransport.HTTP;
    }

    /**
     * @return sample configuration host messaging.
     */
    public final SampleConfigurationHostMessagingType getSampleConfigurationHostMessagingType() {
        if (getSampleConfigurationTransport() == SampleConfigurationTransport.WMQ
                && getCixsAdapterToHostWmqGroup().getMqcihButton().getSelection()) {
            return SampleConfigurationHostMessagingType.MQCIH;
        }
        return SampleConfigurationHostMessagingType.LEGSTAR;
    }
    
    /**
     * @return the HTTP transport control group
     */
    public CixsAdapterToHostHttpGroup getCixsAdapterToHostHttpGroup() {
        return (CixsAdapterToHostHttpGroup) _transportGroups.get(SampleConfigurationTransport.HTTP);
    }

    /**
     * @return the WMQ transport control group
     */
    public CixsAdapterToHostWmqGroup getCixsAdapterToHostWmqGroup() {
        return (CixsAdapterToHostWmqGroup) _transportGroups.get(SampleConfigurationTransport.WMQ);
    }

    /**
     * @return the TCP transport control group
     */
    public CixsAdapterToHostTcpGroup getCixsAdapterToHostTcpGroup() {
        return (CixsAdapterToHostTcpGroup) _transportGroups.get(SampleConfigurationTransport.TCP);
    }

    /**
     * @return the MOCK transport control group
     */
    public CixsAdapterToHostMockGroup getCixsAdapterToHostMockGroup() {
        return (CixsAdapterToHostMockGroup) _transportGroups.get(SampleConfigurationTransport.MOCK);
    }

}
