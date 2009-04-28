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
package org.mule.transport.legstar.gen;

import java.io.File;
import java.util.Map;

import org.mule.transport.legstar.model.CixsMuleComponent;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel;

import com.legstar.cixs.gen.ant.AbstractCixsGenerator;
import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.jaxws.model.HttpTransportParameters;
import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;

/**
 * This class groups methods that are common to all generators.
 */
public abstract class AbstractCixsMuleGenerator extends AbstractCixsGenerator {

    /** This generator name. */
    public static final String CIXS_MULE_GENERATOR_NAME =
        "LegStar Mule Component generator";

    /** Velocity template for ant build jar. */
    public static final String COMPONENT_ANT_BUILD_JAR_VLC_TEMPLATE =
        "vlc/cixsmule-component-ant-build-jar-xml.vm";

    /** Velocity template for adapter-http mule configuration xml. */
    public static final String COMPONENT_ADAPTER_HTTP_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-adapter-http-config-xml.vm";

    /** Velocity template for local mule configuration xml. */
    public static final String COMPONENT_PROXY_HTTP_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-proxy-http-config-xml.vm";

    /** Velocity template for java to host byte array transformer. */
    public static final String OPERATION_JAVA_TO_HOST_VLC_TEMPLATE =
        "vlc/cixsmule-operation-transformer-java-to-host.vm";

    /** Velocity template for host byte array to java transformer. */
    public static final String OPERATION_HOST_TO_JAVA_VLC_TEMPLATE =
        "vlc/cixsmule-operation-transformer-host-to-java.vm";

    /** Velocity template for host byte array to XML transformer. */
    public static final String OPERATION_HOST_TO_XML_VLC_TEMPLATE =
        "vlc/cixsmule-operation-transformer-host-to-xml.vm";

    /** Velocity template for XML to host byte array transformer. */
    public static final String OPERATION_XML_TO_HOST_VLC_TEMPLATE =
        "vlc/cixsmule-operation-transformer-xml-to-host.vm";

    /** The service model name is it appears in templates. */
    public static final String COMPONENT_MODEL_NAME = "muleComponent";
    
    /**
     * Constructor.
     * @param model an instance of a generation model
     */
    public AbstractCixsMuleGenerator(final AbstractAntBuildCixsMuleModel model) {
        super(model);
    }

    /**
     * Create java to host byte array transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateJavaToHostTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateJavaToHostTransformer(operation, parameters,
                    transformersDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateJavaToHostTransformer(operation, parameters,
                    transformersDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create XML to host byte array transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateXmlToHostTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateXmlToHostTransformer(operation, parameters,
                    transformersDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateXmlToHostTransformer(operation, parameters,
                    transformersDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create an object to host byte array transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateJavaToHostTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_JAVA_TO_HOST_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                transformersDir,
                holderType + "ToHostMuleTransformer.java");
    }

    /**
     * Create an XML to host byte array transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateXmlToHostTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_XML_TO_HOST_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                transformersDir,
                holderType + "XmlToHostMuleTransformer.java");
    }

    /**
     * Create host byte array to java transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHostToJavaTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateHostToJavaTransformer(operation, parameters,
                    transformersDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateHostToJavaTransformer(operation, parameters,
                    transformersDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create host byte array to XML transformer for both request
     * and response objects.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHostToXmlTransformers(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir)
    throws CodeGenMakeException {

        if (operation.getInput().size() > 0) {
            generateHostToXmlTransformer(operation, parameters,
                    transformersDir,
                    operation.getRequestHolderType(),
            "Request");
        }
        if (operation.getOutput().size() > 0) {
            generateHostToXmlTransformer(operation, parameters,
                    transformersDir,
                    operation.getResponseHolderType(),
            "Response");
        }
    }

    /**
     * Create a host byte array to object transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHostToJavaTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_HOST_TO_JAVA_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                transformersDir,
                "HostTo" + holderType + "MuleTransformer.java");
    }

    /**
     * Create a host byte array to XML transformer.
     * @param operation the cixs operation
     * @param parameters miscellaneous help parameters
     * @param transformersDir where to store the generated file
     * @param holderType the Java class name for the holder
     * @param propertyName either Request or Response
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateHostToXmlTransformer(
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File transformersDir,
            final String holderType,
            final String propertyName)
    throws CodeGenMakeException {

        parameters.put("propertyName", propertyName);
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_HOST_TO_XML_VLC_TEMPLATE,
                "cixsOperation",
                operation,
                parameters,
                transformersDir,
                "HostTo" + holderType + "XmlMuleTransformer.java");
    }

    /**
     * Create the Mule adapter configuration file for java payloads over http.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAdapterHttpConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir)
    throws CodeGenMakeException {
        parameters.put("clientPayload", "java");
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ADAPTER_HTTP_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                "mule-adapter-http-config-" + component.getName() + ".xml");
    }

    /**
     * Create the Mule adapter configuration file for XML payloads over http.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateAdapterHttpConfigXmlXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir)
    throws CodeGenMakeException {
        parameters.put("clientPayload", "xml");
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ADAPTER_HTTP_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                "mule-adapter-http-config-xml-" + component.getName() + ".xml");
    }

    /**
     * Create the Mule proxy over http configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @throws CodeGenMakeException if generation fails
     */
    public static void generateProxyHttpConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir)
    throws CodeGenMakeException {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_PROXY_HTTP_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                "mule-proxy-http-config-" + component.getName() + ".xml");
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
    throws CodeGenMakeException {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ANT_BUILD_JAR_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentAntFilesDir,
        "build.xml");
    }

    /**
     * Check input values that are common to all derived classes.
     * Check that we are provided with valid locations to
     * generate in.
     * @throws CodeGenMakeException if input is invalid
     */
    public final void checkExtendedInput() throws CodeGenMakeException {

        CodeGenUtil.checkDirectory(
                getTargetSrcDir(), true, "TargetSrcDir");
        CodeGenUtil.checkDirectory(
                getTargetAntDir(), true, "TargetAntDir");
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

        /* Check that we have at least one operation. */
        if (getCixsMuleComponent().getCixsOperations().size() == 0) {
            throw new CodeGenMakeException(
            "No operation was specified");
        }

        /* Check that we have CICS program names mapped to operations */
        for (CixsOperation operation 
                : getCixsMuleComponent().getCixsOperations()) {
            String cicsProgramName = operation.getCicsProgramName();
            if (cicsProgramName == null || cicsProgramName.length() == 0) {
                throw new CodeGenMakeException(
                "Operation must specify a CICS program name");
            }
        }

        checkExtendedExtendedInput();

    }

    /**
     * Give the inheriting generators a chance to add more controls.
     * @throws CodeGenMakeException if control fails
     */
    public abstract void checkExtendedExtendedInput() throws CodeGenMakeException;

    /**
     * Create all artifacts for Mule service.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public final void generate(
            final Map < String, Object > parameters) throws CodeGenMakeException {

        addParameters(parameters);

        /* Determine target files locations */
        File serviceAntFilesDir = getTargetAntDir();

        generateAntBuildJar(
                getCixsMuleComponent(), parameters, serviceAntFilesDir);

        generateExtended(parameters);
    }

    /**
     * Create more artifacts for a Mule component.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public abstract void generateExtended(
            final Map < String, Object > parameters) throws CodeGenMakeException;


    /**
     * Add common parameters expected by templates to come from a parameters map.
     * @param parameters a parameters map to which parameters must be added
     */
    private void addParameters(final Map < String, Object > parameters) {
        parameters.put("generateBaseDir", getGenerateBuildDir());
        parameters.put("targetJarDir", getTargetJarDir());
        parameters.put("targetMuleConfigDir", getTargetMuleConfigDir());
        parameters.put("hostCharset", getHostCharset());

        addExtendedParameters(parameters);
    }


    /**
     * Add common parameters expected by templates to come from a parameters map.
     * @param parameters a parameters map to which parameters must be added
     */
    public abstract void addExtendedParameters(final Map < String, Object > parameters);

    /**
     * @return the Mule component 
     */
    public final CixsMuleComponent getCixsMuleComponent() {
        return (CixsMuleComponent) getCixsService();
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void setCixsMuleComponent(
            final CixsMuleComponent cixsMuleComponent) {
        setCixsService(cixsMuleComponent);
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void add(final CixsMuleComponent cixsMuleComponent) {
        setCixsMuleComponent(cixsMuleComponent);
    }

    /**
     * @param cixsMuleComponent the Mule component to set
     */
    public final void addCixsMuleComponent(
            final CixsMuleComponent cixsMuleComponent) {
        setCixsMuleComponent(cixsMuleComponent);
    }

    /**
     * @return the target mule jar files location
     */
    public final File getTargetJarDir() {
        return getAntModel().getTargetJarDir();
    }

    /**
     * @param targetJarDir the target mule jar files location to set
     */
    public final void setTargetJarDir(final File targetJarDir) {
        getAntModel().setTargetJarDir(targetJarDir);
    }

    /**
     * @return the target configuration files location
     */
    public final File getTargetMuleConfigDir() {
        return getAntModel().getTargetMuleConfigDir();
    }

    /**
     * @param targetMuleConfigDir the target configuration files location to set
     */
    public final void setTargetMuleConfigDir(final File targetMuleConfigDir) {
        getAntModel().setTargetMuleConfigDir(targetMuleConfigDir);
    }

    /**
     * @return the model representing all generation parameters
     */
    public AbstractAntBuildCixsMuleModel getAntModel() {
        return (AbstractAntBuildCixsMuleModel) super.getAntModel();
    }

    /** {@inheritDoc} */
    public final String getGeneratorName() {
        return CIXS_MULE_GENERATOR_NAME;
    }

    /**
     * @return the directory from which this ant script is start
     */
    public final String getGenerateBuildDir() {
        if (getProject() == null) {
            return ".";
        } else {
            return getProject().getBaseDir().getAbsolutePath();
        }
    }

    /**
     * @return the set of HTTP transport parameters
     */
    public HttpTransportParameters getHttpTransportParameters() {
        return getAntModel().getHttpTransportParameters();
    }

    /**
     * @param httpTransportParameters the set of HTTP transport parameters
     */
    public void setHttpTransportParameters(
            final HttpTransportParameters httpTransportParameters) {
        getAntModel().setHttpTransportParameters(httpTransportParameters);
    }

    /**
     * @param httpTransportParameters the set of HTTP transport parameters
     */
    public void addHttpTransportParameters(
            final HttpTransportParameters httpTransportParameters) {
        setHttpTransportParameters(httpTransportParameters);
    }

}
