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

import junit.framework.TestCase;

/**
 * Test cases for CixsMuleGenerator.
 */
public class CixsMuleGeneratorTest extends TestCase {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(
            CixsMuleGeneratorTest.class);

    /**
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        try {
            generator.execute();
        } catch (Exception e) {
            assertEquals("Missing cixs mule component parameter",
                    e.getCause().getMessage());
        }
        CixsMuleComponent cixsMuleComponent = new CixsMuleComponent();
        cixsMuleComponent.setName("muleComponentName");
        try {
            generator.setCixsMuleComponent(cixsMuleComponent);
            generator.execute();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:"
                    + " No directory name was specified",
                    e.getCause().getMessage());
        }
       
    }
    
    /**
     * Check the generation of the intermediate cixs make XML file.
     * @throws Exception if generation fails
     */
    public final void testGetMakeFile() throws Exception {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        generator.init();
        CixsMuleComponent cixsMuleComponent = new CixsMuleComponent();
        cixsMuleComponent.setName("muleComponentName");
        cixsMuleComponent.setInterfaceClassName("MuleComponent");
        cixsMuleComponent.setImplementationClassName("MuleComponentImpl");
        generator.setCixsMuleComponent(cixsMuleComponent);
        generator.setTargetSrcDir("test-gen");
        String makeFileName = generator.getMakeFileName();
 
        String resStr = getContent(makeFileName);
        assertTrue(resStr.contains(
                "<cixstarget name=\"muleComponentName\" targetDir=\"test-gen\">"));
        assertTrue(resStr.contains(
                "<cixstemplate name=\"vlc/cixsmule-component-interface.vm\""
                + " targetFile=\"MuleComponent.java\"/>"));
        assertTrue(resStr.contains(
                "<cixstemplate name=\"vlc/cixsmule-component-implementation.vm\""
                + " targetFile=\"MuleComponentImpl.java\"/>"));
        assertTrue(resStr.contains("</cixstarget>"));
    }
    
    /**
     * Check generation when no operations are specified.
     * @throws Exception if generation fails
     */
    public final void testGenerateClassesNoOperations() throws Exception {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        generator.init();
        CixsMuleComponent cixsMuleComponent = new CixsMuleComponent();
        cixsMuleComponent.setName("muleComponentName");
        cixsMuleComponent.setInterfaceClassName("MuleComponent");
        cixsMuleComponent.setImplementationClassName("MuleComponentImpl");
        generator.setCixsMuleComponent(cixsMuleComponent);
        generator.setTargetSrcDir("test-gen");
        generator.execute();
        String resStr = getContent("test-gen/MuleComponent.java");
        assertTrue(resStr.contains("public interface MuleComponent {"));
        
    }

    /**
     * Check generation for operation with identical input and output structures.
     * @throws Exception if generation fails
     */
    public final void testLsfileaeGenerateClasses() throws Exception {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        generator.init();
        CixsMuleComponent cixsMuleComponent = Cases.getLsfileaeMuleComponent();
        generator.setCixsMuleComponent(cixsMuleComponent);
        generator.setTargetSrcDir("test-gen");
        generator.execute();
        String resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileae/Mulelsfileae.java");
        assertTrue(resStr.contains(
                "public interface MuleLsfileae {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileae/"
                + "MulelsfileaeException.java");
        assertTrue(resStr.contains(
                "public class MuleLsfileaeException extends Exception {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileae/"
                + "MulelsfileaeImpl.java");
        assertTrue(resStr.contains(
                "public class MuleLsfileaeImpl implements MuleLsfileae, Callable {"));
        
    }
    
    /**
     * Check generation for operation with different input and output structures.
     * @throws Exception if generation fails
     */
    public final void testgetLsfilealGenerateClasses() throws Exception {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        generator.init();
        CixsMuleComponent cixsMuleComponent = Cases.getLsfilealMuleComponent();
        generator.setCixsMuleComponent(cixsMuleComponent);
        generator.setTargetSrcDir("test-gen");
        generator.execute();
        String resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileal/Mulelsfileal.java");
        assertTrue(resStr.contains(
                "public interface MuleLsfileal {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileal/"
                + "MulelsfilealException.java");
        assertTrue(resStr.contains(
                "public class MuleLsfilealException extends Exception {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileal/"
                + "MulelsfilealImpl.java");
        assertTrue(resStr.contains(
                "public class MuleLsfilealImpl implements MuleLsfileal, Callable {"));
        
    }

    /**
     * Check generation for CICS containers target component.
     * @throws Exception if generation fails
     */
    public final void testLsfileacGenerateClasses() throws Exception {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        generator.init();
        CixsMuleComponent cixsMuleComponent = Cases.getLsfileacMuleComponent();
        generator.setCixsMuleComponent(cixsMuleComponent);
        generator.setTargetSrcDir("test-gen");
        generator.execute();
        String resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileac/Mulelsfileac.java");
        assertTrue(resStr.contains(
                "public interface MuleLsfileac {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileac/"
                + "MulelsfileacException.java");
        assertTrue(resStr.contains(
                "public class MuleLsfileacException extends Exception {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileac/"
                + "MulelsfileacImpl.java");
        assertTrue(resStr.contains(
                "public class MuleLsfileacImpl implements MuleLsfileac, Callable {"));
        
    }
    
    /**
     * Check generation for multiple operations components.
     * @throws Exception if generation fails
     */
    public final void testLsfileaxGenerateClasses() throws Exception {
        CixsMuleGenerator generator = new CixsMuleGenerator();
        generator.init();
        CixsMuleComponent cixsMuleComponent = Cases.getLsfileaxMuleComponent();
        generator.setCixsMuleComponent(cixsMuleComponent);
        generator.setTargetSrcDir("test-gen");
        generator.execute();
        String resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileax/Mulelsfileax.java");
        assertTrue(resStr.contains(
                "public interface MuleLsfileax {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileax/"
                + "MulelsfileacException.java");
        assertTrue(resStr.contains(
                "public class MuleLsfileacException extends Exception {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileax/"
                + "MulelsfilealException.java");
        assertTrue(resStr.contains(
                "public class MuleLsfilealException extends Exception {"));
        resStr = getContent(
                "test-gen/org/mule/providers/legstar/test/lsfileax/"
                + "MulelsfileaxImpl.java");
        assertTrue(resStr.contains(
                "public class MuleLsfileaxImpl implements MuleLsfileax, Callable {"));
    }
    
    
    /**
     * Reads the content of a file in a string.
     * @param fileName name of the file
     * @return a string with the file content
     * @throws IOException if fails to read file
     */
    private static String getContent(final String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        StringBuilder resStr = new StringBuilder();
        String str = in.readLine();
        while (str != null) {
            LOG.debug(str);
            resStr.append(str);
            str = in.readLine();
        }
        in.close();
        return resStr.toString();
    }

}
