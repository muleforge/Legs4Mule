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

/**
 * This is a model for Cixs to Mule component generation. The generated 
 * artifacts allow a CICS transaction to call a Mule component.
 *
 */
public class AntBuildCixs2MuleModel extends AbstractAntBuildCixsMuleModel {

    /** This generator name. */
    public static final String CIXS2MULE_GENERATOR_NAME =
        "Mule server artifacts generator";

    /** This velocity template that creates an ant build which in turn
     * generates the target component. */
    public static final String CIXS2MULE_VELOCITY_MACRO_NAME =
        "vlc/build-cixs2mule-xml.vm";
    
    /** The target directory where COBOL files will be created. */
    private File mTargetCobolDir;
    
    /**
     * Construct the model.
     */
    public AntBuildCixs2MuleModel() {
        super(CIXS2MULE_GENERATOR_NAME, CIXS2MULE_VELOCITY_MACRO_NAME);
    }

    /**
     * @return the directory where COBOL files will be created
     */
    public final File getTargetCobolDir() {
        return mTargetCobolDir;
    }

    /**
     * @param targetCobolDir the directory where COBOL files will be created to set
     */
    public final void setTargetCobolDir(final File targetCobolDir) {
        mTargetCobolDir = targetCobolDir;
    }

}
