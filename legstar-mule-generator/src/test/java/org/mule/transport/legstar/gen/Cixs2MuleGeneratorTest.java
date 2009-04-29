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

import org.mule.transport.legstar.model.CixsMuleComponent;
import org.mule.transport.legstar.model.WmqTransportParameters;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for CixsMuleGenerator.
 */
public class Cixs2MuleGeneratorTest extends AbstractTestTemplate {

    /** Generator instance. */
    private Cixs2MuleGenerator mGenerator;

    /** @{inheritDoc}*/
    public void setUp() {
        emptyDir(GEN_DIR);
        mGenerator = new Cixs2MuleGenerator();
        mGenerator.init();
    }

    /**
     * Common initialization. Segregate output so that various tests
     * do not overwrite each other.
     * @param muleComponent the service
     */
    private void initCixsMuleComponent(final CixsMuleComponent muleComponent) {
        mGenerator.setCixsMuleComponent(muleComponent);
        mGenerator.setTargetAntDir(GEN_ANT_DIR);
        mGenerator.setTargetPropDir(GEN_PROP_DIR);
        mGenerator.setTargetMuleConfigDir(GEN_CONF_DIR);
        mGenerator.setTargetSrcDir(GEN_SRC_DIR);
        mGenerator.setTargetJarDir(GEN_JAR_DIR);
        mGenerator.setJaxbBinDir(JAXB_BIN_DIR);
        mGenerator.setCoxbBinDir(COXB_BIN_DIR);
        mGenerator.setTargetBinDir(GEN_BIN_DIR);
        mGenerator.setCustBinDir(CUST_BIN_DIR);
        mGenerator.setTargetCobolDir(GEN_COBOL_DIR);
        mGenerator.setHostCharset(HOSTCHARSET);
        
        mGenerator.getUmoComponentTargetParameters().setImplementationName(
                "com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
    }

    /**
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        Cixs2MuleGenerator generator = new Cixs2MuleGenerator();
        try {
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:"
                    + " JaxbBinDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setJaxbBinDir(new File("target/classes"));
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("You must specify a service description",
                    e.getCause().getMessage());
        }
        CixsMuleComponent muleComponent = new CixsMuleComponent();
        try {
            generator.setCixsMuleComponent(muleComponent);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("You must provide a service name",
                    e.getCause().getMessage());
        }
        try {
            muleComponent.setName("muleComponentName");
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetSrcDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetSrcDir(GEN_SRC_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetAntDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetAntDir(GEN_ANT_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetMuleConfigDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetMuleConfigDir(GEN_CONF_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetBinDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetBinDir(GEN_BIN_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("TargetJarDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetJarDir(GEN_JAR_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("No operation was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.getCixsMuleComponent().addCixsOperation(
                    Samples.getJvmQueryMuleComponent().getCixsOperations().get(0));
            generator.getCixsMuleComponent().getCixsOperations().get(0).setCicsProgramName("");
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("Operation must specify a CICS program name",
                    e.getCause().getMessage());
        }
        try {
            generator.getCixsMuleComponent().getCixsOperations().get(0).setCicsProgramName("PROGRAM");
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.IllegalArgumentException:"
                    + " TargetCobolDir: No directory name was specified",
                    e.getCause().getMessage());
        }
        try {
            generator.setTargetCobolDir(GEN_COBOL_DIR);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("Missing target UMO component implementation",
                    e.getCause().getMessage());
        }
        try {
            generator.getUmoComponentTargetParameters().setImplementationName(
                    "com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
            generator.getHttpTransportParameters().setHost(null);
            generator.execute();
            fail();
        } catch (Exception e) {
            assertEquals("You must specify an HTTP host",
                    e.getCause().getMessage());
        }
        try {
            generator.getHttpTransportParameters().setHost("server.com");
            generator.getHttpTransportParameters().setPath(null);
            generator.execute();
            assertEquals("/legstar/services/muleComponentName", generator.getHttpTransportParameters().getPath());
            generator.getHttpTransportParameters().setPath("doesnotstartwithslash");
            generator.execute();
        } catch (Exception e) {
            assertEquals("The HTTP path must start with the / character",
                    e.getCause().getMessage());
        }
        try {
            generator.getHttpTransportParameters().setPath("/a");
            generator.execute();
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /**
     * Check generation for a POJO.
     * @throws Exception if generation fails
     */
    public final void testJvmQueryHttp() throws Exception {
        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        initCixsMuleComponent(muleComponent);
        
        mGenerator.setSampleConfigurationTransport("http");
        mGenerator.getHttpTransportParameters().setHost("megamouss");
        mGenerator.getHttpTransportParameters().setPort(8083);

        mGenerator.execute();
        checkResults(muleComponent);
    }

    /**
     * Check generation for a POJO.
     * @throws Exception if generation fails
     */
    public final void testJvmQueryWmq() throws Exception {
        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        initCixsMuleComponent(muleComponent);

        mGenerator.setSampleConfigurationTransport("wmq");
        mGenerator.getWmqTransportParameters().setConnectionFactory("ConnectionFactory");
        mGenerator.getWmqTransportParameters().setJndiUrl(
                WmqTransportParameters.DEFAULT_JNDI_FS_DIRECTORY);
        mGenerator.getWmqTransportParameters().setJndiContextFactory(
                WmqTransportParameters.DEFAULT_JNDI_CONTEXT_FACTORY);
        mGenerator.getWmqTransportParameters().setZosQueueManager("CSQ1");
        mGenerator.getWmqTransportParameters().setRequestQueue(
                "JVMQUERY.POJO.REQUEST.QUEUE");
        mGenerator.getWmqTransportParameters().setReplyQueue(
                "JVMQUERY.POJO.REPLY.QUEUE");

        mGenerator.execute();
        checkResults(muleComponent);
    }

    /**
     * Check that all expected artifacts are generated.
     * @param muleComponent the service model
     * @throws Exception if check fails
     */
    private void checkResults(final CixsMuleComponent muleComponent) throws Exception {

        compare(mGenerator.getTargetAntDir(),
                "build.xml", muleComponent.getName());
        if (mGenerator.getSampleConfigurationTransport().equalsIgnoreCase("http")) {
            compare(mGenerator.getTargetMuleConfigDir(),
                    "mule-proxy-http-config-" + muleComponent.getName() + ".xml",
                    muleComponent.getName());
        }
        if (mGenerator.getSampleConfigurationTransport().equalsIgnoreCase("wmq")) {
            compare(mGenerator.getTargetMuleConfigDir(),
                    "mule-proxy-wmq-config-" + muleComponent.getName() + ".xml",
                    muleComponent.getName());
        }

        for (CixsOperation operation : muleComponent.getCixsOperations()) {
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    mGenerator.getTargetSrcDir(), operation.getPackageName(), true);

            compare(operationClassFilesDir,
                    "HostTo" + operation.getRequestHolderType() + "MuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getRequestHolderType() + "ToHostMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    "HostTo" + operation.getResponseHolderType() + "MuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getResponseHolderType() + "ToHostMuleTransformer.java",
                    muleComponent.getName());

            compare(operationClassFilesDir,
                    "HostTo" + operation.getRequestHolderType() + "XmlMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getRequestHolderType() + "XmlToHostMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    "HostTo" + operation.getResponseHolderType() + "XmlMuleTransformer.java",
                    muleComponent.getName());
            compare(operationClassFilesDir,
                    operation.getResponseHolderType() + "XmlToHostMuleTransformer.java",
                    muleComponent.getName());

            String expectedCobolRes = "";
            if (mGenerator.getSampleConfigurationTransport().equalsIgnoreCase("http")) {
                expectedCobolRes = getSource(
                        "/org/mule/transport/legstar/gen/" + muleComponent.getName() + '/'
                        + operation.getCicsProgramName()
                        + "-DFHWBCLI.cbl.txt");
            }
            if (mGenerator.getSampleConfigurationTransport().equalsIgnoreCase("wmq")) {
                expectedCobolRes = getSource(
                        "/org/mule/transport/legstar/gen/" + muleComponent.getName() + '/'
                        + operation.getCicsProgramName()
                        + "-MQ.cbl.txt");
            }
                

            String res = getSource(GEN_COBOL_DIR, 
                    operation.getCicsProgramName() + ".cbl");
            /* The substring(1060) is necessary because the template is coming from
             * core legstar and still has a variable part containing the date and
             * time of generation. 1060 will get us beyond the cobol header.*/
            assertEquals(expectedCobolRes.substring(1060), res.substring(1060));
        }

    }
}
