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
import java.util.ArrayList;
import java.util.List;

import org.mule.transport.legstar.model.CixsMuleComponent;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for CixsMuleGenerator.
 */
public class Mule2CixsGeneratorTest extends AbstractTestTemplate {

    /** Generator instance. */
    private Mule2CixsGenerator _generator;
    
    /** True when references should be created. */
    private static final boolean CREATE_REFERENCES = false;

    /** @{inheritDoc}*/
    public void setUp() {
        emptyDir(GEN_DIR);
        _generator = new Mule2CixsGenerator();
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
        _generator.setTargetJarDir(GEN_JAR_DIR);
        _generator.setJaxbBinDir(JAXB_BIN_DIR);
        _generator.setTargetBinDir(GEN_BIN_DIR);
        _generator.setHostCharset(HOSTCHARSET);
        _generator.getHttpTransportParameters().setHost("mainframe");
        _generator.getHttpTransportParameters().setPort(4081);

        /* We want to share expected results with XmlTemplatesTest */
        List < String > pathElements = new ArrayList < String >();
        pathElements.add("c:/some.additional.jar");
        muleComponent.setMuleStartupPathElements(pathElements);
    }

    /**
     * Check controls on input make file.
     */
    public final void testInputValidation() {
        Mule2CixsGenerator generator = new Mule2CixsGenerator();
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
                    Samples.getLsfileaeMuleComponent().getCixsOperations().get(0));
            generator.execute();
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /**
     * Check generation for operation with identical input and output structures.
     * @throws Exception if generation fails
     */
    public final void testLsfileaeGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        initCixsMuleComponent(muleComponent);
        generateAndCheck();
        
        _generator.setSampleConfigurationPayloadType("XML");
        generateAndCheck();
    }

    /**
     * Check generation for operation with different input and output structures.
     * @throws Exception if generation fails
     */
    public final void testLsfilealGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfilealMuleComponent();
        initCixsMuleComponent(muleComponent);
        generateAndCheck();
    }

    /**
     * Check generation for CICS containers target component.
     * @throws Exception if generation fails
     */
    public final void testLsfileacGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        initCixsMuleComponent(muleComponent);
        generateAndCheck();
    }


    /**
     * Check generation for multiple operations components.
     * @throws Exception if generation fails
     */
    public final void testLsfileaxGenerateClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileaxMuleComponent();
        initCixsMuleComponent(muleComponent);
        generateAndCheck();
    }

    /**
     * Check generation for multiple operations components with XML payload.
     * @throws Exception if generation fails
     */
    public final void testLsfileaxGenerateXMLClasses() throws Exception {
        CixsMuleComponent muleComponent = Samples.getLsfileaxMuleComponent();
        initCixsMuleComponent(muleComponent);
        _generator.setSampleConfigurationPayloadType("XML");
        generateAndCheck();
    }

    /**
     * Generate artifacts and check results.
     * @throws Exception if something goes wrong
     */
    public void generateAndCheck() throws Exception {
    	_generator.getAntModel().setSampleConfigurationFileName(
				getAdapterConfigurationFileName(
						_generator.getAntModel().getCixsMuleComponent().getName(),
						_generator.getAntModel().getSampleConfigurationTransport(),
						_generator.getAntModel().getSampleConfigurationPayloadType(),
						_generator.getAntModel().getSampleConfigurationHostMessagingType()));

        _generator.execute();
        checkResults();
    }

    /**
     * Check that all expected artifacts are generated.
     */
    private void checkResults() {
        
        String componentName = _generator.getAntModel().getCixsMuleComponent().getName();
        
    	compare(_generator.getTargetAntDir(),
                "build-jar.xml", componentName);
        compare(_generator.getTargetAntDir(),
                "deploy.xml", componentName);
        
        compare(_generator.getTargetMuleConfigDir(),
        		_generator.getAntModel().getSampleConfigurationFileName(),
                componentName);
        
        for (CixsOperation operation : _generator.getAntModel().getCixsMuleComponent().getCixsOperations()) {
            
            File operationClassFilesDir = CodeGenUtil.classFilesLocation(
                    _generator.getTargetSrcDir(), operation.getPackageName(), false);
            
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
        }
    }

}
