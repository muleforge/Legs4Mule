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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.mule.providers.legstar.model.AbstractAntBuildCixsMuleModel;

import com.legstar.eclipse.plugin.cixscom.wizards
.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.cixscom.wizards
.AbstractCixsGeneratorWizardRunnable;
import com.legstar.eclipse.plugin.mulegen.Activator;
import com.legstar.eclipse.plugin.mulegen.ClasspathInitializer;

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
        List < String > classPathElements = new ArrayList < String >();
        for (String classPathElement 
                : getClassPathElements(cixsGenWizardPage)) {
            classPathElements.add(classPathElement);
        }
        genModel.getCixsMuleComponent().setMuleStartupPathElements(
                classPathElements);
    }

    /**
     * Creates a list of physical locations on the file system for the
     * target Eclipse project classpath elements.
     * This will make sure any Referenced Libraries from the Eclipse project
     * will also be included in the Mule startup script.
     * Of particular importance are the Legs4Mule libraries found in the
     * LegStar Mule container.
     * TODO The list is currently not complete. We are missing variables
     * and containers classpath entries.
     * @param cixsGenWizardPage the associated wisard page
     * @return a list of file system locations for classpath elements
     * @throws InvocationTargetException if list cannot be built
     */
    private Set < String > getClassPathElements(
            final AbstractCixsGeneratorWizardPage cixsGenWizardPage)
            throws InvocationTargetException {
        Set < String > cpeSet = new HashSet < String >();
        IJavaProject javaProject = cixsGenWizardPage.getTargetJavaProject();
        try {
            IPath rootPath =
                javaProject.getProject().getLocation().removeLastSegments(1);
            for (IClasspathEntry cpe : javaProject.getRawClasspath()) {
                if (cpe.getEntryKind()
                        == IClasspathEntry.CPE_SOURCE) {
                    if (cpe.getOutputLocation() == null) {
                        cpeSet.add(rootPath.append(
                                javaProject.getOutputLocation()).toOSString());
                    } else {
                        cpeSet.add(rootPath.append(
                                cpe.getOutputLocation()).toOSString());
                    }
                }
                if (cpe.getEntryKind()
                        == IClasspathEntry.CPE_LIBRARY) {
                    cpeSet.add(cpe.getPath().toOSString());
                }
                if (cpe.getEntryKind()
                        == IClasspathEntry.CPE_CONTAINER
                        && cpe.getPath().equals(
                                new Path(ClasspathInitializer.LIBRARY_NAME))) {
                    IClasspathContainer classpathContainer =
                        JavaCore.getClasspathContainer(cpe.getPath(),
                                javaProject);
                    for (IClasspathEntry cpel
                            : classpathContainer.getClasspathEntries()) {
                        cpeSet.add(cpel.getPath().toOSString());
                    }
                }
            }
        } catch (JavaModelException e) {
            throw new InvocationTargetException(e);
        }
        return cpeSet;
    }


}
