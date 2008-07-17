package org.mule.providers.legstar.model;

import java.io.File;

import com.legstar.cixs.gen.ant.model.AbstractAntBuildCixsModel;

/**
 * Holds the set of parameters needed to generate an ant script file.
 * This can be used by Eclipse plugins to generate ant files.
 * This model is common to all Mule generation types.
 */
public abstract class AbstractAntBuildCixsMuleModel extends AbstractAntBuildCixsModel {
    
    /** Mule product location on file system.*/
    private String mMuleHome;
    
    /** The target directory where Mule configuration files will be created. */
    private File mTargetMuleConfigDir;
    
    /** The target location for mule jar files. */
    private File mTargetJarDir;
    
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

}
