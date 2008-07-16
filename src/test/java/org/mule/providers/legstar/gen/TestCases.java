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

import java.util.ArrayList;
import java.util.List;

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.cixs.gen.model.CixsStructure;

/**
 * Helper class. Creates models ready for code generation.
 */
public final class TestCases {
    
	/** Target package for generated Mule component. */
    public static final String LEGS4MULE_PKG_PREFIX =
	    "org.mule.providers.legstar.test";
    
    /** Cobol binding classes package. As generated by LegStar Coxbgen. */
	public static final String JAXB_PKG_PREFIX = "com.legstar.test.coxb";
	
    /** Address on which local UMO is listening for requests. */
    public static final String LOCAL_UMO_URI = "http://megamouss:8083";
    
	/**
	 * @return a Mule component to serve as model for velocity templates.
	 */
	public static CixsMuleComponent getLsfileaeMuleComponent() {
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("lsfileae");
        muleComponent.setPackageName(TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileae");
        List <CixsOperation> cixsOperations = new ArrayList <CixsOperation>();
        cixsOperations.add(TestCases.getLsfileaeOperation(muleComponent.getName()));
        muleComponent.setCixsOperations(cixsOperations);
        muleComponent.setServiceURI(LOCAL_UMO_URI);
        return muleComponent;
	}
	
	/**
	 * @return a Mule component to serve as model for velocity templates.
	 */
	public static CixsMuleComponent getLsfilealMuleComponent() {
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("lsfileal");
        muleComponent.setPackageName(TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileal");
        List <CixsOperation> cixsOperations = new ArrayList <CixsOperation>();
        cixsOperations.add(TestCases.getLsfilealOperation(muleComponent.getName()));
        muleComponent.setCixsOperations(cixsOperations);
        muleComponent.setServiceURI(LOCAL_UMO_URI);
        return muleComponent;
	}
	/**
	 * @return a Mule component to serve as model for velocity templates.
	 */
	public static CixsMuleComponent getLsfileacMuleComponent() {
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("lsfileac");
        muleComponent.setPackageName(TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileac");
        List <CixsOperation> cixsOperations = new ArrayList <CixsOperation>();
        cixsOperations.add(TestCases.getLsfileacOperation(muleComponent.getName()));
        muleComponent.setCixsOperations(cixsOperations);
        muleComponent.setServiceURI(LOCAL_UMO_URI);
        return muleComponent;
	}
	/**
	 * @return a Mule component to serve as model for velocity templates.
	 */
	public static CixsMuleComponent getLsfileaxMuleComponent() {
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("lsfileax");
        muleComponent.setPackageName(TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileax");
        List <CixsOperation> cixsOperations = new ArrayList <CixsOperation>();
        cixsOperations.add(TestCases.getLsfileaeOperation(muleComponent.getName()));
        cixsOperations.add(TestCases.getLsfileacOperation(muleComponent.getName()));
        muleComponent.setCixsOperations(cixsOperations);
        muleComponent.setServiceURI(LOCAL_UMO_URI);
        return muleComponent;
	}
    /**
     * @return a Mule component to serve as model for velocity templates.
     */
    public static CixsMuleComponent getJvmQueryMuleComponent() {
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        muleComponent.setName("jvmquery");
        muleComponent.setImplementationClassName("com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
        muleComponent.setPackageName(TestCases.LEGS4MULE_PKG_PREFIX + ".jvmquery");
        List <CixsOperation> cixsOperations = new ArrayList <CixsOperation>();
        cixsOperations.add(TestCases.getJvmQueryOperation(muleComponent.getName()));
        muleComponent.setCixsOperations(cixsOperations);
        muleComponent.setServiceURI(LOCAL_UMO_URI);
        return muleComponent;
    }
	/**
	 * @param componentName the component name
	 * @return an operation corresponding to a simple CICS commarea-driven
	 * program case where input and output structures are identical.
	 */
	private static CixsOperation getLsfileaeOperation(String componentName) {
        CixsOperation cixsOperation = new CixsOperation();
        cixsOperation.setCicsProgramName("LSFILEAE");
        cixsOperation.setName("lsfileae");
        cixsOperation.setFaultType("LsfileaeException");
        cixsOperation.setPackageName(LEGS4MULE_PKG_PREFIX + '.' + componentName);

        List <CixsStructure> inStructures = new ArrayList <CixsStructure>();
        CixsStructure inStructure = new CixsStructure();
        inStructure.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileae");
        inStructure.setJaxbType("DfhcommareaType");
        inStructures.add(inStructure);
        cixsOperation.setInput(inStructures);
        
        List <CixsStructure> outStructures = new ArrayList <CixsStructure>();
        CixsStructure outStructure = new CixsStructure();
        outStructure.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileae");
        outStructure.setJaxbType("DfhcommareaType");
        outStructures.add(outStructure);
        cixsOperation.setOutput(outStructures);

        return cixsOperation;
	}

	/**
     * @param componentName the component name
	 * @return an operation corresponding to a CICS commarea-driven program 
	 * case where input and output structures are different.
	 */
	private static CixsOperation getLsfilealOperation(String componentName) {
        CixsOperation cixsOperation = new CixsOperation();
        cixsOperation.setCicsProgramName("LSFILEAL");
        cixsOperation.setName("lsfileal");
        cixsOperation.setFaultType("LsfilealException");
        cixsOperation.setPackageName(LEGS4MULE_PKG_PREFIX + '.' + componentName);

        List <CixsStructure> inStructures = new ArrayList <CixsStructure>();
        CixsStructure inStructure = new CixsStructure();
        inStructure.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileal");
        inStructure.setJaxbType("RequestParmsType");
        inStructures.add(inStructure);
        cixsOperation.setInput(inStructures);
        
        List <CixsStructure> outStructures = new ArrayList <CixsStructure>();
        CixsStructure outStructure = new CixsStructure();
        outStructure.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileal");
        outStructure.setJaxbType("ReplyDataType");
        outStructures.add(outStructure);
        cixsOperation.setOutput(outStructures);

       return cixsOperation;
	}

	/**
     * @param componentName the component name
	 * @return an operation corresponding to a CICS container-driven program.
	 */
	private static CixsOperation getLsfileacOperation(String componentName) {
        CixsOperation cixsOperation = new CixsOperation();
        cixsOperation.setCicsProgramName("LSFILEAC");
        cixsOperation.setName("lsfileac");
        cixsOperation.setCicsChannel("LSFILEAC-CHANNEL");
        cixsOperation.setFaultType("LsfileacException");
        cixsOperation.setPackageName(LEGS4MULE_PKG_PREFIX + '.' + componentName);

        List <CixsStructure> inStructures = new ArrayList <CixsStructure>();
        CixsStructure inStructure1 = new CixsStructure();
        inStructure1.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileac");
        inStructure1.setJaxbType("QueryDataType");
        inStructure1.setCicsContainer("QueryData");
        inStructures.add(inStructure1);

        CixsStructure inStructure2 = new CixsStructure();
        inStructure2.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileac");
        inStructure2.setJaxbType("QueryLimitType");
        inStructure2.setCicsContainer("QueryLimit");
        inStructures.add(inStructure2);
        
        cixsOperation.setInput(inStructures);

        List <CixsStructure> outStructures = new ArrayList <CixsStructure>();
        CixsStructure outStructure1 = new CixsStructure();
        outStructure1.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileac");
        outStructure1.setJaxbType("ReplyDataType");
        outStructure1.setCicsContainer("ReplyData");
        outStructures.add(outStructure1);

        CixsStructure outStructure2 = new CixsStructure();
        outStructure2.setJaxbPackageName(JAXB_PKG_PREFIX + ".lsfileac");
        outStructure2.setJaxbType("ReplyStatusType");
        outStructure2.setCicsContainer("ReplyStatus");
        outStructures.add(outStructure2);
        
        cixsOperation.setOutput(outStructures);

        return cixsOperation;
	}
	
	   /**
     * @param componentName the component name
     * @return an operation corresponding to an inbound CICS request
     * program case where JAXB object hides a POJO.
     */
    private static CixsOperation getJvmQueryOperation(String componentName) {
        CixsOperation cixsOperation = new CixsOperation();
        cixsOperation.setName("jvmquery");
        cixsOperation.setCicsProgramName("JVMQUERY");
        cixsOperation.setPackageName(LEGS4MULE_PKG_PREFIX + '.' + componentName);

        List <CixsStructure> inStructures = new ArrayList <CixsStructure>();
        CixsStructure inStructure = new CixsStructure();
        inStructure.setJaxbPackageName(JAXB_PKG_PREFIX + ".jvmquery");
        inStructure.setJaxbType("JvmQueryRequestType");
        inStructures.add(inStructure);
        cixsOperation.setInput(inStructures);
        
        List <CixsStructure> outStructures = new ArrayList <CixsStructure>();
        CixsStructure outStructure = new CixsStructure();
        outStructure.setJaxbPackageName(JAXB_PKG_PREFIX + ".jvmquery");
        outStructure.setJaxbType("JvmQueryReplyType");
        outStructures.add(outStructure);
        cixsOperation.setOutput(outStructures);

        return cixsOperation;
    }



}
