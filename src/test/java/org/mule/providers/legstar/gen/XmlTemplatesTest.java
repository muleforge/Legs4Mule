package org.mule.providers.legstar.gen;

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for miscellaneous xml artifacts velocity template.
 */
public class XmlTemplatesTest extends AbstractTestTemplate {

    public void testAntBuildJar() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        
        getParameters().put("jarDir", "${env.MULE_HOME}/lib/user");
        getParameters().put("jaxbBinDir", "D:/Legsem/Legstar/Dev/trunk/legstar-jaxbgen-cases/target/classes");
        getParameters().put("coxbBinDir", "D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases/target/classes");
        getParameters().put("cixsBinDir", "D:/Fady/sandbox/workspace2/legstar-mule/target/gen-classes");
        getParameters().put("custBinDir", "D:/Legsem/Legstar/Dev/trunk/legstar-coxbgen-cases//target/classes");
        getParameters().put("propDir", GEN_PROP_DIR);

        String componentAntFilesLocation = GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_ANT_DIR;
        CodeGenUtil.checkDirectory(componentAntFilesLocation, true);
        CixsMuleGenerator.generateAntBuildJar(
                muleComponent, getParameters(), componentAntFilesLocation);
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

    public void testStandaloneConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();

        String componentConfFilesLocation = GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR;
        CodeGenUtil.checkDirectory(componentConfFilesLocation, true);
        CixsMuleGenerator.generateStandaloneConfigXml(
                muleComponent, getParameters(), componentConfFilesLocation);
        String resStr = getSource(
                componentConfFilesLocation,
                "mule-standalone-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-standalone-lsfileae-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<model name=\"lsfileaeModel\">"));
        assertTrue(resStr.contains("<mule-descriptor name=\"lsfileaeUMO\" implementation=\"org.mule.providers.legstar.test.lsfileae.LsfileaeImpl\">"));
        assertTrue(resStr.contains("<endpoint address=\"tcp://localhost:3213/lsfileae\""));
    }

    public void testBridgeConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();

        String componentConfFilesLocation = GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR;
        CodeGenUtil.checkDirectory(componentConfFilesLocation, true);
        CixsMuleGenerator.generateBridgeConfigXml(
                muleComponent, getParameters(), componentConfFilesLocation);
        String resStr = getSource(
                componentConfFilesLocation,
                "mule-bridge-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-bridge-lsfileae-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<transformer name=\"HostByteArrayToDfhcommareaType\" className=\"org.mule.providers.legstar.test.lsfileae.HostByteArrayToDfhcommareaType\"/>"));
        assertTrue(resStr.contains("<transformer name=\"DfhcommareaTypeToHostByteArray\" className=\"org.mule.providers.legstar.test.lsfileae.DfhcommareaTypeToHostByteArray\"/>"));
        assertTrue(resStr.contains("<mule-descriptor name=\"lsfileaeBridgeUMO\""));
        assertTrue(resStr.contains("responseTransformers=\"HostByteArrayToDfhcommareaType\""));
        assertTrue(resStr.contains("transformers=\"DfhcommareaTypeToHostByteArray\" >"));
        assertTrue(resStr.contains("<property name=\"programPropFileName\" value=\"lsfileae.properties\"/>"));
    }

    public void testLocalConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();

        String componentConfFilesLocation = GEN_RES_DIR + '/' + muleComponent.getName() + '/' + GEN_CONF_DIR;
        CodeGenUtil.checkDirectory(componentConfFilesLocation, true);
        CixsMuleGenerator.generateLocalConfigXml(
                muleComponent, getParameters(), componentConfFilesLocation);
        String resStr = getSource(
                componentConfFilesLocation,
                "mule-local-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-local-lsfileae-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<transformer name=\"HostByteArrayToDfhcommareaType\" className=\"org.mule.providers.legstar.test.lsfileae.HostByteArrayToDfhcommareaType\"/>"));
        assertTrue(resStr.contains("<transformer name=\"DfhcommareaTypeToHttpResponse\" className=\"org.mule.providers.legstar.test.lsfileae.DfhcommareaTypeToHttpResponse\"/>"));
        assertTrue(resStr.contains("<mule-descriptor name=\"lsfileaeLocalUMO\""));
        assertTrue(resStr.contains("responseTransformers=\"DfhcommareaTypeToHttpResponse\""));
        assertTrue(resStr.contains("transformers=\"HostByteArrayToDfhcommareaType\""));
    }
}
