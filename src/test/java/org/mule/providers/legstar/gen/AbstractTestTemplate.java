package org.mule.providers.legstar.gen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legstar.cixs.jaxws.gen.CixsHelper;
import com.legstar.codegen.CodeGenHelper;
import com.legstar.codegen.CodeGenUtil;

import junit.framework.TestCase;

/**
 * This is code common to all junit tests that exercise the velocity
 * templates.
 */
public class AbstractTestTemplate extends TestCase {

    /** Code will be generated here. */
    public static final String GEN_SRC_DIR = "src/test/gen/java";

    /** General location for generated artifacts. */
    public static final String GEN_RES_DIR = "D:/Fady/sandbox/workspace2/legstar-mule/src/test/gen/resources";

    /** Property files will be generated here. */
    public static final String GEN_PROP_DIR = GEN_RES_DIR;

    /** Ant scripts will be generated here. */
    public static final String GEN_ANT_DIR = "ant";
    
    /** Configuration files will be generated here. */
    public static final String GEN_CONF_DIR = "conf";
    
    /** Additional parameter set passed to templates */
    private Map <String, Object> mParameters;
    
    /** Helper methods.  */
    private CixsHelper mCixsHelper = new CixsHelper();

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(
            AbstractTestTemplate.class);
    
    /** @{inheritDoc}*/
    @Override
    public void setUp() {
        try {
            CodeGenUtil.initVelocity();
            mParameters = new HashMap <String, Object>();
            CodeGenHelper helper = new CodeGenHelper();
            mParameters.put("helper", helper);
            mParameters.put("cixsHelper", mCixsHelper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * A general purpose reader that gets the file content into a string.
     * @param srcLocation the location of the source artifact
     * @param srcName the source artifact name
     * @return a string containing the generated source
     * @throws Exception if something goes wrong
     */
    public String getSource(
            String srcLocation, String srcName) throws Exception {
        BufferedReader in = new BufferedReader(
                new FileReader(srcLocation + '/' + srcName));
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
     * @return the mCixsHelper
     */
    public final CixsHelper getCixsHelper() {
        return mCixsHelper;
    }

}
