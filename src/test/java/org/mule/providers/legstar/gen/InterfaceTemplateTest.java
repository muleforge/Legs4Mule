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
 * Test cases for the interface velocity template.
 */
public class InterfaceTemplateTest extends TestCase {
	
    /** Velocity template under test. */
	private static final String TEMPLATE = "vlc/cixsmule-component-interface.vm";
	
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(
            InterfaceTemplateTest.class);

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
	public final void testGenerateInterfaceCommareainEqCommareaout() throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfileaeMuleComponent());

        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileae;"));
        assertTrue(w.toString().contains("public interface MuleLsfileae {"));
        assertTrue(w.toString().contains(Cases.JAXB_PKG_PREFIX
                + ".DfhcommareaType lsfileae("));
        assertTrue(w.toString().contains(Cases.JAXB_PKG_PREFIX 
                + ".DfhcommareaType request,"));
        assertTrue(w.toString().contains("MuleHostHeader hostHeader)"));
        assertTrue(w.toString().contains(" throws MuleLsfileaeException;"));
		
	}
	
    /**
     * Generate a Mule component implementation (Commarea in != Commarea out).
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceCommareainNeqCommareaout() throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfilealMuleComponent());

        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileal;"));
        assertTrue(w.toString().contains("public interface MuleLsfileal {"));
        assertTrue(w.toString().contains(Cases.JAXB_PKG_PREFIX
                + ".ReplyDataType lsfileal("));
        assertTrue(w.toString().contains(Cases.JAXB_PKG_PREFIX 
                + ".RequestParmsType request,"));
        assertTrue(w.toString().contains("MuleHostHeader hostHeader)"));
        assertTrue(w.toString().contains(" throws MuleLsfilealException;"));
		
	}
	
    /**
     * Generate a Mule component implementation (Container).
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceContainer() throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfileacMuleComponent());

        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(w.toString().contains("public interface MuleLsfileac {"));
        assertTrue(w.toString().contains("LsfileacResponseHolder lsfileac("));
        assertTrue(w.toString().contains("LsfileacRequestHolder request,"));
        assertTrue(w.toString().contains("MuleHostHeader hostHeader)"));
        assertTrue(w.toString().contains(" throws MuleLsfileacException;"));
		
	}

    /**
     * Generate a Mule component implementation with multiple operations.
     * @throws Exception if test fails
     */
	public final void testGenerateInterfaceMultipleOperations() throws Exception {
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put("muleComponent", Cases.getLsfileaxMuleComponent());

        StringWriter w = new StringWriter();

        Velocity.mergeTemplate(TEMPLATE, "UTF-8", context, w);
        LOG.debug(w.toString());
        assertTrue(w.toString().contains("package "
                + Cases.LEGS4MULE_PKG_PREFIX + ".lsfileax;"));
        assertTrue(w.toString().contains("public interface MuleLsfileax {"));
        assertTrue(w.toString().contains(Cases.JAXB_PKG_PREFIX
                + ".ReplyDataType lsfileal("));
        assertTrue(w.toString().contains(Cases.JAXB_PKG_PREFIX
                + ".RequestParmsType request,"));
        assertTrue(w.toString().contains("MuleHostHeader hostHeader)"));
        assertTrue(w.toString().contains(" throws MuleLsfilealException;"));
        assertTrue(w.toString().contains("LsfileacResponseHolder lsfileac("));
        assertTrue(w.toString().contains("LsfileacRequestHolder request,"));
        assertTrue(w.toString().contains("MuleHostHeader hostHeader)"));
        assertTrue(w.toString().contains(" throws MuleLsfileacException;"));
		
	}

}
