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

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.codegen.CodeGenUtil;

/**
 * Test the generation of a a callable invoker class for Adapters.
 */
public class CallableInvokerVelocityTemplatesTest extends AbstractTestTemplate {

    /**
     * Case of a commarea driven target program.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {
        generateCheck(Samples.getLsfileaeMuleComponent());
    }

    /**
     * Case where input commarea is different from output commarea.
     * @throws Exception if test fails
     */
    public void testLsfileal() throws Exception {
        generateCheck(Samples.getLsfilealMuleComponent());
    }

    /**
     * Case where input commarea is different from output commarea.
     * @throws Exception if test fails
     */
    public void testLsfileac() throws Exception {
        generateCheck(Samples.getLsfileacMuleComponent());
    }

    /**
     * Case where where we have multiple methods.
     * @throws Exception if test fails
     */
    public void testLsfileax() throws Exception {
        generateCheck(Samples.getLsfileaxMuleComponent());
    }

    /**
     * Produce the artifact and test that it matches the expected content.
     * @param service the service object
     * @throws Exception if test fails
     */
    private void generateCheck(final CixsMuleComponent service) throws Exception {

        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, service.getPackageName(), true);
        AbstractCixsMuleGenerator.generateAdapterCallableInvoker(
                service, getParameters(), componentClassFilesDir);
        compare(componentClassFilesDir,
                service.getInterfaceClassName() + "Callable.java");
    }
}
