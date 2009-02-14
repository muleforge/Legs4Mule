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

import org.mule.providers.legstar.model.AntBuildMule2CixsModel;

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
        try {
            CodeGenUtil.checkDirectory(
                    getTargetPropDir(), true, "TargetPropDir");
        } catch (IllegalArgumentException e) {
            throw new CodeGenMakeException(e);
        }
    }

    /**
     * Create all artifacts for Mule adapter service.
     * @param parameters a predefined set of parameters useful for generation
     * @throws CodeGenMakeException if generation fails
     */
    public final void generateExtended(
            final Map < String, Object > parameters) throws CodeGenMakeException {

        /* Determine target files locations */
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                getTargetSrcDir(), getCixsMuleComponent().getPackageName(), true);
        File componentConfFilesDir = getTargetMuleConfigDir();
        File operationPropertiesFilesDir = getTargetPropDir();


        /* Produce artifacts for standalone component */
        generateAdapterCallableInvoker(
                getCixsMuleComponent(), parameters, componentClassFilesDir);
        generateAdapterStandaloneConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir);

        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations())
        {

            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);

            generateHolders(
                    operation, parameters, operationClassFilesDir);
            Jaxws2CixsGenerator.generateProgramProperties(
                    operation, parameters, operationPropertiesFilesDir);
            generateProgramInvoker(
                    operation, parameters, operationClassFilesDir);

        }

        /* Produce artifacts for bridge component  */
        generateAdapterBridgeConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir);

        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations())
        {
            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);

            generateHbaToObjectTransformers(
                    operation, parameters, operationClassFilesDir);
            generateObjectToHbaTransformers(
                    operation, parameters, operationClassFilesDir);

        }

    }

    /**
     * @return the URI that the host exposes to consumers
     */
    public final String getHostURI() {
        return ((AntBuildMule2CixsModel) getAntModel()).getHostURI();
    }

    /**
     * @param hostURI the URI that the host exposes to consumers to set
     */
    public final void setHostURI(final String hostURI) {
        ((AntBuildMule2CixsModel) getAntModel()).setHostURI(hostURI);
    }

    /** {@inheritDoc} */
    public void addExtendedParameters(final Map < String, Object > parameters) {
        parameters.put("generationTarget", GENERATION_TARGET);
        parameters.put("hostURI", getHostURI());
    }

}
