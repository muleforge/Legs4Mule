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
 * Test cases for transformers velocity templates.
 */
public class TransformersTemplatesTest extends AbstractTestTemplate {

    /**
     * Case LSFILEAE.
     * @throws Exception if something goes wrong
     */
    public void testLsfileaeToHostByteArray() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateObjectToHbaTransformer(
                operation, getParameters(), componentClassFilesDir,
                "Dfhcommarea", "Request");
        compare(componentClassFilesDir,
                operation.getRequestHolderType() + "ToHostByteArray.java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testLsfileacToHostByteArray() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateObjectToHbaTransformer(
                operation, getParameters(), componentClassFilesDir,
                "LsfileacRequestHolder", "Request");
        compare(componentClassFilesDir,
                operation.getRequestHolderType() + "ToHostByteArray.java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case JVMQuery.
     * @throws Exception if something goes wrong
     */
    public void testJvmQueryToHostByteArray() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        CodeGenUtil.checkDirectory(componentClassFilesDir, true);
        AbstractCixsMuleGenerator.generateObjectToHbaTransformer(
                operation, getParameters(), componentClassFilesDir,
                operation.getRequestHolderType(), "Request");
        compare(componentClassFilesDir,
                operation.getRequestHolderType() + "ToHostByteArray.java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case LSFILEAE.
     * @throws Exception if something goes wrong
     */
    public void testHostByteArrayToLsfileae() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateHbaToObjectTransformer(
                operation, getParameters(), componentClassFilesDir,
                "Dfhcommarea", "Request");
        compare(componentClassFilesDir,
                "HostByteArrayTo" + operation.getRequestHolderType() + ".java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testHostByteArrayToLsfileac() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateHbaToObjectTransformer(
                operation, getParameters(), componentClassFilesDir,
                "LsfileacRequestHolder", "Request");
        compare(componentClassFilesDir,
                "HostByteArrayTo" + operation.getRequestHolderType() + ".java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case JVMQuery.
     * @throws Exception if something goes wrong
     */
    public void testHostByteArrayToJvmQuery() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateHbaToObjectTransformer(
                operation, getParameters(), componentClassFilesDir,
                operation.getRequestHolderType(), "Request");
        compare(componentClassFilesDir,
                "HostByteArrayTo" + operation.getRequestHolderType() + ".java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case LSFILEAE.
     * @throws Exception if something goes wrong
     */
    public void testLsfileaeToHttpResponse() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), componentClassFilesDir,
                "Dfhcommarea", "Request");
        compare(componentClassFilesDir,
                operation.getRequestHolderType() + "ToHttpResponse.java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testLsfileacToHttpResponse() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), componentClassFilesDir,
                "LsfileacRequestHolder", "Request");
        compare(componentClassFilesDir,
                operation.getRequestHolderType() + "ToHttpResponse.java",
                muleComponent.getInterfaceClassName());
    }

    /**
     * Case JvmQuery.
     * @throws Exception if something goes wrong
     */
    public void testJvmQueryToHttpResponse() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        AbstractCixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), componentClassFilesDir,
                operation.getRequestHolderType(), "Request");
        compare(componentClassFilesDir,
                operation.getRequestHolderType() + "ToHttpResponse.java",
                muleComponent.getInterfaceClassName());
    }

}
