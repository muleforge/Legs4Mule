/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.legstar.gen.TestCases;

import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

public class AntBuildMule2CixsModelTest extends TestCase {

	/** Logger. */
	private static final Log LOG = LogFactory.getLog(AntBuildMule2CixsModelTest.class);

	/** Code will be generated here. */
	private static final String GEN_SRC_DIR = "src/test/gen/ant";

    public void setUp() {
        CodeGenUtil.checkDirectory(GEN_SRC_DIR, true);
 }
	public void testMule2CixsBuild() throws Exception {

		AntBuildMule2CixsModel antModel = new AntBuildMule2CixsModel();
        antModel.setProductLocation("/Users/Fady/sandbox/legstar-1.2.0");
        antModel.setMulegenProductLocation("/Users/Fady/sandbox/mulegen-1.2.0");
        antModel.setProbeFile(new File("probe.file.tmp"));

        CixsMuleComponent cixsMuleComponent = TestCases.getLsfileaeMuleComponent();
        
        antModel.setCixsMuleComponent(cixsMuleComponent);
        antModel.setCoxbBinDir(new File("src/test/gen/target/classes"));
        antModel.setCustBinDir(new File("src/test/gen/target/classes"));
        antModel.setJaxbBinDir(new File("src/test/gen/target/classes"));
        antModel.setMuleHome("${env.MULE_HOME}");
        antModel.setTargetAntDir(new File("src/test/gen/ant"));
        antModel.setTargetBinDir(new File("src/test/gen/target/classes"));
        antModel.setTargetJarDir(new File("src/test/gen/jars"));
        antModel.setTargetMuleConfigDir(new File("src/test/gen/conf"));
        antModel.setTargetPropDir(new File("src/test/gen/properties"));
        antModel.setTargetSrcDir(new File("src/test/gen/java"));
        antModel.setHostURI("http://192.168.0.110:4081");
        antModel.setHostCharset("IBM01147");

		antModel.generateBuild(CodeGenUtil.getFile(GEN_SRC_DIR, "test.txt"));
		
		BufferedReader in = new BufferedReader(new FileReader(GEN_SRC_DIR + "/test.txt"));
		String resStr = "";
		String str = in.readLine();
		while (str != null) {
			LOG.debug(str);
			resStr += str;
			str = in.readLine();
		}
		in.close();
		assertTrue(resStr.contains("<project basedir=\"/Users/Fady/sandbox/legstar-1.2.0\" default=\"signalSuccess\" name=\"generate-mule2cics\">"));
        assertTrue(resStr.contains("<fileset dir=\"/Users/Fady/sandbox/mulegen-1.2.0\" includes=\"lib/*.jar\" />"));
		assertTrue(resStr.contains("<fileset dir=\"${env.MULE_HOME}\" includes=\"lib/mule/*.jar\" />"));
		assertTrue(resStr.contains("<taskdef name=\"mule2cixsgen\""));
		assertTrue(resStr.contains("classname=\"org.mule.providers.legstar.gen.Mule2CixsGenerator\""));
		assertTrue(resStr.replace('\\', '/').contains("<mule2cixsgen targetSrcDir=\"src/test/gen/java\""));
		assertTrue(resStr.replace('\\', '/').contains("targetMuleConfigDir=\"src/test/gen/conf\""));
        assertTrue(resStr.replace('\\', '/').contains("targetPropDir=\"src/test/gen/properties\""));
        assertTrue(resStr.replace('\\', '/').contains("targetAntDir=\"src/test/gen/ant\""));
        assertTrue(resStr.replace('\\', '/').contains("targetJarDir=\"src/test/gen/jars\""));
        assertTrue(resStr.replace('\\', '/').contains("targetBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.replace('\\', '/').contains("jaxbBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.replace('\\', '/').contains("coxbBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.replace('\\', '/').contains("custBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.contains("hostURI=\"http://192.168.0.110:4081\""));
        assertTrue(resStr.contains("hostCharset=\"IBM01147\""));

		assertTrue(resStr.contains("<cixsMuleComponent name=\"lsfileae\""));
		assertTrue(resStr.contains("packageName=\"org.mule.providers.legstar.test.lsfileae\">"));
		assertTrue(resStr.contains("<cixsOperation name=\"lsfileae\""));
		assertTrue(resStr.contains("cicsProgramName=\"LSFILEAE\""));
		assertTrue(resStr.contains("jaxbType=\"DfhcommareaType\""));
		assertTrue(resStr.contains("jaxbPackageName=\"com.legstar.test.coxb.lsfileae\""));
		assertTrue(resStr.replace('\\', '/').contains("<mkdir dir=\"src/test/gen/target/classes\"/>"));
		assertTrue(resStr.replace('\\', '/').contains("<javac srcdir=\"src/test/gen/java\""));
	}
	
}
