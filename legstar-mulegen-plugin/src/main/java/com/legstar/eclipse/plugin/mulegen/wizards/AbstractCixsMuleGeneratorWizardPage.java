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
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel;

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
    private Text _targetMuleConfigDirText = null;

    /** Where Mule takes users jars from. */
    private Text _targetJarDirText = null;

    /**
     * Construct the page.
     * @param selection the current workbench selection
     * @param pageName the page name
     * @param pageTitle the page title
     * @param pageDesc the page description
     * @param mappingFile the mapping file
     * @param genModel the generation model
     */
    protected AbstractCixsMuleGeneratorWizardPage(
            final IStructuredSelection selection,
            final String pageName,
            final String pageTitle,
            final String pageDesc,
            final IFile mappingFile,
            final AbstractAntBuildCixsMuleModel genModel) {
        super(selection, pageName, pageTitle, pageDesc, mappingFile, genModel);
    }

    /** {@inheritDoc} */
    public void addWidgetsToCixsGroup(final Composite arg0) {
    }

    /** {@inheritDoc} */
    public void addWidgetsToTargetGroup(final Composite container) {
        _targetMuleConfigDirText = createDirectoryFieldEditor(container,
                "targetMuleConfigDir",
                Messages.target_mule_config_location_label + ':');
    }

    /** {@inheritDoc} */
    public void addWidgetsToCoxbGroup(final Composite container) {
    }

    /** {@inheritDoc} */
    public void addWidgetsToDeploymentGroup(final Composite container) {
        _targetJarDirText = createTextField(container, getStore(),
                "targetJarDir",
                Messages.target_mule_jar_location_label + ':');
    }

    /** {@inheritDoc} */
    public void initExtendedWidgets(final IProject project) {

        setTargetMuleConfigDir(getInitTargetDir(
                getGenModel().getTargetMuleConfigDir(),
                PreferenceConstants.TARGET_MULE_CONFIG_FOLDER,
                true));

        setTargetJarDir(getInitTargetJarDir());

    }
    
    /**
     * @return an initial value
     */
    public String getInitTargetJarDir() {
        File initValue = getGenModel().getTargetJarDir();
        if (initValue == null) {
            return getStore().getString(
                    PreferenceConstants.MULE_USER_JAR_FOLDER);
        } else {
            return initValue.getPath();
        }
        
    }

    /** {@inheritDoc} */
    public void createExtendedListeners() {
        _targetMuleConfigDirText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
        _targetJarDirText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                dialogChanged();
            }
        });
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
     * Store the selected values in the project scoped preference store.
     */
    public void updateGenModelExtended() {
        getGenModel().setMuleHome(getMuleHome());
        try {
            getGenModel().setMulegenProductLocation(
                    getWizard().getPluginInstallLocation());
        } catch (InvocationTargetException e) {
            updateStatus(Messages.unable_to_locate_plugin_installation_msg);
        }
        getGenModel().setTargetJarDir(new File(getTargetJarDir()));
        getGenModel().setTargetMuleConfigDir(new File(getTargetMuleConfigDir()));
    }

    /**
     * @param targetJarDirLocation Where generated Jar files reside
     */
    public void setTargetJarDir(final String targetJarDirLocation) {
        _targetJarDirText.setText(targetJarDirLocation);
    }

    /**
     * @return Where generated Jar files reside
     */
    public String getTargetJarDir() {
        return _targetJarDirText.getText();
    }

    /**
     * @param targetMuleConfigDirLocation Where generated Mule configurations
     *  reside
     */
    public void setTargetMuleConfigDir(
            final String targetMuleConfigDirLocation) {
        _targetMuleConfigDirText.setText(targetMuleConfigDirLocation);
    }

    /**
     * @return Where generated Mule configurations reside
     */
    public String getTargetMuleConfigDir() {
        return _targetMuleConfigDirText.getText();
    }

    /** {@inheritDoc} */
    public AbstractCixsActivator getActivator() {
        return Activator.getDefault();
    }

    /**
     * @return Mule home
     */
    public String getMuleHome() {
        return getStore().getString(
                PreferenceConstants.MULE_INSTALL_FOLDER);
    }
    
    /** {@inheritDoc}*/
    public AbstractCixsMuleGeneratorWizard getWizard() {
        return (AbstractCixsMuleGeneratorWizard) super.getWizard();
    }


    /**
     * @return the data model
     */
    public AbstractAntBuildCixsMuleModel getGenModel() {
        return (AbstractAntBuildCixsMuleModel) super.getGenModel();
    }
}
