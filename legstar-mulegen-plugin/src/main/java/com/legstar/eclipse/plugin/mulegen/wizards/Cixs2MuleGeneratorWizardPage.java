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
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizard;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.Activator;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * This page collects parameters needed for Cixs to Mule artifacts generation.
 *
 */
public class Cixs2MuleGeneratorWizardPage
extends AbstractCixsMuleGeneratorWizardPage {

    /** Page name. */
    private static final String PAGE_NAME = "Cixs2MuleGeneratorWizardPage";

    /** Mule component name. */
    private Text mMuleUMOImplementationText = null;

    /** Where generated COBOL source reside. */
    private Text mTargetCobolDirText = null;

    /** The UMO URI exposed to mainframe programs. */
    private Text mServiceURIText = null;

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
        Group group = createGroup(container, Messages.umo_group_label, 3);

        createLabel(group, Messages.umo_class_name_label + ':');
        mMuleUMOImplementationText = createText(group);
        mMuleUMOImplementationText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
        Button browseButton = createButton(group,
                com.legstar.eclipse.plugin.common.Messages.browse_button_label);
        browseButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                try {
                    SelectionDialog dialog = JavaUI.createTypeDialog(
                            getShell(),
                            PlatformUI.getWorkbench().getProgressService(),
                            SearchEngine.createWorkspaceScope(),
                            IJavaElementSearchConstants.CONSIDER_CLASSES,
                            false);
                    if (Window.OK == dialog.open()) {
                        Object[] results = dialog.getResult();
                        if (results != null && results.length > 0) {
                            setMuleUMOImplementation(((IType) results[0])
                                    .getFullyQualifiedName());
                        }
                    }
                } catch (JavaModelException e1) {
                    AbstractWizard.errorDialog(getShell(),
                            Messages.class_selection_error_dialog_title,
                            Activator.PLUGIN_ID,
                            Messages.class_selection_error_short_msg,
                            NLS.bind(Messages.class_selection_error_long_msg,
                                    e1.getMessage()));
                    AbstractWizard.logCoreException(e1, Activator.PLUGIN_ID);
                }
            }
        });
        super.addCixsGroup(container);
    }

    /** {@inheritDoc} */
    public void addWidgetsToTargetGroup(final Composite container) {
        super.addWidgetsToTargetGroup(container);
        mTargetCobolDirText = createDirectoryFieldEditor(container,
                "targetCobolDir",
                Messages.cobol_target_location_label + ':');
        mTargetCobolDirText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        super.addWidgetsToDeploymentGroup(container);
        createLabel(container,
                Messages.proxy_uri_label + ':');
        mServiceURIText = createText(container); 
        mServiceURIText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        // TODO Auto-generated method stub
        
    }

    /** {@inheritDoc} */
    public void storeExtendedProjectPreferences() {
        // TODO Auto-generated method stub
        
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        super.initExtendedWidgets(project);
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        setTargetCobolDir(getDefaultTargetDir(store,
                PreferenceConstants.COBOL_SAMPLE_FOLDER));

        setServiceURI(store.getString(
                PreferenceConstants.SERVICE_URI));
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {
        if (!super.validateExtendedWidgets()) {
            return false;
        }
        if (getMuleUMOImplementation().length() == 0) {
            updateStatus(Messages.invalid_umo_class_name_msg);
            return false;
        }
        try {
            CodeGenUtil.checkHttpURI(getServiceURI());
        } catch (CodeGenMakeException e) {
            updateStatus(Messages.invalid_proxy_uri_msg);
            return false;
        }
        if (!checkDirectory(getTargetCobolDir(),
                Messages.invalid_cobol_target_location_msg)) {
            return false;
        }

        return true;
    }

    /**
     * @return the Mule UMO implementation class name
     */
    public final String getMuleUMOImplementation() {
        return mMuleUMOImplementationText.getText();
    }

    /**
     * @param muleUMOImplementation the mule UMO implementation to set
     */
    public final void setMuleUMOImplementation(
            final String muleUMOImplementation) {
        mMuleUMOImplementationText.setText(muleUMOImplementation);
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
     * @param serviceURI URI exposed to mainframe programs
     */
    public void setServiceURI(final String serviceURI) {
        mServiceURIText.setText(serviceURI);
    }

    /**
     * @return URI exposed to mainframe programs
     */
    public String getServiceURI() {
        return mServiceURIText.getText();
    }

}
