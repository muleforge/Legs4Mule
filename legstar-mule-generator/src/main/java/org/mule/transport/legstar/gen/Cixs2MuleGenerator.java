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

import org.mule.transport.legstar.model.AntBuildCixs2MuleModel;
import org.mule.transport.legstar.model.CixsMuleComponent;
import org.mule.transport.legstar.model.UmoComponentParameters;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.jaxws.gen.StructuresGenerator;
import com.legstar.cixs.gen.model.options.CobolHttpClientType;
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
     * This generator produces an adapter.
     */
    private static final String GENERATION_TARGET = "proxy";

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
    public final void checkExtendedExtendedInput() throws CodeGenMakeException {
        try {
            /* We need a target location for generated sample COBOL client */
            CodeGenUtil.checkDirectory(
                    getTargetCobolDir(), true, "TargetCobolDir");
            
            /* Check parameters needed depending on target type */
            getUmoComponentTargetParameters().check();

            /* Check that there is exactly one operation.
             * TODO: Add support for multiple operations.  */
            if (getCixsMuleComponent().getCixsOperations().size() != 1) {
                throw new CodeGenMakeException(
                "One, and only one, operation per service supported");
            }
        } catch (IllegalArgumentException e) {
            throw new CodeGenMakeException(e);
        }
    }

    /**
     * Create all artifacts for Mule proxy service.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public final void generateExtended(
            final Map < String, Object > parameters) throws CodeGenMakeException {
        

        /* Determine target files locations */
        File componentConfFilesDir = getTargetMuleConfigDir();
        
        /* Produce sample configurations  */
        generateProxyConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir,
                getSampleConfigurationTransportInternal());
        
        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations()) {

            parameters.put("cixsOperation", operation);

            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);
            
            generateHostToJavaTransformers(
                    operation, parameters, operationClassFilesDir);
            generateJavaToHostTransformers(
                    operation, parameters, operationClassFilesDir);

            generateHostToXmlTransformers(
                    operation, parameters, operationClassFilesDir);
            generateXmlToHostTransformers(
                    operation, parameters, operationClassFilesDir);

            /* Generate sample COBOL clients. */
            switch(getSampleConfigurationTransportInternal()) {
            case HTTP:
                generateCobolSampleHttpClient(
                        getCixsMuleComponent(), operation, parameters,
                        getTargetCobolDir(),
                        getSampleCobolHttpClientTypeInternal());
                break;
            case WMQ:
                generateCobolSampleWmqClient(
                        getCixsMuleComponent(), operation, parameters,
                        getTargetCobolDir());
                break;
            default:
                break;
            }
        }
        
    }
    
    /** {@inheritDoc}*/
    public void addExtendedParameters(final Map < String, Object > parameters) {
 
        parameters.put("generationTarget", GENERATION_TARGET);
        /* This is needed to generated COBOL structures. */
        parameters.put("structHelper", new StructuresGenerator());

        getAntModel().getUmoComponentTargetParameters().add(parameters);

    }

    /**
     * @return a good default path that the host could use to reach
     *  the generated service proxy
     */
    public final String getDefaultServiceHttpPath() {
        
        return AntBuildCixs2MuleModel.DEFAULT_SERVER_PATH_TEMPLATE.replace(
                "${service.name}", getCixsMuleComponent().getName());
    }
 
    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @return the Http Cobol Client Type in use.
     */
    protected CobolHttpClientType getSampleCobolHttpClientTypeInternal() {
        return getAntModel().getSampleCobolHttpClientType();
    }

    /**
     * @return the Http Cobol Client Type in use.
     */
    public String getSampleCobolHttpClientType() {
        return getSampleCobolHttpClientTypeInternal().toString();
    }

    /**
     * When ant 1.7.0 will become widespread, we will be able to expose
     * this method directly (support for enum JDK 1.5).
     * @param sampleCobolHttpClientType the Http Cobol Client Type in use.
     */
    private void setSampleCobolHttpClientTypeInternal(
            final CobolHttpClientType sampleCobolHttpClientType) {
        getAntModel().setSampleCobolHttpClientType(sampleCobolHttpClientType);
    }

    /**
     * @param sampleCobolHttpClientType the Http Cobol Client Type in use.
     */
    public void setSampleCobolHttpClientType(final String sampleCobolHttpClientType) {
        CobolHttpClientType value = CobolHttpClientType.valueOf(
                    sampleCobolHttpClientType.toUpperCase(Locale.getDefault()));
        setSampleCobolHttpClientTypeInternal(value);
    }

    /**
     * @return the directory where COBOL files will be created
     */
    public final File getTargetCobolDir() {
        return ((AntBuildCixs2MuleModel) getAntModel()).getTargetCobolDir();
    }

    /**
     * @param targetCobolDir the directory where COBOL files will be created to set
     */
    public final void setTargetCobolDir(final File targetCobolDir) {
        ((AntBuildCixs2MuleModel) getAntModel()).setTargetCobolDir(targetCobolDir);
    }

    /**
     * @return the set of parameters needed to invoke an UMO Component
     */
    public UmoComponentParameters getUmoComponentTargetParameters() {
        return getAntModel().getUmoComponentTargetParameters();
    }

    /**
     * @param umoComponentTargetParameters the set of parameters needed to invoke an UMO Component to set
     */
    public void setUmoComponentTargetParameters(
            final UmoComponentParameters umoComponentTargetParameters) {
        getAntModel().setUmoComponentTargetParameters(umoComponentTargetParameters);
    }

    /**
     * @param umoComponentTargetParameters the set of parameters needed to invoke an UMO Component to set
     */
    public void addUmoComponentTargetParameters(
            final UmoComponentParameters umoComponentTargetParameters) {
        getAntModel().setUmoComponentTargetParameters(umoComponentTargetParameters);
    }

    /** {@inheritDoc}  */
    public final AntBuildCixs2MuleModel getAntModel() {
        return (AntBuildCixs2MuleModel) super.getAntModel();
    }
    
}
