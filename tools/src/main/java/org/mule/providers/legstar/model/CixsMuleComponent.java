/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.model;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Path.PathElement;

import com.legstar.cixs.gen.model.AbstractCixsService;

/**
 * Describes a Mule-LegStar Component with Mainframe access capabilities.
 */
public class CixsMuleComponent extends AbstractCixsService
{

    /** List of additional classpath elements that might be needed by
     * generated Mule startup procedure. */
    private List < Path > mMuleStartupPathElements = new ArrayList < Path > ();
    
    /**
     * @param path an additional classpath element that might be needed
     *  by generated Mule startup procedure
     */
    public final void addMuleStartupPathElement(final Path path)
    {
        mMuleStartupPathElements.add(path);
    }
    
    /**
     * @return the list of additional classpath elements that might be needed
     *  by generated Mule startup procedure
     */
    public final List < String > getMuleStartupPathElements()
    {
        List < String > pathElements = new ArrayList < String > ();
        for (Path path : mMuleStartupPathElements)
        {
            for (String pathElement : path.list())
            {
                pathElements.add(pathElement);
            }
        }
        return pathElements;
    }
    
    /**
     * From a list of file locations on the file system, creates a list of
     * path elements ant style.
     * @param pathElements a list of files to add on classpath
     */
    public final void setMuleStartupPathElements(final List < String > pathElements)
    {
        if (pathElements.size() == 0)
        {
            return;
        }
        Project antProject = new Project();
        Path path = new Path(antProject);
        for (String element : pathElements)
        {
            PathElement pathElement = path.createPathElement();
            pathElement.setLocation(new File(element));
        }
        mMuleStartupPathElements.add(path);
    }
 
}
