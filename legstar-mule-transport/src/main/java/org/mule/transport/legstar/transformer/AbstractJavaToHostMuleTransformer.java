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
package org.mule.transport.legstar.transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.coxb.transform.HostTransformException;

/**
 * This ESB transformer converts java object into host data using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractJavaToHostMuleTransformer
extends AbstractHostJavaMuleTransformer implements IObjectToHostTransformer {

    /** Logger. */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * Constructor for single part transformers.
     * <p/>
     * Java to Host transformers expect a type object as input and produces a 
     * byte array corresponding to mainframe raw data.
     * Inheriting classes will set the Source type.
     * @param bindingTransformers a set of transformers for the part type.
     */
    public AbstractJavaToHostMuleTransformer(
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
    public AbstractJavaToHostMuleTransformer(
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
    public byte[] transform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {

        if (_log.isDebugEnabled()) {
            _log.debug("ESB Message before processing:");
            _log.debug(esbMessage);
        }
        try {

            /* Single part messages come with binding transformers */
            if (getBindingTransformers() != null) {
                byte[] hostData = getBindingTransformers().toHost(
                        esbMessage.getPayload(), getHostCharset(esbMessage));

                return wrapHostData(hostData, esbMessage);
            } else {
                Map < String, byte[] > hostDataMap = new HashMap < String, byte[] >();
                for (Entry < String, AbstractTransformers > entry
                        : getBindingTransformersMap().entrySet()) {
                    Object valueObject = getObjectFromHolder(esbMessage.getPayload(), entry.getKey());
                    hostDataMap.put(entry.getKey(),
                            entry.getValue().toHost(
                                    valueObject, getHostCharset(esbMessage)));
                }
                return wrapHostData(hostDataMap, esbMessage);
            }

        } catch (HostTransformException e) {
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
