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
package org.mule.providers.legstar.transformers;

import java.util.Map;
import java.util.Map.Entry;

import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.host.HostContext;
import com.legstar.host.invoke.CicsProgram;
import com.legstar.host.invoke.CicsProgramException;
import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.ContainerPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarHeaderPart;
import com.legstar.messaging.LegStarMessage;

/**
 * ESB Transformers derived from this class deal with esb messages which content is
 * pure zos data.
 * <p/>
 * The zos data is either a formatted LegStarMessage (if the correspondent on zos
 * is LegStar-aware) or raw zos data which is the case when the correspondent does
 * not use LegStar (case of WMQ for instance).
 * <p/>
 * Usage of LegStarMessage or not is determined dynamically, therefore a single
 * service can service both LegStar-aware and non LegStar-aware correspondents
 * at the same time.
 */
public abstract class AbstractHostMuleTransformer extends AbstractMessageAwareTransformer {

    /** Name of property holding the mainframe character set. */
    public static final String HOST_CHARSET_PROPERTY = "hostCharset";

    /** Name of property holding if mainframe needs to trace incoming requests. */
    public static final String HOST_TRACE_MODE_PROPERTY = "hostTraceMode";

    /** A boolean property indicating that peer expects host data to be wrapped as a LegStarMessage. */
    public static final String IS_LEGSTAR_MESSAGING = "isLegStarMessaging";
    
    /** Name of esb message property holding target Mainframe program properties file name. */
    public static final String PROGRAM_PROP_FILE_NAME = "programPropFileName";

    /** Message labels. */
    private final LegstarMessages mLegstarMessages = new LegstarMessages();

    /**
     * Checks an ESB message properties to detect if peer is expecting host data
     * to be wrapped as a LegStarMessage.
     * @param esbMessage the ESB message containing host data
     * @return true if there is proof that upstream action requested LegStarMessage wrapping
     */
    public static boolean isLegStarMessaging(final MuleMessage esbMessage) {
        return esbMessage.getBooleanProperty(IS_LEGSTAR_MESSAGING, false);
    }
    
    /**
     * Mark an ESB Message to signal if the peer requests the host data to be
     * wrapped in a LegStarMessage or not.
     * @param esbMessage the ESB message to mark
     * @param isLegStarMessaging whether to mark with true or false
     */
    public static void setLegStarMessaging(
            final MuleMessage esbMessage, final boolean isLegStarMessaging) {
        esbMessage.setBooleanProperty(
                IS_LEGSTAR_MESSAGING, Boolean.valueOf(isLegStarMessaging));
    }

    /**
     * Gives a chance for a message to carry the mainframe character set.
     * @param message the esb message
     * @return the Target Mainframe character set from the
     * message properties or the configured one
     */
    public String getHostCharset(final MuleMessage message) {
        return message.getStringProperty(
                HOST_CHARSET_PROPERTY, HostContext.getDefaultHostCharsetName());
    }

    /**
     * @return Message labels
     */
    public LegstarMessages getLegstarMessages() {
        return mLegstarMessages;
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
                    getLegstarMessages().hostTransformFailure(), this, e);
        } catch (HeaderPartException e) {
            throw new TransformerException(
                    getLegstarMessages().hostTransformFailure(), this, e);
        }
    }

    /**
     * If the mainframe is expecting a formatted LegStar message, this method
     * wraps a single part payload into a LegStarMessage.
     * @param hostData the single part mainframe payload
     * @param esbMessage the original mule message
     * @return the payload eventually wrapped in a LegStarMessage
     * @throws TransformerException if wrapping fails
     */
    public byte[] wrapHostData(
            final byte[] hostData, final MuleMessage esbMessage) throws TransformerException {
        try {
            /* Get the target program attributes if any. */
            String programPropFileName = esbMessage.getStringProperty(
                    PROGRAM_PROP_FILE_NAME, null);

            /* If we are passed target program attributes wrap in a legstar message */
            if (programPropFileName != null && programPropFileName.length() > 0) {
                LegStarMessage legStarMessage = createLegStarMessage(programPropFileName);
                legStarMessage.addDataPart(new CommareaPart(hostData));
                return legStarMessage.toByteArray();
            } else {
                return hostData;
            }
        } catch (TransformerException e) {
            throw new TransformerException(
                    getLegstarMessages().hostTransformFailure(), this, e);
        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    getLegstarMessages().hostMessageFormatFailure(), this, e);
        }
    }

    /**
     * If the mainframe is expecting a formatted LegStar message, this method
     * wraps a multi part payloads into a LegStarMessage.
     * @param hostDataMap the multi part mainframe payload (one pentry per container)
     * @param esbMessage the original mule message
     * @return the payload eventually wrapped in a LegStarMessage
     * @throws TransformerException if wrapping fails
     */
    public byte[] wrapHostData(
            final Map < String, byte[] > hostDataMap, final MuleMessage esbMessage) throws TransformerException {
        try {
            /* Get the target program attributes if any. */
            String programPropFileName = esbMessage.getStringProperty(
                    PROGRAM_PROP_FILE_NAME, null);

            /* Multi-part messages are always wrapped in a legstar message */
            LegStarMessage legStarMessage = createLegStarMessage(programPropFileName);
            for (Entry < String, byte[] > entry : hostDataMap.entrySet()) {
                legStarMessage.addDataPart(new ContainerPart(entry.getKey(),
                        entry.getValue()));
            }
            return legStarMessage.toByteArray();
        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    getLegstarMessages().hostMessageFormatFailure(), this, e);
        }
    }

}
