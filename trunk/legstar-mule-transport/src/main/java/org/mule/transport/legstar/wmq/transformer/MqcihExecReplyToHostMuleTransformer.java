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
package org.mule.transport.legstar.wmq.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transport.legstar.cixs.transformer.AbstractExecReplyToHostMuleTransformer;

import com.legstar.messaging.HeaderPartException;
import com.legstar.messaging.HostMessageFormatException;
import com.legstar.mq.mqcih.Mqcih;
import com.legstar.mq.mqcih.bind.MqcihTransformers;

/**
 * <code>MqcihToHostMuleTransformer</code> unwraps host data from an MQCIH formatted message.
 * <p/>
 * Source MQCIH serialized message.
 * <p/>
 * The return type is a single byte array with the actual host payload.
 */
public class MqcihExecReplyToHostMuleTransformer extends AbstractExecReplyToHostMuleTransformer {

    /** Byte length for a CICS program name. */
    private static final int CICS_PROGRAM_NAME_LEN = 8;

    /** {@inheritDoc} */
    @Override
    public byte[] hostTransform(final MuleMessage esbMessage, final String outputEncoding)
            throws TransformerException {

        try {
            byte[] mqcihHost = esbMessage.getPayloadAsBytes();
            
            /* TODO optimize */
            MqcihTransformers mqcihTransformers = new MqcihTransformers();
            Mqcih mqcihJava = mqcihTransformers.toJava(mqcihHost,
                    getHostCharset());

            /* Check that execution worked. If not, the message content is an
             * explicit error message 
             * TODO get the error message from the message content */
            if (mqcihJava.getMqcihCompcode() != 0) {
                throw new TransformerException(
                        getI18NMessages().mqcihNonZeroCompCode(
                                mqcihJava.getMqcihCompcode()), this);
            }
            /* When binary data is returned following MQCIH format should be none */
            if (!mqcihJava.getMqcihFormat().equals("")) {
                throw new TransformerException(
                        getI18NMessages().mqcihFormatNotNone(
                                mqcihJava.getMqcihFormat()), this);
            }

            /* Read the program ID */
            byte[] programNameHost = new byte[CICS_PROGRAM_NAME_LEN];
            System.arraycopy(mqcihHost, mqcihJava.getMqcihStruclength(),
                    programNameHost, 0, CICS_PROGRAM_NAME_LEN);

            /* Get the host payload */
            byte[] payload = 
                new byte[mqcihHost.length - mqcihJava.getMqcihStruclength()
                         - CICS_PROGRAM_NAME_LEN];
            System.arraycopy(mqcihHost,
                    mqcihJava.getMqcihStruclength() + CICS_PROGRAM_NAME_LEN,
                    payload, 0, payload.length);
            
            return payload;

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
