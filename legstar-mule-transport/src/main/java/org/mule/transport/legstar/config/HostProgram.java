package org.mule.transport.legstar.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.legstar.config.Constants;

/**
 * A bean that represents a mainframe program attributes.
 */
public class HostProgram {
    
    /** Mainframe program name. */
    private String _name;
    
    /** For commarea-driven programs, this is the largest commarea size supported.*/
    private int _maxDataLength;

    /** For commarea-driven programs, actual input data size.*/
    private int _dataLength;

    /** For container-driven programs, this is the channel name. */
    private String _channelName;
    
    /** List of input containers. */
    private List < HostContainer > _inputContainers = new ArrayList < HostContainer >();
    
    /** List of output containers. */
    private List < HostContainer > _outputContainers = new ArrayList < HostContainer >();
    
    
    /**
     * Bean constructor.
     */
    public HostProgram() {
        
    }

    /**
     * Construct from a legacy program attributes property file.
     * @param propertyFileName a property file name on the classpath
     * TODO Add TransID, SyncOnReturn and SysID
     */
    public HostProgram(final String propertyFileName) {
        try {
            Properties props = ConfigUtils.loadFromPropFile(propertyFileName);
            setName(props.getProperty(Constants.CICS_PROGRAM_NAME_KEY));
            setMaxDataLength(ConfigUtils.getInt(props, Constants.CICS_LENGTH_KEY));
            setDataLength(ConfigUtils.getInt(props, Constants.CICS_DATALEN_KEY));
            setChannelName(props.getProperty(Constants.CICS_CHANNEL_KEY));
            loadContainer(props, getInputContainers(),
                    Constants.CICS_IN_CONTAINERS_KEY,
                    Constants.CICS_IN_CONTAINERS_LEN_KEY);
            loadContainer(props, getOutputContainers(),
                    Constants.CICS_OUT_CONTAINERS_KEY,
                    Constants.CICS_OUT_CONTAINERS_LEN_KEY);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns a map of the program attributes using key names that are
     * compatible with the legstar messaging protocol.
     * 
     * TODO Add TransID, SyncOnReturn and SysID
     * 
     * @return map of program attributes
     */
    public final Map < String, Object > getLegstarAttributesMap() {
        HashMap < String, Object > map = new HashMap < String, Object >();

        /* Add mandatory keys */
        map.put(Constants.CICS_PROGRAM_NAME_KEY, getName());

        /* Pass on Channel or Commarea mandatory parameters */
        if (getChannelName() == null || getChannelName().length() == 0) {
            map.put(Constants.CICS_LENGTH_KEY, Integer.toString(getMaxDataLength()));
            map.put(Constants.CICS_DATALEN_KEY, Integer.toString(getDataLength()));
        } else {
            map.put(Constants.CICS_CHANNEL_KEY, getChannelName());
            /* Pass output container names as a string array */
            if (getOutputContainers() != null) {
                String[] outContainers =
                    new String[getOutputContainers().size()];
                int i = 0;
                for (HostContainer container : getOutputContainers()) {
                    outContainers[i] = container.getName();
                    i++;
                }
                map.put(Constants.CICS_OUT_CONTAINERS_KEY, outContainers);
            }
        }

        return map;
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
    public void loadContainer(
            final Properties props,
            final List < HostContainer > containers,
            final String nameKey,
            final String lengthKey) {
        int i = 1;
        String name = props.getProperty(nameKey + '_' + i);
        while (name != null && name.length() > 0) {
            HostContainer container = new HostContainer();
            container.setName(name);
            container.setMaxLength(ConfigUtils.getInt(props, lengthKey + '_' + i));
            containers.add(container);
            name = props.getProperty(nameKey + '_' + ++i);
        }
    }

    /**
     * @return mainframe program name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name mainframe program name
     */
    public void setName(final String name) {
        _name = name;
    }

    /**
     * @return largest commarea size supported
     */
    public int getMaxDataLength() {
        return _maxDataLength;
    }

    /**
     * @param dataLength largest commarea size supported
     */
    public void setMaxDataLength(final int dataLength) {
        _maxDataLength = dataLength;
    }

    /**
     * @return actual input data size in commarea
     */
    public int getDataLength() {
        return _dataLength;
    }

    /**
     * @param length  actual input data size in commarea
     */
    public void setDataLength(final int length) {
        _dataLength = length;
    }

    /**
     * @return channel name for container-driven programs
     */
    public String getChannelName() {
        return _channelName;
    }

    /**
     * @param name channel name for container-driven programs
     */
    public void setChannelName(final String name) {
        _channelName = name;
    }

    /**
     * Add an input container.
     * @param container the new container to add
     */
    public void addInputContainer(final HostContainer container) {
        _inputContainers.add(container);
    }
    
    /**
     * @return the input containers list
     */
    public List < HostContainer > getInputContainers() {
        return _inputContainers;
    }

    /**
     * @param containers the input containers list to set
     */
    public void setInputContainers(final List < HostContainer > containers) {
        _inputContainers = containers;
    }

    /**
     * Add an output container.
     * @param container the new container to add
     */
    public void addOutputContainer(final HostContainer container) {
        _outputContainers.add(container);
    }
    
    /**
     * @return the output containers list
     */
    public List < HostContainer > getOutputContainers() {
        return _outputContainers;
    }

    /**
     * @param containers the output containers list to set
     */
    public void setOutputContainers(final List < HostContainer > containers) {
        _outputContainers = containers;
    }

}
