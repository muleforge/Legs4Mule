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

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardRunnable;

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


}
