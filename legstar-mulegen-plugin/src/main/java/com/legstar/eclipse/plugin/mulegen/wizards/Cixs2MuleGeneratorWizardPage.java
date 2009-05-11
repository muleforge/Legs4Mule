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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;

import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.Activator;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * This page collects parameters needed for Cixs to Mule artifacts generation.
 *
 */
public class Cixs2MuleGeneratorWizardPage extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Cixs2MuleGeneratorWizardPage";

    /** Where generated COBOL source reside. */
    private Text mTargetCobolDirText = null;

    /** Keeps a reference on the deployment group container. */
    private Composite mDeploymentGroup = null;

    /** Settings for target selection group. */
    private Group mTargetGroup = null;

    /** The UMO component target controls group. */
    private CixsProxyUmoComponentTargetGroup mUmoComponentTargetGroup;

    /** HTTP Proxy client parameters. */
    private CixsHostToProxyHttpGroup mCixsHostToProxyHttpGroup;

    /** WMQ Proxy client parameters. */
    private CixsHostToProxyWmqGroup mCixsHostToProxyWmqGroup;

    /**
     * Construct the page.
     * @param selection the current workbench selection
     * @param mappingFile the mapping file
     */
    protected Cixs2MuleGeneratorWizardPage(
            final IStructuredSelection selection, final IFile mappingFile) {
        super(PAGE_NAME,
                Messages.cixs_to_mule_wizard_page_title,
                Messages.cixs_to_mule_wizard_page_description,
                selection,
                mappingFile);
    }

    /** {@inheritDoc} */
    protected void addCixsGroup(final Composite container) {

        mTargetGroup = createGroup(container, Messages.target_selection_group_label, 3);
        createLabel(mTargetGroup, Messages.target_selection_label + ':');
        Composite composite = new Composite(mTargetGroup, SWT.NULL);
        composite.setLayout(new RowLayout());

        mUmoComponentTargetGroup = new CixsProxyUmoComponentTargetGroup(this);

        mUmoComponentTargetGroup.createButton(composite);

        mUmoComponentTargetGroup.createControls(mTargetGroup);

        super.addCixsGroup(container);
    }

    /** {@inheritDoc} */
    public void addWidgetsToTargetGroup(final Composite container) {
        super.addWidgetsToTargetGroup(container);
        mTargetCobolDirText = createDirectoryFieldEditor(container,
                "targetCobolDir",
                Messages.cobol_target_location_label + ':');
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        super.addWidgetsToDeploymentGroup(container);
        mDeploymentGroup = container;

        createLabel(container, Messages.sample_configuration_transport_label + ":");
        Composite composite = new Composite(container, SWT.NULL);
        composite.setLayout(new RowLayout());

        mCixsHostToProxyHttpGroup = new CixsHostToProxyHttpGroup(this);
        mCixsHostToProxyHttpGroup.createButton(composite);
        mCixsHostToProxyHttpGroup.createControls(container);

        mCixsHostToProxyWmqGroup = new CixsHostToProxyWmqGroup(this);
        mCixsHostToProxyWmqGroup.createButton(composite);
        mCixsHostToProxyWmqGroup.createControls(container);
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        setTargetCobolDir(getDefaultTargetDir(store,
                PreferenceConstants.COBOL_SAMPLE_FOLDER));

        getUmoComponentTargetGroup().initControls();
        getCixsHostToProxyHttpGroup().initControls();
        getCixsHostToProxyWmqGroup().initControls();

        getUmoComponentTargetGroup().getButton().setSelection(true);

        /* Make sure one of the adapter transports groups is visible */
        SampleConfigurationTransport sampleConfigurationTransport =
            SampleConfigurationTransport.valueOf(getProjectPreferences().get(
                    PreferenceConstants.PROXY_LAST_SAMPLE_CONFIGURATION_TRANSPORT,
                    getStore().getDefaultString(
                            PreferenceConstants.PROXY_LAST_SAMPLE_CONFIGURATION_TRANSPORT)));
                
        if (sampleConfigurationTransport == SampleConfigurationTransport.HTTP) {
            getCixsHostToProxyHttpGroup().getButton().setSelection(true);
            getCixsHostToProxyWmqGroup().getButton().setSelection(false);
        }
        if (sampleConfigurationTransport == SampleConfigurationTransport.WMQ) {
            getCixsHostToProxyHttpGroup().getButton().setSelection(false);
            getCixsHostToProxyWmqGroup().getButton().setSelection(true);
        }
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        super.createExtendedListeners();
        mTargetCobolDirText.addModifyListener(new ModifyListener() {
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

        getUmoComponentTargetGroup().setVisibility();
        getCixsHostToProxyHttpGroup().setVisibility();
        getCixsHostToProxyWmqGroup().setVisibility();

        getShell().layout(new Control[] {mTargetGroup, mDeploymentGroup});

        if (!super.validateExtendedWidgets()) {
            return false;
        }

        if (!getUmoComponentTargetGroup().validateControls()) {
            return false;
        }
        
        if (getSampleConfigurationTransport() == SampleConfigurationTransport.HTTP
                && !getCixsHostToProxyHttpGroup().validateControls()) {
            return false;
        }
        if (getSampleConfigurationTransport() == SampleConfigurationTransport.WMQ
                && !getCixsHostToProxyWmqGroup().validateControls()) {
            return false;
        }

        if (!checkDirectory(getTargetCobolDir(),
                Messages.invalid_cobol_target_location_msg)) {
            return false;
        }

        return true;
    }

    /** {@inheritDoc} */
    public void storeExtendedProjectPreferences() {
        super.storeExtendedProjectPreferences();
        
        getUmoComponentTargetGroup().storeProjectPreferences();
        getCixsHostToProxyHttpGroup().storeProjectPreferences();
        getCixsHostToProxyWmqGroup().storeProjectPreferences();
        getProjectPreferences().put(
                PreferenceConstants.PROXY_LAST_SAMPLE_CONFIGURATION_TRANSPORT,
                getSampleConfigurationTransport().toString());
    }

    /**
     * @param targetCobolDirLocation Where generated Cobol files reside
     */
    public void setTargetCobolDir(final String targetCobolDirLocation) {
        mTargetCobolDirText.setText(targetCobolDirLocation);
    }

    /**
     * @return Where generated Cobol files reside
     */
    public String getTargetCobolDir() {
        return mTargetCobolDirText.getText();
    }

    /**
     * @return the UMO component target controls group
     */
    public CixsProxyUmoComponentTargetGroup getUmoComponentTargetGroup() {
        return mUmoComponentTargetGroup;
    }

    /**
     * @return the HTTP Proxy client parameters
     */
    public CixsHostToProxyHttpGroup getCixsHostToProxyHttpGroup() {
        return mCixsHostToProxyHttpGroup;
    }

    /**
     * @return the WMQ Proxy client parameters
     */
    public CixsHostToProxyWmqGroup getCixsHostToProxyWmqGroup() {
        return mCixsHostToProxyWmqGroup;
    }

    /**
     * @return the client transport selected
     */
    public final SampleConfigurationTransport getSampleConfigurationTransport() {
        if (getCixsHostToProxyHttpGroup().getSelection()) {
            return SampleConfigurationTransport.HTTP;
        }
        if (getCixsHostToProxyWmqGroup().getSelection()) {
            return SampleConfigurationTransport.WMQ;
        }
        return SampleConfigurationTransport.HTTP;
    }
    
}
