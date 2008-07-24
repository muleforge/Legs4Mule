/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.gen;

import java.io.File;
import java.util.Map;

import org.mule.providers.legstar.model.CixsMuleComponent;
import org.mule.providers.legstar.model.AbstractAntBuildCixsMuleModel;

import com.legstar.cixs.gen.ant.AbstractCixsGenerator;
import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.jaxws.gen.Jaxws2CixsGenerator;
import com.legstar.codegen.CodeGenMakeException;

/**
 * This class groups methods that are common to all generators.
 */
public abstract class AbstractCixsMuleGenerator extends AbstractCixsGenerator
{

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

    /** Velocity template for ant mule startup. */
    public static final String COMPONENT_ANT_START_MULE_VLC_TEMPLATE =
        "vlc/cixsmule-start-mule-xml.vm";

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

    /** Velocity template for local mule configuration xml. */
    public static final String COMPONENT_LOG4J_PROP_VLC_TEMPLATE =
        "vlc/cixsmule-log4j-xml.vm";

    /** The service model name is it appears in templates. */
    public static final String COMPONENT_MODEL_NAME = "muleComponent";

    /**
     * Constructor.
     * @param model an instance of a generation model
     */
    public AbstractCixsMuleGenerator(final AbstractAntBuildCixsMuleModel model)
    {
        super(model);
    }

    /**
     * Create the Mule Interface class file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateInterface(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentClassFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_INTERFACE_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentClassFilesDir,
                component.getInterfaceClassName() + ".java");
    }

    /**
     * Create the Mule Implementation class file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateImplementation(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentClassFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_IMPLEMENTATION_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentClassFilesDir,
                component.getImplementationClassName() + ".java");
    }

    /**
     * Create the Propram properties file.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param componentPropertiesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateProgramProperties(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File componentPropertiesDir)
    throws CodeGenMakeException
    {
        generateFile(Jaxws2CixsGenerator.JAXWS_TO_CIXS_GENERATOR_NAME,
                Jaxws2CixsGenerator.OPERATION_PROGRAM_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                componentPropertiesDir,
                operation.getCicsProgramName().toLowerCase() + ".properties");
    }

    /**
     * Create a fault class (Mule Exception).
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateFault(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_FAULT_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                operationClassFilesDir,
                operation.getFaultType() + ".java");
    }

    /**
     * Create a holder classes for channel/containers.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHolders(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir)
    throws CodeGenMakeException
    {

        if (operation.getCicsChannel() == null
                || operation.getCicsChannel().length() == 0)
        {
            return;
        }

        if (operation.getInput().size() > 0)
        {
            parameters.put("propertyName", "Request");
            generateFile(CIXS_MULE_GENERATOR_NAME,
                    OPERATION_HOLDER_VLC_TEMPLATE,
                    "cixsOperation",
                    operation,
                    parameters,
                    operationClassFilesDir,
                    operation.getRequestHolderType() + ".java");
        }
        if (operation.getOutput().size() > 0)
        {
            parameters.put("propertyName", "Response");
            generateFile(CIXS_MULE_GENERATOR_NAME,
                    OPERATION_HOLDER_VLC_TEMPLATE,
                    "cixsOperation",
                    operation,
                    parameters,
                    operationClassFilesDir,
                    operation.getResponseHolderType() + ".java");
        }
    }

    /**
     * Create the Mule Ant Build Jar file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAntBuildJar(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentAntFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ANT_BUILD_JAR_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentAntFilesDir,
        "build.xml");
    }

    /**
     * Create the Mule Startup ant file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesDir where to store the generated file
     * @param configFileName the name of the configuration file to pass on Mule
     *  startup
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAntStartMule(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentAntFilesDir,
            final String configFileName)
    throws CodeGenMakeException
    {
        parameters.put("configFileName", configFileName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ANT_START_MULE_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentAntFilesDir,
                "start-" + configFileName);
    }

    /**
     * Create the Mule stand alone configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateStandaloneConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_STANDALONE_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                "mule-standalone-config-" + component.getName() + ".xml");
    }

    /**
     * Create a Mule startup ant script for stand alone configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAntStartMuleStandaloneConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentAntFilesDir)
    throws CodeGenMakeException
    {
        generateAntStartMule(component,
                parameters,
                componentAntFilesDir,
                "mule-standalone-config-" + component.getName() + ".xml");
    }

    /**
     * Create objects to host byte array transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHbaTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir)
    throws CodeGenMakeException
    {

        if (operation.getInput().size() > 0)
        {
            generateObjectToHbaTransformer(operation, parameters,
                    operationClassFilesDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0)
        {
            generateObjectToHbaTransformer(operation, parameters,
                    operationClassFilesDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create an object to host byte array transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHbaTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException
    {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_OBJECT_TO_HBA_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                operationClassFilesDir,
                holderType + "ToHostByteArray.java");
    }

    /**
     * Create host byte array to objects transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHbaToObjectTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir)
    throws CodeGenMakeException
    {

        if (operation.getInput().size() > 0)
        {
            generateHbaToObjectTransformer(operation, parameters,
                    operationClassFilesDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0)
        {
            generateHbaToObjectTransformer(operation, parameters,
                    operationClassFilesDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create a host byte array to object transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHbaToObjectTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException
    {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_HBA_TO_OBJECT_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                operationClassFilesDir,
                "HostByteArrayTo" + holderType + ".java");
    }

    /**
     * Create objects to http response transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHttpResponseTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir)
    throws CodeGenMakeException
    {

        if (operation.getInput().size() > 0)
        {
            generateObjectToHttpResponseTransformer(operation, parameters,
                    operationClassFilesDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0)
        {
            generateObjectToHttpResponseTransformer(operation, parameters,
                    operationClassFilesDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create an object to http response transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param operationClassFilesDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateObjectToHttpResponseTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File operationClassFilesDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException
    {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_OBJECT_TO_HTTP_RESPONSE_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                operationClassFilesDir,
                holderType + "ToHttpResponse.java");
    }

    /**
     * Create the Mule bridge configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateBridgeConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_BRIDGE_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                "mule-bridge-config-" + component.getName() + ".xml");
    }

    /**
     * Create a Mule startup ant script for bridge configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAntStartMuleBridgeConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentAntFilesDir)
    throws CodeGenMakeException
    {
        generateAntStartMule(component,
                parameters,
                componentAntFilesDir,
                "mule-bridge-config-" + component.getName() + ".xml");
    }

    /**
     * Create the Mule local configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateLocalConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_LOCAL_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                "mule-local-config-" + component.getName() + ".xml");
    }

    /**
     * Create a Mule startup ant script for local configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAntStartMuleLocalConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentAntFilesDir)
    throws CodeGenMakeException
    {
        generateAntStartMule(component,
                parameters,
                componentAntFilesDir,
                "mule-local-config-" + component.getName() + ".xml");
    }

    /**
     * Create a log4j properties file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentPropertiesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateLog4jProperties(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentPropertiesDir)
    throws CodeGenMakeException
    {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_LOG4J_PROP_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentPropertiesDir,
                "log4j" + ".properties");
    }

    /**
     * @return the Mule component 
     */
    public final CixsMuleComponent getCixsMuleComponent()
    {
        return (CixsMuleComponent) getCixsService();
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void setCixsMuleComponent(
            final CixsMuleComponent cixsMuleComponent)
    {
        setCixsService(cixsMuleComponent);
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void add(final CixsMuleComponent cixsMuleComponent)
    {
        setCixsMuleComponent(cixsMuleComponent);
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void addCixsMuleComponent(
            final CixsMuleComponent cixsMuleComponent)
    {
        setCixsMuleComponent(cixsMuleComponent);
    }

    /**
     * @return the target mule jar files location
     */
    public final File getTargetJarDir()
    {
        return getModel().getTargetJarDir();
    }

    /**
     * @param targetJarDir the target mule jar files location to set
     */
    public final void setTargetJarDir(final File targetJarDir)
    {
        getModel().setTargetJarDir(targetJarDir);
    }

    /**
     * @return the target configuration files location
     */
    public final File getTargetMuleConfigDir()
    {
        return getModel().getTargetMuleConfigDir();
    }

    /**
     * @param targetMuleConfigDir the target configuration files location to set
     */
    public final void setTargetMuleConfigDir(final File targetMuleConfigDir)
    {
        getModel().setTargetMuleConfigDir(targetMuleConfigDir);
    }

    /**
     * @return the model representing all generation parameters
     */
    public final AbstractAntBuildCixsMuleModel getModel()
    {
        return (AbstractAntBuildCixsMuleModel) super.getModel();
    }

    /** {@inheritDoc} */
    public final String getGeneratorName()
    {
        return CIXS_MULE_GENERATOR_NAME;
    }

    /**
     * @return the directory from which this ant script is start
     */
    public final String getGenerateBuildDir()
    {
        if (getProject() == null)
        {
            return ".";
        }
        else
        {
            return getProject().getBaseDir().getAbsolutePath();
        }
    }

}
