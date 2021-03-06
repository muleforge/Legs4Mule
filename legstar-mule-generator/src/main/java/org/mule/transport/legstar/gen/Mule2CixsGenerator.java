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

import org.mule.transport.legstar.model.AntBuildMule2CixsModel;
import org.mule.transport.legstar.model.options.TcpTransportParameters;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.jaxws.gen.Jaxws2CixsGenerator;
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
     * This generator produces an adapter.
     */
    private static final String GENERATION_TARGET = "adapter";

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
    public final void checkExtendedExtendedInput() throws CodeGenMakeException {
        if (getSampleConfigurationTransport().equalsIgnoreCase("tcp")) {
            getTcpTransportParameters().check();
        }
    }

    /** {@inheritDoc} */
    public void addExtendedParameters(final Map < String, Object > parameters) {
        parameters.put("generationTarget", GENERATION_TARGET);
        if (getSampleConfigurationTransport().equalsIgnoreCase("tcp")) {
            getAntModel().getTcpTransportParameters().add(parameters);
        }

    }

    /**
     * Create all artifacts for Mule adapter service.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public final void generateExtended(
            final Map < String, Object > parameters) throws CodeGenMakeException {

        /* Produce mule configuration samples  */

        /* Determine target files locations */
        File componentConfFilesDir = getTargetMuleConfigDir();

        generateAdapterConfigXml(
                getCixsMuleComponent(),
                parameters,
                componentConfFilesDir,
                getSampleConfigurationFileName(),
                getSampleConfigurationTransportInternal(),
                getSampleConfigurationPayloadTypeInternal(),
                getSampleConfigurationHostMessagingTypeInternal());

        /* Produce transformers and holders classes  */
        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations())
        {
            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(),
                    operation.getPackageName(),
                    true);

            Jaxws2CixsGenerator.generateHolders(
                    operation, parameters, operationClassFilesDir);

            switch (getSampleConfigurationPayloadTypeInternal()) {
            case JAVA:
                generateHostToJavaTransformers(operation, parameters,
                        operationClassFilesDir);
                generateJavaToHostTransformers(operation, parameters,
                        operationClassFilesDir);
                break;
            case XML:
                generateHostToXmlTransformers(operation, parameters,
                        operationClassFilesDir);
                generateXmlToHostTransformers(operation, parameters,
                        operationClassFilesDir);
                break;
            default:
                break;
            }
        }

    }

    /**
     * Generate default values where they are missing in the model. This
     * will reduce the amount of code in the velocity templates.
     */
    protected void completeModel() {
        /* Some artifacts like holders have XML markup. They need an XML namespace */
        getCixsService().setNamespace(
                Jaxws2CixsGenerator.DEFAULT_WSDL_TARGET_NAMESPACE_PREFIX
                + '/' + getCixsService().getName());

        for (CixsOperation operation : getCixsService().getCixsOperations()) {
            if (operation.getPackageName() == null 
                    || operation.getPackageName().length() == 0) {
                operation.setPackageName(getCixsService().getPackageName());
            }
            if (operation.getNamespace() == null 
                    || operation.getNamespace().length() == 0) {
                operation.setNamespace(getCixsService().getNamespace());
            }
        }
    }

    /**
     * @return a good default path that the host could use to reach
     *  the generated service adapter
     */
    public final String getDefaultServiceHttpPath() {
        return AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH;
    }

    /**
     * @return the set of WMQ transport parameters
     */
    public TcpTransportParameters getTcpTransportParameters() {
        return getAntModel().getTcpTransportParameters();
    }

    /**
     * @param tcpTransportParameters the set of TCP transport parameters
     */
    public void setTcpTransportParameters(
            final TcpTransportParameters tcpTransportParameters) {
        getAntModel().setTcpTransportParameters(tcpTransportParameters);
    }

    /**
     * @param tcpTransportParameters the set of TCP transport parameters
     */
    public void addTcpTransportParameters(
            final TcpTransportParameters tcpTransportParameters) {
        getAntModel().setTcpTransportParameters(tcpTransportParameters);
    }

    /**
     * @return the model representing all generation parameters
     */
    public AntBuildMule2CixsModel getAntModel() {
        return (AntBuildMule2CixsModel) super.getAntModel();
    }
}
