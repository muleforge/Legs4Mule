package org.mule.providers.legstar.gen;

import java.io.File;

import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.codegen.CodeGenUtil;

/**
 * Test cases for miscellaneous xml artifacts velocity template.
 */
public class XmlTemplatesTest extends AbstractTestTemplate {
    
    public void setUp() {
        super.setUp();
        getParameters().put("targetJarDir", "${env.MULE_HOME}/lib/user");
        getParameters().put("jaxbBinDir", "legstar-jaxbgen-cases/target/classes");
        getParameters().put("coxbBinDir", "legstar-coxbgen-cases/target/classes");
        getParameters().put("targetBinDir", "target/gen-classes");
        getParameters().put("custBinDir", "legstar-coxbgen-cases/target/classes");
        getParameters().put("targetPropDir", GEN_PROP_DIR);
    }

    public void testAntBuildJar() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();

        File componentAntFilesDir = new File(GEN_ANT_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        Mule2CixsGenerator.generateAntBuildJar(
                muleComponent, getParameters(), componentAntFilesDir);
        String resStr = getSource(
                componentAntFilesDir,
                "build.xml");

        assertTrue(resStr.contains("<property name=\"jarFile\" value=\"${env.MULE_HOME}/lib/user/mule-legstar-lsfileae.jar\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"legstar-jaxbgen-cases/target/classes\">"));
        assertTrue(resStr.contains("<include name=\"com/legstar/test/coxb/lsfileae/*.class\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"legstar-coxbgen-cases/target/classes\">"));
        assertTrue(resStr.contains("<include name=\"com/legstar/test/coxb/lsfileae/bind/*.class\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"target/gen-classes\">"));
        assertTrue(resStr.contains("<include name=\"org/mule/providers/legstar/test/lsfileae/*.class\"/>"));
        assertTrue(resStr.contains("<fileset dir=\"legstar-coxbgen-cases/target/classes\">"));
        assertTrue(resStr.contains("<include name=\"**/lsfileae/**/*.class\"/>"));
        assertTrue(resStr.replace('\\', '/').contains("<fileset dir=\"src/test/gen/prop\">"));
        assertTrue(resStr.contains("<include name=\"lsfileae.properties\"/>"));
    }

    public void testStandaloneConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();

        File componentConfFilesDir = new File(
                GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateStandaloneConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);
        String resStr = getSource(
                componentConfFilesDir,
                "mule-standalone-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-standalone-lsfileae-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<model name=\"lsfileaeModel\">"));
        assertTrue(resStr.contains("<mule-descriptor name=\"lsfileaeUMO\" implementation=\"org.mule.providers.legstar.test.lsfileae.LsfileaeImpl\">"));
        assertTrue(resStr.contains("<endpoint address=\"tcp://localhost:3213/lsfileae\""));
    }

    public void testBridgeConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        getParameters().put("hostCharset", "IBM01140");
        getParameters().put("hostURI", "http://192.168.0.110:4081");

        File componentConfFilesDir = new File(
                GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateBridgeConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);
        String resStr = getSource(
                componentConfFilesDir,
                "mule-bridge-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-bridge-lsfileae-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<transformer name=\"HostByteArrayToDfhcommareaType\" className=\"org.mule.providers.legstar.test.lsfileae.HostByteArrayToDfhcommareaType\"/>"));
        assertTrue(resStr.contains("<transformer name=\"DfhcommareaTypeToHostByteArray\" className=\"org.mule.providers.legstar.test.lsfileae.DfhcommareaTypeToHostByteArray\"/>"));
        assertTrue(resStr.contains("<mule-descriptor name=\"lsfileaeBridgeUMO\""));
        assertTrue(resStr.contains("responseTransformers=\"HostByteArrayToDfhcommareaType\""));
        assertTrue(resStr.contains("transformers=\"DfhcommareaTypeToHostByteArray\" >"));
        assertTrue(resStr.contains("<property name=\"programPropFileName\" value=\"lsfileae.properties\"/>"));
        assertTrue(resStr.contains("<endpoint address=\"legstar:http://192.168.0.110:4081\""));
        assertTrue(resStr.contains("<property name=\"hostCharset\" value=\"IBM01140\"/>"));
    }

    public void testLocalConfigXml() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getJvmQueryMuleComponent();
        getParameters().put("serviceURI", "http://megamouss:8083");
        getParameters().put("hostCharset", "IBM01140");

        File componentConfFilesDir = new File(GEN_CONF_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentConfFilesDir, true);
        AbstractCixsMuleGenerator.generateLocalConfigXml(
                muleComponent, getParameters(), componentConfFilesDir);
        String resStr = getSource(
                componentConfFilesDir,
                "mule-local-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.contains("<mule-configuration id=\"mule-legstar-local-jvmquery-config\" version=\"1.0\">"));
        assertTrue(resStr.contains("<description>Mule configuration file for jvmquery local Legs4Mule component</description>"));
        assertTrue(resStr.contains("<transformer name=\"HostByteArrayToJvmQueryRequestType\" className=\"org.mule.providers.legstar.test.jvmquery.HostByteArrayToJvmQueryRequestType\"/>"));
        assertTrue(resStr.contains("<transformer name=\"JvmQueryRequestTypeToHttpResponse\" className=\"org.mule.providers.legstar.test.jvmquery.JvmQueryRequestTypeToHttpResponse\"/>"));
        assertTrue(resStr.contains("<transformer name=\"HostByteArrayToJvmQueryReplyType\" className=\"org.mule.providers.legstar.test.jvmquery.HostByteArrayToJvmQueryReplyType\"/>"));
        assertTrue(resStr.contains("<transformer name=\"JvmQueryReplyTypeToHttpResponse\" className=\"org.mule.providers.legstar.test.jvmquery.JvmQueryReplyTypeToHttpResponse\"/>"));
        assertTrue(resStr.contains("<mule-descriptor name=\"jvmqueryLocalUMO\""));
        assertTrue(resStr.contains("implementation=\"com.legstar.xsdc.test.cases.jvmquery.JVMQuery\">"));
        assertTrue(resStr.contains("<endpoint address=\"legstar:http://megamouss:8083\""));
        assertTrue(resStr.contains("<property name=\"hostCharset\" value=\"IBM01140\"/>"));
    }

    public void testAntStartMule() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        getParameters().put("targetMuleConfigDir", GEN_CONF_DIR);

        File componentAntFilesDir = new File(GEN_ANT_DIR, muleComponent.getName());
        CodeGenUtil.checkDirectory(componentAntFilesDir, true);
        Mule2CixsGenerator.generateAntStartMule(
                muleComponent, getParameters(), componentAntFilesDir,
                "mule-bridge-config-" + muleComponent.getName() + ".xml");
        String resStr = getSource(
                componentAntFilesDir,
                "start-mule-bridge-config-" + muleComponent.getName() + ".xml");

        assertTrue(resStr.replace("\\", "/").contains("<property name=\"conf.file\" value=\"file:///src/test/gen/conf/mule-bridge-config-lsfileae.xml\"/>"));
        assertTrue(resStr.replace("\\", "/").contains("<jvmarg value=\"-Dlog4j.configuration=file:///src/test/gen/prop/log4j.properties\"/>"));
        assertTrue(resStr.contains("<dirset dir=\"target/gen-classes\"/>"));
        assertTrue(resStr.contains("<dirset dir=\"legstar-coxbgen-cases/target/classes\"/>"));
        assertTrue(resStr.replace("\\", "/").contains("<dirset dir=\"src/test/gen/prop\"/>"));
    }



}
