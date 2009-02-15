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

import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizard;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.legstar.eclipse.plugin.cixscom.wizards
.AbstractCixsGeneratorWizardLauncher;
import com.legstar.eclipse.plugin.cixscom.wizards.ICixsGeneratorWizardLauncher;
import com.legstar.eclipse.plugin.mulegen.Messages;

/**
 * Implementation of a component generator which basically hands over
 * control to the Mule wizard.
 */
public class Cixs2MuleGeneratorWizardLauncher
extends AbstractCixsGeneratorWizardLauncher {

    /**
     * Get an instance of the wizard.
     * @param mappingFile the mapping file
     * @return a new instance of the wizard
     * @throws CoreException if wizard cannot be instantiated
     */
    protected IWizard getWizard(
            final IFile mappingFile) throws CoreException {
        return new Cixs2MuleGeneratorWizard(mappingFile);
    }

    /** {@inheritDoc} */
    public String getName() {
        return Messages.cixs_to_mule_wizard_page_title;
    }

    /**
     * Register an OSGI component generation service that can be discovered
     * dynamically by other plugins (bundles).
     * @param context the current bundle context
     * @return a registration service
     */
    public static ServiceRegistration register(
            final BundleContext context) {
        return context.registerService(
                ICixsGeneratorWizardLauncher.class.getName(),
                new Cixs2MuleGeneratorWizardLauncher(),
                new Properties());
    }
}
