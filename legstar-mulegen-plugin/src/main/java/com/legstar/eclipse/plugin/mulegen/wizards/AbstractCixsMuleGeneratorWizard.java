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
import com.legstar.eclipse.plugin.common.wizards.AbstractWizard;
import com.legstar.eclipse.plugin.mulegen.Activator;

/**
 * This wizard role is to create a set of Mule artifacts.
 */
public abstract class AbstractCixsMuleGeneratorWizard extends AbstractCixsGeneratorWizard {

    /**
     * Constructor.
     * @param mappingFile an mapping file
     * @throws CoreException if initialization goes wrong 
     */
    public AbstractCixsMuleGeneratorWizard(
            final IFile mappingFile) throws CoreException {
        super(mappingFile);
    }

    /** {@inheritDoc} */
    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

    /**
     * Determines where the common LegStar plugin is installed on the file
     * system.
     * 
     * @return the plugin location
     * @throws InvocationTargetException if location cannot be determined
     */
    public String getPluginInstallLocation()
            throws InvocationTargetException {
        return AbstractWizard.getPluginInstallLocation(getPluginId());
    }
}
