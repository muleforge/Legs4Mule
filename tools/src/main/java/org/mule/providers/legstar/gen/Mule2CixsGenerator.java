/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.legstar.gen;

import java.io.File;
import java.util.Map;

import org.mule.providers.legstar.model.AntBuildMule2CixsModel;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;

/**
 * This Ant task creates the various Mule artifacts needed to implement
 * a Mule component that acts as an adapter for a mainframe program.
 * Mule clients can consume this adapter as any other Mule components
 * but internally the adapter use the LegStar transport to call a 
 * a mainframe program.
 */
public class Mule2CixsGenerator extends AbstractCixsMuleGenerator {

    /**
     * Constructor.
     */
    public Mule2CixsGenerator() {
        super(new AntBuildMule2CixsModel());
    }
    
    /**
     * Check that input values are valid.
     * @throws CodeGenMakeException if input is invalid
     */
    public void checkExtendedInput() throws CodeGenMakeException {
        try {
            /* Check that we are provided with valid locations to
             * generate in.*/
            CodeGenUtil.checkDirectory(
                    getTargetSrcDir(), true, "TargetSrcDir");
            CodeGenUtil.checkDirectory(
                    getTargetAntDir(), true, "TargetAntDir");
            CodeGenUtil.checkDirectory(
                    getTargetPropDir(), true, "TargetPropDir");
            CodeGenUtil.checkDirectory(
                    getTargetMuleConfigDir(), true, "TargetMuleConfigDir");

            /* Check that we are provided with valid locations to
             * reference.*/
            if (getTargetBinDir() == null) {
                throw (new IllegalArgumentException(
                        "TargetBinDir: No directory name was specified"));
            }
            if (getTargetJarDir() == null) {
                throw (new IllegalArgumentException(
                        "TargetJarDir: No directory name was specified"));
            }
           
            CodeGenUtil.checkHttpURI(getHostURI());
        } catch (IllegalArgumentException e) {
            throw new CodeGenMakeException(e);
        }
    }
    
    /**
     * Create all artifacts for Mule component.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public void generate(
            final Map < String, Object > parameters) throws CodeGenMakeException {

        parameters.put("targetJarDir", getTargetJarDir());
        parameters.put("targetMuleConfigDir", getTargetMuleConfigDir());
        parameters.put("hostCharset", getHostCharset());
        parameters.put("hostURI", getHostURI());
        parameters.put("generateBaseDir", getGenerateBuildDir());
       
        /* Determine target files locations */
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                getTargetSrcDir(), getCixsMuleComponent().getPackageName(), true);
        File componentAntFilesDir = getTargetAntDir();
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        File componentConfFilesDir = getTargetMuleConfigDir();
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        File operationPropertiesFilesDir = getTargetPropDir();
        CodeGenUtil.checkDirectory(operationPropertiesFilesDir, true);
        
        
        /* Produce artifacts for standalone component */
        generateInterface(
                getCixsMuleComponent(), parameters, componentClassFilesDir);
        generateImplementation(
                getCixsMuleComponent(), parameters, componentClassFilesDir);
        generateAntBuildJar(
                getCixsMuleComponent(), parameters, componentAntFilesDir);
        generateStandaloneConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir);
        generateAntStartMuleStandaloneConfigXml(
                getCixsMuleComponent(), parameters, componentAntFilesDir);
        generateLog4jProperties(
                getCixsMuleComponent(), parameters, operationPropertiesFilesDir);
        
        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations()) {

            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);
            
            generateFault(
                    operation, parameters, operationClassFilesDir);
            generateHolders(
                    operation, parameters, operationClassFilesDir);
            generateProgramProperties(
                    operation, parameters, operationPropertiesFilesDir);
            
        }
        
        /* Produce artifacts for bridge component  */
        generateBridgeConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir);
        generateAntStartMuleBridgeConfigXml(
                getCixsMuleComponent(), parameters, componentAntFilesDir);
        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations()) {
            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);
            
            generateHbaToObjectTransformers(
                    operation, parameters, operationClassFilesDir);
            generateObjectToHbaTransformers(
                    operation, parameters, operationClassFilesDir);
            
        }
        
    }
    
    public AntBuildMule2CixsModel getModel() {
        return (AntBuildMule2CixsModel) super.getModel();
    }

    /**
     * @return the URI that the host exposes to consumers
     */
    public final String getHostURI() {
        return getModel().getHostURI();
    }

    /**
     * @param hostURI the URI that the host exposes to consumers to set
     */
    public final void setHostURI(final String hostURI) {
        getModel().setHostURI(hostURI);
    }

}
