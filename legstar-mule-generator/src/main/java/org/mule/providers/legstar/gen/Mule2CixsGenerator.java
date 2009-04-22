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

            /* Check that we have a URI to expose to mainframe programs */
            getHttpTransportParameters().check();

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
        File componentConfFilesDir = getTargetMuleConfigDir();
        File operationPropertiesFilesDir = getTargetPropDir();


        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations())
        {

            /* Determine target files locations */
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(), operation.getPackageName(), true);

            Jaxws2CixsGenerator.generateHolders(
                    operation, parameters, operationClassFilesDir);
            Jaxws2CixsGenerator.generateProgramProperties(
                    operation, parameters, operationPropertiesFilesDir);

        }

        /* Produce artifacts for bridge component  */
        generateAdapterHttpConfigXml(
                getCixsMuleComponent(), parameters, componentConfFilesDir);

        for (CixsOperation operation : getCixsMuleComponent().getCixsOperations())
        {
            /* Determine target files locations */
            File transformersDir = CodeGenUtil.classFilesLocation(
                    getTargetSrcDir(),
                    operation.getPackageName(),
                    true);

            generateHostToJavaTransformers(
                    operation, parameters, transformersDir);
            generateJavaToHostTransformers(
                    operation, parameters, transformersDir);

        }

    }

    /** {@inheritDoc} */
    public void addExtendedParameters(final Map < String, Object > parameters) {
        parameters.put("generationTarget", GENERATION_TARGET);
        getAntModel().getHttpTransportParameters().add(parameters);
    }

}
