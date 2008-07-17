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
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

/**
 * Test cases for the implementation velocity template.
 */
public class ImplementationTemplateTest extends TestCase {
	
    /** Code will be generated here. */
    private static final String GEN_SRC_DIR = "src/test/gen/java";

    private Map <String, Object> mParameters;
    
    /** Logger. */
    private static final Log LOG = LogFactory.getLog(ImplementationTemplateTest.class);

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
	public final void testGenerateImplementationCommareainEqCommareaout()
	        throws Exception {
	    CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                new File(GEN_SRC_DIR), muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateImplementation(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getImplementationClassName() + ".java");

        assertTrue(resStr.contains("package " + TestCases.LEGS4MULE_PKG_PREFIX
                + ".lsfileae;"));
        assertTrue(resStr.contains("import " + TestCases.JAXB_PKG_PREFIX
                + ".lsfileae.DfhcommareaType;"));
        assertTrue(resStr.contains("import " + TestCases.JAXB_PKG_PREFIX
                + ".lsfileae.bind.DfhcommareaTypeBinding;"));
        assertTrue(resStr.contains(
                "public class LsfileaeImpl implements Lsfileae, Callable {"));
        assertTrue(resStr.contains(
                "private static final String LSFILEAE_PROP_FILE"
                + " = \"lsfileae.properties\";"));
        assertTrue(resStr.contains("DfhcommareaTypeBinding inputDfhcommareaTypeBinding ="));
        assertTrue(resStr.contains("new DfhcommareaTypeBinding(request);"));
        assertTrue(resStr.contains("DfhcommareaTypeBinding outputDfhcommareaTypeBinding ="));
        assertTrue(resStr.contains("new DfhcommareaTypeBinding();"));
        assertTrue(resStr.contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(resStr.contains("inputDfhcommareaTypeBinding,"));
        assertTrue(resStr.contains("outputDfhcommareaTypeBinding);"));
        assertTrue(resStr.contains("reply = outputDfhcommareaTypeBinding.getDfhcommareaType();"));
        assertTrue(resStr.contains("if (request instanceof DfhcommareaType) {"));
	}
	
	/**
	 * Generate a Mule component implementation (Commarea in != Commarea out).
     * @throws Exception if test fails
	 */
	public final void testGenerateImplementationCommareainNeqCommareaout()
	        throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfilealMuleComponent();
        
        File componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                new File(GEN_SRC_DIR), muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateImplementation(
                muleComponent, mParameters, componentClassFilesLocation);
        String resStr = getSource(
                componentClassFilesLocation,
                muleComponent.getImplementationClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileal;"));
        assertTrue(resStr.contains("import "
                + TestCases.JAXB_PKG_PREFIX + ".lsfileal.RequestParmsType;"));
        assertTrue(resStr.contains("import "
                + TestCases.JAXB_PKG_PREFIX + ".lsfileal.bind.RequestParmsTypeBinding;"));
        assertTrue(resStr.contains("import "
                + TestCases.JAXB_PKG_PREFIX + ".lsfileal.ReplyDataType;"));
        assertTrue(resStr.contains("import "
                + TestCases.JAXB_PKG_PREFIX + ".lsfileal.bind.ReplyDataTypeBinding;"));
        assertTrue(resStr.contains(
                "public class LsfilealImpl implements Lsfileal, Callable {"));
        assertTrue(resStr.contains(
                "private static final String LSFILEAL_PROP_FILE"
                + " = \"lsfileal.properties\";"));
        assertTrue(resStr.contains("RequestParmsTypeBinding inputRequestParmsTypeBinding"));
        assertTrue(resStr.contains("new RequestParmsTypeBinding(request);"));
        assertTrue(resStr.contains("ReplyDataTypeBinding outputReplyDataTypeBinding ="));
        assertTrue(resStr.contains("new ReplyDataTypeBinding();"));
        assertTrue(resStr.contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(resStr.contains("inputRequestParmsTypeBinding,"));
        assertTrue(resStr.contains("outputReplyDataTypeBinding);"));
        assertTrue(resStr.contains("reply = outputReplyDataTypeBinding.getReplyDataType();"));
	}
	
	/**
	 * Generate a Mule component implementation (Container).
     * @throws Exception if test fails
	 */
	public final void testGenerateImplementationContainer() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                new File(GEN_SRC_DIR), muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateImplementation(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getImplementationClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(resStr.contains("import java.util.LinkedHashMap;"));
        assertTrue(resStr.contains("import java.util.Map;"));
        assertTrue(resStr.contains(
                "import com.legstar.coxb.ICobolComplexBinding;"));
        assertTrue(resStr.contains(
                "public class LsfileacImpl implements Lsfileac, Callable {"));
        assertTrue(resStr.contains(
                "private static final String LSFILEAC_PROP_FILE"
                + " = \"lsfileac.properties\";"));
        assertTrue(resStr.contains(
                "Map <String, ICobolComplexBinding> inputParts ="));
        assertTrue(resStr.contains(
                "new LinkedHashMap <String, ICobolComplexBinding>();"));
        assertTrue(resStr.contains("QueryDataTypeBinding inputQueryDataTypeBinding ="));
        assertTrue(resStr.contains(
                "new QueryDataTypeBinding(request.getQueryData());"));
        assertTrue(resStr.contains("inputParts.put(\"QueryData\", inputQueryDataTypeBinding);"));
        assertTrue(resStr.contains("QueryLimitTypeBinding inputQueryLimitTypeBinding ="));
        assertTrue(resStr.contains(
                "new QueryLimitTypeBinding(request.getQueryLimit());"));
        assertTrue(resStr.contains("inputParts.put(\"QueryLimit\", inputQueryLimitTypeBinding);"));
       assertTrue(resStr.contains(
                "Map <String, ICobolComplexBinding> outputParts ="));
        assertTrue(resStr.contains(
                "new LinkedHashMap <String, ICobolComplexBinding>();"));
        assertTrue(resStr.contains("ReplyDataTypeBinding outputReplyDataTypeBinding ="));
        assertTrue(resStr.contains("new ReplyDataTypeBinding();"));
        assertTrue(resStr.contains("ReplyStatusTypeBinding outputReplyStatusTypeBinding ="));
        assertTrue(resStr.contains("new ReplyStatusTypeBinding();"));
        assertTrue(resStr.contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(resStr.contains("inputParts,"));
        assertTrue(resStr.contains("outputParts);"));
        assertTrue(resStr.contains("reply = new LsfileacResponseHolder();"));
        assertTrue(resStr.contains(
                "reply.setReplyData(outputReplyDataTypeBinding.getReplyDataType());"));
        assertTrue(resStr.contains(
                "reply.setReplyStatus(outputReplyStatusTypeBinding.getReplyStatusType());"));
 	}

	/**
	 * Generate a Mule component implementation with multiple operations.
     * @throws Exception if test fails
	 */
	public final void testGenerateImplementationMultipleOperations() throws Exception {
        CixsMuleComponent muleComponent = TestCases.getLsfileaxMuleComponent();
        
        File componentClassFilesDir = CodeGenUtil.classFilesLocation(
                new File(GEN_SRC_DIR), muleComponent.getPackageName(), true);
        Mule2CixsGenerator.generateImplementation(
                muleComponent, mParameters, componentClassFilesDir);
        String resStr = getSource(
                componentClassFilesDir,
                muleComponent.getImplementationClassName() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileax;"));
        assertTrue(resStr.contains("import java.util.LinkedHashMap;"));
        assertTrue(resStr.contains("import java.util.Map;"));
        assertTrue(resStr.contains(
                "import com.legstar.coxb.ICobolComplexBinding;"));
        assertTrue(resStr.contains(
                "import " + TestCases.JAXB_PKG_PREFIX + ".lsfileae.DfhcommareaType;"));
        assertTrue(resStr.contains(
                "import " + TestCases.JAXB_PKG_PREFIX
                + ".lsfileae.bind.DfhcommareaTypeBinding;"));
        assertTrue(resStr.contains(
                "public class LsfileaxImpl implements Lsfileax, Callable {"));
        assertTrue(resStr.contains(
                "private static final String LSFILEAE_PROP_FILE"
                + " = \"lsfileae.properties\";"));
        assertTrue(resStr.contains(
                "private static final String LSFILEAC_PROP_FILE"
                + " = \"lsfileac.properties\";"));
        assertTrue(resStr.contains("DfhcommareaTypeBinding inputDfhcommareaTypeBinding ="));
        assertTrue(resStr.contains("new DfhcommareaTypeBinding(request);"));
        assertTrue(resStr.contains("DfhcommareaTypeBinding outputDfhcommareaTypeBinding ="));
        assertTrue(resStr.contains(
                "new DfhcommareaTypeBinding();"));
        assertTrue(resStr.contains("QueryLimitTypeBinding inputQueryLimitTypeBinding ="));
        assertTrue(resStr.contains(
                "new QueryLimitTypeBinding(request.getQueryLimit());"));
        assertTrue(resStr.contains("ReplyDataTypeBinding outputReplyDataTypeBinding ="));
        assertTrue(resStr.contains("new ReplyDataTypeBinding();"));
        assertTrue(resStr.contains("ReplyStatusTypeBinding outputReplyStatusTypeBinding ="));
        assertTrue(resStr.contains("new ReplyStatusTypeBinding();"));
        assertTrue(resStr.contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(resStr.contains("inputParts,"));
        assertTrue(resStr.contains("outputParts);"));
        assertTrue(resStr.contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(resStr.contains("inputDfhcommareaTypeBinding,"));
        assertTrue(resStr.contains("outputDfhcommareaTypeBinding);"));
        assertTrue(resStr.contains("reply = outputDfhcommareaTypeBinding.getDfhcommareaType();"));
        assertTrue(resStr.contains("reply = new LsfileacResponseHolder();"));
        assertTrue(resStr.contains(
                "reply.setReplyData(outputReplyDataTypeBinding.getReplyDataType());"));
        assertTrue(resStr.contains(
                "reply.setReplyStatus(outputReplyStatusTypeBinding.getReplyStatusType());"));
	}
	
    private String getSource(File srcDir, String srcName) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File(srcDir, srcName)));
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
