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
package org.mule.transport.legstar.cixs.transformer;

import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transport.legstar.config.HostProgram;

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
public class HostToLegstarExecRequestMuleTransformer extends AbstractHostToExecRequestMuleTransformer {

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
            LegStarMessage legStarMessage = createLegStarMessage(getHostProgram());
            legStarMessage.addDataPart(new CommareaPart(hostData));
            return legStarMessage.toByteArray();
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
            LegStarMessage legStarMessage = createLegStarMessage(getHostProgram());
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

    /** {@inheritDoc}  */
    @Override
    public void setMessageProperties(final MuleMessage esbMessage) {
        esbMessage.setCorrelationId(
                getHostCompatibleCorrelationId(esbMessage.getCorrelationId()));
    }

    /**
     * Produce an empty architected LegStar message.
     * If a program properties file name is passed, it is used to build the
     * message header.
     * @param hostProgram the target program attributes
     * @return a LegStar message with no data parts
     * @throws TransformerException if message cannot be built
     */
    public final LegStarMessage createLegStarMessage(
            final HostProgram hostProgram) throws TransformerException {
        try {
            LegStarMessage legStarMessage = new LegStarMessage();
            legStarMessage.setHeaderPart(new LegStarHeaderPart(0, getHostProgram().toJSONHost()));
            return legStarMessage;
        } catch (HeaderPartException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (JSONException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }
    }

}
