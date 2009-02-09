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

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for CixsMuleGenerator.
 */
public class Cixs2MuleGeneratorTest extends AbstractTestTemplate {

    private Cixs2MuleGenerator mGenerator;

    /** The host character set. */
    public static final String HOSTCHARSET = "IBM01140";
    
    public void setUp() {
        mGenerator = new Cixs2MuleGenerator();
        mGenerator.init();
        mGenerator.setTargetSrcDir(GEN_SRC_DIR);
        mGenerator.setTargetJarDir(new File("${env.MULE_HOME}/lib/user"));
        mGenerator.setJaxbBinDir(new File("target/classes"));
        mGenerator.setCoxbBinDir(new File("target/classes"));
        mGenerator.setTargetBinDir(new File("target/gen-classes"));
        mGenerator.setCustBinDir(new File("legstar-coxbgen-cases//target/classes"));
        mGenerator.setTargetCobolDir(GEN_COBOL_DIR);
        mGenerator.setHostCharset(HOSTCHARSET);
        mGenerator.setTargetPropDir(GEN_PROP_DIR);
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
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        Cixs2MuleGenerator generator = new Cixs2MuleGenerator();
        try {
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
            		" JaxbBinDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setJaxbBinDir(new File("target/classes"));
            generator.execute();
        } catch (Exception e) {
            assertEquals("You must specify a service description",
                    e.getCause().getMessage());
        }
        CixsMuleComponent muleComponent = new CixsMuleComponent();
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
            		" TargetAntDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetAntDir(GEN_ANT_DIR);
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
            		" TargetMuleConfigDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetMuleConfigDir(GEN_CONF_DIR);
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
            		" TargetPropDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetPropDir(GEN_PROP_DIR);
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:" +
                    " TargetCobolDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetCobolDir(GEN_COBOL_DIR);
            generator.execute();
        } catch (Exception e) {
            assertEquals("You must specify a valid URI",
                    e.getCause().getMessage());
        }
        try {
            muleComponent.setServiceURI("http://server.com");
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
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        initCixsMuleComponent(muleComponent);
        mGenerator.execute();
        checkLocalResults(muleComponent);
    }

    private void checkLocalResults(CixsMuleComponent muleComponent)
    throws Exception{

        String resStr = getSource(mGenerator.getTargetAntDir(), "build.xml");
        assertTrue(resStr.replace('\\', '/').contains(
                "<property name=\"jarFile\" value=\"${env.MULE_HOME}/lib/user/mule-legstar-" + muleComponent.getName() + ".jar\"/>"));

        resStr = getSource(mGenerator.getTargetMuleConfigDir(),
                "mule-local-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.contains(
                "<mule-configuration id=\"mule-legstar-local-" + muleComponent.getName() + "-config\" version=\"1.0\">"));

        resStr = getSource(mGenerator.getTargetAntDir(),
                "start-mule-local-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.replace("\\", "/").contains(
                "<property name=\"conf.file\" value=\"file:///src/test/gen/conf/" +
                muleComponent.getName() +
                "/mule-local-config-" +
                muleComponent.getName() +
                ".xml\"/>"));

        resStr = getSource(mGenerator.getTargetPropDir(),
                "log4j" + ".properties");
        assertTrue(resStr.contains(
                "log4j.logger.org.mule=INFO"));
        
        for (CixsOperation operation : muleComponent.getCixsOperations()) {
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    mGenerator.getTargetSrcDir(), operation.getPackageName(), true);

            resStr = getSource(operationClassFilesDir, 
                    "HostByteArrayTo" + operation.getRequestHolderType() + ".java");
            assertTrue(resStr.contains(
                    "public class HostByteArrayTo" + operation.getRequestHolderType() + " extends AbstractLegStarTransformer {"));

            resStr = getSource(operationClassFilesDir, 
                    operation.getResponseHolderType() + "ToHttpResponse.java");
            assertTrue(resStr.contains(
                    "public class " + operation.getRequestHolderType() + "ToHttpResponse"));
            assertTrue(resStr.contains(
                    "extends AbstractObjectToHttpResponseTransformer {"));

            resStr = getSource(GEN_COBOL_DIR, 
                    operation.getCicsProgramName() + ".cbl");
            assertTrue(resStr.contains(
                    "PROGRAM-ID. " + operation.getCicsProgramName() + "."));
            assertTrue(resStr.contains(
                    "10 COM-NUMBER PIC 9(6)."));
       }

    }
}
