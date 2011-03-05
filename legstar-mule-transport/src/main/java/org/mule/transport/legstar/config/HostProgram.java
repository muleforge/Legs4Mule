package org.mule.transport.legstar.config;

import java.util.List;

import com.legstar.host.invoke.model.HostContainer;


/**
 * A slightly enhanced host program bean.
 * <p/>
 * The maxDataLength field is more meaningful than length.
 * The channelName field is more meaningful than channel.
 * The inputContainer field is more meaningful than inContainer.
 * The outputContainers field is more meaningful than outContainer.
 *
 */
public class HostProgram extends com.legstar.host.invoke.model.HostProgram {

    /**
     * @return largest commarea size supported
     */
    public int getMaxDataLength() {
        return getLength();
    }

    /**
     * @param maxDataLength largest commarea size supported
     */
    public void setMaxDataLength(final int maxDataLength) {
        setLength(maxDataLength);
    }

    /**
     * @return channel name for container-driven programs
     */
    public String getChannelName() {
        return getChannel();
    }

    /**
     * @param name channel name for container-driven programs
     */
    public void setChannelName(final String name) {
        setChannel(name);
    }

    /**
     * Add an input container.
     * @param container the new container to add
     */
    public void addInputContainer(final HostContainer container) {
       getInContainers().add(container);
    }
    
    /**
     * @return the input containers list
     */
    public List < HostContainer > getInputContainers() {
        return getInContainers();
    }

    /**
     * @param containers the input containers list to set
     */
    public void setInputContainers(final List < HostContainer > containers) {
        setInContainers(containers);
    }

    /**
     * Add an output container.
     * @param container the new container to add
     */
    public void addOutputContainer(final HostContainer container) {
        getOutContainers().add(container);
    }
    
    /**
     * @return the output containers list
     */
    public List < HostContainer > getOutputContainers() {
        return getOutContainers();
    }

    /**
     * @param containers the output containers list to set
     */
    public void setOutputContainers(final List < HostContainer > containers) {
        setOutContainers(containers);
    }
}
