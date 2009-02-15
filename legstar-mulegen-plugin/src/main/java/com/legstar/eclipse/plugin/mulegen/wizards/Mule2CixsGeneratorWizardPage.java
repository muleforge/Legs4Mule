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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;
import com.legstar.eclipse.plugin.mulegen.Activator;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * This page collects parameters needed for Mule to Cixs artifacts generation.
 *
 */
public class Mule2CixsGeneratorWizardPage
extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Mule2CixsGeneratorWizardPage";

    /** The Mainframe URI exposed to Mule clients. */
    private Text mHostURIText = null;

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

        createLabel(container, Messages.mainframe_uri_label + ':');
        mHostURIText = createText(container); 
        mHostURIText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        setHostURI(store.getString(
                PreferenceConstants.HOST_URI));
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {
        if (!super.validateExtendedWidgets()) {
            return false;
        }
        try {
            CodeGenUtil.checkHttpURI(getHostURI());
        } catch (CodeGenMakeException e) {
            updateStatus(Messages.invalid_mainframe_uri_msg);
            return false;
        }
        return true;
    }

    /**
     * @param hostURI URI exposed by mainframe for Mule clients
     */
    public void setHostURI(final String hostURI) {
        mHostURIText.setText(hostURI);
    }

    /**
     * @return URI exposed to mainframe programs
     */
    public String getHostURI() {
        return mHostURIText.getText();
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        // TODO Auto-generated method stub
        
    }

    /** {@inheritDoc} */
    public void storeExtendedProjectPreferences() {
        // TODO Auto-generated method stub
        
    }

}
