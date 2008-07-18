/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.legstar.gen;

import java.io.File;

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for CixsMuleGenerator.
 */
public class Mule2CixsGeneratorTest extends AbstractTestTemplate {

    private Mule2CixsGenerator mGenerator;
 
    /** The host character set. */
    public static final String HOSTCHARSET = "IBM01140";

    public void setUp() {
        mGenerator = new Mule2CixsGenerator();
        mGenerator.init();
        mGenerator.setTargetSrcDir(GEN_SRC_DIR);
        mGenerator.setTargetPropDir(GEN_PROP_DIR);
        mGenerator.setTargetJarDir(GEN_JAR_DIR);
        mGenerator.setJaxbBinDir(JAXB_BIN_DIR);
        mGenerator.setTargetBinDir(GEN_BIN_DIR);
        mGenerator.setHostCharset(HOSTCHARSET);
        mGenerator.setHostURI("http://192.168.0.110:4081");
    }

    /**
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        Mule2CixsGenerator generator = new Mule2CixsGenerator();
        try {
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
                    " JaxbBinDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        try {
            generator.setJaxbBinDir(new File("target/classes"));
            generator.execute();
        } catch (Exception e) {
            assertEquals("You must specify a service description",
                    e.getCause().getMessage());
        }
        try {
            generator.setCixsMuleComponent(muleComponent);
            generator.execute();
        } catch (Exception e) {
            assertEquals("You must provide a service name",
                    e.getCause().getMessage());
        }
        try {
            muleComponent.setName("muleComponentName");
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
            		" TargetSrcDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetSrcDir(GEN_SRC_DIR);
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
                    " TargetAntDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetSrcDir(GEN_SRC_DIR);
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
                    " TargetAntDir: No directory name was specified",
                    e.getCause().getMessage());
        }

    }

    private void initCixsMuleComponent(CixsMuleComponent muleComponent) {
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(
                new File(GEN_ANT_DIR, muleComponent.getName()));
        mGenerator.setTargetPropDir(
                new File(GEN_PROP_DIR, muleComponent.getName()));
        mGenerator.setTargetMuleConfigDir(
                new File(GEN_CONF_DIR, muleComponent.getName()));
    }

    /**
     * Check generation when no operations are specified.
     * @throws Exception if generation fails
     */
    public final void testGenerateClassesNoOperations() throws Exception {
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("muleComponentName");
        muleComponent.setInterfaceClassName("MuleComponent");
        muleComponent.setImplementationClassName("MuleComponentImpl");
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        String resStr = getSource(GEN_SRC_DIR, "MuleComponent.java");
        assertTrue(resStr.contains("public interface MuleComponent {"));

    }

    /**
     * Check generation for operation with identical input and output structures.
     * @throws Exception if generation fails
     */
    public final void testLsfileaeGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkStandaloneResults(muleComponent);
        checkBridgeResults(muleComponent);
    }

    /**
     * Check generation for operation with different input and output structures.
     * @throws Exception if generation fails
     */
    public final void testgetLsfilealGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfilealMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkStandaloneResults(muleComponent);
        checkBridgeResults(muleComponent);
    }

    /**
     * Check generation for CICS containers target component.
     * @throws Exception if generation fails
     */
    public final void testLsfileacGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkStandaloneResults(muleComponent);
        checkBridgeResults(muleComponent);
    }

    /**
     * Check generation for multiple operations components.
     * @throws Exception if generation fails
     */
    public final void testLsfileaxGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileaxMuleComponent();

        File componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();

        String resStr = getSource(componentClassFilesLocation, 
                muleComponent.getInterfaceClassName() + ".java");
        assertTrue(resStr.contains(
        "public interface Lsfileax {"));

        resStr = getSource(componentClassFilesLocation, 
                muleComponent.getCixsOperations().get(0).getFaultType() + ".java");
        assertTrue(resStr.contains(
        "public class LsfileaeException extends Exception {"));

        resStr = getSource(componentClassFilesLocation, 
                muleComponent.getCixsOperations().get(1).getFaultType() + ".java");
        assertTrue(resStr.contains(
        "public class LsfileacException extends Exception {"));

        resStr = getSource(componentClassFilesLocation, 
                muleComponent.getImplementationClassName() + ".java");
        assertTrue(resStr.contains(
        "public class LsfileaxImpl implements Lsfileax, Callable {"));

    }

    private void checkStandaloneResults(CixsMuleComponent muleComponent)
    throws Exception{

        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                mGenerator.getTargetSrcDir(), muleComponent.getPackageName(), true);

        String resStr = getSource(componentClassFilesDir, 
                muleComponent.getInterfaceClassName() + ".java");
        assertTrue(resStr.contains(
                "public interface " + muleComponent.getInterfaceClassName() + " {"));

        resStr = getSource(componentClassFilesDir, 
                muleComponent.getCixsOperations().get(0).getFaultType() + ".java");
        assertTrue(resStr.contains(
                "public class " + muleComponent.getInterfaceClassName() + "Exception extends Exception {"));

        resStr = getSource(componentClassFilesDir, 
                muleComponent.getImplementationClassName() + ".java");
        assertTrue(resStr.contains(
                "public class " + muleComponent.getInterfaceClassName() + "Impl implements " + muleComponent.getInterfaceClassName() + ", Callable {"));

        resStr = getSource(mGenerator.getTargetAntDir(), "build.xml");
        assertTrue(resStr.replace('\\', '/').contains(
                "<property name=\"jarFile\" value=\"${env.MULE_HOME}/lib/user/mule-legstar-" + muleComponent.getName() + ".jar\"/>"));

        resStr = getSource(mGenerator.getTargetMuleConfigDir(),
                "mule-standalone-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.contains(
                "<mule-descriptor name=\"" + muleComponent.getName() + "UMO\" implementation=\"org.mule.providers.legstar.test." + muleComponent.getName() + "." + muleComponent.getInterfaceClassName() + "Impl\">"));

        resStr = getSource(mGenerator.getTargetAntDir(),
                "start-mule-standalone-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.replace("\\", "/").contains(
                "<property name=\"conf.file\" value=\"file:///src/test/gen/conf/" +
                muleComponent.getName() +
                "/mule-standalone-config-" +
                muleComponent.getName() +
                ".xml\"/>"));

        resStr = getSource(mGenerator.getTargetPropDir(),
                muleComponent.getCixsOperations().get(0).getCicsProgramName().toLowerCase() + ".properties");
        assertTrue(resStr.contains(
                "CICSProgramName=" + muleComponent.getCixsOperations().get(0).getCicsProgramName() + ""));

        resStr = getSource(mGenerator.getTargetPropDir(),
                "log4j" + ".properties");
        assertTrue(resStr.contains(
                "log4j.logger.org.mule=INFO"));
    }

    private void checkBridgeResults(CixsMuleComponent muleComponent)
    throws Exception{

        String resStr = getSource(mGenerator.getTargetMuleConfigDir(),
                "mule-bridge-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.contains(
                "<mule-configuration id=\"mule-legstar-bridge-" + muleComponent.getName() + "-config\" version=\"1.0\">"));
        
        resStr = getSource(mGenerator.getTargetAntDir(),
                "start-mule-bridge-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.replace("\\", "/").contains(
                "<property name=\"conf.file\" value=\"file:///src/test/gen/conf/" +
                muleComponent.getName() +
                "/mule-bridge-config-" +
                muleComponent.getName() +
                ".xml\"/>"));

       for (CixsOperation operation : muleComponent.getCixsOperations()) {
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    mGenerator.getTargetSrcDir(), operation.getPackageName(), true);

            resStr = getSource(operationClassFilesDir, 
                    "HostByteArrayTo" + operation.getRequestHolderType() + ".java");
            assertTrue(resStr.contains(
                    "public class HostByteArrayTo" + operation.getRequestHolderType() + " extends AbstractLegStarTransformer {"));

            resStr = getSource(operationClassFilesDir, 
                    "HostByteArrayTo" + operation.getResponseHolderType() + ".java");
            assertTrue(resStr.contains(
                    "public class HostByteArrayTo" + operation.getResponseHolderType() + " extends AbstractLegStarTransformer {"));

            resStr = getSource(operationClassFilesDir,
                    operation.getRequestHolderType() + "ToHostByteArray.java");
            assertTrue(resStr.contains(
                    "public class " + operation.getRequestHolderType() + "ToHostByteArray extends AbstractLegStarTransformer {"));

            resStr = getSource(operationClassFilesDir, 
                    operation.getResponseHolderType() + "ToHostByteArray.java");
            assertTrue(resStr.contains(
                    "public class " + operation.getResponseHolderType() + "ToHostByteArray extends AbstractLegStarTransformer {"));
        }

    }
}
