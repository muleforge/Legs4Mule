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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.mule.providers.legstar.model.CixsMuleComponent;

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
        mGenerator.setTargetPropDir(GEN_PROP_DIR);
        mGenerator.setTargetMuleConfigDir(GEN_CONF_DIR);
        mGenerator.setTargetSrcDir(GEN_SRC_DIR);
        mGenerator.setTargetPropDir(GEN_PROP_DIR);
        mGenerator.setTargetJarDir(GEN_JAR_DIR);
        mGenerator.setJaxbBinDir(JAXB_BIN_DIR);
        mGenerator.setTargetBinDir(GEN_BIN_DIR);
        mGenerator.setHostCharset(HOSTCHARSET);
        mGenerator.setHostURI(LEGSTAR_HOST_URI);

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
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:"
                    + " TargetPropDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetPropDir(GEN_PROP_DIR);
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
                "build.xml", muleComponent.getInterfaceClassName());
        compare(mGenerator.getTargetMuleConfigDir(),
                "mule-adapter-http-config-" + muleComponent.getName() + ".xml");
        
        for (CixsOperation operation : muleComponent.getCixsOperations()) {
            
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    mGenerator.getTargetSrcDir(), operation.getPackageName(), false);
            
            compare(mGenerator.getTargetPropDir(),
                    operation.getCicsProgramName().toLowerCase(
                            Locale.getDefault()) + ".properties",
                    muleComponent.getInterfaceClassName());

            compare(operationClassFilesDir,
                    "HostByteArrayTo" + operation.getRequestHolderType() + ".java",
                    muleComponent.getInterfaceClassName());
            compare(operationClassFilesDir,
                    operation.getRequestHolderType() + "ToHostByteArray.java",
                    muleComponent.getInterfaceClassName());
            compare(operationClassFilesDir,
                    "HostByteArrayTo" + operation.getResponseHolderType() + ".java",
                    muleComponent.getInterfaceClassName());
            compare(operationClassFilesDir,
                    operation.getResponseHolderType() + "ToHostByteArray.java",
                    muleComponent.getInterfaceClassName());
        }
    }

}
