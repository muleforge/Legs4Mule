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
import org.mule.providers.legstar.model.AbstractAntBuildCixsMuleModel;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardRunnable;
import com.legstar.eclipse.plugin.mulegen.Activator;

/**
 * Background task that performs the actual artifacts generation. The process
 * involves 2 steps:
 * <ul>
 *  <li>Build an ant script file using a velocity template</li>
 *  <li>Launch the ant script as a background process</li>
 * </ul>
 */
public abstract class AbstractCixsMuleGeneratorWizardRunnable
extends AbstractCixsGeneratorWizardRunnable {

    /**
     * Constructs the backend generation task. 
     * @param cixsGenWizardPage the main wizard page
     * @param antFileNameId a prefix to append before file name.
     * @throws InvocationTargetException if construction fails
     */
    public AbstractCixsMuleGeneratorWizardRunnable(
            final AbstractCixsGeneratorWizardPage cixsGenWizardPage,
            final String antFileNameId) throws InvocationTargetException {
        super(cixsGenWizardPage, antFileNameId);
    }

    /**
     * Contribute specific mule generator properties to the ant model.
     * @param cixsGenWizardPage the wizard page
     * @param genModel the target model
     * @throws InvocationTargetException if model cannot be set
     */
    protected void setModel(
            final AbstractCixsGeneratorWizardPage cixsGenWizardPage,
            final AbstractAntBuildCixsMuleModel genModel)
    throws InvocationTargetException {

        genModel.setMulegenProductLocation(getPluginInstallLocation(
                Activator.PLUGIN_ID));
        super.setModel(cixsGenWizardPage, genModel);
    }


}
