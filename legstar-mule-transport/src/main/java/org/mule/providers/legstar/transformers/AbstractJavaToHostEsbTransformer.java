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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.coxb.transform.HostTransformException;
import com.legstar.host.invoke.CicsProgram;
import com.legstar.host.invoke.CicsProgramException;
import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.ContainerPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarHeaderPart;
import com.legstar.messaging.LegStarMessage;

/**
 * This ESB transformer converts java object into host data using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractJavaToHostEsbTransformer extends AbstractHostEsbTransformer {

    /** Name of esb message property holding target Mainframe program properties file name. */
    public static final String PROGRAM_PROP_FILE_NAME = "programPropFileName";

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(AbstractJavaToHostEsbTransformer.class);

    /**
     * Constructor for single part transformers.
     * <p/>
     * Java to Host transformers expect a type object as input and produces a 
     * byte array corresponding to mainframe raw data.
     * Inheriting classes will set the Source type.
     * @param bindingTransformers a set of transformers for the part type.
     */
    public AbstractJavaToHostEsbTransformer(
            final AbstractTransformers bindingTransformers) {
        super(bindingTransformers);
        setReturnClass(byte[].class);
    }

    /**
     * Constructor for multi-part transformers.
     * <p/>
     * Java to Host transformers expect a type object as input and produces a 
     * byte array corresponding to mainframe raw data.
     * Inheriting classes will set the Source type.
     * @param bindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractJavaToHostEsbTransformer(
            final Map < String, AbstractTransformers > bindingTransformersMap) {
        super(bindingTransformersMap);
        setReturnClass(byte[].class);
    }

    /**
     * {@inheritDoc}
     * The nature of the binding transformers passed by inherited class determines
     * if this is a multi part transformer or not.
     * <p/>
     * Single part transformers can either serialize to raw mainframe data or be
     * encapsulated in an architected LegStar Message. This is determined by the 
     * presence of a boolean in the incoming esb message properties.
     * <p/>
     * When a formatted LegStar message needs to be produced, the target program
     * properties are collected from a string in the incoming esb message properties.
     *  */
    public Object transform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("ESB Message before processing:");
            LOG.debug(esbMessage);
        }
        try {

            /* Get the target program attributes if any. */
            String programPropFileName = esbMessage.getStringProperty(
                    PROGRAM_PROP_FILE_NAME, null);

            /* Single part messages come with binding transformers */
            if (getBindingTransformers() != null) {
                byte[] hostData = getBindingTransformers().toHost(
                        esbMessage.getPayload(), getHostCharset(esbMessage));

                /* If we are passed target program attributes wrap in a legstar message */
                if (programPropFileName != null && programPropFileName.length() > 0) {
                    LegStarMessage legStarMessage = createLegStarMessage(programPropFileName);
                    legStarMessage.addDataPart(new CommareaPart(hostData));
                    return legStarMessage.toByteArray();
                } else {
                    return hostData;
                }
            } else {
                /* Multi-part messages are always wrapped in a legstar message */
                LegStarMessage legStarMessage = createLegStarMessage(programPropFileName);
                for (Entry < String, AbstractTransformers > entry
                        : getBindingTransformersMap().entrySet()) {
                    Object valueObject = getObjectFromHolder(esbMessage.getPayload(), entry.getKey());
                    legStarMessage.addDataPart(new ContainerPart(entry.getKey(),
                            entry.getValue().toHost(
                                    valueObject, getHostCharset(esbMessage))));
                }
                return legStarMessage.toByteArray();
            }

        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    getLegstarMessages().hostMessageFormatFailure(), this, e);
        } catch (HostTransformException e) {
            throw new TransformerException(
                    getLegstarMessages().hostTransformFailure(), this, e);
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
    public LegStarMessage createLegStarMessage(
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
     * When a holder object for multi part payload needs to be turned into
     * host data, we need to associate inner objects with part ID.
     * Multi part transformers must override this method.
     * @param holderObject holder object
     * @param partID the part identifier or container name
     * @return a holder object
     * @throws TransformerException if creating holder fails
     */
    /** {@inheritDoc} */
    public Object getObjectFromHolder(
            final Object holderObject,
            final String partID) throws TransformerException {
        throw new TransformerException(
                getLegstarMessages().noMultiPartSupportFailure(), this);
    }

}
