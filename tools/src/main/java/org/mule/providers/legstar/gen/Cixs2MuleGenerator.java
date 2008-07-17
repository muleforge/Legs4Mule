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
import java.util.List;
import java.util.Map;

import org.mule.providers.legstar.model.AntBuildCixs2MuleModel;
import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.jaxws.gen.Cixs2JaxwsGenerator;
import com.legstar.cixs.jaxws.gen.StructuresGenerator;
import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;

/**
 * This Ant task creates the various Mule artifacts needed to provide
 * mainframe programs with access to a Mule component. These artifacts
 * help setup a LegStar transport Mule listener that receives execution
 * requests from the Mainframe and forwards this request to an existing 
 * POJO within Mule.
 * 
 */
public class Cixs2MuleGenerator extends AbstractCixsMuleGenerator {

    /**
     * Constructor.
     */
    public Cixs2MuleGenerator() {
        super(new AntBuildCixs2MuleModel());
    }
    
    /**
     * Check that input values are valid.
     * @throws CodeGenMakeException if input is invalid
     */
    public void checkExtendedInput() throws CodeGenMakeException {
        try {
            CodeGenUtil.checkDirectory(
                    getTargetAntDir(), true, "TargetAntDir");
            CodeGenUtil.checkDirectory(
                    getTargetMuleConfigDir(), true, "TargetMuleConfigDir");
            CodeGenUtil.checkDirectory(
                    getTargetCobolDir(), true, "TargetCobolDir");
            CodeGenUtil.checkHttpURI(getCixsMuleComponent().getServiceURI());
            
            /* Check that we have CICS program names mapped to operations */
            for (CixsOperation operation 
                    : getCixsMuleComponent().getCixsOperations()) {
                String cicsProgramName = operation.getCicsProgramName();
                if (cicsProgramName == null || cicsProgramName.length() == 0) {
                    throw new CodeGenMakeException(
                            "Operation must specify a CICS program name");
                }
            }
        } catch (IllegalArgumentException e) {
            throw new CodeGenMakeException(e);
        }
    }
    /**
     * Create all artifacts for Mule server.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public void generate(
            final Map < String, Object > parameters) throws CodeGenMakeException {
        
        parameters.put("targetJarDir", getTargetJarDir());
        parameters.put("serviceURI", getCixsMuleComponent().getServiceURI());
        parameters.put("hostCharset", getHostCharset());
        parameters.put("structHelper", new StructuresGenerator());

        /* Determine target files locations */
        File componentAntFilesDir = getTargetAntDir();
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        File componentConfFilesDir = getTargetMuleConfigDir();
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        File componentCobolCicsClientDir = getTargetCobolDir();
        CodeGenUtil.checkDirectory(componentCobolCicsClientDir, true);
        
        /* Produce artifacts for standalone component */
        generateAntBuildJar(
                getCixsMuleComponent(), parameters, componentAntFilesDir);
        
        /* Produce artifacts for local component  */
        generateLocalConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir);
        for (CixsOperation operation : getCixsOperations()) {
            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);
            
            generateHbaToObjectTransformers(
                    operation, parameters, operationClassFilesDir);
            generateObjectToHbaTransformers(
                    operation, parameters, operationClassFilesDir);
            generateObjectToHttpResponseTransformers(
                    operation, parameters, operationClassFilesDir);

            parameters.put("cixsOperation", operation);
            generateCobolCicsClient(
                    getCixsMuleComponent(), operation, parameters,
                    componentCobolCicsClientDir);
            
        }
        
    }
    
    /**
     * Create a COBOl CICS Client program to use for testing.
     * @param component the Mule component description
     * @param operation the operation for which a program is to be generated
     * @param parameters the set of parameters to pass to template engine
     * @param cobolFilesDir location where COBOL code should be generated
     * @throws CodeGenMakeException if generation fails
     */
    protected static void generateCobolCicsClient(
            final CixsMuleComponent component,
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File cobolFilesDir) throws CodeGenMakeException {
            generateFile(Cixs2JaxwsGenerator.CIXS_TO_JAXWS_GENERATOR_NAME,
                    Cixs2JaxwsGenerator.OPERATION_COBOL_CICS_CLIENT_VLC_TEMPLATE,
                    Cixs2JaxwsGenerator.SERVICE_MODEL_NAME,
                    component,
                    parameters,
                    cobolFilesDir,
                    operation.getCicsProgramName() + ".cbl");
    }

    /**
     * @return the directory where COBOL files will be created
     */
    public final File getTargetCobolDir() {
        return getModel().getTargetCobolDir();
    }

    /**
     * @param targetCobolDir the directory where COBOL files will be created to set
     */
    public final void setTargetCobolDir(File targetCobolDir) {
        getModel().setTargetCobolDir(targetCobolDir);
    }

    public AntBuildCixs2MuleModel getModel() {
        return (AntBuildCixs2MuleModel) super.getModel();
    }

    /**
     * Convenience method to get the inner mapped operations.
     * @return the operations list
     */
    public List < CixsOperation > getCixsOperations() {
        return getCixsMuleComponent().getCixsOperations();
    }
    
}
