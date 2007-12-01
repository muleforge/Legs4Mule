package org.mule.providers.legstar.gen.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * Test cases for CixsMake.
 */
public class CixsMakeTest extends TestCase {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CixsMakeTest.class);

    /**
     * Check controls on input make file.
     */
    public final void testCixsMakeInputValidation() {
        CixsMake cixsMake = new CixsMake();
        cixsMake.setModelName("modelName");
        cixsMake.setModel("model");
        try {
            cixsMake.execute();
        } catch (RuntimeException e) {
            assertEquals("Missing cixs make file parameter", e.getMessage());
        }
        cixsMake.setCixsMakeFileName("tarass.boulba");
        try {
            cixsMake.execute();
        } catch (RuntimeException e) {
            assertEquals("Cixs make file tarass.boulba does not exist", e.getMessage());
        }
    }
    
    /**
     * Check controls on input make file tag &lt;cixstarget&gt;.
     * @throws IOException if file cannot be read
     */
    public final void testCixsMakeNoTargetTag() throws IOException {
        File tempMakeFile = File.createTempFile("test-temp", "xml");
        /* Create a temporary make file */
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter(tempMakeFile));
        out.write("<somethingElse/>");
        out.close();

        CixsMake cixsMake = new CixsMake();
        cixsMake.setModelName("modelName");
        cixsMake.setModel("model");
        cixsMake.setCixsMakeFileName(tempMakeFile.getPath());
        try {
            cixsMake.execute();
        } catch (RuntimeException e) {
            assertEquals("Empty or invalid cixs make file", e.getMessage());
        }
    }
    
    /**
     * Check controls on input make file tag &lt;cixstarget name=""&gt;.
     * @throws IOException if file cannot be read
     */
    public final void testCixsMakeNoTargetName() throws IOException {
        File tempMakeFile = File.createTempFile("test-temp", "xml");
        /* Create a temporary make file */
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter(tempMakeFile));
        out.write("<cixstarget/>");
        out.close();

        CixsMake cixsMake = new CixsMake();
        cixsMake.setModelName("modelName");
        cixsMake.setModel("model");
        cixsMake.setCixsMakeFileName(tempMakeFile.getPath());
        try {
            cixsMake.execute();
        } catch (RuntimeException e) {
            assertEquals("Missing name attribute for cixstarget element", e.getMessage());
        }
    }

    /**
     * Check controls on input make file tag &lt;cixstemplate name=""&gt;.
     * @throws IOException if file cannot be read
     */
    public final void testCixsMakeTemplateNoTemplateName() throws IOException {
        File tempMakeFile = File.createTempFile("test-temp", "xml");
        /* Create a temporary make file */
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter(tempMakeFile));
        out.write("<cixstarget name=\"aTarget\"><cixstemplate/></cixstarget>");
        out.close();

        CixsMake cixsMake = new CixsMake();
        cixsMake.setModelName("modelName");
        cixsMake.setModel("model");
        cixsMake.setCixsMakeFileName(tempMakeFile.getPath());
        try {
            cixsMake.execute();
        } catch (RuntimeException e) {
            assertEquals(
                    "Missing template name attribute for cixstemplate element",
                    e.getMessage());
        }
    }

    /**
     * Check controls on input make file tag &lt;cixstemplate targetFile=""&gt;.
     * @throws IOException if file cannot be read
     */
    public final void testCixsMakeTemplateNoTargetFileName() throws IOException {
        File tempMakeFile = File.createTempFile("test-temp", "xml");
        /* Create a temporary make file */
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter(tempMakeFile));
        out.write(
                "<cixstarget name=\"aTarget\"><cixstemplate name=\"tt.vm\"/>"
                +  "</cixstarget>");
        out.close();

        CixsMake cixsMake = new CixsMake();
        cixsMake.setModelName("modelName");
        cixsMake.setModel("model");
        cixsMake.setCixsMakeFileName(tempMakeFile.getPath());
        try {
            cixsMake.execute();
        } catch (RuntimeException e) {
            assertEquals(
                    "Missing template target file name attribute for"
                    + " cixstemplate element",
                    e.getMessage());
        }
    }

    /**
     * Check generation.
     * @throws IOException if file cannot be read
     */
    public final void testCixsMakeTemplateWithParameters() throws IOException {
        File tempMakeFile = File.createTempFile("test-temp", "xml");
        /* Create a temporary make file */
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter(tempMakeFile));
        out.write("<cixstarget name=\"aTarget\" targetDir=\"test-gen\">"
                + "<cixstemplate name=\"testtemplate.vm\" targetFile=\"test.text\">"
                + "<parm1 value=\"value1\"/><parm2 value=\"value2\"/>"
                + "</cixstemplate></cixstarget>");
        out.close();

        CixsMake cixsMake = new CixsMake();
        cixsMake.init();
        cixsMake.setModelName("modelName");
        cixsMake.setModel("model");
        cixsMake.setCixsMakeFileName(tempMakeFile.getPath());
        cixsMake.execute();
        BufferedReader in = new BufferedReader(new FileReader("test-gen/test.text"));
        String resStr = "";
        String str = in.readLine();
        while (str != null) {
            LOG.debug(str);
            resStr += str;
            str = in.readLine();
        }
        in.close();
        assertTrue(resStr.contains("Using value1 and value2"));
    }
}
