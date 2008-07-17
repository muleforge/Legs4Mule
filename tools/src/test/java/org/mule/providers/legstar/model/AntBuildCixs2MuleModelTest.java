package org.mule.providers.legstar.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.legstar.gen.TestCases;

import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

public class AntBuildCixs2MuleModelTest extends TestCase {

	/** Logger. */
	private static final Log LOG = LogFactory.getLog(AntBuildCixs2MuleModelTest.class);

	/** Code will be generated here. */
	private static final String GEN_SRC_DIR = "src/test/gen/ant";
	
	
	public void setUp() {
	       CodeGenUtil.checkDirectory(GEN_SRC_DIR, true);
	}

	public void testCix2MuleBuild() throws Exception {

		AntBuildCixs2MuleModel antModel = new AntBuildCixs2MuleModel();
        antModel.setProductLocation("/Users/Fady/sandbox/legstar-1.2.0");
        antModel.setProbeFile(new File("probe.file.tmp"));

        CixsMuleComponent cixsMuleComponent = TestCases.getJvmQueryMuleComponent();
        
        antModel.setCixsMuleComponent(cixsMuleComponent);
        antModel.setCoxbBinDir(new File("src/test/gen/target/classes"));
        antModel.setCustBinDir(new File("src/test/gen/target/classes"));
        antModel.setJaxbBinDir(new File("src/test/gen/target/classes"));
        antModel.setMuleHome("${env.MULE_HOME}");
        antModel.setTargetAntDir(new File("src/test/gen/ant"));
        antModel.setTargetBinDir(new File("src/test/gen/target/classes"));
        antModel.setTargetCobolDir(new File("src/test/gen/target/cobol"));
        antModel.setTargetJarDir(new File("src/test/gen/jars"));
        antModel.setTargetMuleConfigDir(new File("src/test/gen/conf"));
        antModel.setTargetPropDir(new File("src/test/gen/properties"));
        antModel.setTargetSrcDir(new File("src/test/gen/java"));
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
		assertTrue(resStr.contains("<project basedir=\"/Users/Fady/sandbox/legstar-1.2.0\" default=\"signalSuccess\" name=\"generate-cixs2mule\">"));
		assertTrue(resStr.contains("<fileset dir=\"${env.MULE_HOME}\" includes=\"lib/mule/*.jar\" />"));
        assertTrue(resStr.contains("<fileset dir=\"${env.MULE_HOME}\" includes=\"lib/user/*.jar\" />"));
        assertTrue(resStr.contains("<fileset dir=\"${env.MULE_HOME}\" includes=\"lib/opt/*.jar\" />"));
		assertTrue(resStr.contains("<taskdef name=\"cixs2mulegen\""));
		assertTrue(resStr.contains("classname=\"org.mule.providers.legstar.gen.Cixs2MuleGenerator\""));
		assertTrue(resStr.replace('\\', '/').contains("<cixs2mulegen targetSrcDir=\"src/test/gen/java\""));
		assertTrue(resStr.replace('\\', '/').contains("targetMuleConfigDir=\"src/test/gen/conf\""));
        assertTrue(resStr.replace('\\', '/').contains("targetPropDir=\"src/test/gen/properties\""));
        assertTrue(resStr.replace('\\', '/').contains("targetAntDir=\"src/test/gen/ant\""));
        assertTrue(resStr.replace('\\', '/').contains("targetJarDir=\"src/test/gen/jars\""));
        assertTrue(resStr.replace('\\', '/').contains("targetBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.replace('\\', '/').contains("targetCobolDir=\"src/test/gen/target/cobol\""));
        assertTrue(resStr.replace('\\', '/').contains("jaxbBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.replace('\\', '/').contains("coxbBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.replace('\\', '/').contains("custBinDir=\"src/test/gen/target/classes\""));
        assertTrue(resStr.contains("serviceURI=\"http://megamouss:8083\""));
        assertTrue(resStr.contains("hostCharset=\"IBM01147\""));

		assertTrue(resStr.contains("implementationClassName=\"com.legstar.xsdc.test.cases.jvmquery.JVMQuery\""));
        assertTrue(resStr.contains("packageName=\"org.mule.providers.legstar.test.jvmquery\""));
		assertTrue(resStr.contains("name=\"jvmquery\""));
		assertTrue(resStr.contains("<cixsOperation name=\"jvmquery\""));
        assertTrue(resStr.contains("cicsProgramName=\"JVMQUERY\""));
		assertTrue(resStr.contains("jaxbType=\"JvmQueryRequestType\""));
		assertTrue(resStr.contains("jaxbPackageName=\"com.legstar.test.coxb.jvmquery\""));
        assertTrue(resStr.contains("jaxbType=\"JvmQueryReplyType\""));
        assertTrue(resStr.contains("jaxbPackageName=\"com.legstar.test.coxb.jvmquery\""));
		assertTrue(resStr.replace('\\', '/').contains("<mkdir dir=\"src/test/gen/target/classes\"/>"));
		assertTrue(resStr.replace('\\', '/').contains("<javac srcdir=\"src/test/gen/java\""));
	}
	
}
