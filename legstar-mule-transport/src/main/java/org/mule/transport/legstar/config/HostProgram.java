package org.mule.transport.legstar.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;

import com.legstar.config.Constants;

/**
 * A slightly enhanced host program bean.
 * <p/>
 * The maxDataLength field is more meaningful than length. The channelName field
 * is more meaningful than channel. The inputContainer field is more meaningful
 * than inContainer. The outputContainers field is more meaningful than
 * outContainer.
 * <p/>
 * TODO duplicate of com.legstar.host.invoke.model.HostProgram needs to go away
 * when http://code.google.com/p/legstar/issues/detail?id=132 is fixed
 * 
 */
public class HostProgram {

    /** The host program name. */
    private String _name;

    /** The size of the commarea. */
    private int _maxDataLength;

    /** The size of the input data. */
    private int _dataLength;

    /** The remote CICS ID. */
    private String _sysID;

    /** Syncpoint forced on return. */
    private Boolean _syncOnReturn;

    /** The remote CICS transaction ID to use. */
    private String _transID;

    /** The CICS Channel used to link to this program. */
    private String _channelName;

    /** The list of input containers names and their max host byte size. */
    private List < HostContainer > _inputContainer = new ArrayList < HostContainer >();

    /** The list of output containers names and their max host byte size. */
    private List < HostContainer > _outputContainers = new ArrayList < HostContainer >();

    /**
     * @return largest commarea size supported
     */
    public int getMaxDataLength() {
        return _maxDataLength;
    }

    /**
     * @param maxDataLength largest commarea size supported
     */
    public void setMaxDataLength(final int maxDataLength) {
        _maxDataLength = maxDataLength;
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
        _inputContainer.add(container);
    }
    
    /**
     * @return the input containers list
     */
    public List < HostContainer > getInputContainers() {
        return _inputContainer;
    }

    /**
     * @param containers the input containers list to set
     */
    public void setInputContainers(final List < HostContainer > containers) {
        _inputContainer = containers;
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

    /**
     * @return Returns the size of the input data.
     */
    public int getDataLength() {
        return _dataLength;
    }

    /**
     * @param dataLength the size of the input data.
     */
    public void setDataLength(final int dataLength) {
        _dataLength = dataLength;
    }

    /**
     * @return Returns the host program name.
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name host program name.
     */
    public void setName(final String name) {
        _name = name;
    }

    /**
     * @return Returns the Syncpoint forced on return mode.
     */
    public Boolean getSyncOnReturn() {
        return _syncOnReturn;
    }

    /**
     * @param syncOnReturn Syncpoint forced on return mode.
     */
    public void setSyncOnReturn(final boolean syncOnReturn) {
        _syncOnReturn = syncOnReturn;
    }

    /**
     * @return Returns the remote CICS ID.
     */
    public String getSysID() {
        return _sysID;
    }

    /**
     * @param sysID remote CICS ID.
     */
    public void setSysID(final String sysID) {
        _sysID = sysID;
    }

    /**
     * @return Returns the remote CICS transaction ID to use.
     */
    public String getTransID() {
        return _transID;
    }

    /**
     * @param transID remote CICS transaction ID to use.
     */
    public void setTransID(final String transID) {
        _transID = transID;
    }

    /**
     * Host program properties are sent to the host as a JSON serialization
     * converted to a basic host character set.
     * <p/>
     * Mainframe programs in charge of reading that JSON serialization are not
     * full fledged JSON parser so we simplify things here.
     * 
     * @return a JSON serialization in host character set
     * @throws JSONException if something is wrong with the attributes
     */
    public String toJSONHost() throws JSONException {
            JSONStringer stringer = new JSONStringer();
            stringer.object();
            stringer.key(Constants.CICS_PROGRAM_NAME_KEY);
            stringer.value(getName());
            if (hasChannel()) {
                stringer.key(Constants.CICS_CHANNEL_KEY);
                stringer.value(getChannelName());
                /*
                 * Host has no interest in input containers (there is enough
                 * info in the message data parts). It also has no interest in
                 * output containers maximum length.
                 */
                if (getOutputContainers() != null
                        && getOutputContainers().size() > 0) {
                    stringer.key(Constants.CICS_OUT_CONTAINERS_KEY);
                    stringer.value(toJSONNames(getOutputContainers()));
                }
            } else {
                stringer.key(Constants.CICS_LENGTH_KEY);
                /* Host is not expecting int types, only strings */
                stringer.value(Integer.toString(getMaxDataLength()));
                stringer.key(Constants.CICS_DATALEN_KEY);
                stringer.value(Integer.toString(getDataLength()));
            }
            if (getSysID() != null) {
                stringer.key(Constants.CICS_SYSID_KEY);
                stringer.value(getSysID());
            }
            if (getSyncOnReturn() != null) {
                stringer.key(Constants.CICS_SYNCONRET_KEY);
                /* Host is not expecting boolean types, only strings */
                stringer.value(getSyncOnReturn().toString());
            }
            if (getTransID() != null) {
                stringer.key(Constants.CICS_TRANSID_KEY);
                stringer.value(getTransID());
            }
            stringer.endObject();
            return stringer.toString();
    }

    /**
     * @return true if this program uses channel/containers
     */
    public boolean hasChannel() {
        return (getChannelName() != null && getChannelName().length() > 0);
    }

    /**
     * @param containers the list of host containers
     * @return a JSON array where items are containers names
     * @throws JSONException if JSON failure
     */
    private JSONArray toJSONNames(final List < HostContainer > containers)
            throws JSONException {
        JSONArray jContainers = new JSONArray();
        for (HostContainer container : containers) {
            jContainers.put(container.getName());
        }
        return jContainers;
    }
}
