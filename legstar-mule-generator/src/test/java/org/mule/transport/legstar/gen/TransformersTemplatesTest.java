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

import org.mule.transport.legstar.model.CixsMuleComponent;

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
    public void testLsfileaeToHostMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateJavaToHostTransformer(
                operation, getParameters(), transformersDir,
                "Dfhcommarea", "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "ToHostMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testLsfileacToHostMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateJavaToHostTransformer(
                operation, getParameters(), transformersDir,
                "LsfileacRequestHolder", "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "ToHostMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case JVMQuery.
     * @throws Exception if something goes wrong
     */
    public void testJvmQueryToHostMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        CodeGenUtil.checkDirectory(transformersDir, true);
        AbstractCixsMuleGenerator.generateJavaToHostTransformer(
                operation, getParameters(), transformersDir,
                operation.getRequestHolderType(), "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "ToHostMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAE.
     * @throws Exception if something goes wrong
     */
    public void testHostToLsfileaeMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateHostToJavaTransformer(
                operation, getParameters(), transformersDir,
                "Dfhcommarea", "Request");
        compare(transformersDir,
                "HostTo" + operation.getRequestHolderType() + "MuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testHostToLsfileacMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateHostToJavaTransformer(
                operation, getParameters(), transformersDir,
                "LsfileacRequestHolder", "Request");
        compare(transformersDir,
                "HostTo" + operation.getRequestHolderType() + "MuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case JVMQuery.
     * @throws Exception if something goes wrong
     */
    public void testHostToJvmQueryMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateHostToJavaTransformer(
                operation, getParameters(), transformersDir,
                operation.getRequestHolderType(), "Request");
        compare(transformersDir,
                "HostTo" + operation.getRequestHolderType() + "MuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAE.
     * @throws Exception if something goes wrong
     */
    public void testLsfileaeToHttpResponse() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), transformersDir,
                "Dfhcommarea", "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "ToHttpResponse.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testLsfileacToHttpResponse() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), transformersDir,
                "LsfileacRequestHolder", "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "ToHttpResponse.java",
                muleComponent.getName());
    }

    /**
     * Case JvmQuery.
     * @throws Exception if something goes wrong
     */
    public void testJvmQueryToHttpResponse() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), transformersDir,
                operation.getRequestHolderType(), "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "ToHttpResponse.java",
                muleComponent.getName());
    }
    
    /**
     * 
     * @param muleComponent the mule component
     * @return the generated transformers class files location
     */
    private File getTransformersDir(final CixsMuleComponent muleComponent) {
        return CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR,
                muleComponent.getPackageName(),
                true);
    }

}
