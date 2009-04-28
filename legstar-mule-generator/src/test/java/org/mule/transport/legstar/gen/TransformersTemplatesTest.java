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
import com.legstar.cixs.jaxws.gen.Jaxws2CixsGenerator;
import com.legstar.cixs.jaxws.model.WebServiceParameters;
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
    public void testLsfileaeXmlToHostMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateXmlToHostTransformer(
                operation, getParameters(), transformersDir,
                "Dfhcommarea", "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "XmlToHostMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testLsfileacXmlToHostMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateXmlToHostTransformer(
                operation, getParameters(), transformersDir,
                "LsfileacRequestHolder", "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "XmlToHostMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case JVMQuery.
     * @throws Exception if something goes wrong
     */
    public void testJvmQueryXmlToHostMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        CodeGenUtil.checkDirectory(transformersDir, true);
        AbstractCixsMuleGenerator.generateXmlToHostTransformer(
                operation, getParameters(), transformersDir,
                operation.getRequestHolderType(), "Request");
        compare(transformersDir,
                operation.getRequestHolderType() + "XmlToHostMuleTransformer.java",
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
    public void testHostToLsfileaeXmlMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateHostToXmlTransformer(
                operation, getParameters(), transformersDir,
                "Dfhcommarea", "Request");
        compare(transformersDir,
                "HostTo" + operation.getRequestHolderType() + "XmlMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case LSFILEAC.
     * @throws Exception if something goes wrong
     */
    public void testHostToLsfileacXmlMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        getParameters().put(WebServiceParameters.WSDL_TARGET_NAMESPACE_PROPERTY,
                Jaxws2CixsGenerator.DEFAULT_WSDL_TARGET_NAMESPACE_PREFIX
                + '/' + muleComponent.getName());
        AbstractCixsMuleGenerator.generateHostToXmlTransformer(
                operation, getParameters(), transformersDir,
                "LsfileacRequestHolder", "Request");
        compare(transformersDir,
                "HostTo" + operation.getRequestHolderType() + "XmlMuleTransformer.java",
                muleComponent.getName());
    }

    /**
     * Case JVMQuery.
     * @throws Exception if something goes wrong
     */
    public void testHostToJvmQueryXmlMuleTransformer() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        File transformersDir = getTransformersDir(muleComponent);
        AbstractCixsMuleGenerator.generateHostToXmlTransformer(
                operation, getParameters(), transformersDir,
                operation.getRequestHolderType(), "Request");
        compare(transformersDir,
                "HostTo" + operation.getRequestHolderType() + "XmlMuleTransformer.java",
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
