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
package org.mule.transport.legstar.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.legstar.config.Constants;

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

    /**
     * A helper to create a hostProgram configuration from a properties file.
     * @param propertyFileName the property file name
     * @return a configured host program
     * @throws IOException if properties file cannot be read
     */
    public static HostProgram getHostProgram(final String propertyFileName) throws IOException {
        HostProgram hostProgram = new HostProgram();
        Properties props = loadFromPropFile(propertyFileName);
        hostProgram.setName(props.getProperty(Constants.CICS_PROGRAM_NAME_KEY));
        hostProgram.setMaxDataLength(ConfigUtils.getInt(props, Constants.CICS_LENGTH_KEY));
        hostProgram.setDataLength(ConfigUtils.getInt(props, Constants.CICS_DATALEN_KEY));
        hostProgram.setChannelName(props.getProperty(Constants.CICS_CHANNEL_KEY));
        loadContainer(props, hostProgram.getInputContainers(),
                Constants.CICS_IN_CONTAINERS_KEY,
                Constants.CICS_IN_CONTAINERS_LEN_KEY);
        loadContainer(props, hostProgram.getOutputContainers(),
                Constants.CICS_OUT_CONTAINERS_KEY,
                Constants.CICS_OUT_CONTAINERS_LEN_KEY);
        return hostProgram;
    }

    /**
     * Create a map with container names and associated max size from entries
     * in a property file.
     * List of items are expected to be stored as a set of properties suffixed
     * with _n where n in the item rank. 
     * @param props program attributes as properties 
     * @param containers a list of containers to fill
     * @param nameKey the properties key for container name
     * @param lengthKey the properties key for container size
     */
    public static void loadContainer(
            final Properties props,
            final List < HostContainer > containers,
            final String nameKey,
            final String lengthKey) {
        int i = 1;
        String name = props.getProperty(nameKey + '_' + i);
        while (name != null && name.length() > 0) {
            HostContainer container = new HostContainer();
            container.setName(name);
            container.setMaxLength(ConfigUtils.getInt(props, lengthKey + '_'
                    + i));
            containers.add(container);
            name = props.getProperty(nameKey + '_' + ++i);
        }
    }

}
