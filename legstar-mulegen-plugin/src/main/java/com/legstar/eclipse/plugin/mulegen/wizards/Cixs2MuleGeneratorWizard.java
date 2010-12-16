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
import org.mule.transport.legstar.model.AntBuildCixs2MuleModel;

import com.legstar.eclipse.plugin.cixscom.wizards
.AbstractCixsGeneratorWizardRunnable;

/**
 * This wizard role is to create a set of Mule artifacts that allows CICS 
 * clients to access a Mule UMO component.
 */

public class Cixs2MuleGeneratorWizard extends AbstractCixsMuleGeneratorWizard {

    /** What we are trying to generate. */
    public static final String GENERATION_SUBJECT = "Mule proxy Service";

    /** The main page of controls. */
    private Cixs2MuleGeneratorWizardPage _cixs2MuleGenPage;

    /**
     * Constructor for Cixs2MuleGeneratorWizard.
     * @param mappingFile an mapping file
     * @throws CoreException if initialization goes wrong 
     */
    public Cixs2MuleGeneratorWizard(
            final IFile mappingFile) throws CoreException {
        super(mappingFile);
    }

    /**
     * Adding the page to the wizard.
     */
    public final void addPages() {
        _cixs2MuleGenPage = new Cixs2MuleGeneratorWizardPage(
                getInitialSelection(), getMappingFile(), getGenModel());
        addPage(_cixs2MuleGenPage);
    }

    /** {@inheritDoc} */
    protected AbstractCixsGeneratorWizardRunnable getRunnable()
    throws InvocationTargetException {
        return new Cixs2MuleGeneratorWizardRunnable(_cixs2MuleGenPage);
    }

    /** {@inheritDoc} */
    @Override
    public AntBuildCixs2MuleModel createGenModel(final Properties props) {
        return new AntBuildCixs2MuleModel(props);
    }

    /** {@inheritDoc} */
    @Override
    public AntBuildCixs2MuleModel getGenModel() {
        return (AntBuildCixs2MuleModel) super.getGenModel();
    }

    /** {@inheritDoc} */
    @Override
    public String getGenerationSubject() {
        return GENERATION_SUBJECT;
    }

    /** {@inheritDoc} */
    @Override
    public IRunnableWithProgress getWizardRunnable()
            throws InvocationTargetException {
        return new Cixs2MuleGeneratorWizardRunnable(_cixs2MuleGenPage);
    }

}
