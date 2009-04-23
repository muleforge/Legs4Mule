/*******************************************************************************
 * Copyright (c) 2009 LegSem.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     LegSem - initial API and implementation
 ******************************************************************************/
package org.mule.transport.legstar.gen;

import java.io.File;

import org.mule.transport.legstar.model.AntBuildMule2CixsModel;
import org.mule.transport.legstar.model.CixsMuleComponent;
import org.mule.transport.legstar.model.UmoComponentParameters;

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
        compare(componentAntFilesDir, "build.xml",
                muleComponent.getName());
    }

    /**
     * Creates the mule adapter http config.
     * @throws Exception if generation fails
     */
    public void testAdapterHttpConfigXml() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        getParameters().put("hostCharset", "IBM01140");
        HttpTransportParameters httpTransportParameters = new HttpTransportParameters();
        httpTransportParameters.setHost(AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST);
        httpTransportParameters.setPort(AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT);
        httpTransportParameters.setPath(AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH);
        httpTransportParameters.add(getParameters());

        File componentConfFilesDir = new File(
                GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateAdapterHttpConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);

        compare(componentConfFilesDir,
                "mule-adapter-http-config-" + muleComponent.getName() + ".xml",
                muleComponent.getName());
    }

    /**
     * Creates the mule proxy http config.
     * @throws Exception if generation fails
     */
    public void testProxyHttpConfigXml() throws Exception {

        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        HttpTransportParameters httpTransportParameters = new HttpTransportParameters();
        httpTransportParameters.setHost("megamouss");
        httpTransportParameters.setPort(8083);
        httpTransportParameters.setPath("/legstar/services/jvmquery");
        httpTransportParameters.add(getParameters());
        getParameters().put("hostCharset", "IBM01140");
        
        UmoComponentParameters umoComponentParameters = new UmoComponentParameters();
        umoComponentParameters.setImplementationName("com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
        umoComponentParameters.add(getParameters());

        File componentConfFilesDir = new File(GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateProxyHttpConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);
        compare(componentConfFilesDir,
                "mule-proxy-http-config-" + muleComponent.getName() + ".xml",
                muleComponent.getName());
    }

}
