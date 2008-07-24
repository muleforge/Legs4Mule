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

import org.mule.providers.legstar.http.LegstarHttpConnector;
import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;

import com.legstar.coxb.host.HostContext;
import com.legstar.host.invoke.CicsProgram;
import com.legstar.host.invoke.CicsProgramException;
import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.LegStarMessage;
import com.legstar.messaging.impl.LegStarMessageImpl;

/**
 * <code>AbstractLegStarTransformer</code> provides common methods to
 * all LegStar transformers.
 */
public abstract class AbstractLegStarTransformer extends AbstractEventAwareTransformer
{


    /** Mainframe character set. */
    private String mHostCharset;

    /** Target Mainframe program properties file name. */
    private String mProgramPropFileName;

    /**
     * These properties must be passed from a Mule client or set in
     * a Mule configuration file at the endpoint level.
     * @param message the message in the current context
     * @throws TransformerException if properties cannot be recovered
     */
    public final void getProperties(final UMOMessage message) throws TransformerException
    {
        mHostCharset = message.getStringProperty(
                LegstarHttpConnector.HOST_CHARSET_PROPERTY,
                HostContext.getDefaultHostCharsetName());

        mProgramPropFileName = message.getStringProperty(
                LegstarHttpConnector.PROGRAM_PROP_FILE_NAME, null);
    }

    /**
     * Creates an instance of a LegStar message.
     * @param message the message in the current context
     * @return an empty LegStar message instance
     * @throws TransformerException if message cannot be created
     */
    public final LegStarMessageImpl getLegStarMessage(final UMOMessage message) throws TransformerException
    {
        /* Get properties from the UMO message, these might help shape
         * the LegStar message */
        getProperties(message);
        try 
        {
            /* If the LegStar message being prepared is aimed at the host,
             * important parameters are derived from the program structure */
            if (getProgramPropFileName() != null 
                    && getProgramPropFileName().length() != 0)
            {
                return new LegStarMessageImpl(
                        new CicsProgram(getProgramPropFileName()).
                        getProgramAttrMap());
            }
            else
            {
                return new LegStarMessageImpl();
            }
        }
        catch (CicsProgramException e)
        {
            throw new TransformerException(
                    LegstarMessages.invalidProgramPropertyFile(
                            getProgramPropFileName()), this, e);
        }
        catch (HeaderPartException e)
        {
            throw new TransformerException(
                    LegstarMessages.invalidLegstarHeader(), this, e);
        }
    }

    /**
     * This method is useful when we need to transform a host byte array
     * into a jaxb object. It will transform the byte array into a
     * LegStar message so that a derived transformer is relieved from
     * this task.
     * @param src the current object in the transformer chain
     * @param encoding the Mule message encoding
     * @param context the Mule context
     * @return a LegStar message
     * @throws TransformerException in transformation failed
     */
    public final LegStarMessageImpl getLegStarMessageForByteArray(
            final Object src,
            final String encoding,
            final UMOEventContext context)
    throws TransformerException
    {
        try
        {
            /* Get the properties we need */
            getProperties(context.getMessage());

            /* First turn the byte array to a LegStar message */
            LegStarMessage legStarMessage;
            if (src instanceof byte[])
            {
                ByteArrayToLegStarMessage xformer =
                    new ByteArrayToLegStarMessage();
                legStarMessage = (LegStarMessage) xformer.transform(
                        src, encoding, context);
            }
            else
            {
                legStarMessage = (LegStarMessage) src;
            }

            /* Unwrap the response and convert to a java data object */
            return new LegStarMessageImpl(legStarMessage);
        }
        catch (HeaderPartException e)
        {
            throw new TransformerException(
                    LegstarMessages.invalidLegstarHeader(), this, e);
        }
    }

    /**
     * @return the Mainframe character set
     */
    public final String getHostCharset()
    {
        if (mHostCharset == null || mHostCharset.length() == 0)
        {
            return HostContext.getDefaultHostCharsetName();
        }
        return mHostCharset;
    }

    /**
     * @return the Target Mainframe program properties file name
     */
    public final String getProgramPropFileName()
    {
        return mProgramPropFileName;
    }

    /**
     * @param hostCharset the Mainframe character set to set
     */
    public final void setHostCharset(final String hostCharset)
    {
        mHostCharset = hostCharset;
    }

    /**
     * @param programPropFileName the Target Mainframe program properties file name to set
     */
    public final void setProgramPropFileName(final String programPropFileName)
    {
        mProgramPropFileName = programPropFileName;
    }


}
