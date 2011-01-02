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
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.legstar.cixs.gen.ant.CixsHelper;
import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

/**
 * This is code common to all junit tests that exercise the velocity
 * templates.
 */
public class AbstractTestTemplate extends TestCase {

    /** Parent generation folder. */
    public static final File GEN_DIR = new File("target/src/gen");

    /** Location of JAXB classes. */
    public static final File JAXB_BIN_DIR = new File("target/classes");

    /** Location of COXB classes. */
    public static final File COXB_BIN_DIR = new File("target/classes");

    /** Location of custom classes. */
    public static final File CUST_BIN_DIR = new File("target/classes");

    /** Code will be generated here. */
    public static final File GEN_SRC_DIR = new File("target/src/gen/java");

    /** Configuration files will be generated here. */
    public static final File GEN_CONF_DIR = new File("target/src/gen/conf");

    /** Web descriptors files will be generated here. */
    public static final File GEN_ANT_DIR = new File("target/src/gen/ant");

    /** Reference to jar files location. */
    public static final File GEN_JAR_DIR = new File("${env.MULE_HOME}/lib/user");

    /** Reference to binaries location. */
    public static final File GEN_BIN_DIR = new File("target/gen-classes");

    /** COBOL code will be generated here. */
    public static final File GEN_COBOL_DIR = new File("target/src/gen/cobol");

    /** Distributable archives are generated here. */
    public static final File GEN_DIST_DIR = new File("target/dist");
    
    /** The host character set. */
    public static final String HOSTCHARSET = "IBM01140";

    /** The legstar-mule transport URI targeting CICS TS 3.1. */
    public static final String LEGSTAR_HOST_URI = "legstar:http://mainframe:4081/CICS/CWBA/LSWEBBIN";

    /** Additional parameter set passed to templates. */
    private Map < String, Object > mParameters;
    
    /** Relative location of test resources.*/
    private static final String TEST_RESOURCES_DIR = "src/test/resources";
    
    /** This means references should be created instead of compared to results. */
    private boolean _createReferences;

    /**
     * @return true if references should be created instead of compared to results
     */
    public boolean isCreateReferences() {
		return _createReferences;
	}


	/**
	 * @param createReferences true if references should be created instead of compared to results
	 */
	public void setCreateReferences(boolean createReferences) {
		_createReferences = createReferences;
	}

	/** Logger. */
    private final Log _log = LogFactory.getLog(getClass());

    /** @{inheritDoc}*/
    public void setUp() {
        try {
            emptyDir(GEN_DIR);
            CodeGenUtil.initVelocity();
            mParameters = new HashMap < String, Object >();
            CodeGenHelper helper = new CodeGenHelper();
            mParameters.put("helper", helper);
            mParameters.put("cixsHelper", new CixsHelper());
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
            final File srcDir, final String srcName) throws Exception {
    	String content = FileUtils.readFileToString(new File(srcDir, srcName));
    	if (_log.isDebugEnabled()) {
    		_log.debug(content);
    	}
    	return content;
    }

    /**
     * Reads a text file from the resources and returns the content in a string.
     * @param resourceName the fully qualified resource
     * @return a string with the entire content
     * @throws IOException if reading fails
     */
	public String getSource(final String resourceName) throws IOException {
		String content = FileUtils.readFileToString(new File(TEST_RESOURCES_DIR
				+ resourceName));
		if (_log.isDebugEnabled()) {
			_log.debug(content);
		}
		return content;
	}

    /**
     * @return the mParameters
     */
    public final Map < String, Object > getParameters() {
        return mParameters;
    }

    /**
     * Recreates a folder after emptying its content.
     * @param dir the folder to empy
     */
    public void emptyDir(final File dir) {
        deleteDir(dir);
        dir.mkdirs();
    }

    /**
     * Destroys a folder and all of its content.
     * @param dir the folder to destroy
     */
    public void deleteDir(final File dir) {
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    deleteDir(file);
                }
                file.delete();
            }
        }
    }

    /**
     * Assuming the local machine is running Mule.
     * @return the local machine IP address
     */
    public String getLocalIPAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            byte[] ipAddr = addr.getAddress();
            String ipAddrStr = "";
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }
            return ipAddrStr;
        } catch (UnknownHostException e) {
            return "";
        }

    }

    /**
     * Reads a generated artifact and compares its content to a reference file.
     * @param dir the folder where the generated artifact is
     * @param fileName the name of the generate artifact file (not qualified)
     */
    public void compare(
            final File dir,
            final String fileName) {
        compare(dir, fileName, null);
    }
 
    /**
     * Reads a generated artifact and compares its content to a reference file.
     * @param dir the folder where the generated artifact is
     * @param fileName the name of the generate artifact file (not qualified)
     * @param prefix used to disambiguate artifacts with conflicting names
     */
    public void compare(
            final File dir,
            final String fileName,
            final String prefix) {
        try {
            /* Change any windows style backslash */
            String result = getSource(dir, fileName).replace('\\', '/');
            String reference = "/org/mule/transport/legstar/gen/";
            if (prefix != null && prefix.length() > 0) {
                reference += prefix + "/";
            }
            reference += fileName + ".txt";
            if (isCreateReferences()) {
            	createReference(reference, result);
            } else {
	            String expectedRes = getSource(reference).replace('\\', '/');
	            assertEquals(expectedRes, result);
            }
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * When test cases are initially built, this method create a reference
     * using test results.
     * @param resourceName the reference resource name
     * @param content the file content
     */
    public void createReference(final String resourceName, final String content) {
    	try {
			FileUtils.writeStringToFile(new File(TEST_RESOURCES_DIR + resourceName),
					content);
		} catch (IOException e) {
            fail(e.getMessage());
		}
    	
    }
}
