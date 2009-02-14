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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.coxb.transform.HostTransformException;
import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarMessage;
import com.legstar.messaging.LegStarMessagePart;

/**
 * This ESB Transformer converts host data into a java object using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractHostToJavaEsbTransformer extends AbstractHostEsbTransformer {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(AbstractHostToJavaEsbTransformer.class);

    /**
     * Constructor for single part transformers.
     * <p/>
     * Host to Java transformers expect byte array sources corresponding to
     * mainframe raw data.
     * Inheriting classes will set the return class.
     * @param bindingTransformers a set of transformers for the part type.
     */
    public AbstractHostToJavaEsbTransformer(
            final AbstractTransformers bindingTransformers) {
        super(bindingTransformers);
        registerSourceType(byte[].class);
    }

    /**
     * Constructor for multi-part transformers.
     * <p/>
     * Host to Java transformers expect byte array sources corresponding to
     * mainframe raw data.
     * Inheriting classes will set the return class.
     * @param bindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractHostToJavaEsbTransformer(
            final Map < String, AbstractTransformers > bindingTransformersMap) {
        super(bindingTransformersMap);
        registerSourceType(byte[].class);
    }

    /**
     * {@inheritDoc}
     * Detect if client is using LegStar messaging. If he does,
     * store that information in the ESB message properties so
     * that downstream actions know that the caller is expecting
     * a LegStarMessage reply.
     */
    public Object transform(
            final Object src,
            final String encoding,
            final UMOEventContext context) throws TransformerException {
        UMOMessage esbMessage = context.getMessage();
        if (LOG.isDebugEnabled()) {
            LOG.debug("ESB Message before processing:");
            LOG.debug(esbMessage);
        }
        try {
            
            byte[] hostData = getRawHostContent(src);
            
            if (LegStarMessage.isLegStarMessage(hostData)) {
                setLegStarMessaging(esbMessage, true);
                LegStarMessage legStarMessage = new LegStarMessage();
                legStarMessage.fromByteArray(hostData, 0);
                return toJava(legStarMessage, getHostCharset(esbMessage));
            } else {
                setLegStarMessaging(esbMessage, false);
                return getBindingTransformers().toJava(hostData, getHostCharset(esbMessage));
            }

        } catch (UnsupportedEncodingException e) {
            throw new TransformerException(
                    LegstarMessages.encodingFailure(getHostCharset(esbMessage)), this, e);
        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    LegstarMessages.hostMessageFormatFailure(), this, e);
        } catch (HostTransformException e) {
            throw new TransformerException(
                    LegstarMessages.hostTransformFailure(), this, e);
        } catch (HeaderPartException e) {
            throw new TransformerException(
                    LegstarMessages.hostTransformFailure(), this, e);
        }

    }

    /**
     * When the host is sending an architected LegStar message, it can be single
     * or multi part. This code transforms each part. If there is a single one,
     * it is immediately returned otherwise, each part is transformed individually
     * then they are all wrapped in a holder object which is returned.
     * @param legstarMessage a LegStar message
     * @param hostCharset the host character set
     * @return a java object
     * @throws TransformerException if transformation failed
     */
    public Object toJava(
            final LegStarMessage legstarMessage,
            final String hostCharset) throws TransformerException {

        try {
            Map < String, Object > transformedParts = new HashMap < String, Object >();
            for (LegStarMessagePart part : legstarMessage.getDataParts()) {
                if (part.getPartID().equals(CommareaPart.COMMAREA_PART_ID)) {
                    return getBindingTransformers().toJava(
                            part.getContent(), hostCharset);
                }
                transformedParts.put(part.getPartID(), 
                        getBindingTransformersMap().get(part.getPartID()).toJava(
                                part.getContent(), hostCharset));

            }
            return createHolder(transformedParts);
        } catch (HostTransformException e) {
            throw new TransformerException(
                    LegstarMessages.hostTransformFailure(), this, e);
        }

    }

    /**
     * When multiple part were received from the host, each transformed part
     * is stored in a map. Inheriting classes are responsible from wrapping
     * these parts into a typed holder object.
     * Multi part transformers must override this method.
     * @param transformedParts a map of transformed types
     * @return a holder object
     * @throws TransformerException if creating holder fails
     */
    /** {@inheritDoc} */
    public Object createHolder(
            final Map < String, Object > transformedParts) throws TransformerException {
        throw new TransformerException(
                LegstarMessages.noMultiPartSupportFailure(), this);
    }

    /**
     * Sanity check the source.
     * @param src source object
     * @return the content if sane
     * @throws HostTransformException if content is corrupted
     */
    public byte[] getRawHostContent(final Object src) throws HostTransformException {
        if (src == null) {
            throw new HostTransformException("Request content is null");
        }
        if (!(src instanceof byte[])) {
            throw new HostTransformException("Request content is not a byte array");
        }
        return (byte[]) src;
    }

}
