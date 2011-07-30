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

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for CixsMuleGenerator.
 */
public class Cixs2MuleGeneratorTest extends AbstractTestTemplate {

    /** Generator instance. */
    private Cixs2MuleGenerator _generator;

    /** True when references should be created. */
    private static final boolean CREATE_REFERENCES = false;

    /** @{inheritDoc}*/
    public void setUp() {
        emptyDir(GEN_DIR);
        _generator = new Cixs2MuleGenerator();
        _generator.init();
        setCreateReferences(CREATE_REFERENCES);
    }

    /**
     * Common initialization. Segregate output so that various tests
     * do not overwrite each other.
     * @param muleComponent the service
     */
    private void initCixsMuleComponent(final CixsMuleComponent muleComponent) {
        _generator.setCixsMuleComponent(muleComponent);
        _generator.setTargetAntDir(GEN_ANT_DIR);
        _generator.setTargetMuleConfigDir(GEN_CONF_DIR);
        _generator.setTargetSrcDir(GEN_SRC_DIR);
        _generator.setTargetDistDir(GEN_DIST_DIR);
        _generator.setTargetAppsDir(GEN_APPS_DIR);
        _generator.setJaxbBinDir(JAXB_BIN_DIR);
        _generator.setCoxbBinDir(COXB_BIN_DIR);
        _generator.setTargetBinDir(GEN_BIN_DIR);
        _generator.setCustBinDir(CUST_BIN_DIR);
        _generator.setTargetCobolDir(GEN_COBOL_DIR);
        _generator.setHostCharset(HOSTCHARSET);
        
        _generator.getUmoComponentTargetParameters().setImplementationName(
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
            assertEquals("You must provide a service name",
                    e.getCause().getMessage());
        }
        try {
            generator.getCixsMuleComponent().setName("muleComponentName");
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
            assertEquals("TargetDistDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetDistDir(GEN_DIST_DIR);
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
            assertEquals("TargetAppsDir: No directory name was specified",
                    e.getMessage());
        }
        try {
            generator.setTargetAppsDir(GEN_APPS_DIR);
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
        
        _generator.setSampleConfigurationTransport("http");
        _generator.getHttpTransportParameters().setHost("megamouss");
        _generator.getHttpTransportParameters().setPort(8083);

        generateAndCheck();
    }

    /**
     * Check generation for a POJO.
     * @throws Exception if generation fails
     */
    public final void testJvmQueryWmq() throws Exception {
        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        initCixsMuleComponent(muleComponent);

        _generator.setSampleConfigurationTransport("wmq");
        _generator.getWmqTransportParameters().setConnectionFactory("ConnectionFactory");
        _generator.getWmqTransportParameters().setJndiUrl(
                "src/test/resources/host-jndi");
        _generator.getWmqTransportParameters().setJndiContextFactory(
                "org.mule.transport.legstar.config.HostContextFactory");
        _generator.getWmqTransportParameters().setZosQueueManager("CSQ1");
        _generator.getWmqTransportParameters().setRequestQueue(
                "JVMQUERY.POJO.REQUEST.QUEUE");
        _generator.getWmqTransportParameters().setReplyQueue(
                "JVMQUERY.POJO.REPLY.QUEUE");

        generateAndCheck();
    }

    /**
     * Generate artifacts and check results.
     * @throws Exception if something goes wrong
     */
    public void generateAndCheck() throws Exception {
    	_generator.getAntModel().setSampleConfigurationFileName(
				getProxyConfigurationFileName(
						_generator.getAntModel().getCixsMuleComponent().getName(),
						_generator.getAntModel().getSampleConfigurationTransport()));

        _generator.execute();
        checkResults();
    }

    /**
     * Check that all expected artifacts are generated.
     * @param muleComponent the service model
     * @param sampleConfigurationPayloadType sample payload type
     * @throws Exception if check fails
     */
    private void checkResults() throws Exception {

        String componentName = _generator.getAntModel().getCixsMuleComponent().getName();

        compare(_generator.getTargetAntDir(),
        		AbstractCixsMuleGenerator.CREATE_ZIP_FILE_NAME, componentName);
        compare(_generator.getTargetAntDir(),
        		AbstractCixsMuleGenerator.DEPLOY_ZIP_FILE_NAME, componentName);

        compare(_generator.getTargetMuleConfigDir(),
        		_generator.getAntModel().getSampleConfigurationFileName(),
                componentName);

        for (CixsOperation operation : _generator.getAntModel().getCixsMuleComponent().getCixsOperations()) {
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    _generator.getTargetSrcDir(), operation.getPackageName(), true);

            switch (_generator.getAntModel().getSampleConfigurationPayloadType()) {
            case JAVA:
                compare(operationClassFilesDir, "HostTo"
                        + operation.getRequestHolderType()
                        + "MuleTransformer.java", componentName);
                compare(operationClassFilesDir, operation
                        .getRequestHolderType()
                        + "ToHostMuleTransformer.java", componentName);
                compare(operationClassFilesDir, "HostTo"
                        + operation.getResponseHolderType()
                        + "MuleTransformer.java", componentName);
                compare(operationClassFilesDir, operation
                        .getResponseHolderType()
                        + "ToHostMuleTransformer.java", componentName);

                break;
            case XML:
                compare(operationClassFilesDir, "HostTo"
                        + operation.getRequestHolderType()
                        + "XmlMuleTransformer.java", componentName);
                compare(operationClassFilesDir, operation
                        .getRequestHolderType()
                        + "XmlToHostMuleTransformer.java", componentName);
                compare(operationClassFilesDir, "HostTo"
                        + operation.getResponseHolderType()
                        + "XmlMuleTransformer.java", componentName);
                compare(operationClassFilesDir, operation
                        .getResponseHolderType()
                        + "XmlToHostMuleTransformer.java", componentName);
                break;
            default:
                break;
            }

            String expectedCobolRes = "";
            if (_generator.getSampleConfigurationTransport().equalsIgnoreCase("http")) {
                expectedCobolRes = getSource(
                        "/org/mule/transport/legstar/gen/" + componentName + '/'
                        + operation.getCicsProgramName()
                        + "-DFHWBCLI.cbl.txt");
            }
            if (_generator.getSampleConfigurationTransport().equalsIgnoreCase("wmq")) {
                expectedCobolRes = getSource(
                        "/org/mule/transport/legstar/gen/" + componentName + '/'
                        + operation.getCicsProgramName()
                        + "-MQ.cbl.txt");
            }
                

            String res = getSource(GEN_COBOL_DIR, 
                    operation.getCicsProgramName() + ".cbl");
            assertEquals(expectedCobolRes, res);
        }

    }
}
