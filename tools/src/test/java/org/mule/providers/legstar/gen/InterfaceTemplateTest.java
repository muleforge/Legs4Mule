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
import java.util.HashMap;
import java.util.Map;

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for the interface velocity template.
 */
public class InterfaceTemplateTest extends AbstractTestTemplate {
	
    private Map <String, Object> mParameters;
    
    /** @{inheritDoc}*/
    @Override
 	public final void setUp() {
        try {
            CodeGenUtil.initVelocity();
            mParameters = new HashMap <String, Object>();
            CodeGenHelper helper = new CodeGenHelper();
            mParameters.put("helper", helper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
    /**
     * Generate a Mule component implementation (Commarea in == Commarea out).
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceCommareainEqCommareaout() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateInterface(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getInterfaceClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileae;"));
        assertTrue(resStr.contains("public interface Lsfileae {"));
        assertTrue(resStr.contains("DfhcommareaType lsfileae("));
        assertTrue(resStr.contains("DfhcommareaType request,"));
        assertTrue(resStr.contains("MuleHostHeader hostHeader)"));
        assertTrue(resStr.contains(" throws LsfileaeException;"));
		
	}
	
    /**
     * Generate a Mule component implementation (Commarea in != Commarea out).
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceCommareainNeqCommareaout() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfilealMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateInterface(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getInterfaceClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileal;"));
        assertTrue(resStr.contains("public interface Lsfileal {"));
        assertTrue(resStr.contains("ReplyDataType lsfileal("));
        assertTrue(resStr.contains("RequestParmsType request,"));
        assertTrue(resStr.contains("MuleHostHeader hostHeader)"));
        assertTrue(resStr.contains(" throws LsfilealException;"));
		
	}
	
    /**
     * Generate a Mule component implementation (Container).
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceContainer() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateInterface(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getInterfaceClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(resStr.contains("public interface Lsfileac {"));
        assertTrue(resStr.contains("LsfileacResponseHolder lsfileac("));
        assertTrue(resStr.contains("LsfileacRequestHolder request,"));
        assertTrue(resStr.contains("MuleHostHeader hostHeader)"));
        assertTrue(resStr.contains(" throws LsfileacException;"));
		
	}

    /**
     * Generate a Mule component implementation with multiple operations.
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceMultipleOperations() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileaxMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateInterface(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getInterfaceClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileax;"));
        assertTrue(resStr.contains("public interface Lsfileax {"));
        assertTrue(resStr.contains("DfhcommareaType lsfileae("));
        assertTrue(resStr.contains("DfhcommareaType request,"));
        assertTrue(resStr.contains("MuleHostHeader hostHeader)"));
        assertTrue(resStr.contains(" throws LsfileaeException;"));
        assertTrue(resStr.contains("LsfileacResponseHolder lsfileac("));
        assertTrue(resStr.contains("LsfileacRequestHolder request,"));
        assertTrue(resStr.contains("MuleHostHeader hostHeader)"));
        assertTrue(resStr.contains(" throws LsfileacException;"));
		
	}
    
}
