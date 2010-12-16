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

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.mule.transport.legstar.model.AntBuildCixs2MuleModel;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;

import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * This page collects parameters needed for Cixs to Mule artifacts generation.
 *
 */
public class Cixs2MuleGeneratorWizardPage extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Cixs2MuleGeneratorWizardPage";

    /** Where generated COBOL source reside. */
    private Text _targetCobolDirText = null;

    /** Keeps a reference on the deployment group container. */
    private Composite _deploymentGroup = null;

    /** Settings for target selection group. */
    private Group _targetGroup = null;

    /** The UMO component target controls group. */
    private CixsProxyUmoComponentTargetGroup _umoComponentTargetGroup;

    /** HTTP Proxy client parameters. */
    private CixsHostToProxyHttpGroup _cixsHostToProxyHttpGroup;

    /** WMQ Proxy client parameters. */
    private CixsHostToProxyWmqGroup _cixsHostToProxyWmqGroup;

    /**
     * Construct the page.
     * @param selection the current workbench selection
     * @param mappingFile the mapping file
     * @param genModel the generation model
     */
    protected Cixs2MuleGeneratorWizardPage(
            final IStructuredSelection selection,
            final IFile mappingFile,
            final AntBuildCixs2MuleModel genModel) {
        super(selection,
                PAGE_NAME,
                Messages.cixs_to_mule_wizard_page_title,
                Messages.cixs_to_mule_wizard_page_description,
                mappingFile,
                genModel);
    }

    /** {@inheritDoc} */
    protected void addCixsGroup(final Composite container) {

        _targetGroup = createGroup(container,
                Messages.target_selection_group_label, 3);
        createLabel(_targetGroup, Messages.target_selection_label + ':');
        Composite composite = new Composite(_targetGroup, SWT.NULL);
        composite.setLayout(new RowLayout());

        _umoComponentTargetGroup = new CixsProxyUmoComponentTargetGroup(this,
                getGenModel().getUmoComponentTargetParameters(),
                true);

        _umoComponentTargetGroup.createButton(composite);

        _umoComponentTargetGroup.createControls(_targetGroup);

        super.addCixsGroup(container);
    }

    /** {@inheritDoc} */
    public void addWidgetsToTargetGroup(final Composite container) {
        super.addWidgetsToTargetGroup(container);
        _targetCobolDirText = createDirectoryFieldEditor(container,
                "targetCobolDir",
                Messages.cobol_target_location_label + ':');
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        super.addWidgetsToDeploymentGroup(container);
        _deploymentGroup = container;

        createLabel(container, Messages.sample_configuration_transport_label
                + ":");
        Composite composite = new Composite(container, SWT.NULL);
        composite.setLayout(new RowLayout());

        _cixsHostToProxyHttpGroup = new CixsHostToProxyHttpGroup(
                this,
                getGenModel().getHttpTransportParameters(),
                getGenModel().getSampleCobolHttpClientType(),
                getGenModel().getSampleConfigurationTransport() == SampleConfigurationTransport.HTTP);
        _cixsHostToProxyHttpGroup.createButton(composite);
        _cixsHostToProxyHttpGroup.createControls(container);

        _cixsHostToProxyWmqGroup = new CixsHostToProxyWmqGroup(
                this,
                getGenModel().getWmqTransportParameters(),
                getGenModel().getSampleConfigurationTransport() == SampleConfigurationTransport.WMQ);
        _cixsHostToProxyWmqGroup.createButton(composite);
        _cixsHostToProxyWmqGroup.createControls(container);
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);

        setTargetCobolDir(getInitTargetDir(getGenModel().getTargetCobolDir(),
                PreferenceConstants.DEFAULT_COBOL_SAMPLE_FOLDER,
                true));

        getUmoComponentTargetGroup().initControls();
        getCixsHostToProxyHttpGroup().initControls();
        getCixsHostToProxyWmqGroup().initControls();


    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        super.createExtendedListeners();
        _targetCobolDirText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });

        getUmoComponentTargetGroup().createListeners();
        getCixsHostToProxyHttpGroup().createListeners();
        getCixsHostToProxyWmqGroup().createListeners();
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {

        if (!super.validateExtendedWidgets()) {
            return false;
        }

        getUmoComponentTargetGroup().setVisibility();
        getCixsHostToProxyHttpGroup().setVisibility();
        getCixsHostToProxyWmqGroup().setVisibility();

        getShell().layout(new Control[] {_targetGroup, _deploymentGroup});

        if (!super.validateExtendedWidgets()) {
            return false;
        }

        if (!getUmoComponentTargetGroup().validateControls()) {
            return false;
        }
        
        if (getCixsHostToProxyHttpGroup().isSelected()
                && !getCixsHostToProxyHttpGroup().validateControls()) {
            return false;
        }
        if (getCixsHostToProxyWmqGroup().isSelected()
                && !getCixsHostToProxyWmqGroup().validateControls()) {
            return false;
        }

        if (!checkDirectory(getTargetCobolDir(),
                Messages.invalid_cobol_target_location_msg)) {
            return false;
        }

        return true;
    }

    /**
     * Store the selected values in the project scoped preference store.
     */
    public void updateGenModelExtended() {
        
        super.updateGenModelExtended();

        getUmoComponentTargetGroup().updateGenModel();
        getCixsHostToProxyHttpGroup().updateGenModel();
        getCixsHostToProxyWmqGroup().updateGenModel();

        if (getCixsHostToProxyHttpGroup().isSelected()) {
            getGenModel().setSampleConfigurationTransport(SampleConfigurationTransport.HTTP);
        }
        if (getCixsHostToProxyWmqGroup().isSelected()) {
            getGenModel().setSampleConfigurationTransport(SampleConfigurationTransport.WMQ);
        }
        
        getGenModel().setTargetCobolDir(new File(getTargetCobolDir()));
    }

    /**
     * @param targetCobolDirLocation Where generated Cobol files reside
     */
    public void setTargetCobolDir(final String targetCobolDirLocation) {
        _targetCobolDirText.setText(targetCobolDirLocation);
    }

    /**
     * @return Where generated Cobol files reside
     */
    public String getTargetCobolDir() {
        return _targetCobolDirText.getText();
    }

    /**
     * @return the UMO component target controls group
     */
    public CixsProxyUmoComponentTargetGroup getUmoComponentTargetGroup() {
        return _umoComponentTargetGroup;
    }

    /**
     * @return the HTTP Proxy client parameters
     */
    public CixsHostToProxyHttpGroup getCixsHostToProxyHttpGroup() {
        return _cixsHostToProxyHttpGroup;
    }

    /**
     * @return the WMQ Proxy client parameters
     */
    public CixsHostToProxyWmqGroup getCixsHostToProxyWmqGroup() {
        return _cixsHostToProxyWmqGroup;
    }

    /**
     * @return the data model
     */
    public AntBuildCixs2MuleModel getGenModel() {
        return (AntBuildCixs2MuleModel) super.getGenModel();
    }
}
