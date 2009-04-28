package org.mule.transport.legstar.cixs.transformer;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transport.legstar.i18n.LegstarMessages;

import com.legstar.host.invoke.CicsProgram;
import com.legstar.host.invoke.CicsProgramException;
import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.ContainerPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarHeaderPart;
import com.legstar.messaging.LegStarMessage;

/**
 * <code>HostToLegstarMuleTransformer</code> wraps host data into an
 * architected {@link com.legstar.messaging.LegStarMessage}.
 * <p/>
 * Source types are either simple byte arrays which means the target
 * is a single part, commarea type, program, or maps of byte arrays
 * which means the target program is container-aware.
 * <p/>
 * The return type is a byte array ready to be sent to the mainframe.
 * It is expected that the mainframe has the LegStar modules installed.
 */
public class HostToLegstarMuleTransformer extends AbstractMessageAwareTransformer {

    /** Target mainframe program attributes.*/
    private String _programPropFileName;
    
    /** True if LegStar mainframe modules should trace execution.*/
    private boolean _hostTraceMode;

    /** Message labels. */
    private final LegstarMessages mI18NMessages = new LegstarMessages();

    /** Logger. */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * Constructor registers source and return classes.
     */
    public HostToLegstarMuleTransformer() {
        registerSourceType(Map.class);
        registerSourceType(byte[].class);
        setReturnClass(byte[].class);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public Object transform(final MuleMessage esbMessage, final String outputEncoding)
            throws TransformerException {

        if (_log.isDebugEnabled()) {
            _log.debug("Transform request for type " + esbMessage.getPayload().getClass().getSimpleName());
        }
        Object src = esbMessage.getPayload();
        
        if (src instanceof byte[]) {
            return wrapHostData((byte[]) src, esbMessage);
        } else if (src instanceof Map) {
            return wrapHostData((Map < String, byte[] >) src, esbMessage);
        }
        return null;
    }

    /**
     * This method wraps a single part payload into a LegStarMessage.
     * @param hostData the single part mainframe payload
     * @param esbMessage the original mule message
     * @return the payload eventually wrapped in a LegStarMessage
     * @throws TransformerException if wrapping fails
     */
    public byte[] wrapHostData(
            final byte[] hostData, final MuleMessage esbMessage) throws TransformerException {
        try {
            /* If we are passed target program attributes wrap in a legstar message */
            if (getProgramPropFileName() != null && getProgramPropFileName().length() > 0) {
                LegStarMessage legStarMessage = createLegStarMessage(getProgramPropFileName());
                legStarMessage.addDataPart(new CommareaPart(hostData));
                return legStarMessage.toByteArray();
            } else {
                return hostData;
            }
        } catch (TransformerException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    getI18NMessages().hostMessageFormatFailure(), this, e);
        }
    }

    /**
     * If the mainframe is expecting a formatted LegStar message, this method
     * wraps a multi part payloads into a LegStarMessage.
     * @param hostDataMap the multi part mainframe payload (one entry per container)
     * @param esbMessage the original mule message
     * @return the payload eventually wrapped in a LegStarMessage
     * @throws TransformerException if wrapping fails
     */
    public byte[] wrapHostData(
            final Map < String, byte[] > hostDataMap, final MuleMessage esbMessage) throws TransformerException {
        try {

            /* Multi-part messages are always wrapped in a legstar message */
            LegStarMessage legStarMessage = createLegStarMessage(getProgramPropFileName());
            for (Entry < String, byte[] > entry : hostDataMap.entrySet()) {
                legStarMessage.addDataPart(new ContainerPart(entry.getKey(),
                        entry.getValue()));
            }
            return legStarMessage.toByteArray();
        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    getI18NMessages().hostMessageFormatFailure(), this, e);
        }
    }

    /**
     * Produce an empty architected LegStar message.
     * If a program properties file name is passed, it is used to build the
     * message header.
     * @param programPropFileName the target program properties file name
     * @return a LegStar message with no data parts
     * @throws TransformerException if message cannot be built
     */
    public final LegStarMessage createLegStarMessage(
            final String programPropFileName) throws TransformerException {
        try {
            LegStarMessage legStarMessage = new LegStarMessage();
            if (programPropFileName != null && programPropFileName.length() > 0) {
                CicsProgram cicsProgram = new CicsProgram(programPropFileName);
                LegStarHeaderPart headerPart = new LegStarHeaderPart(
                        cicsProgram.getProgramAttrMap(), 0);
                legStarMessage.setHeaderPart(headerPart);
            }
            return legStarMessage;
        } catch (CicsProgramException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (HeaderPartException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }
    }

    /**
     * @return Target mainframe program attributes
     */
    public String getProgramPropFileName() {
        return _programPropFileName;
    }

    /**
     * @param propFileName target mainframe program attributes
     */
    public void setProgramPropFileName(final String propFileName) {
        _programPropFileName = propFileName;
    }

    /**
     * @return true if LegStar mainframe modules should trace execution
     */
    public boolean isHostTraceMode() {
        return _hostTraceMode;
    }

    /**
     * @param traceMode true if LegStar mainframe modules should trace execution
     */
    public void setHostTraceMode(final boolean traceMode) {
        _hostTraceMode = traceMode;
    }

    /**
     * @return Message labels
     */
    public LegstarMessages getI18NMessages() {
        return mI18NMessages;
    }

}
