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

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.mule.providers.legstar.gen.util.CixsMuleGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * Test cases for the implementation velocity template.
 */
public class ImplementationTemplateTest extends TestCase {
	
	/** Velocity template under test. */
    private static final String TEMPLATE = "vlc/cixsmule-component-implementation.vm";

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(
            ImplementationTemplateTest.class);

    /** @{inheritDoc}*/
    @Override
    public final void setUp() {
		try {
            CixsMuleGenUtil.initVelocity();
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
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfileaeMuleComponent());

        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package " + Cases.LEGS4MULE_PKG_PREFIX
                + ".lsfileae;"));
        assertTrue(w.toString().contains("import " + Cases.JAXB_PKG_PREFIX
                + ".lsfileae.DfhcommareaType;"));
        assertTrue(w.toString().contains("import " + Cases.JAXB_PKG_PREFIX
                + ".lsfileae.bind.DfhcommareaTypeBinding;"));
        assertTrue(w.toString().contains(
                "public class MuleLsfileaeImpl implements MuleLsfileae, Callable {"));
        assertTrue(w.toString().contains(
                "private static final String LSFILEAE_PROP_FILE"
                + " = \"lsfileae.properties\";"));
        assertTrue(w.toString().contains("DfhcommareaTypeBinding input1"));
        assertTrue(w.toString().contains("new DfhcommareaTypeBinding(request);"));
        assertTrue(w.toString().contains("DfhcommareaTypeBinding output1 ="));
        assertTrue(w.toString().contains("new DfhcommareaTypeBinding();"));
        assertTrue(w.toString().contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(w.toString().contains("input1,"));
        assertTrue(w.toString().contains("output1);"));
        assertTrue(w.toString().contains("reply = output1.getDfhcommareaType();"));
	}
	
	/**
	 * Generate a Mule component implementation (Commarea in != Commarea out).
     * @throws Exception if test fails
	 */
	public final void testGenerateImplementationCommareainNeqCommareaout()
	        throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfilealMuleComponent());
 
        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileal;"));
        assertTrue(w.toString().contains("import "
                + Cases.JAXB_PKG_PREFIX + ".lsfileal.RequestParmsType;"));
        assertTrue(w.toString().contains("import "
                + Cases.JAXB_PKG_PREFIX + ".lsfileal.bind.RequestParmsTypeBinding;"));
        assertTrue(w.toString().contains("import "
                + Cases.JAXB_PKG_PREFIX + ".lsfileal.ReplyDataType;"));
        assertTrue(w.toString().contains("import "
                + Cases.JAXB_PKG_PREFIX + ".lsfileal.bind.ReplyDataTypeBinding;"));
        assertTrue(w.toString().contains(
                "public class MuleLsfilealImpl implements MuleLsfileal, Callable {"));
        assertTrue(w.toString().contains(
                "private static final String LSFILEAL_PROP_FILE"
                + " = \"lsfileal.properties\";"));
        assertTrue(w.toString().contains("RequestParmsTypeBinding input1"));
        assertTrue(w.toString().contains("new RequestParmsTypeBinding(request);"));
        assertTrue(w.toString().contains("ReplyDataTypeBinding output1 ="));
        assertTrue(w.toString().contains("new ReplyDataTypeBinding();"));
        assertTrue(w.toString().contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(w.toString().contains("input1,"));
        assertTrue(w.toString().contains("output1);"));
        assertTrue(w.toString().contains("reply = output1.getReplyDataType();"));
	}
	
	/**
	 * Generate a Mule component implementation (Container).
     * @throws Exception if test fails
	 */
	public final void testGenerateImplementationContainer() throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfileacMuleComponent());
 
        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(w.toString().contains("import java.util.LinkedHashMap;"));
        assertTrue(w.toString().contains("import java.util.Map;"));
        assertTrue(w.toString().contains(
                "import com.legstar.coxb.ICobolComplexBinding;"));
        assertTrue(w.toString().contains(
                "public class MuleLsfileacImpl implements MuleLsfileac, Callable {"));
        assertTrue(w.toString().contains(
                "private static final String LSFILEAC_PROP_FILE"
                + " = \"lsfileac.properties\";"));
        assertTrue(w.toString().contains(
                "Map <String, ICobolComplexBinding> inputParts ="));
        assertTrue(w.toString().contains(
                "new LinkedHashMap <String, ICobolComplexBinding>();"));
        assertTrue(w.toString().contains("QueryDataTypeBinding input1 ="));
        assertTrue(w.toString().contains(
                "new QueryDataTypeBinding(request.get(QueryData));"));
        assertTrue(w.toString().contains("QueryLimitTypeBinding input2 ="));
        assertTrue(w.toString().contains(
                "new QueryLimitTypeBinding(request.get(QueryLimit));"));
        assertTrue(w.toString().contains(
                "Map <String, ICobolComplexBinding> outputParts ="));
        assertTrue(w.toString().contains(
                "new LinkedHashMap <String, ICobolComplexBinding>();"));
        assertTrue(w.toString().contains("ReplyDataTypeBinding output1 ="));
        assertTrue(w.toString().contains("new ReplyDataTypeBinding();"));
        assertTrue(w.toString().contains("ReplyStatusTypeBinding output2 ="));
        assertTrue(w.toString().contains("new ReplyStatusTypeBinding();"));
        assertTrue(w.toString().contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(w.toString().contains("inputParts,"));
        assertTrue(w.toString().contains("outputParts);"));
        assertTrue(w.toString().contains("reply = new LsfileacResponseHolder();"));
        assertTrue(w.toString().contains(
                "reply.setReplyData(output1.getReplyDataType());"));
        assertTrue(w.toString().contains(
                "reply.setReplyStatus(output2.getReplyStatusType());"));
 	}

	/**
	 * Generate a Mule component implementation with multiple operations.
     * @throws Exception if test fails
	 */
	public final void testGenerateImplementationMultipleOperations() throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfileaxMuleComponent());

        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileax;"));
        assertTrue(w.toString().contains("import java.util.LinkedHashMap;"));
        assertTrue(w.toString().contains("import java.util.Map;"));
        assertTrue(w.toString().contains(
                "import com.legstar.coxb.ICobolComplexBinding;"));
        assertTrue(w.toString().contains(
                "import " + Cases.JAXB_PKG_PREFIX + ".lsfileal.RequestParmsType;"));
        assertTrue(w.toString().contains(
                "import " + Cases.JAXB_PKG_PREFIX
                + ".lsfileal.bind.RequestParmsTypeBinding;"));
        assertTrue(w.toString().contains(
                "import " + Cases.JAXB_PKG_PREFIX + ".lsfileal.ReplyDataType;"));
        assertTrue(w.toString().contains(
                "import " + Cases.JAXB_PKG_PREFIX
                + ".lsfileal.bind.ReplyDataTypeBinding;"));
        assertTrue(w.toString().contains(
                "public class MuleLsfileaxImpl implements MuleLsfileax, Callable {"));
        assertTrue(w.toString().contains(
                "private static final String LSFILEAL_PROP_FILE"
                + " = \"lsfileal.properties\";"));
        assertTrue(w.toString().contains(
                "private static final String LSFILEAC_PROP_FILE"
                + " = \"lsfileac.properties\";"));
        assertTrue(w.toString().contains("RequestParmsTypeBinding input1 ="));
        assertTrue(w.toString().contains("new RequestParmsTypeBinding(request);"));
        assertTrue(w.toString().contains("QueryDataTypeBinding input1 ="));
        assertTrue(w.toString().contains(
                "new QueryDataTypeBinding(request.get(QueryData));"));
        assertTrue(w.toString().contains("QueryLimitTypeBinding input2 ="));
        assertTrue(w.toString().contains(
                "new QueryLimitTypeBinding(request.get(QueryLimit));"));
        assertTrue(w.toString().contains("ReplyDataTypeBinding output1 ="));
        assertTrue(w.toString().contains("new ReplyDataTypeBinding();"));
        assertTrue(w.toString().contains("ReplyDataTypeBinding output1 ="));
        assertTrue(w.toString().contains("new ReplyDataTypeBinding();"));
        assertTrue(w.toString().contains("ReplyStatusTypeBinding output2 ="));
        assertTrue(w.toString().contains("new ReplyStatusTypeBinding();"));
        assertTrue(w.toString().contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(w.toString().contains("inputParts,"));
        assertTrue(w.toString().contains("outputParts);"));
        assertTrue(w.toString().contains(
                "mInvoker.invoke(hostHeader.getHostRequestID(),"));
        assertTrue(w.toString().contains("input1,"));
        assertTrue(w.toString().contains("output1);"));
        assertTrue(w.toString().contains("reply = output1.getReplyDataType();"));
        assertTrue(w.toString().contains("reply = new LsfileacResponseHolder();"));
        assertTrue(w.toString().contains(
                "reply.setReplyData(output1.getReplyDataType());"));
        assertTrue(w.toString().contains(
                "reply.setReplyStatus(output2.getReplyStatusType());"));
	}
	
}
