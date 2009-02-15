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
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsActivator;
import com.legstar.eclipse.plugin.cixscom.wizards
.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.mulegen.ClasspathInitializer;
import com.legstar.eclipse.plugin.mulegen.Activator;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * These are the common widgets to both Cixs 2 Mule and Mule 2 Cixs
 * wizards.
 */
public abstract class AbstractCixsMuleGeneratorWizardPage
extends AbstractCixsGeneratorWizardPage {

    /** Where generated Mule configuration files reside. */
    private Text mTargetMuleConfigDirText = null;

    /** Where Mule takes users jars from. */
    private Text mTargetJarDirText = null;

    /**
     * Construct the page.
     * @param pageName the page name
     * @param pageTitle the page title
     * @param pageDesc the page description
     * @param selection the current workbench selection
     * @param mappingFile the mapping file
     */
    protected AbstractCixsMuleGeneratorWizardPage(
            final String pageName,
            final String pageTitle,
            final String pageDesc,
            final IStructuredSelection selection,
            final IFile mappingFile) {
        super(selection, pageName, pageTitle, pageDesc, mappingFile);
    }

    /** {@inheritDoc} */
    public void addWidgetsToCixsGroup(final Composite arg0) {
    }

    /** {@inheritDoc} */
    public void addWidgetsToTargetGroup(final Composite container) {
        mTargetMuleConfigDirText = createDirectoryFieldEditor(container,
                "targetMuleConfigDir",
                Messages.target_mule_config_location_label + ':');
        mTargetMuleConfigDirText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
    }

    /** {@inheritDoc} */
    public void addWidgetsToCoxbGroup(final Composite container) {
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        mTargetJarDirText = createTextField(container, getStore(),
                "targetJarDir",
                Messages.target_mule_jar_location_label + ':');
        mTargetJarDirText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        setTargetMuleConfigDir(getDefaultTargetDir(store,
                PreferenceConstants.TARGET_MULE_CONFIG_FOLDER));

        setTargetJarDir(store.getDefaultString(
                PreferenceConstants.MULE_USER_JAR_FOLDER));
    }

    /** {@inheritDoc} */
    public boolean validateExtendedWidgets() {
        if (!checkDirectory(getTargetMuleConfigDir(),
                Messages.invalid_mule_config_location_msg)) {
            return false;
        }
        /* Project is valid. Make sure the project is ready for
         * generated sources compilation. */
        return setupProject();
    }

    /**
     * Make sure the target project has LegStar Mule libraries on its
     *  classpath.
     * @return false if the setup failed
     */
    protected boolean setupProject() {
        if (!lookupContainerLibrary(getTargetJavaProject(),
                ClasspathInitializer.LIBRARY_NAME)) {
            try {
                setupContainerLibrary(getTargetJavaProject(),
                        ClasspathInitializer.LIBRARY_NAME);
            } catch (JavaModelException e) {
                updateStatus(NLS.bind(
                        Messages.classpath_setup_failure_msg,
                        getTargetSrcDir(), e.getMessage()));
                return false;
            }
        }
        return true;
    }

    /**
     * @param targetJarDirLocation Where generated Jar files reside
     */
    public void setTargetJarDir(final String targetJarDirLocation) {
        mTargetJarDirText.setText(targetJarDirLocation);
    }

    /**
     * @return Where generated Jar files reside
     */
    public String getTargetJarDir() {
        return mTargetJarDirText.getText();
    }

    /**
     * @param targetMuleConfigDirLocation Where generated Mule configurations
     *  reside
     */
    public void setTargetMuleConfigDir(
            final String targetMuleConfigDirLocation) {
        mTargetMuleConfigDirText.setText(targetMuleConfigDirLocation);
    }

    /**
     * @return Where generated Mule configurations reside
     */
    public String getTargetMuleConfigDir() {
        return mTargetMuleConfigDirText.getText();
    }

    /** {@inheritDoc} */
    public AbstractCixsActivator getActivator() {
        return Activator.getDefault();
    }

    /**
     * @return Mule home
     */
    public String getMuleHome() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        return store.getString(
                PreferenceConstants.MULE_INSTALL_FOLDER);
    }


}
