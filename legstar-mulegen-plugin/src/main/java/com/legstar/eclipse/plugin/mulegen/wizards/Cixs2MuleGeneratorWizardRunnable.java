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


/**
 * Background task that performs the actual artifacts generation. The process
 * involves 2 steps:
 * <ul>
 *  <li>Build an ant script file using a velocity template</li>
 *  <li>Launch the ant script as a background process</li>
 * </ul>
 */
public class Cixs2MuleGeneratorWizardRunnable
extends AbstractCixsMuleGeneratorWizardRunnable {

    /** Part of the ant script file name generated. Allows segregating
     * this ant file from the ones produced by other LegStar wizards. */
    private static final String ANT_FILE_NAME_ID = "mule-c2m-";

    /**
     * Constructs the backend generation task. 
     * The ant script will be generated under the folder from
     * preferences.
     * TODO allow user to select the ant script location
     * @param cixs2MuleGenWizardPage the main wizard page
     * @throws InvocationTargetException if construction fails
     */
    public Cixs2MuleGeneratorWizardRunnable(
            final Cixs2MuleGeneratorWizardPage cixs2MuleGenWizardPage)
    throws InvocationTargetException {
        super(cixs2MuleGenWizardPage, ANT_FILE_NAME_ID);
    }

}
