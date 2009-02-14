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

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test the generation of a an operation invoker class for Adapters.
 */
public class ProgramInvokerVelocityTemplatesTest extends AbstractTestTemplate {

    /**
     * Case of a commarea driven target program.
     * @throws Exception if test fails
     */
    public void testLsfileae() throws Exception {

        CixsMuleComponent service = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = service.getCixsOperations().get(0);

        File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, operation.getPackageName(), true);
        AbstractCixsMuleGenerator.generateProgramInvoker(
                operation, getParameters(), operationClassFilesDir);
        compare(operationClassFilesDir,
                operation.getClassName() + "ProgramInvoker.java",
                service.getInterfaceClassName());
    }

    /**
     * Case where input commarea is different from output commarea.
     * @throws Exception if test fails
     */
    public void testLsfileal() throws Exception {

        CixsMuleComponent service = Samples.getLsfilealMuleComponent();
        CixsOperation operation = service.getCixsOperations().get(0);

        File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, operation.getPackageName(), true);
        AbstractCixsMuleGenerator.generateProgramInvoker(
                operation, getParameters(), operationClassFilesDir);
        compare(operationClassFilesDir,
                operation.getClassName() + "ProgramInvoker.java",
                service.getInterfaceClassName());
    }

    /**
     * Case where input commarea is different from output commarea.
     * @throws Exception if test fails
     */
    public void testLsfileac() throws Exception {

        CixsMuleComponent service = Samples.getLsfileacMuleComponent();
        CixsOperation operation = service.getCixsOperations().get(0);

        File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, operation.getPackageName(), true);
        AbstractCixsMuleGenerator.generateProgramInvoker(
                operation, getParameters(), operationClassFilesDir);
        compare(operationClassFilesDir,
                operation.getClassName() + "ProgramInvoker.java",
                service.getInterfaceClassName());
    }

}
