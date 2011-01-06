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

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.mule.transport.legstar.model.AntBuildMule2CixsModel;

/**
 * This wizard role is to create a set of Mule artifacts that allows Mule 
 * clients to access a CICS program as a local UMO component.
 */
public class Mule2CixsGeneratorWizard extends AbstractCixsMuleGeneratorWizard {

    /** What we are trying to generate. */
    public static final String GENERATION_SUBJECT = "Mule Service adapter";

    /** The main page of controls. */
    private Mule2CixsGeneratorWizardPage _mule2CixsGenPage;

    /**
     * Constructor for Mule2CixsGeneratorWizard.
     * @param mappingFile an mapping file
     * @throws CoreException if initialization goes wrong 
     */
    public Mule2CixsGeneratorWizard(
            final IFile mappingFile) throws CoreException {
        super(mappingFile);
    }

    /**
     * Adding the page to the wizard.
     */
    @Override
    public final void addPages() {
        _mule2CixsGenPage = new Mule2CixsGeneratorWizardPage(
                getInitialSelection(), getMappingFile(), getGenModel());
        addPage(_mule2CixsGenPage);
    }


    /** {@inheritDoc} */
    public AntBuildMule2CixsModel createGenModel(final Properties props) {
        return new AntBuildMule2CixsModel(props);
    }

    /** {@inheritDoc} */
    @Override
    public AntBuildMule2CixsModel getGenModel() {
        return (AntBuildMule2CixsModel) super.getGenModel();
    }

    /** {@inheritDoc} */
    public String getGenerationSubject() {
        return GENERATION_SUBJECT;
    }

    /** {@inheritDoc} */
    public IRunnableWithProgress getWizardRunnable()
            throws InvocationTargetException {
        return new Mule2CixsGeneratorWizardRunnable(_mule2CixsGenPage);
    }

}
