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
import org.mule.transport.legstar.model.WmqTransportParameters;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationPayloadType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;

import com.legstar.cixs.gen.model.options.HttpTransportParameters;
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
        getParameters().put("targetMuleConfigDir", GEN_CONF_DIR.getPath());
        getParameters().put("targetDistDir", GEN_DIST_DIR.getPath());
        getParameters().put("generateBaseDir", ".");
        getParameters().put("targetAntDir", GEN_ANT_DIR.getPath());
    }

    /**
     * build-jar.xml creates the mule component ready for deployment.
     * @throws Exception if generation fails
     */
    public void testAntBuildJar() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        getParameters().put("generationTarget", "adapter");

        File componentAntFilesDir = new File(GEN_ANT_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        String filename = Mule2CixsGenerator.generateAntBuildJar(
                muleComponent, getParameters(), componentAntFilesDir);
        compare(componentAntFilesDir, filename,
                muleComponent.getName());
    }

    /**
     * deploy.xml deploys the jar archive to a Mule server.
     * @throws Exception if generation fails
     */
    public void testAntDeploy() throws Exception {

        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        getParameters().put("generationTarget", "adapter");

        File componentAntFilesDir = new File(GEN_ANT_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        String filename = Mule2CixsGenerator.generateAntDeploy(
                muleComponent, getParameters(), componentAntFilesDir);
        compare(componentAntFilesDir, filename,
                muleComponent.getName());
    }

    /**
     * Creates a mule adapter config generation.
     * @param muleComponent the component under test
     * @param transport HTTP or WMQ
     * @param payload XML or JAVA
     * @param messaging the type of messaging expected by the mainframe
     * @throws Exception if generation fails
     */
    private void testAdapterConfigXml(
            final CixsMuleComponent muleComponent,
            final SampleConfigurationTransport transport,
            final SampleConfigurationPayloadType payload,
            final SampleConfigurationHostMessagingType messaging) throws Exception {

        getParameters().put("hostCharset", "IBM01140");
        switch (transport) {
        case HTTP:
            addAdapterHttpParameters();
            break;
        case WMQ:
            addAdapterWmqParameters();
            break;
        default:
            break;
        }

        File componentConfFilesDir = new File(
                GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        String fileName = AbstractCixsMuleGenerator.generateAdapterConfigXml(
                muleComponent, getParameters(), componentConfFilesDir,
                transport,
                payload,
                messaging);

        compare(componentConfFilesDir, fileName, muleComponent.getName());
    }

    /**
     * Creates a mule proxy config generation.
     * @param muleComponent the component under test
     * @param transport HTTP or WMQ
     * @throws Exception if generation fails
     */
    private void testProxyConfigXml(
            final CixsMuleComponent muleComponent,
            final SampleConfigurationTransport transport) throws Exception {

        getParameters().put("hostCharset", "IBM01140");

        UmoComponentParameters umoComponentParameters = new UmoComponentParameters();
        umoComponentParameters.setImplementationName("com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
        umoComponentParameters.add(getParameters());

        switch (transport) {
        case HTTP:
            addProxyHttpParameters();
            break;
        case WMQ:
            addProxyWmqParameters();
            break;
        default:
            break;
        }

        File componentConfFilesDir = new File(
                GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        String fileName = AbstractCixsMuleGenerator.generateProxyConfigXml(
                muleComponent, getParameters(), componentConfFilesDir,
                transport);

        compare(componentConfFilesDir, fileName, muleComponent.getName());
    }

    /**
     * Adapter HTTP parameter set.
     */
    private void addAdapterHttpParameters() {
        HttpTransportParameters httpTransportParameters = new HttpTransportParameters();
        httpTransportParameters.setHost(AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST);
        httpTransportParameters.setPort(AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT);
        httpTransportParameters.setPath(AntBuildMule2CixsModel.ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH);
        httpTransportParameters.add(getParameters());
    }

    /**
     * Adapter WMQ parameter set.
     */
    private void addAdapterWmqParameters() {
        WmqTransportParameters wmqTransportParameters = new WmqTransportParameters();
        wmqTransportParameters.setJndiUrl(
                WmqTransportParameters.DEFAULT_JNDI_FS_DIRECTORY);
        wmqTransportParameters.setJndiContextFactory(
                WmqTransportParameters.DEFAULT_JNDI_CONTEXT_FACTORY);
        wmqTransportParameters.setConnectionFactory("ConnectionFactory");
        wmqTransportParameters.setRequestQueue("CICSA.REQUEST.QUEUE");
        wmqTransportParameters.setReplyQueue("CICSA.REPLY.QUEUE");
        wmqTransportParameters.add(getParameters());
    }

    /**
     * Proxy HTTP parameter set.
     */
    private void addProxyHttpParameters() {
        HttpTransportParameters httpTransportParameters = new HttpTransportParameters();
        httpTransportParameters.setHost("megamouss");
        httpTransportParameters.setPort(8083);
        httpTransportParameters.setPath("/legstar/services/jvmquery");
        httpTransportParameters.add(getParameters());
    }

    /**
     * Proxy WMQ parameter set.
     */
    private void addProxyWmqParameters() {
        WmqTransportParameters wmqTransportParameters = new WmqTransportParameters();
        wmqTransportParameters.setJndiUrl(
                WmqTransportParameters.DEFAULT_JNDI_FS_DIRECTORY);
        wmqTransportParameters.setJndiContextFactory(
                WmqTransportParameters.DEFAULT_JNDI_CONTEXT_FACTORY);
        wmqTransportParameters.setConnectionFactory("ConnectionFactory");
        wmqTransportParameters.setRequestQueue("JVMQUERY.POJO.REQUEST.QUEUE");
        wmqTransportParameters.setReplyQueue("JVMQUERY.POJO.REPLY.QUEUE");
        wmqTransportParameters.add(getParameters());
    }
    /**
     * LSFILEAE HTTP JAVA test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaeAdapterHttpJavaConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaeMuleComponent(),
                SampleConfigurationTransport.HTTP,
                SampleConfigurationPayloadType.JAVA,
                SampleConfigurationHostMessagingType.LEGSTAR);
    }

    /**
     * LSFILEAE HTTP XML test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaeAdapterHttpXmlConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaeMuleComponent(),
                SampleConfigurationTransport.HTTP,
                SampleConfigurationPayloadType.XML,
                SampleConfigurationHostMessagingType.LEGSTAR);
    }

    /**
     * LSFILEAE WMQ JAVA LEGSTAR test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaeAdapterWmqJavaLegstarConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaeMuleComponent(),
                SampleConfigurationTransport.WMQ,
                SampleConfigurationPayloadType.JAVA,
                SampleConfigurationHostMessagingType.LEGSTAR);
    }

    /**
     * LSFILEAE WMQ JAVA MQCIH test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaeAdapterWmqJavaMqcihConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaeMuleComponent(),
                SampleConfigurationTransport.WMQ,
                SampleConfigurationPayloadType.JAVA,
                SampleConfigurationHostMessagingType.MQCIH);
    }

    /**
     * LSFILEAX HTTP JAVA test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaxAdapterHttpJavaConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaxMuleComponent(),
                SampleConfigurationTransport.HTTP,
                SampleConfigurationPayloadType.JAVA,
                SampleConfigurationHostMessagingType.LEGSTAR);
    }

    /**
     * LSFILEAX HTTP XML test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaxAdapterHttpXmlConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaxMuleComponent(),
                SampleConfigurationTransport.HTTP,
                SampleConfigurationPayloadType.XML,
                SampleConfigurationHostMessagingType.LEGSTAR);
    }

    /**
     * LSFILEAX WMQ JAVA test case.
     * @throws Exception if generation fails
     */
    public void testLsfileaxAdapterWmqJavaConfigXml() throws Exception {
        testAdapterConfigXml(Samples.getLsfileaxMuleComponent(),
                SampleConfigurationTransport.WMQ,
                SampleConfigurationPayloadType.JAVA,
                SampleConfigurationHostMessagingType.LEGSTAR);
    }

    /**
     * JVMQUERY HTTP test case.
     * @throws Exception if generation fails
     */
    public void testJvmqueryProxyHttpConfigXml() throws Exception {
        testProxyConfigXml(Samples.getJvmQueryMuleComponent(),
                SampleConfigurationTransport.HTTP);
    }

    /**
     * JVMQUERY WMQ test case.
     * @throws Exception if generation fails
     */
    public void testJvmqueryProxyWmqConfigXml() throws Exception {
        testProxyConfigXml(Samples.getJvmQueryMuleComponent(),
                SampleConfigurationTransport.WMQ);
    }

}
