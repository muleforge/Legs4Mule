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

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

import org.mule.transport.legstar.model.UmoComponentParameters;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizard;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Activator;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * Holds the controls for an UMO Component proxy target.
 * <p/>
 * A POJO is described by a UMO component implementation and a service name.
 *
 */
public class CixsProxyUmoComponentTargetGroup extends AbstractCixsControlsGroup {

    /** Target UMO Component UMO component implementation. */
    private Text mTargetUmoComponentImplementationNameText = null;

    /**
     * Construct this control holder attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     */
    public CixsProxyUmoComponentTargetGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }

    /**
     * {@inheritDoc} 
     */
    public void createButton(final Composite composite) {
        super.createButton(composite, "UMO Component");
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        super.createControls(composite, Messages.target_umo_component_group_label, 3);

        AbstractWizardPage.createLabel(getGroup(),
                Messages.target_umo_component_implementation_name_label + ':');
        mTargetUmoComponentImplementationNameText = AbstractWizardPage.createText(getGroup());

        Button browseButton = AbstractWizardPage.createButton(getGroup(),
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
                            setImplementationName(((IType) results[0])
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
    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {

        mTargetUmoComponentImplementationNameText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {
        setImplementationName(getProjectPreferences().get(
                PreferenceConstants.PROXY_LAST_UMO_COMPONENT_IMPLEMENTATION_NAME, ""));
    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {
        getProjectPreferences().put(
                PreferenceConstants.PROXY_LAST_UMO_COMPONENT_IMPLEMENTATION_NAME, getImplementationName());
    }

    /**
     * {@inheritDoc} 
     */
    public boolean validateControls() {
        if (getImplementationName().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_target_umo_component_implementation_name_msg);
            return false;
        }
        return true;
    }

    /**
     * @return the target UMO component implementation
     */
    public final String getImplementationName() {
        return mTargetUmoComponentImplementationNameText.getText();
    }

    /**
     * @param implementationName target UMO component implementation to set
     */
    public final void setImplementationName(
            final String implementationName) {
        mTargetUmoComponentImplementationNameText.setText(implementationName);
    }

    /**
     * @return the target UMO Component parameters as a formatted parameters object
     */
    public UmoComponentParameters getUmoComponentTargetParameters() {
        UmoComponentParameters umoComponentParameters = new UmoComponentParameters();
        umoComponentParameters.setImplementationName(getImplementationName());
        return umoComponentParameters;
        
    }

}
