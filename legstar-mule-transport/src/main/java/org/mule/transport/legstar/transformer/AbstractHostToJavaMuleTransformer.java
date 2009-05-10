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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.coxb.transform.HostTransformException;

/**
 * This ESB Transformer converts host data into a java object using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractHostToJavaMuleTransformer extends AbstractHostJavaMuleTransformer {

    /**
     * Constructor for single part transformers.
     * <p/>
     * Host to Java transformers expect byte array sources corresponding to
     * mainframe raw data.
     * Alternatively, source can be an inputStream in which case, it is 
     * assumed to stream a byte array.
     * Inheriting classes will set the return class.
     * @param bindingTransformers a set of transformers for the part type.
     */
    public AbstractHostToJavaMuleTransformer(
            final AbstractTransformers bindingTransformers) {
        super(bindingTransformers);
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
    }

    /**
     * Constructor for multi-part transformers.
     * <p/>
     * Host to Java transformers expects a map of byte arrays for multi part
     * payloads.
     * Inheriting classes will set the return class.
     * @param bindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractHostToJavaMuleTransformer(
            final Map < String, AbstractTransformers > bindingTransformersMap) {
        super(bindingTransformersMap);
        registerSourceType(Map.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object hostTransform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {
        
        try {
            
            Object src = esbMessage.getPayload();
            
            if (src instanceof byte[]) {
                return getBindingTransformers().toJava(
                        (byte[]) src,
                        getHostCharset(esbMessage));

            } else if (src instanceof InputStream) {
                return getBindingTransformers().toJava(
                        IOUtils.toByteArray((InputStream) src),
                        getHostCharset(esbMessage));

            } else if (src instanceof Map) {
                return toJava((Map < String, byte[] >) src,
                        getHostCharset(esbMessage));
                
            } else {
                return null;
            }
            
        } catch (HostTransformException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (Exception e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }

    }

    /**
     * When the host is sending an multiple parts part, this code transforms each part.
     * Each part is transformed individually then they are all wrapped in a holder
     * object which is returned.
     * @param hostParts a map of byte arrays, one for each part
     * @param hostCharset the host character set
     * @return a java object
     * @throws TransformerException if transformation failed
     */
    public Object toJava(
            final Map < String, byte[] > hostParts,
            final String hostCharset) throws TransformerException {

        try {
            Map < String, Object > transformedParts = new HashMap < String, Object >();
            for (Entry < String, byte[] > hostPart : hostParts.entrySet()) {
                transformedParts.put(hostPart.getKey(), 
                        getBindingTransformersMap().get(hostPart.getKey()).toJava(
                                hostPart.getValue(), hostCharset));
            }
            return createHolder(transformedParts);
        } catch (HostTransformException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
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
                getI18NMessages().noMultiPartSupportFailure(), this);
    }

}
