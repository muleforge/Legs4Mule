package org.mule.transport.legstar.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

/**
 * Interface implemented by transformers with capability to produce host byte arrays which
 * content is mainframe data.
 *
 */
public interface IObjectToHostTransformer {

    /**
     * Generic transform method.
     * @see org.mule.transformer.AbstractMessageAwareTransformer#transform(MuleMessage, String)
     * @param message a Mule message
     * @param outputEncoding the output encoding expected (ignored in this context)
     * @return a single byte array containing host data for single part mainframe payloads
     *  or a map of byte arrays for multipart mainframe payloads.
     * @throws TransformerException if transformation fails
     */
    Object transform(MuleMessage message, String outputEncoding) throws TransformerException;
}
