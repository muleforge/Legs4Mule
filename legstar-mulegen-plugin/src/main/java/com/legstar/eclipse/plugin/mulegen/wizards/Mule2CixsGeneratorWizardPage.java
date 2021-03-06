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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.mule.transport.legstar.model.AntBuildMule2CixsModel;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationPayloadType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.mulegen.Messages;

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
    private Composite _deploymentGroup = null;
    
    /** Selection of client to adapter java payload. */
    private Button _javaPayloadButton = null;

    /** Selection of client to adapter XML payload. */
    private Button _xmlPayloadButton = null;


    /**
     * Construct the page.
     * 
     * @param selection the current workbench selection
     * @param mappingFile the mapping file
     * @param genModel the generation model
     */
    protected Mule2CixsGeneratorWizardPage(
            final IStructuredSelection selection,
            final IFile mappingFile,
            final AntBuildMule2CixsModel genModel) {
        super(selection, PAGE_NAME,
                Messages.mule_to_cixs_wizard_page_title,
                Messages.mule_to_cixs_wizard_page_description,
                mappingFile,
                genModel);
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        super.addWidgetsToDeploymentGroup(container);

        _deploymentGroup = container;

        createLabel(container, Messages.client_to_adapter_payload_label + ":");
        Composite buttonsComposite = new Composite(container, SWT.NULL);
        buttonsComposite.setLayout(new RowLayout());

        _javaPayloadButton = new Button(buttonsComposite, SWT.RADIO);
        _javaPayloadButton.setText("JAVA");

        _xmlPayloadButton = new Button(buttonsComposite, SWT.RADIO);
        _xmlPayloadButton.setText("XML");

        createLabel(container, Messages.adapter_transport_label + ":");
        Composite composite = new Composite(container, SWT.NULL);
        composite.setLayout(new RowLayout());
        
        /* Add supported transport groups. */
        _transportGroups.put(SampleConfigurationTransport.HTTP,
                new CixsAdapterHttpGroup(this,
                        getGenModel().getHttpTransportParameters(),
                        getGenModel().getSampleConfigurationTransport() == SampleConfigurationTransport.HTTP));
        _transportGroups.put(SampleConfigurationTransport.WMQ,
                new CixsAdapterWmqGroup(this,
                        getGenModel().getWmqTransportParameters(),
                        getGenModel().getSampleConfigurationHostMessagingType(),
                        getGenModel().getSampleConfigurationTransport() == SampleConfigurationTransport.WMQ));
        _transportGroups.put(SampleConfigurationTransport.TCP,
                new CixsAdapterTcpGroup(this,
                        getGenModel().getTcpTransportParameters(),
                        getGenModel().getSampleConfigurationTransport() == SampleConfigurationTransport.TCP));
        _transportGroups.put(SampleConfigurationTransport.MOCK,
                new CixsAdapterMockGroup(this,
                        getGenModel().getSampleConfigurationTransport() == SampleConfigurationTransport.MOCK));
        
        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.createButton(composite);
            controlsGroup.createControls(container);
        }

    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);

        getJavaPayloadButton()
                .setSelection(
                        getGenModel().getSampleConfigurationPayloadType() == SampleConfigurationPayloadType.JAVA);
        getXmlPayloadButton()
                .setSelection(
                        getGenModel().getSampleConfigurationPayloadType() == SampleConfigurationPayloadType.XML);

        for (AbstractCixsControlsGroup controlsGroup : _transportGroups
                .values()) {
            controlsGroup.initControls();
        }

        /* FIXME Have to hardcode the size of the wizard page, otherwise
         * the wmq group appears truncated. */
        getShell().setSize(800, 650);
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        super.createExtendedListeners();
        
        getJavaPayloadButton().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                dialogChanged();
            }
        });
 
        getXmlPayloadButton().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                dialogChanged();
            }
        });

        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.createListeners();
        }
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {

        for (AbstractCixsControlsGroup controlsGroup : _transportGroups.values()) {
            controlsGroup.setVisibility();
        }

        getShell().layout(new Control[] {_deploymentGroup});

        if (!super.validateExtendedWidgets()) {
            return false;
        }
        for (Map.Entry < SampleConfigurationTransport, AbstractCixsControlsGroup > entry 
                : _transportGroups.entrySet()) {
            if (entry.getValue().isSelected()) {
                if (!entry.getValue().validateControls()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Store the selected values in the project scoped preference store.
     */
    public void updateGenModelExtended() {
        super.updateGenModelExtended();

        if (getJavaPayloadButton().getSelection()) {
            getGenModel().setSampleConfigurationPayloadType(
                    SampleConfigurationPayloadType.JAVA);
        }
        if (getXmlPayloadButton().getSelection()) {
            getGenModel().setSampleConfigurationPayloadType(
                    SampleConfigurationPayloadType.XML);
        }

        for (Map.Entry < SampleConfigurationTransport, AbstractCixsControlsGroup > entry 
                : _transportGroups.entrySet()) {
            if (entry.getValue().isSelected()) {
                entry.getValue().updateGenModel();
                getGenModel().setSampleConfigurationTransport(entry.getKey());
                if (entry.getKey() == SampleConfigurationTransport.WMQ) {
                    getGenModel().setSampleConfigurationHostMessagingType(
                            getSampleConfigurationHostMessagingType());
                }
            }
        }

    }

    /**
     * @return sample configuration host messaging.
     */
    public final SampleConfigurationHostMessagingType getSampleConfigurationHostMessagingType() {
        if (getCixsAdapterToHostWmqGroup().getMqcihButton().getSelection()) {
            return SampleConfigurationHostMessagingType.MQCIH;
        }
        return SampleConfigurationHostMessagingType.LEGSTAR;
    }
    
    /**
     * @return the HTTP transport control group
     */
    public CixsAdapterHttpGroup getCixsAdapterToHostHttpGroup() {
        return (CixsAdapterHttpGroup) _transportGroups.get(SampleConfigurationTransport.HTTP);
    }

    /**
     * @return the WMQ transport control group
     */
    public CixsAdapterWmqGroup getCixsAdapterToHostWmqGroup() {
        return (CixsAdapterWmqGroup) _transportGroups.get(SampleConfigurationTransport.WMQ);
    }

    /**
     * @return the TCP transport control group
     */
    public CixsAdapterTcpGroup getCixsAdapterToHostTcpGroup() {
        return (CixsAdapterTcpGroup) _transportGroups.get(SampleConfigurationTransport.TCP);
    }

    /**
     * @return the MOCK transport control group
     */
    public CixsAdapterMockGroup getCixsAdapterToHostMockGroup() {
        return (CixsAdapterMockGroup) _transportGroups.get(SampleConfigurationTransport.MOCK);
    }

    /**
     * @return the selection of client to adapter java payload
     */
    public Button getJavaPayloadButton() {
        return _javaPayloadButton;
    }

    /**
     * @return the selection of client to adapter XML payload
     */
    public Button getXmlPayloadButton() {
        return _xmlPayloadButton;
    }

    /**
     * @return the data model
     */
    public AntBuildMule2CixsModel getGenModel() {
        return (AntBuildMule2CixsModel) super.getGenModel();
    }
}
