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
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.mule.providers.legstar.model.CixsMuleComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.jaxws.gen.CixsHelper;
import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;

/**
 * This Ant task creates the various Mule Component artifacts needed
 * for a complete Mule component with LegStar access capabilities.
 */
public class CixsMuleGenerator extends Task {

    /** This generator name. */
    public static final String CIXS_MULE_GENERATOR_NAME =
        "LegStar Mule Component generator";

    /** Velocity template for component interface. */
    public static final String COMPONENT_INTERFACE_VLC_TEMPLATE =
        "vlc/cixsmule-component-interface.vm";

    /** Velocity template for component implementation. */
    public static final String COMPONENT_IMPLEMENTATION_VLC_TEMPLATE =
        "vlc/cixsmule-component-implementation.vm";

    /** Velocity template for fault. */
    public static final String OPERATION_FAULT_VLC_TEMPLATE =
        "vlc/cixsmule-operation-fault.vm";

    /** Velocity template for holder. */
    public static final String OPERATION_HOLDER_VLC_TEMPLATE =
        "vlc/cixsmule-operation-holder.vm";

    /** Velocity template for program. */
    public static final String OPERATION_PROGRAM_VLC_TEMPLATE =
        "vlc/cixsmule-operation-program.vm";

    /** Velocity template for ant build jar. */
    public static final String COMPONENT_ANT_BUILD_JAR_VLC_TEMPLATE =
        "vlc/cixsmule-component-ant-build-jar-xml.vm";
    
    /** Velocity template for standalone mule configuration xml. */
    public static final String COMPONENT_STANDALONE_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-standalone-config-xml.vm";
    
    /** Velocity template for object to host byte array transformer. */
    public static final String OPERATION_OBJECT_TO_HBA_VLC_TEMPLATE =
        "vlc/cixsmule-operation-object-to-hba-transformer.vm";
    
    /** Velocity template for host byte array to object transformer. */
    public static final String OPERATION_HBA_TO_OBJECT_VLC_TEMPLATE =
        "vlc/cixsmule-operation-hba-to-object-transformer.vm";
    
    /** Velocity template for object to http response transformer. */
    public static final String OPERATION_OBJECT_TO_HTTP_RESPONSE_VLC_TEMPLATE =
        "vlc/cixsmule-operation-object-to-http-response-transformer.vm";
    
    /** Velocity template for bridge mule configuration xml. */
    public static final String COMPONENT_BRIDGE_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-bridge-config-xml.vm";
    
    /** Velocity template for local mule configuration xml. */
    public static final String COMPONENT_LOCAL_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-local-config-xml.vm";
    
    /** Service descriptor. */
    private CixsMuleComponent mCixsMuleComponent;
    
    /** Target location for generated source. */
    private String mTargetSrcDir;
    
    /** Target location for ant deployment script. */
    private String mTargetAntDir;
    
    /** Target location for mule jar files. */
    private String mTargetJarDir;
    
    /** Target location for properties files. */
    private String mTargetPropDir;
    
    /** Target location for configuration files. */
    private String mTargetConfDir;
    
    /** Location of jaxb classes binaries. */
    private String mJaxbBinDir;
    
    /** Location of jaxb classes binaries. */
    private String mCoxbBinDir;
    
    /** Location of Mule component binaries. */
    private String mCixsBinDir;
    
    /** Location of custom binaries. */
    private String mCustBinDir;
    
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CixsMuleGenerator.class);

    /** @{inheritDoc}*/
    @Override
   public final void init() {
        LOG.info("Initializing Mule component generator");
        try {
            CodeGenUtil.initVelocity();
        } catch (Exception e) {
            throw new BuildException(e.getMessage());
        }
    }
    
    /**
     * Check that enough input parameters are set and then
     * generate the requested artifacts.
     * 
     * */
    @Override
    public final void execute() {
        LOG.info("Generating Mule component artifacts for "
                + ((mCixsMuleComponent == null) ? "null"
                        : mCixsMuleComponent.getName()));
        long start = System.currentTimeMillis();

        try {
            checkInput();
            generate();

        } catch (CodeGenMakeException e) {
            LOG.error("Mule component generator failure", e);
            throw new BuildException(e);
        }

        long end = System.currentTimeMillis();
        LOG.info("Generation success for " + mCixsMuleComponent.getName());
        LOG.info("Duration = " + (end - start) + " ms");
    }
    
    /**
     * Check that input values are valid.
     * @throws CodeGenMakeException if input is invalid
     */
    private void checkInput() throws CodeGenMakeException {
        if (mCixsMuleComponent == null) {
            throw new CodeGenMakeException(
                    "Missing cixs mule component parameter");
        }
        try {
            CodeGenUtil.checkDirectory(mTargetSrcDir, true);
            CodeGenUtil.checkDirectory(mTargetAntDir, true);
            CodeGenUtil.checkDirectory(mTargetPropDir, true);
            CodeGenUtil.checkDirectory(mTargetConfDir, true);
        } catch (IllegalArgumentException e) {
            throw new CodeGenMakeException(e);
        }
    }
    
    /**
     * Create all artifacts for Mule component.
     * @throws CodeGenMakeException if generation fails
     */
    private void generate() throws CodeGenMakeException {
        Map < String, Object > parameters = new HashMap < String, Object >();
        CodeGenHelper helper = new CodeGenHelper();
        parameters.put("helper", helper);
        CixsHelper cixsHelper = new CixsHelper();
        parameters.put("cixsHelper", cixsHelper);

        /* These parameters are primarily useful for the ant build template */
        parameters.put("jarDir", getTargetJarDir());
        parameters.put("jaxbBinDir", getJaxbBinDir());
        parameters.put("coxbBinDir", getCoxbBinDir());
        parameters.put("cixsBinDir", getCixsBinDir());
        parameters.put("custBinDir", getCustBinDir());
        parameters.put("propDir", getTargetPropDir());

        /* Determine target files locations */
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                mTargetSrcDir, mCixsMuleComponent.getPackageName());
        String componentAntFilesLocation = getTargetAntDir();
        CodeGenUtil.checkDirectory(componentAntFilesLocation, true);
        String componentConfFilesLocation = getTargetConfDir();
        CodeGenUtil.checkDirectory(componentConfFilesLocation, true);
        
        /* Produce artifacts for standalone component */
        generateInterface(
                mCixsMuleComponent, parameters, componentClassFilesLocation);
        generateImplementation(
                mCixsMuleComponent, parameters, componentClassFilesLocation);
        generateAntBuildJar(
                mCixsMuleComponent, parameters, componentAntFilesLocation);
        generateStandaloneConfigXml(
                mCixsMuleComponent, parameters, componentConfFilesLocation);
        
        for (CixsOperation operation : mCixsMuleComponent.getCixsOperations()) {
            String operationPackageName = cixsHelper.getOperationPackageName(
                    operation, mCixsMuleComponent.getPackageName());
            parameters.put("operationPackageName", operationPackageName);

            /* Determine target files locations */
            String operationClassFilesLocation = CodeGenUtil.classFilesLocation(
                    mTargetSrcDir, operationPackageName);
            String operationPropertiesFilesLocation = getTargetPropDir();
            CodeGenUtil.checkDirectory(operationPropertiesFilesLocation, true);
            
            generateFault(
                    operation, parameters, operationClassFilesLocation);
            generateHolders(
                    operation, parameters, operationClassFilesLocation);
            generateProgramProperties(
                    operation, parameters, operationPropertiesFilesLocation);
            
        }
        
        /* Produce artifacts for bridge component  */
        generateBridgeConfigXml(
                mCixsMuleComponent, parameters, componentConfFilesLocation);
        for (CixsOperation operation : mCixsMuleComponent.getCixsOperations()) {
            String operationPackageName = cixsHelper.getOperationPackageName(
                    operation, mCixsMuleComponent.getPackageName());
            parameters.put("operationPackageName", operationPackageName);

            /* Determine target files locations */
            String operationClassFilesLocation = CodeGenUtil.classFilesLocation(
                    mTargetSrcDir, operationPackageName);
            String operationPropertiesFilesLocation = getTargetPropDir();
            CodeGenUtil.checkDirectory(operationPropertiesFilesLocation, true);
            
            generateHbaToObjectTransformers(
                    operation, parameters, operationClassFilesLocation);
            generateObjectToHbaTransformers(
                    operation, parameters, operationClassFilesLocation);
            
        }
        
        /* Produce artifacts for local component  */
        generateLocalConfigXml(
                mCixsMuleComponent, parameters, componentConfFilesLocation);
        for (CixsOperation operation : mCixsMuleComponent.getCixsOperations()) {
            String operationPackageName = cixsHelper.getOperationPackageName(
                    operation, mCixsMuleComponent.getPackageName());
            parameters.put("operationPackageName", operationPackageName);

            /* Determine target files locations */
            String operationClassFilesLocation = CodeGenUtil.classFilesLocation(
                    mTargetSrcDir, operationPackageName);
            String operationPropertiesFilesLocation = getTargetPropDir();
            CodeGenUtil.checkDirectory(operationPropertiesFilesLocation, true);
            
            generateHbaToObjectTransformers(
                    operation, parameters, operationClassFilesLocation);
            generateObjectToHttpResponseTransformers(
                    operation, parameters, operationClassFilesLocation);
            
        }
        
    }

    /**
     * Create the Mule Interface class file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateInterface(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final String componentClassFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentClassFilesLocation,
                component.getInterfaceClassName() + ".java");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                COMPONENT_INTERFACE_VLC_TEMPLATE,
                "muleComponent", component,
                parameters,
                targetFile);
    }
    
    /**
     * Create the Mule Implementation class file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateImplementation(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final String componentClassFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentClassFilesLocation,
                component.getImplementationClassName() + ".java");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                COMPONENT_IMPLEMENTATION_VLC_TEMPLATE,
                "muleComponent", component,
                parameters,
                targetFile);
    }
    
    /**
     * Create the Propram properties file.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param componentPropertiesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateProgramProperties(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String componentPropertiesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentPropertiesLocation,
                operation.getCicsProgramName().toLowerCase() + ".properties");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                OPERATION_PROGRAM_VLC_TEMPLATE,
                "cixsOperation", operation,
                parameters,
                targetFile);
    }
    
    /**
     * Create a fault class (Mule Exception).
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateFault(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(operationClassFilesLocation,
                operation.getFaultType() + ".java");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                OPERATION_FAULT_VLC_TEMPLATE,
                "cixsOperation", operation,
                parameters,
                targetFile);
    }

    /**
     * Create a holder classes for channel/containers.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHolders(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation)
    throws CodeGenMakeException {

        if (operation.getCicsChannel() == null
                || operation.getCicsChannel().length() == 0) {
            return;
        }
        
        if (operation.getInput().size() > 0) {
            File targetFile = CodeGenUtil.getFile(operationClassFilesLocation,
                    operation.getRequestHolderType() + ".java");
            LOG.info("Generating " + targetFile.getAbsolutePath());
            parameters.put("propertyName", "Request");
            CodeGenUtil.processTemplate(
                    CIXS_MULE_GENERATOR_NAME,
                    OPERATION_HOLDER_VLC_TEMPLATE,
                    "cixsOperation", operation,
                    parameters,
                    targetFile);
        }
        if (operation.getOutput().size() > 0) {
            File targetFile = CodeGenUtil.getFile(operationClassFilesLocation,
                    operation.getResponseHolderType() + ".java");
            LOG.info("Generating " + targetFile.getAbsolutePath());
            parameters.put("propertyName", "Response");
            CodeGenUtil.processTemplate(
                    CIXS_MULE_GENERATOR_NAME,
                    OPERATION_HOLDER_VLC_TEMPLATE,
                    "cixsOperation", operation,
                    parameters,
                    targetFile);
        }
    }

    /**
     * Create the Mule Ant Build Jar file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAntBuildJar(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final String componentAntFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentAntFilesLocation,
            "build.xml");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ANT_BUILD_JAR_VLC_TEMPLATE,
                "muleComponent", component,
                parameters,
                targetFile);
    }
    
    /**
     * Create the Mule stand alone configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateStandaloneConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final String componentConfFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentConfFilesLocation,
                "mule-standalone-config-" + component.getName() + ".xml");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                COMPONENT_STANDALONE_CONFIG_XML_VLC_TEMPLATE,
                "muleComponent", component,
                parameters,
                targetFile);
    }
    
    /**
     * Create objects to host byte array transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHbaTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateObjectToHbaTransformer(operation, parameters,
                    operationClassFilesLocation,
                    operation.getRequestHolderType(),
                    "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateObjectToHbaTransformer(operation, parameters,
                    operationClassFilesLocation,
                    operation.getResponseHolderType(),
                    "Response");
        }
    }

    /**
     * Create an object to host byte array transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHbaTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(operationClassFilesLocation,
                holderType + "ToHostByteArray.java");
        LOG.info("Generating " + targetFile.getAbsolutePath());

        parameters.put("propertyName", propertyName);
        
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                OPERATION_OBJECT_TO_HBA_VLC_TEMPLATE,
                "cixsOperation", operation,
                parameters,
                targetFile);
    }

    /**
     * Create host byte array to objects transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHbaToObjectTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateHbaToObjectTransformer(operation, parameters,
                    operationClassFilesLocation,
                    operation.getRequestHolderType(),
                    "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateHbaToObjectTransformer(operation, parameters,
                    operationClassFilesLocation,
                    operation.getResponseHolderType(),
                    "Response");
        }
    }

    /**
     * Create a host byte array to object transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHbaToObjectTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(operationClassFilesLocation,
                "HostByteArrayTo" + holderType + ".java");
        LOG.info("Generating " + targetFile.getAbsolutePath());

        parameters.put("propertyName", propertyName);
        
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                OPERATION_HBA_TO_OBJECT_VLC_TEMPLATE,
                "cixsOperation", operation,
                parameters,
                targetFile);
    }

    /**
     * Create objects to http response transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHttpResponseTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateObjectToHttpResponseTransformer(operation, parameters,
                    operationClassFilesLocation,
                    operation.getRequestHolderType(),
                    "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateObjectToHttpResponseTransformer(operation, parameters,
                    operationClassFilesLocation,
                    operation.getResponseHolderType(),
                    "Response");
        }
    }

    /**
     * Create an object to http response transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesLocation where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHttpResponseTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final String operationClassFilesLocation,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(operationClassFilesLocation,
                holderType + "ToHttpResponse.java");
        LOG.info("Generating " + targetFile.getAbsolutePath());

        parameters.put("propertyName", propertyName);
        
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                OPERATION_OBJECT_TO_HTTP_RESPONSE_VLC_TEMPLATE,
                "cixsOperation", operation,
                parameters,
                targetFile);
    }

    /**
     * Create the Mule bridge configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateBridgeConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final String componentConfFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentConfFilesLocation,
                "mule-bridge-config-" + component.getName() + ".xml");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                COMPONENT_BRIDGE_CONFIG_XML_VLC_TEMPLATE,
                "muleComponent", component,
                parameters,
                targetFile);
    }
    
    /**
     * Create the Mule local configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesLocation where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateLocalConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final String componentConfFilesLocation)
    throws CodeGenMakeException {

        File targetFile = CodeGenUtil.getFile(componentConfFilesLocation,
                "mule-local-config-" + component.getName() + ".xml");
        LOG.info("Generating " + targetFile.getAbsolutePath());
        CodeGenUtil.processTemplate(
                CIXS_MULE_GENERATOR_NAME,
                COMPONENT_LOCAL_CONFIG_XML_VLC_TEMPLATE,
                "muleComponent", component,
                parameters,
                targetFile);
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
    /**
     * @return custom binaries location
     */
    public final String getCustBinDir() {
        return mCustBinDir;
    }

    /**
     * @param custBinDir the custom binaries location to set
     */
    public final void setCustBinDir(final String custBinDir) {
        mCustBinDir = custBinDir;
    }

    /**
     * @return the target properties files location
     */
    public final String getTargetPropDir() {
        return mTargetPropDir;
    }

    /**
     * @param targetPropDir the target properties files location to set
     */
    public final void setTargetPropDir(final String targetPropDir) {
        mTargetPropDir = targetPropDir;
    }

    /**
     * @return the Mule component binaries
     */
    public final String getCixsBinDir() {
        return mCixsBinDir;
    }

    /**
     * @param cixsBinDir the Mule component binaries to set
     */
    public final void setCixsBinDir(final String cixsBinDir) {
        mCixsBinDir = cixsBinDir;
    }

    /**
     * @return the jaxb binaries location
     */
    public final String getJaxbBinDir() {
        return mJaxbBinDir;
    }

    /**
     * @param jaxbBinDir the jaxb binaries location to set
     */
    public final void setJaxbBinDir(final String jaxbBinDir) {
        mJaxbBinDir = jaxbBinDir;
    }

    /**
     * @return the coxb binaries location
     */
    public final String getCoxbBinDir() {
        return mCoxbBinDir;
    }

    /**
     * @param coxbBinDir the coxb binaries location to set
     */
    public final void setCoxbBinDir(final String coxbBinDir) {
        mCoxbBinDir = coxbBinDir;
    }

    /**
     * @return the location for ant deployment script
     */
    public final String getTargetAntDir() {
        return mTargetAntDir;
    }

    /**
     * @param targetAntDir the location for ant deployment script to set
     */
    public final void setTargetAntDir(final String targetAntDir) {
        mTargetAntDir = targetAntDir;
    }

    /**
     * @return the target mule jar files location
     */
    public final String getTargetJarDir() {
        return mTargetJarDir;
    }

    /**
     * @param targetJarDir the target mule jar files location to set
     */
    public final void setTargetJarDir(final String targetJarDir) {
        mTargetJarDir = targetJarDir;
    }

    /**
     * @return the target configuration files location
     */
    public final String getTargetConfDir() {
        return mTargetConfDir;
    }

    /**
     * @param targetConfDir the target configuration files location to set
     */
    public final void setTargetConfDir(final String targetConfDir) {
        mTargetConfDir = targetConfDir;
    }

}
