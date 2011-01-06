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
     * @see org.mule.transformer.AbstractMessageTransformer#transformMessage(MuleMessage, String)
     * @param message a Mule message
     * @param outputEncoding the output encoding expected (ignored in this context)
     * @return a single byte array containing host data for single part mainframe payloads
     *  or a map of byte arrays for multipart mainframe payloads.
     * @throws TransformerException if transformation fails
     */
    Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException;
}
