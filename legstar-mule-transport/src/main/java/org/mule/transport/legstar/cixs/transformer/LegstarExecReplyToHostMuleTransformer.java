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

import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.legstar.messaging.CommareaPart;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.messaging.LegStarMessage;
import com.legstar.messaging.LegStarMessagePart;

/**
 * <code>LegstarToHostMuleTransformer</code> unwraps host data from an
 * architected {@link com.legstar.messaging.LegStarMessage}.
 * <p/>
 * Source LegStar message is single part of multi part.
 * <p/>
 * The return type is a single byte array for single part messages or
 * a map of byte arrays -one entry per part) for multi part messages.
 */
public class LegstarExecReplyToHostMuleTransformer extends AbstractExecReplyToHostMuleTransformer {

    /** {@inheritDoc} */
    @Override
    public Object hostTransform(final MuleMessage esbMessage, final String outputEncoding)
            throws TransformerException {

        try {
            LegStarMessage legStarMessage = new LegStarMessage();
            legStarMessage.fromByteArray(esbMessage.getPayloadAsBytes(), 0);
            
            if (legStarMessage.getDataParts().size() > 0) {
                LegStarMessagePart part0 = legStarMessage.getDataParts().get(0);
                if (part0.getPartID().equals(CommareaPart.COMMAREA_PART_ID)) {
                    return part0.getContent();
                }
                Map < String, byte[] > parts = new HashMap < String, byte[] >();
                for (int i = 0; i < legStarMessage.getDataParts().size(); i++) {
                    LegStarMessagePart part = legStarMessage.getDataParts().get(i);
                    parts.put(part.getPartID(), part.getContent());
                }
                return parts;
            }
            return null;

        } catch (HeaderPartException e) {
            throw new TransformerException(
                    getI18NMessages().hostMessageFormatFailure(), this, e);
        } catch (HostMessageFormatException e) {
            throw new TransformerException(
                    getI18NMessages().hostMessageFormatFailure(), this, e);
        } catch (Exception e) {
            throw new TransformerException(
                    getI18NMessages().hostMessageFormatFailure(), this, e);
        }
    }

}
