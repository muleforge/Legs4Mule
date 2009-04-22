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

import com.legstar.eclipse.plugin.mulegen.Messages;

/**
 * This page collects parameters needed for Mule to Cixs artifacts generation.
 *
 */
public class Mule2CixsGeneratorWizardPage
extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Mule2CixsGeneratorWizardPage";

    /** HTTP adapter to mainframe transport parameters. */
    private CixsAdapterToHostHttpGroup mCixsAdapterToHostHttpGroup;

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

        createLabel(container, Messages.adapter_to_host_transport_label + ":");
        Composite composite = new Composite(container, SWT.NULL);
        composite.setLayout(new RowLayout());

        mCixsAdapterToHostHttpGroup = new CixsAdapterToHostHttpGroup(this);
        mCixsAdapterToHostHttpGroup.createButton(composite);
        mCixsAdapterToHostHttpGroup.createControls(container);
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);

        getCixsAdapterToHostHttpGroup().initControls();
        getCixsAdapterToHostHttpGroup().getButton().setSelection(true);
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        getCixsAdapterToHostHttpGroup().createListeners();
        
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {
        
        getCixsAdapterToHostHttpGroup().setVisibility();

        if (!super.validateExtendedWidgets()) {
            return false;
        }
        if(!getCixsAdapterToHostHttpGroup().validateControls()) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    public void storeExtendedProjectPreferences() {
        getCixsAdapterToHostHttpGroup().storeProjectPreferences();
        
    }

    /**
     * @return HTTP adapter to mainframe transport parameters
     */
    public CixsAdapterToHostHttpGroup getCixsAdapterToHostHttpGroup() {
        return mCixsAdapterToHostHttpGroup;
    }

}
