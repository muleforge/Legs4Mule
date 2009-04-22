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
package org.mule.providers.legstar.model;

import java.io.File;

import com.legstar.cixs.gen.ant.model.AbstractAntBuildCixsModel;
import com.legstar.cixs.jaxws.model.HttpTransportParameters;

/**
 * Holds the set of parameters needed to generate an ant script file.
 * This can be used by Eclipse plugins to generate ant files.
 * This model is common to all Mule generation types.
 */
public abstract class AbstractAntBuildCixsMuleModel extends AbstractAntBuildCixsModel
{
    
    /** Mule product location on file system.*/
    private String mMuleHome;
    
    /** Where the LegStar Mule generator product is installed on the file system.
     * The Mule generator might reside somewhere else than the LegStar core product. */
    private String mMulegenProductLocation;
    
    /** The target directory where Mule configuration files will be created. */
    private File mTargetMuleConfigDir;
    
    /** The target location for mule jar files. */
    private File mTargetJarDir;
    
    /** Set of parameters needed for HTTP transport. */
    private HttpTransportParameters mHttpTransportParameters = new HttpTransportParameters();

    /**
     * Construct the model with a generator name and velocity template.
     * @param generatorName to designate the generator
     * @param vlcTemplate a velocity template that accecpts this model
     */
    public AbstractAntBuildCixsMuleModel(
            final String generatorName, final String vlcTemplate) {
        super(generatorName, vlcTemplate);
    }
    
    /**
     * @return the Mule product location on file system
     */
    public final String getMuleHome() {
        return mMuleHome;
    }

    /**
     * @param muleHome the Mule product location on file system to set
     */
    public final void setMuleHome(final String muleHome) {
        mMuleHome = muleHome;
    }

    /**
     * @return the target directory where Mule configuration files will be
     *  created
     */
    public final File getTargetMuleConfigDir() {
        return mTargetMuleConfigDir;
    }

    /**
     * @param targetMuleConfigDir the target directory where Mule configuration
     *  files will be created to set
     */
    public final void setTargetMuleConfigDir(
            final File targetMuleConfigDir) {
        mTargetMuleConfigDir = targetMuleConfigDir;
    }

    /**
     * @return the target location for mule jar files
     */
    public final File getTargetJarDir() {
        return mTargetJarDir;
    }

    /**
     * @param targetJarDir the target location for mule jar files to set
     */
    public final void setTargetJarDir(final File targetJarDir) {
        mTargetJarDir = targetJarDir;
    }

    /**
     * @return the the Mule-Legstar component being generated
     */
    public final CixsMuleComponent getCixsMuleComponent() {
        return (CixsMuleComponent) getCixsService();
    }

    /**
     * @param cixsMuleComponent the the Mule-Legstar component being generated
     *  to set
     */
    public final void setCixsMuleComponent(
            final CixsMuleComponent cixsMuleComponent) {
        setCixsService(cixsMuleComponent);
    }

    /**
     * @return Where the LegStar Mule generator product is installed on the file system.
     * The Mule generator might reside somewhere else than the LegStar core product.
     * This is needed for ant scripts classpaths.
     */
    public final String getMulegenProductLocation() {
        return mMulegenProductLocation;
    }

    /**
     * @param mulegenProductLocation Where the LegStar Mule generator product
     *  is installed
     */
    public final void setMulegenProductLocation(
            final String mulegenProductLocation) {
        mMulegenProductLocation = mulegenProductLocation;
    }

    /**
     * @return set of parameters needed for HTTP transport
     */
    public HttpTransportParameters getHttpTransportParameters() {
        return mHttpTransportParameters;
    }

    /**
     * @param httpTransportParameters set of parameters needed for HTTP transport
     */
    public void setHttpTransportParameters(
            final HttpTransportParameters httpTransportParameters) {
        mHttpTransportParameters = httpTransportParameters;
    }

}
