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
import org.mule.providers.legstar.model.UmoComponentParameters;

import com.legstar.cixs.jaxws.model.HttpTransportParameters;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for miscellaneous xml artifacts velocity template.
 */
public class XmlTemplatesTest extends AbstractTestTemplate {

    /** @{inheritDoc}*/
    public void setUp() {
        super.setUp();
        getParameters().put("targetJarDir", GEN_JAR_DIR.getPath());
        getParameters().put("jaxbBinDir", JAXB_BIN_DIR.getPath());
        getParameters().put("coxbBinDir", COXB_BIN_DIR.getPath());
        getParameters().put("targetBinDir", GEN_BIN_DIR.getPath());
        getParameters().put("custBinDir", CUST_BIN_DIR.getPath());
        getParameters().put("targetPropDir", GEN_PROP_DIR.getPath());
        getParameters().put("targetMuleConfigDir", GEN_CONF_DIR.getPath());
    }

    /**
     * build.xml creates the mule component ready for deployment.
     * @throws Exception if generation fails
     */
    public void testAntBuildJar() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        getParameters().put("generationTarget", "adapter");

        File componentAntFilesDir = new File(GEN_ANT_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        Mule2CixsGenerator.generateAntBuildJar(
                muleComponent, getParameters(), componentAntFilesDir);
        compare(componentAntFilesDir, "build.xml", muleComponent.getInterfaceClassName());
    }

    /**
     * mule-adapter-standalone-config.xml creates the mule standalone config.
     * @throws Exception if generation fails
     */
    public void testAdapterStandaloneConfigXml() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();

        File componentConfFilesDir = new File(GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateAdapterStandaloneConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);
        compare(componentConfFilesDir,
                "mule-adapter-standalone-config-" + muleComponent.getName() + ".xml");
    }

    /**
     * mule-bridge-config.xml creates the mule bridge config.
     * @throws Exception if generation fails
     */
    public void testAdapterBridgeConfigXml() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        getParameters().put("hostCharset", "IBM01140");
        getParameters().put("hostURI", LEGSTAR_HOST_URI);

        File componentConfFilesDir = new File(
                GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateAdapterBridgeConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);

        compare(componentConfFilesDir,
                "mule-adapter-bridge-config-" + muleComponent.getName() + ".xml");
    }

    /**
     * mule-proxy-config.xml creates the mule local config.
     * @throws Exception if generation fails
     */
    public void testProxyConfigXml() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        HttpTransportParameters httpTransportParameters = new HttpTransportParameters();
        httpTransportParameters.setHost("megamouss");
        httpTransportParameters.setPort(8083);
        httpTransportParameters.setPath("/legstar/services/jvmquery/");
        httpTransportParameters.add(getParameters());
        getParameters().put("hostCharset", "IBM01140");
        
        UmoComponentParameters umoComponentParameters = new UmoComponentParameters();
        umoComponentParameters.setImplementationName("com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
        umoComponentParameters.add(getParameters());

        File componentConfFilesDir = new File(GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateProxyConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);
        compare(componentConfFilesDir,
                "mule-proxy-config-" + muleComponent.getName() + ".xml");
    }

}
