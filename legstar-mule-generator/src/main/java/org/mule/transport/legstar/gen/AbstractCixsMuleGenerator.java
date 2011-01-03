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
import java.util.Locale;
import java.util.Map;

import org.mule.transport.legstar.model.CixsMuleComponent;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationPayloadType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;

import com.legstar.cixs.gen.ant.AbstractCixsGenerator;
import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.gen.model.options.CobolHttpClientType;
import com.legstar.cixs.gen.model.options.HttpTransportParameters;
import com.legstar.cixs.gen.model.options.WmqTransportParameters;
import com.legstar.cixs.jaxws.gen.Cixs2JaxwsGenerator;
import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.CodeGenUtil;

/**
 * This class groups methods that are common to all generators.
 */
public abstract class AbstractCixsMuleGenerator extends AbstractCixsGenerator {

    /** This generator name. */
    public static final String CIXS_MULE_GENERATOR_NAME =
        "LegStar Mule Component generator";

    /** Velocity template for ant build zip. */
    public static final String COMPONENT_ANT_BUILD_ZIP_VLC_TEMPLATE =
        "vlc/cixsmule-component-ant-build-zip-xml.vm";

    /** Velocity template for service ant-deploy. */
    public static final String COMPONENT_ANT_DEPLOY_VLC_TEMPLATE =
        "vlc/cixsmule-component-ant-deploy-xml.vm";

    /** Velocity template for adapter mule configuration xml. */
    public static final String COMPONENT_ADAPTER_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-adapter-config-xml.vm";

    /** Velocity template for proxy mule configuration xml. */
    public static final String COMPONENT_PROXY_CONFIG_XML_VLC_TEMPLATE =
        "vlc/cixsmule-component-proxy-config-xml.vm";

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

    /** Velocity template for COBOL client generation. */
    public static final String OPERATION_COBOL_CICS_WMQ_CLIENT_VLC_TEMPLATE =
        "vlc/cixsmule-operation-cobol-cics-wmq-client.vm";

    /** The service model name is it appears in templates. */
    public static final String COMPONENT_MODEL_NAME = "muleComponent";
    
    /** The ant script name to create a zip archive.*/
    public static final String CREATE_ZIP_FILE_NAME = "build-zip.xml";
    
    /** The ant script name to deploy the zip archive.*/
    public static final String DEPLOY_ZIP_FILE_NAME = "deploy.xml";
    
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
     * Create the Mule adapter configuration file for java payloads.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @param sampleConfigurationFileName the configuration file name
     * @param transport the type of transport selected for the sample configuration
     * @param payloadType whether the sample configuration is for java or XML payloads
     * @param messagingType the type of messaging expected by the mainframe
     * @return the generated file name
     * @throws CodeGenMakeException if generation fails
     */
    public static String generateAdapterConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir,
            final String sampleConfigurationFileName,
            final SampleConfigurationTransport transport,
            final SampleConfigurationPayloadType payloadType,
            final SampleConfigurationHostMessagingType messagingType)
    throws CodeGenMakeException {

        parameters.put("sampleConfigurationTransport",
                transport.toString().toLowerCase(Locale.getDefault()));
        parameters.put("sampleConfigurationPayloadType",
                payloadType.toString().toLowerCase(Locale.getDefault()));
        parameters.put("sampleConfigurationHostMessagingType",
                messagingType.toString().toLowerCase(Locale.getDefault()));

        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ADAPTER_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                sampleConfigurationFileName);

        return sampleConfigurationFileName;
    }
    
    /**
     * Create the Mule proxy configuration XML file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentConfFilesDir where to store the generated file
     * @param sampleConfigurationFileName the configuration file name
     * @param transport the type of transport selected for the sample configuration
     * @return the generated file name
     * @throws CodeGenMakeException if generation fails
     */
    public static String generateProxyConfigXml(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentConfFilesDir,
            final String sampleConfigurationFileName,
            final SampleConfigurationTransport transport)
    throws CodeGenMakeException {

        parameters.put("sampleConfigurationTransport",
                transport.toString().toLowerCase(Locale.getDefault()));

        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_PROXY_CONFIG_XML_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentConfFilesDir,
                sampleConfigurationFileName);
        
        return sampleConfigurationFileName;
    }

    /**
     * Create the Mule Ant Build Zip file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param componentAntFilesDir where to store the generated file
     * @return the generated file name
     * @throws CodeGenMakeException if generation fails
     */
    public static String generateAntBuildZip(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File componentAntFilesDir)
    throws CodeGenMakeException {
        String fileName = CREATE_ZIP_FILE_NAME;
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ANT_BUILD_ZIP_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                componentAntFilesDir,
                fileName);
        return fileName;
    }

    /**
     * Create the deploy Ant Build file.
     * @param component the Mule component description
     * @param parameters miscellaneous help parameters
     * @param serviceAntFilesDir where to store the generated file
     * @return the generated file name
     * @throws CodeGenMakeException if generation fails
     */
    public static String generateAntDeploy(
            final CixsMuleComponent component,
            final Map < String, Object > parameters,
            final File serviceAntFilesDir)
    throws CodeGenMakeException {
        String fileName = "deploy.xml";
        generateFile(CIXS_MULE_GENERATOR_NAME,
                COMPONENT_ANT_DEPLOY_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                component,
                parameters,
                serviceAntFilesDir,
                fileName);
        return fileName;
    }
    /**
     * Create a COBOl CICS HTTP Client program to use for testing.
     * TODO Move to core LegStar
     * @param service the ESB service description
     * @param operation the operation for which a program is to be generated
     * @param parameters the set of parameters to pass to template engine
     * @param cobolFilesDir location where COBOL code should be generated
     * @param cobolHttpClientType the type of cobol http client to generate
     * @throws CodeGenMakeException if generation fails
     */
    protected static void generateCobolSampleHttpClient(
            final CixsMuleComponent service,
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File cobolFilesDir,
            final CobolHttpClientType cobolHttpClientType) throws CodeGenMakeException {
        String template;
        switch(cobolHttpClientType) {
        case DFHWBCLI:
            template = Cixs2JaxwsGenerator.OPERATION_COBOL_CICS_DFHWBCLI_CLIENT_VLC_TEMPLATE;
            break;
        case WEBAPI:
            template = Cixs2JaxwsGenerator.OPERATION_COBOL_CICS_WEBAPI_CLIENT_VLC_TEMPLATE;
            break;
        default:
            template = Cixs2JaxwsGenerator.OPERATION_COBOL_CICS_LSHTTAPI_CLIENT_VLC_TEMPLATE;
        }
        generateFile(CIXS_MULE_GENERATOR_NAME,
                template,
                COMPONENT_MODEL_NAME,
                service,
                parameters,
                cobolFilesDir,
                operation.getCicsProgramName() + ".cbl");
    }

    /**
     * Create a COBOl CICS WMQ Client program to use for testing.
     * TODO Move to core LegStar
     * @param service the proxy service description
     * @param operation the operation for which a program is to be generated
     * @param parameters the set of parameters to pass to template engine
     * @param cobolFilesDir location where COBOL code should be generated
     * @throws CodeGenMakeException if generation fails
     */
    protected static void generateCobolSampleWmqClient(
            final CixsMuleComponent service,
            final CixsOperation operation,
            final Map < String, Object > parameters,
            final File cobolFilesDir) throws CodeGenMakeException {
        generateFile(CIXS_MULE_GENERATOR_NAME,
                OPERATION_COBOL_CICS_WMQ_CLIENT_VLC_TEMPLATE,
                COMPONENT_MODEL_NAME,
                service,
                parameters,
                cobolFilesDir,
                operation.getCicsProgramName() + ".cbl");
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
        CodeGenUtil.checkDirectory(
                getTargetDistDir(), true, "TargetDistDir");

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

        /* Check sample transport parameters */
        if (getSampleConfigurationTransport().equalsIgnoreCase("http")) {
            if (getHttpTransportParameters().getPath() == null
                    || getHttpTransportParameters().getPath().length() == 0) {
                getHttpTransportParameters().setPath(getDefaultServiceHttpPath());
            }
            getHttpTransportParameters().check();
        }

        if (getSampleConfigurationTransport().equalsIgnoreCase("wmq")) {
            getWmqTransportParameters().check();
        }
        
        checkExtendedExtendedInput();

    }

    /**
     * @return a good default HTTPpath
     */
    public abstract String getDefaultServiceHttpPath();
    
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

        generateAntBuildZip(
                getCixsMuleComponent(), parameters, serviceAntFilesDir);
        generateAntDeploy(
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

        /* Add sample transport related parameters */
        switch(getSampleConfigurationTransportInternal()) {
        case HTTP:
            getAntModel().getHttpTransportParameters().add(parameters);
            // FIXME this code prevents any usage of the HTTPS scheme
			parameters.put("legstarHttpAddress", getAntModel()
					.getHttpTransportParameters().getUrl().replace("http:",
							"legstar:"));
            break;
        case WMQ:
            getAntModel().getWmqTransportParameters().add(parameters);
            break;
        default:
            break;
        }
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
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @return the transport used by generated samples.
     */
    protected SampleConfigurationTransport getSampleConfigurationTransportInternal() {
        return getAntModel().getSampleConfigurationTransport();
    }

    /**
     * @return the transport used by generated samples.
     */
    public String getSampleConfigurationTransport() {
        return getSampleConfigurationTransportInternal().toString();
    }

    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @param sampleConfigurationTransport the transport used by generated samples.
     */
    private void setSampleConfigurationTransportInternal(
            final SampleConfigurationTransport sampleConfigurationTransport) {
        getAntModel().setSampleConfigurationTransport(sampleConfigurationTransport);
    }

    /**
     * @param sampleConfigurationTransport the transport used by generated samples.
     */
    public void setSampleConfigurationTransport(
            final String sampleConfigurationTransport) {
        SampleConfigurationTransport value = SampleConfigurationTransport.valueOf(
                    sampleConfigurationTransport.toUpperCase(Locale.getDefault()));
        setSampleConfigurationTransportInternal(value);
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

    /**
     * @return the set of WMQ transport parameters
     */
    public WmqTransportParameters getWmqTransportParameters() {
        return getAntModel().getWmqTransportParameters();
    }

    /**
     * @param wmqTransportParameters the set of WMQ transport parameters
     */
    public void setWmqTransportParameters(
            final WmqTransportParameters wmqTransportParameters) {
        getAntModel().setWmqTransportParameters(wmqTransportParameters);
    }

    /**
     * @param wmqTransportParameters the set of WMQ transport parameters
     */
    public void addWmqTransportParameters(
            final WmqTransportParameters wmqTransportParameters) {
        getAntModel().setWmqTransportParameters(wmqTransportParameters);
    }

    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @return the host messaging used by generated service configuration samples.
     */
    protected SampleConfigurationHostMessagingType getSampleConfigurationHostMessagingTypeInternal() {
        return getAntModel().getSampleConfigurationHostMessagingType();
    }

    /**
     * @return the host messaging used by generated service configuration samples.
     */
    public String getSampleConfigurationHostMessagingType() {
        return getSampleConfigurationHostMessagingTypeInternal().toString();
    }

    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @param sampleConfigurationHostMessagingType the host messaging used by generated service configuration samples.
     */
    private void setSampleConfigurationHostMessagingTypeInternal(
            final SampleConfigurationHostMessagingType sampleConfigurationHostMessagingType) {
        getAntModel().setSampleConfigurationHostMessagingType(sampleConfigurationHostMessagingType);
    }

    /**
     * @param sampleConfigurationHostMessagingType the host messaging used by generated service configuration samples.
     */
    public void setSampleConfigurationHostMessagingType(
            final String sampleConfigurationHostMessagingType) {
        SampleConfigurationHostMessagingType value = SampleConfigurationHostMessagingType.valueOf(
                    sampleConfigurationHostMessagingType.toUpperCase(Locale.getDefault()));
        setSampleConfigurationHostMessagingTypeInternal(value);
    }

    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @return the payload type (serialized java object or XML) for samples.
     */
    protected SampleConfigurationPayloadType getSampleConfigurationPayloadTypeInternal() {
        return getAntModel().getSampleConfigurationPayloadType();
    }

    /**
     * @return the payload type (serialized java object or XML) for samples.
     */
    public String getSampleConfigurationPayloadType() {
        return getSampleConfigurationPayloadTypeInternal().toString();
    }

    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @param payload type (serialized java object or XML) for samples.
     */
    private void setSampleConfigurationPayloadTypeInternal(
            final SampleConfigurationPayloadType sampleConfigurationPayloadType) {
        getAntModel().setSampleConfigurationPayloadType(sampleConfigurationPayloadType);
    }

    /**
     * @param sampleConfigurationPayloadType the payload type (serialized java object or XML) for samples.
     */
	public void setSampleConfigurationPayloadType(
			final String sampleConfigurationPayloadType) {
		setSampleConfigurationPayloadTypeInternal(SampleConfigurationPayloadType
				.valueOf(sampleConfigurationPayloadType));
	}

    /**
     * @return the sample configuration file name
     */
    public String getSampleConfigurationFileName() {
		return getAntModel().getSampleConfigurationFileName();
	}

	/**
	 * @param sampleConfigurationFileName sample configuration file name
	 */
	public void setSampleConfigurationFileName(
			final String sampleConfigurationFileName) {
		getAntModel().setSampleConfigurationFileName(sampleConfigurationFileName);
	}

}
