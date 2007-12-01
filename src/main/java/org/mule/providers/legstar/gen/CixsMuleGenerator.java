package org.mule.providers.legstar.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.mule.providers.legstar.gen.util.CixsMake;
import org.mule.providers.legstar.gen.util.CixsMuleGenUtil;
import org.mule.providers.legstar.model.CixsMuleComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Ant task creates the various Mule Component artifacts needed
 * for a complete Mule component with LegStar access capabilities.
 */
public class CixsMuleGenerator extends Task {

    /** Service descriptor. */
    private CixsMuleComponent mCixsMuleComponent;
    
        /** Target location for generated source. */
    private String mTargetSrcDir;
    
    /** Template for XML holding the make steps. */
    private static final String TEMPLATE = "vlc/cixsmule-cixsmake-xml.vm";
 
    /** Pattern for temporary files. */
    private static final String TEMP_PATTERN = "cixsmule";
    
    /** Suffix for temporary files. */
    private static final String TEMP_SUFFIX = ".tmp";

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CixsMuleGenerator.class);

    /** @{inheritDoc}*/
    @Override
   public final void init() {
        LOG.info("Initializing Mule component generator");
        try {
            CixsMuleGenUtil.initVelocity();
        } catch (Exception e) {
            throw new BuildException(e.getMessage());
        }
    }
    
    /**
     * Create a temporary cixs make file describing the templates that
     * needs to be applied to create a complete Mule component.
     * 
     * */
    @Override
    public final void execute() {
        long start = System.currentTimeMillis();
        
        try {
            checkInput();
            CixsMake cixsMake = new CixsMake();
            cixsMake.init();
            cixsMake.setModelName("muleComponent");
            cixsMake.setModel(mCixsMuleComponent);
            cixsMake.setCixsMakeFileName(getMakeFileName());
            cixsMake.execute();
            
        } catch (CixsMuleGenerationException e) {
            LOG.error("Mule component generator failure", e);
            throw new BuildException(e);
        }
        
        long end = System.currentTimeMillis();
        LOG.info("Generation success for {}", mCixsMuleComponent.getName());
        LOG.info("Duration = {} ms", (end - start));
    }
    
    /**
     * Check that input values are valid.
     * @throws CixsMuleGenerationException if input is invalid
     */
    private void checkInput() throws CixsMuleGenerationException {
        if (mCixsMuleComponent == null) {
            throw new CixsMuleGenerationException(
                    "Missing cixs mule component parameter");
        }
        try {
            CixsMuleGenUtil.checkDirectory(mTargetSrcDir, true);
        } catch (IllegalArgumentException e) {
            throw new CixsMuleGenerationException(e);
        }
    }
    
    /**
     * Create a temporary XML holding the make instructions for a Mule
     * component. The XML is built from a velocity template.
     * @return the temporary file name.
     * @throws CixsMuleGenerationException if XML make file creation fails
     */
    public final String getMakeFileName() throws CixsMuleGenerationException {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("targetName", mCixsMuleComponent.getName());
        context.put("targetSrcDir", mTargetSrcDir
                + CixsMuleGenUtil.relativeLocation(mCixsMuleComponent.getPackageName()));
        context.put("muleComponent", mCixsMuleComponent);
        StringWriter w = new StringWriter();
        try {
            Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
            File makeFile = File.createTempFile(TEMP_PATTERN, TEMP_SUFFIX);
            makeFile.deleteOnExit();
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(makeFile));
                out.write(w.toString());
            } catch (IOException e) {
                throw new CixsMuleGenerationException(e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return makeFile.getAbsolutePath();
            
        } catch (ResourceNotFoundException e) {
            throw new CixsMuleGenerationException(e);
        } catch (ParseErrorException e) {
            throw new CixsMuleGenerationException(e);
        } catch (MethodInvocationException e) {
            throw new CixsMuleGenerationException(e);
        } catch (IOException e) {
            throw new CixsMuleGenerationException(e);
        } catch (Exception e) {
            throw new CixsMuleGenerationException(e);
        }
    }


    
    /**
     * @return the Mule component 
     */
    public final CixsMuleComponent getCixsMuleComponent() {
        return mCixsMuleComponent;
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void setCixsMuleComponent(final CixsMuleComponent cixsMuleComponent) {
        mCixsMuleComponent = cixsMuleComponent;
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void add(final CixsMuleComponent cixsMuleComponent) {
        mCixsMuleComponent = cixsMuleComponent;
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void addCixsMuleComponent(final CixsMuleComponent cixsMuleComponent) {
        mCixsMuleComponent = cixsMuleComponent;
    }

    /**
     * @return the target source directory
     */
    public final String getTargetSrcDir() {
        return mTargetSrcDir;
    }

    /**
     * @param targetSrcDir the target source directory to set
     */
    public final void setTargetSrcDir(final String targetSrcDir) {
        mTargetSrcDir = targetSrcDir;
    }


}
