package org.mule.transport.legstar.config;

/**
 * A container attributes.
 * <p/>
 * Container-driven programs receive and produce named containers.
 *
 */
public class HostContainer {
    
    /** Container name. */
    private String _name;
    
    /** Container maximum size. */
    private int _maxLength;

    /**
     * @return the container name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the _name to set
     */
    public void setName(final String name) {
       _name = name;
    }

    /**
     * @return the _maxLength
     */
    public int getMaxLength() {
        return _maxLength;
    }

    /**
     * @param length the _maxLength to set
     */
    public void setMaxLength(final int length) {
        _maxLength = length;
    }

}
