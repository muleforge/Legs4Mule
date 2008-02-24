package org.mule.providers.legstar.gen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.mule.providers.legstar.model.CixsMuleComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

/**
 * Test cases for miscellaneous artifacts velocity template.
 */
public class MiscTemplatesTest extends TestCase {

    /** General location for generated artifacts. */
    private static final String GEN_DIR = "D:/Fady/sandbox/workspace2/legstar-mule/src/test/gen/resources";

    /** Property files will be generated here. */
    private static final String GEN_PROP_DIR = GEN_DIR;

    /** Ant scripts will be generated here. */
    private static final String GEN_ANT_DIR = "ant";
    
    /** Configuration files will be generated here. */
    private static final String GEN_CONF_DIR = "conf";
    
    private Map <String, Object> mParameters;
    
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(
            InterfaceTemplateTest.class);

    /** @{inheritDoc}*/
    @Override
    public final void setUp() {
        try {
            CodeGenUtil.initVelocity();
            mParameters = new HashMap <String, Object>();
            CodeGenHelper helper = new CodeGenHelper();
            mParameters.put("helper", helper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void testAntBuildJar() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        
        mParameters.put("jarDir", "${env.MULE_HOME}/lib/user");
        mParameters.put("jaxbBinDir", "D:/Legsem/Legstar/Dev/trunk/legstar-jaxbgen-cases/target/classes");
        mParameters.put("coxbBinDir", "D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases/target/classes");
        mParameters.put("cixsBinDir", "D:/Fady/sandbox/workspace2/legstar-mule/target/gen-classes");
        mParameters.put("custBinDir", "D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases//target/classes");
        mParameters.put("propDir", GEN_PROP_DIR);

        String componentAntFilesLocation = GEN_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR;
        CodeGenUtil.checkDirectory(componentAntFilesLocation, true);
        CixsMuleGenerator.generateAntBuildJar(
                muleComponent, mParameters, componentAntFilesLocation);
        String resStr = getSource(
                componentAntFilesLocation,
                "build.xml");

        assertTrue(resStr.contains("<property name=\"jarFile\" value=\"${env.MULE_HOME}/lib/user/mule-legstar-lsfileae.jar\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"D:/Legsem/Legstar/Dev/trunk/legstar-jaxbgen-cases/target/classes\">"));
        assertTrue(resStr.contains("<include name=\"com/legstar/test/coxb/lsfileae/*.class\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases/target/classes\">"));
        assertTrue(resStr.contains("<include name=\"com/legstar/test/coxb/lsfileae/bind/*.class\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"D:/Fady/sandbox/workspace2/legstar-mule/target/gen-classes\">"));
        assertTrue(resStr.contains("<include name=\"**/lsfileae/**/*.class\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"D:/Fady/sandbox/workspace2/legstar-mule/src/test/gen/resources\">"));
        assertTrue(resStr.contains("<include name=\"lsfileae.properties\"/>"));
    }

    public void testConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();

        String componentConfFilesLocation = GEN_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR;
        CodeGenUtil.checkDirectory(componentConfFilesLocation, true);
        CixsMuleGenerator.generateConfigXml(
                muleComponent, mParameters, componentConfFilesLocation);
        String resStr = getSource(
                componentConfFilesLocation,
                "mule-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-lsfileae-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<model name=\"lsfileaeModel\">"));
        assertTrue(resStr.contains("<mule-descriptor name=\"lsfileaeUMO\" implementation=\"org.mule.providers.legstar.test.lsfileae.LsfileaeImpl\">"));
        assertTrue(resStr.contains("<endpoint address=\"tcp://localhost:3213/lsfileae\""));
    }

    private String getSource(String srcLocation, String srcName) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(srcLocation + '/' + srcName));
        String resStr = "";
        String str = in.readLine();
        while (str != null) {
            LOG.debug(str);
            resStr += str;
            str = in.readLine();
        }
        in.close();
        return resStr;
    }


}
