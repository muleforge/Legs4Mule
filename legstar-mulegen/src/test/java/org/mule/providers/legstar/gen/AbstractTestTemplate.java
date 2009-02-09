/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenUtil;
import com.legstar.coxb.gen.CoxbHelper;

import junit.framework.TestCase;

/**
 * This is code common to all junit tests that exercise the velocity
 * templates.
 */
public class AbstractTestTemplate extends TestCase {

    /** Parent generation folder. */
    public static final File GEN_DIR = new File("src/test/gen");
    
    /** Location of JAXB classes. */
    public static final File JAXB_BIN_DIR = new File("target/classes");

    /** Code will be generated here. */
    public static final File GEN_SRC_DIR = new File("src/test/gen/java");

    /** Configuration files will be generated here. */
    public static final File GEN_CONF_DIR = new File("src/test/gen/conf");
    
    /** Web descriptors files will be generated here. */
    public static final File GEN_ANT_DIR = new File("src/test/gen/ant");
    
    /** Properties files will be generated here. */
    public static final File GEN_PROP_DIR = new File("src/test/gen/prop");
    
    /** Reference to jar files location. */
    public static final File GEN_JAR_DIR = new File("${env.MULE_HOME}/lib/user");

    /** Reference to binaries location. */
    public static final File GEN_BIN_DIR = new File("src/test/gen/target/classes");

    /** COBOL code will be generated here. */
    public static final File GEN_COBOL_DIR = new File("src/test/gen/cobol");
    
    /** Additional parameter set passed to templates */
    private Map <String, Object> mParameters;
    
    /** Logger. */
    private static final Log LOG = LogFactory.getLog(AbstractTestTemplate.class);
    
    /** @{inheritDoc}*/
    @Override
    public void setUp() {
        try {
            emptyDir(GEN_DIR);
            CodeGenUtil.initVelocity();
            mParameters = new HashMap <String, Object>();
            CodeGenHelper helper = new CodeGenHelper();
            mParameters.put("helper", helper);
            mParameters.put("coxbHelper", new CoxbHelper());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * A general purpose reader that gets the file content into a string.
     * @param srcDir the location of the source artifact
     * @param srcName the source artifact name
     * @return a string containing the generated source
     * @throws Exception if something goes wrong
     */
    public String getSource(
            File srcDir, String srcName) throws Exception {
        BufferedReader in = new BufferedReader(
                new FileReader(new File(srcDir, srcName)));
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


    /**
     * @return the mParameters
     */
    public final Map<String, Object> getParameters() {
        return mParameters;
    }

    /**
     * Recreates a folder after emptying its content.
     * @param dir the folder to empy
     */
    public void emptyDir(File dir) {
        deleteDir(dir);
        dir.mkdirs();
    }
    
    /**
     * Destroys a folder and all of its content.
     * @param dir the folder to destroy
     */
    public void deleteDir(File dir) {
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    deleteDir(file);
                }
                file.delete();
            }
        }
    }

}
