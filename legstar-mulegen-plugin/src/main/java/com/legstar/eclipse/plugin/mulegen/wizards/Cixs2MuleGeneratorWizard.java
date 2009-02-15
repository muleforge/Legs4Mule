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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizard;
import com.legstar.eclipse.plugin.cixscom.wizards
.AbstractCixsGeneratorWizardRunnable;
import com.legstar.eclipse.plugin.mulegen.Activator;

/**
 * This wizard role is to create a set of Mule artifacts that allows CICS 
 * clients to access a Mule UMO component.
 */

public class Cixs2MuleGeneratorWizard extends AbstractCixsGeneratorWizard {

    /** The main page of controls. */
    private Cixs2MuleGeneratorWizardPage mCixs2MuleGenPage;

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
        mCixs2MuleGenPage = new Cixs2MuleGeneratorWizardPage(
                getInitialSelection(), getMappingFile());
        addPage(mCixs2MuleGenPage);
    }

    /** {@inheritDoc} */
    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

    /** {@inheritDoc} */
    protected AbstractCixsGeneratorWizardRunnable getRunnable()
    throws InvocationTargetException {
        return new Cixs2MuleGeneratorWizardRunnable(mCixs2MuleGenPage);
    }

}
