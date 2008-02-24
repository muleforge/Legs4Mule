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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.mule.providers.legstar.model.CixsMuleComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

/**
 * Test cases for CixsMuleGenerator.
 */
public class CixsMuleGeneratorTest extends TestCase {

    /** Code will be generated here. */
    private static final String GEN_SRC_DIR = "src/test/gen/java";

    /** Resources will be generated here (absolute location). */
    private static final String GEN_RES_DIR = "D:/Fady/sandbox/workspace2/legstar-mule/src/test/gen/resources";

    /** Ant scripts will be generated here (relative location). */
    private static final String GEN_ANT_DIR = "ant";

    /** Configuration files will be generated here. */
    private static final String GEN_CONF_DIR = "conf";
    
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(
            CixsMuleGeneratorTest.class);

    private CixsMuleGenerator mGenerator;

    public void setUp() {
        mGenerator = new CixsMuleGenerator();
        mGenerator.init();
        mGenerator.setTargetSrcDir(GEN_SRC_DIR);
        mGenerator.setTargetPropDir(GEN_RES_DIR);
        mGenerator.setTargetJarDir("${env.MULE_HOME}/lib/user");
        mGenerator.setJaxbBinDir("D:/Legsem/Legstar/Dev/trunk/legstar-jaxbgen-cases/target/classes");
        mGenerator.setCoxbBinDir("D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases/target/classes");
        mGenerator.setCixsBinDir("D:/Fady/sandbox/workspace2/legstar-mule/target/gen-classes");
        mGenerator.setCustBinDir("D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases//target/classes");
    }
    
    /**
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        try {
            mGenerator.execute();
        } catch (Exception e) {
            assertEquals("Missing cixs mule component parameter",
                    e.getCause().getMessage());
        }
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("muleComponentName");
        try {
            mGenerator.setCixsMuleComponent(muleComponent);
            mGenerator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:"
                    + " No directory name was specified",
                    e.getCause().getMessage());
        }
       
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
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR);
        mGenerator.setTargetConfDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR);
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
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR);
        mGenerator.setTargetConfDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR);
        mGenerator.execute();
        checkResults(muleComponent);
    }
    
    /**
     * Check generation for operation with different input and output structures.
     * @throws Exception if generation fails
     */
    public final void testgetLsfilealGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfilealMuleComponent();
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR);
        mGenerator.setTargetConfDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR);
        mGenerator.execute();
        checkResults(muleComponent);
    }

    /**
     * Check generation for CICS containers target component.
     * @throws Exception if generation fails
     */
    public final void testLsfileacGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR);
        mGenerator.setTargetConfDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR);
        mGenerator.execute();
        checkResults(muleComponent);
    }
    
    /**
     * Check generation for multiple operations components.
     * @throws Exception if generation fails
     */
    public final void testLsfileaxGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileaxMuleComponent();
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR);
        mGenerator.setTargetConfDir(GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR);
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
    
    private void checkResults(CixsMuleComponent muleComponent)
                throws Exception{
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                mGenerator.getTargetSrcDir(), muleComponent.getPackageName());
                
        String resStr = getSource(componentClassFilesLocation, 
                muleComponent.getInterfaceClassName() + ".java");
        assertTrue(resStr.contains(
                "public interface " + muleComponent.getInterfaceClassName() + " {"));
        
        resStr = getSource(componentClassFilesLocation, 
                muleComponent.getCixsOperations().get(0).getFaultType() + ".java");
        assertTrue(resStr.contains(
                "public class " + muleComponent.getInterfaceClassName() + "Exception extends Exception {"));
        
        resStr = getSource(componentClassFilesLocation, 
                muleComponent.getImplementationClassName() + ".java");
        assertTrue(resStr.contains(
                "public class " + muleComponent.getInterfaceClassName() + "Impl implements " + muleComponent.getInterfaceClassName() + ", Callable {"));
        
        resStr = getSource(mGenerator.getTargetAntDir(), "build.xml");
        assertTrue(resStr.contains(
                "<property name=\"jarFile\" value=\"${env.MULE_HOME}/lib/user/mule-legstar-" + muleComponent.getName() + ".jar\"/>"));
        
        resStr = getSource(mGenerator.getTargetConfDir(),
                "mule-config-" + muleComponent.getName() + ".xml");
        assertTrue(resStr.contains(
                "<mule-descriptor name=\"" + muleComponent.getName() + "UMO\" implementation=\"org.mule.providers.legstar.test." + muleComponent.getName() + "." + muleComponent.getInterfaceClassName() + "Impl\">"));

        resStr = getSource(mGenerator.getTargetPropDir(),
                muleComponent.getCixsOperations().get(0).getCicsProgramName().toLowerCase() + ".properties");
        assertTrue(resStr.contains(
                "CICSProgramName=" + muleComponent.getCixsOperations().get(0).getCicsProgramName() + ""));
    }
    
    
    /**
     * Reads the content of a file in a string.
     * @param srcLocation where the file is located
     * @param srcName name of the file
     * @return a string with the file content
     * @throws IOException if fails to read file
     */
    private String getSource(String srcLocation, String srcName) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(srcLocation + '/' + srcName));
        String resStr = "";
        String str = in.readLine();
        while (str != null) {
            LOG.debug(str);
            resStr += str;
            str = in.readLine();
        }
        in.close();
        return resStr;
    }

}
