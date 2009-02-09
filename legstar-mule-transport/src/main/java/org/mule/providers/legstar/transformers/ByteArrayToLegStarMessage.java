/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/

package org.mule.providers.legstar.transformers;

import java.io.ByteArrayInputStream;

import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;

import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostReceiveException;
import com.legstar.messaging.LegStarMessage;

/**
 * <code>ByteArrayToLegStarMessage</code> turn a byte array to an architected
 * LegStarMessage.
 */
public class ByteArrayToLegStarMessage extends AbstractEventAwareTransformer
{


    /**
     * Construct the transformer. Specify source and return types.
     */
    public ByteArrayToLegStarMessage()
    {
        registerSourceType(byte[].class);
        setReturnClass(LegStarMessage.class);
    }

    /** {@inheritDoc} */
    public final Object transform(
            final Object src,
            final String encoding,
            final UMOEventContext context)
    throws TransformerException
    {

        byte[] hostBytes = (byte[]) src;
        ByteArrayInputStream hostStream = new ByteArrayInputStream(hostBytes);

        try
        {
            LegStarMessage requestMessage = new LegStarMessage();
            requestMessage.recvFromHost(hostStream);
            return requestMessage;
        }
        catch (HeaderPartException e)
        {
            throw new TransformerException(
                    LegstarMessages.invalidLegstarHeader(), this, e);
        }
        catch (HostReceiveException e)
        {
            throw new TransformerException(
                    LegstarMessages.errorReceivingHostData(), this, e);
        }
    }

}
