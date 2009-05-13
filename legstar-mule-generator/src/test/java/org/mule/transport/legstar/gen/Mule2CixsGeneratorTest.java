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
import java.util.ArrayList;
import java.util.List;

import org.mule.transport.legstar.model.CixsMuleComponent;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationPayloadType;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for CixsMuleGenerator.
 */
public class Mule2CixsGeneratorTest extends AbstractTestTemplate {

    /** Generator instance. */
    private Mule2CixsGenerator mGenerator;

    /** @{inheritDoc}*/
    public void setUp() {
        emptyDir(GEN_DIR);
        mGenerator = new Mule2CixsGenerator();
        mGenerator.init();
    }

    /**
     * Common initialization. Segregate output so that various tests
     * do not overwrite each other.
     * @param muleComponent the service
     */
    private void initCixsMuleComponent(final CixsMuleComponent muleComponent) {
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_ANT_DIR);
        mGenerator.setTargetMuleConfigDir(GEN_CONF_DIR);
        mGenerator.setTargetSrcDir(GEN_SRC_DIR);
        mGenerator.setTargetJarDir(GEN_JAR_DIR);
        mGenerator.setJaxbBinDir(JAXB_BIN_DIR);
        mGenerator.setTargetBinDir(GEN_BIN_DIR);
        mGenerator.setHostCharset(HOSTCHARSET);
        mGenerator.getHttpTransportParameters().setHost("mainframe");
        mGenerator.getHttpTransportParameters().setPort(4081);

        /* We want to share expected results with XmlTemplatesTest */
        List < String > pathElements = new ArrayList < String >();
        pathElements.add("c:/some.additional.jar");
        muleComponent.setMuleStartupPathElements(pathElements);
    }

    /**
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        Mule2CixsGenerator generator = new Mule2CixsGenerator();
        try {
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:"
                    + " JaxbBinDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        try {
            generator.setJaxbBinDir(new File("target/classes"));
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("You must specify a service description",
                    e.getCause().getMessage());
        }
        try {
            generator.setCixsMuleComponent(muleComponent);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("You must provide a service name",
                    e.getCause().getMessage());
        }
        try {
            muleComponent.setName("muleComponentName");
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetSrcDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetSrcDir(GEN_SRC_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetAntDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetAntDir(GEN_ANT_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetMuleConfigDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetMuleConfigDir(GEN_CONF_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetBinDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetBinDir(GEN_BIN_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetJarDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetJarDir(GEN_JAR_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("No operation was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.getCixsMuleComponent().addCixsOperation(
                    Samples.getLsfileaeMuleComponent().getCixsOperations().get(0));
            generator.execute();
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /**
     * Check generation for operation with identical input and output structures.
     * @throws Exception if generation fails
     */
    public final void testLsfileaeGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkResults(muleComponent);
    }

    /**
     * Check generation for operation with different input and output structures.
     * @throws Exception if generation fails
     */
    public final void testLsfilealGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfilealMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkResults(muleComponent);
    }

    /**
     * Check generation for CICS containers target component.
     * @throws Exception if generation fails
     */
    public final void testLsfileacGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkResults(muleComponent);
    }


    /**
     * Check generation for multiple operations components.
     * @throws Exception if generation fails
     */
    public final void testLsfileaxGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileaxMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkResults(muleComponent);
    }

    /**
     * Check that all expected artifacts are generated.
     * @param muleComponent the service model
     */
    private void checkResults(final CixsMuleComponent muleComponent) {
        
        compare(mGenerator.getTargetAntDir(),
                "build.xml", muleComponent.getName());
        
        String congFileName = AbstractCixsMuleGenerator.getAdapterConfigurationFileName(
                muleComponent.getName(),
                mGenerator.getSampleConfigurationTransportInternal(),
                SampleConfigurationPayloadType.JAVA,
                mGenerator.getSampleConfigurationHostMessagingTypeInternal());
        compare(mGenerator.getTargetMuleConfigDir(),
                congFileName,
                muleComponent.getName());
        
        congFileName = AbstractCixsMuleGenerator.getAdapterConfigurationFileName(
                muleComponent.getName(),
                mGenerator.getSampleConfigurationTransportInternal(),
                SampleConfigurationPayloadType.XML,
                mGenerator.getSampleConfigurationHostMessagingTypeInternal());
        compare(mGenerator.getTargetMuleConfigDir(),
                congFileName,
                muleComponent.getName());
        
        for (CixsOperation operation : muleComponent.getCixsOperations()) {
            
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    mGenerator.getTargetSrcDir(), operation.getPackageName(), false);
            
            compare(operationClassFilesDir,
                    "HostTo" + operation.getRequestHolderType() + "MuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getRequestHolderType() + "ToHostMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    "HostTo" + operation.getResponseHolderType() + "MuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getResponseHolderType() + "ToHostMuleTransformer.java",
                    muleComponent.getName());

            compare(operationClassFilesDir,
                    "HostTo" + operation.getRequestHolderType() + "XmlMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getRequestHolderType() + "XmlToHostMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    "HostTo" + operation.getResponseHolderType() + "XmlMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getResponseHolderType() + "XmlToHostMuleTransformer.java",
                    muleComponent.getName());
        }
    }

}
