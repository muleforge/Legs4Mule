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


import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transport.legstar.LegstarConnector;
import org.mule.transport.legstar.cixs.transformer.AbstractHostToExecRequestMuleTransformer;
import org.mule.transport.legstar.config.HostCredentials;
import org.mule.transport.legstar.config.HostProgram;

import com.legstar.coxb.transform.HostTransformException;
import com.legstar.mq.mqcih.Mqcih;
import com.legstar.mq.mqcih.bind.MqcihTransformers;

/**
 * <code>HostToMqcihMuleTransformer</code> wraps host data into an IBM CICS MQ
 * Bridge (MQCIH) payload.
 * <p/>
 * Source type is a simple byte array holding mainframe data. This is the
 * content of the commarea passed to the target CICS program by CICS MQ Bridge.
 * LegStar transformers typically produce this type of byte arrays.
 * <p/>
 * The return type is a byte array ready to be sent to the mainframe.
 * It is expected that the mainframe has the IBM CICS MQ Bridge installed.
 */
public class HostToMqcihExecRequestMuleTransformer extends AbstractHostToExecRequestMuleTransformer {

    /**
     * Time in milliseconds that the MQGET calls issued by the bridge
     * should wait for second and subsequent request messages for the unit of
     * work started by this message.
     * */
    private int _waitInterval = -1;
    
    
    /** Flag indicating that a syncpoint should be taken upon return from 
     * execution of DPL program.
     * TODO revise when we implement single phase commit */
    private static final int MQCIH_SYNC_ON_RETURN = 4;

    /** Flag indicating that reply should not include trailing low-values. */
    private static final int MQCIH_REPLY_WITHOUT_NULLS = 2;
    
    /** Byte length for a CICS program name. */
    private static final int CICS_PROGRAM_NAME_LEN = 8;
    
    /**
     * This method wraps a single part payload into an MQCIH formatted message.
     * The payload is the concatenation of the MQCIH header, the target
     * CICS program name and finally the commarea data.
     * @param hostData the single part mainframe payload
     * @param esbMessage the original mule message
     * @return the payload MQCIH message
     * @throws TransformerException if wrapping fails
     */
    public byte[] wrapHostData(
            final byte[] hostData, final MuleMessage esbMessage) throws TransformerException {
        try {
            LegstarConnector connector = (LegstarConnector) getEndpoint().getConnector();
            byte[] mqcihBytes = getMqcihBytes(getHostProgram(),
                    connector.getHostCredentials(esbMessage));
            byte[] result =
                new byte[mqcihBytes.length + CICS_PROGRAM_NAME_LEN + hostData.length];
            System.arraycopy(mqcihBytes, 0,
                    result, 0, mqcihBytes.length);
            System.arraycopy(getHostProgram().getName().getBytes(getHostCharset()), 0,
                    result, mqcihBytes.length, CICS_PROGRAM_NAME_LEN);
            System.arraycopy(hostData, 0,
                    result, mqcihBytes.length + CICS_PROGRAM_NAME_LEN, hostData.length);
            
            return result;
        } catch (TransformerException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (UnsupportedEncodingException e) {
            throw new TransformerException(
                    getI18NMessages().encodingFailure(getHostCharset()), this, e);
        }
    }

    /** {@inheritDoc}  */
    @Override
    public byte[] wrapHostData(final Map < String, byte[] > hostDataMap,
            final MuleMessage esbMessage) throws TransformerException {
        throw new TransformerException(
                getI18NMessages().noMultiPartSupportFailure(), this);
    }

    /** {@inheritDoc}  */
    @Override
	public void setMessageProperties(final MuleMessage esbMessage) {
		/* CKBR uses the correlationID for UOW lifecycle management. */
		esbMessage.setCorrelationId("AMQ!NEW_SESSION_CORRELID");

		/*
		 * Tells MQ-mainframe that the content is an MQCIH payload and that it
		 * is already encoded in mainframe character set.
		 */
		esbMessage.setOutboundProperty("JMS_IBM_Format", "MQCICS  ");
		esbMessage.setOutboundProperty("JMS_IBM_Character_Set",
				getCCSID(getHostCharset()));

	}

    /**
     * The mainframe CCSID can be derived from the java character set.
     * TODO Find a more reliable way of deriving CCSID from java charset name.
     * @param hostCharset the mainframe character set
     * @return the MQ CCSID
     */
    private int getCCSID(final String hostCharset) {
        if (hostCharset.equals("IBM-Thai")) {
            return 838;
        }
        if (hostCharset.startsWith("IBM")) {
            return Integer.parseInt(hostCharset.substring(3));
        }
        if (hostCharset.startsWith("x-IBM")) {
            return Integer.parseInt(hostCharset.substring(5));
        }
        return 500;
    }
    /**
     * Produce the serialization of the MQCIH header as a byte array in the
     * target host character set.
     * @param hostProgram the target program attributes
     * @param hostCredentials the credentials used to authenticate to the mainframe
     * @return MQCIH as a byte array
     * @throws TransformerException if message cannot be built
     */
    public final byte[] getMqcihBytes(
            final HostProgram hostProgram,
            final HostCredentials hostCredentials) throws TransformerException {
        try {
            Mqcih mqcih = new Mqcih();

            /* MQCIH needs the output length to include the program name length */
            mqcih.setMqcihOutputdatalength(
                    hostProgram.getMaxDataLength() + CICS_PROGRAM_NAME_LEN);
            
            /* Just in case this is needed. Doc says: only applies if you are using
             *  an authorization level of VERIFY_UOW or VERIFY_ALL*/
            mqcih.setMqcihAuthenticator(new String(hostCredentials.getPassword()));
            
            if (getWaitInterval() > -1) {
                mqcih.setMqcihGetwaitinterval(getWaitInterval());
            }
            mqcih.setMqcihFlags(MQCIH_REPLY_WITHOUT_NULLS + MQCIH_SYNC_ON_RETURN);

            /* TODO optimize */
            MqcihTransformers mqcihTransformers = new MqcihTransformers();
            return mqcihTransformers.toHost(mqcih, getHostCharset());
                    
        } catch (HostTransformException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }
    }
    
    /**
     * @return Time in milliseconds that the MQGET calls issued by the bridge
     * should wait for second and subsequent request messages for the unit of
     * work started by this message.
     * */
    public final int getWaitInterval() {
        return _waitInterval;
    }

    /**
     * @param waitInterval Time in milliseconds that the MQGET calls issued by
     * the bridge should wait for second and subsequent request messages for
     * the unit of work started by this message.
     * */
    public final void setWaitInterval(final int waitInterval) {
        _waitInterval =  waitInterval;
    }

}
