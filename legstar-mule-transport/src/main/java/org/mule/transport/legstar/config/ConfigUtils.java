package org.mule.transport.legstar.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper methods for configuration.
 *
 */
public final class ConfigUtils {
    
    
    /**
     * Utility class.
     */
    private ConfigUtils() {
        
    }
    
    /**
     * Loads a properties file from classpath into memory.
     * @param propFileName the properties file name
     * @return the in-memory properties file
     * @throws IOException if file cannot be loaded
     */
    public static Properties loadFromPropFile(
            final String propFileName) throws IOException {

        /* load the program properties files */
        Properties properties = new Properties();

        /* We use the thread loader rather than the class loader because this
         * class will live in a different jar file than the calling
         * application. */
        InputStream in =
            Thread.currentThread().getContextClassLoader().
            getResourceAsStream(propFileName);
        if (in == null) {
            throw (new FileNotFoundException(propFileName));
        }
        properties.load(in);
        in.close();
        return properties;
    }
    
    /**
     * Safe get for integers.
     * @param props a properties set
     * @param key the key to value
     * @return an integer
     */
    public static int getInt(final Properties props, final String key) {
        String s = props.getProperty(key, "0");
        return Integer.parseInt(s);
    }
}
