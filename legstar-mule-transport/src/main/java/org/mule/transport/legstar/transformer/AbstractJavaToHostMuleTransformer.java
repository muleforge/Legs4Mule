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

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.types.DataTypeFactory;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.coxb.transform.HostTransformException;

/**
 * This ESB transformer converts java object into host data using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractJavaToHostMuleTransformer
extends AbstractHostJavaMuleTransformer implements IObjectToHostTransformer {


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
        setReturnDataType(DataTypeFactory.BYTE_ARRAY);
    }

    /**
     * Constructor for multi-part transformers.
     * <p/>
     * Java to Host transformers expect a type object as input and produces a 
     * map of byte arrays corresponding to mainframe raw data parts.
     * Inheriting classes will set the Source type.
     * @param bindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractJavaToHostMuleTransformer(
            final Map < String, AbstractTransformers > bindingTransformersMap) {
        super(bindingTransformersMap);
        setReturnDataType(DataTypeFactory.create(Map.class));
    }

    /**
     * {@inheritDoc}
     * The nature of the binding transformers passed by inherited class determines
     * if this is a multi part transformer or not.
     * <p/>
     *  */
    public Object hostTransform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {

        try {

            /* Single part messages come with binding transformers */
            if (getBindingTransformers() != null) {
                byte[] hostData = getBindingTransformers().toHost(
                        esbMessage.getPayload(), getHostCharset(esbMessage));
                return hostData;
            } else {
                Map < String, byte[] > hostDataMap = new HashMap < String, byte[] >();
                for (Entry < String, AbstractTransformers > entry
                        : getBindingTransformersMap().entrySet()) {
                    Object valueObject = getObjectFromHolder(esbMessage.getPayload(), entry.getKey());
                    hostDataMap.put(entry.getKey(),
                            entry.getValue().toHost(
                                    valueObject, getHostCharset(esbMessage)));
                }
                return hostDataMap;
            }

        } catch (HostTransformException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
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
                getI18NMessages().noMultiPartSupportFailure(), this);
    }

}
