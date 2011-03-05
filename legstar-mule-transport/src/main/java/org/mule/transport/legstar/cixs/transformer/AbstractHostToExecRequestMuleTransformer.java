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

import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.legstar.config.HostProgram;
import org.mule.transport.legstar.transformer.AbstractHostMuleTransformer;


/**
 * Code common to host to transformers that produce mainframe program
 * execution requests.
 * <p/>
 * Source data is already in mainframe format. These transformers wrap
 * that mainframe payload into a messaging envelope understood by the
 * target execution environment on the mainframe.
 */
public abstract class AbstractHostToExecRequestMuleTransformer extends AbstractHostMuleTransformer {

    /** Target mainframe program attributes. */
    private HostProgram _hostProgram;

    /** Reply URL. This is useful when the request needs to instruct the mainframe on
     * where to store the reply. */
    private String _replyTo;
    
    /** True if mainframe server is to produce detailed traces. */
    private boolean _hostTraceOn = false;
    
    /** Property name that holds true if mainframe should trace execution requests. */
    public static final String LEGSTAR_HOST_TRACE_ON_KEY = "LEGSTAR_HOST_TRACE_ON";
    
    /** WMQ message IDs and correlation IDs are exactly 24 bytes long. */
    private static final int WMQ_ID_LEN = 24;
    
    /** WMQ message IDs and correlation IDs are actually byte arrays. The MQ-JMS
     * library converts the content into a String starting with prefix ID: 
     * followed by the hex representation of the 24 bytes. */
    private static final String WMQ_JMS_ID_PREFIX = "ID:";

    /**
     * Constructor registers source and return classes.
     */
    public AbstractHostToExecRequestMuleTransformer() {
        registerSourceType(DataTypeFactory.create(Map.class));
        registerSourceType(DataTypeFactory.BYTE_ARRAY);
        setReturnDataType(DataTypeFactory.BYTE_ARRAY);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
	public Object hostTransform(final MuleMessage esbMessage,
			final String outputEncoding) throws TransformerException {

		Object src = esbMessage.getPayload();

		byte[] wrappedHostData = null;
		if (src instanceof byte[]) {
			wrappedHostData = wrapHostData((byte[]) src, esbMessage);
		} else if (src instanceof Map) {
			wrappedHostData = wrapHostData((Map<String, byte[]>) src,
					esbMessage);
		}

		/* This will useful when the message is dispatched to the mainframe. */
		esbMessage.setReplyTo(getReplyTo());

		/*
		 * If the message specifies a host trace mode keep it. otherwise use the
		 * setting at the transformer level
		 */
		boolean isHostTraceOn = esbMessage.getInboundProperty(
				LEGSTAR_HOST_TRACE_ON_KEY, isHostTraceOn());
		esbMessage
				.setOutboundProperty(LEGSTAR_HOST_TRACE_ON_KEY, isHostTraceOn);

		setMessageProperties(esbMessage);

		return wrappedHostData;
	}

    /**
     * This method wraps a single part payload into a mainframe program execution request message.
     * @param hostData the single part mainframe payload
     * @param esbMessage the original mule message
     * @return the payload eventually wrapped in a mainframe program execution request message
     * @throws TransformerException if wrapping fails
     */
    public abstract byte[] wrapHostData(
            final byte[] hostData, final MuleMessage esbMessage) throws TransformerException;

    /**
     * If the mainframe is expecting a formatted mainframe program execution request message, this method
     * wraps a multi part payloads into a mainframe program execution request message.
     * @param hostDataMap the multi part mainframe payload (one entry per container)
     * @param esbMessage the original mule message
     * @return the payload eventually wrapped in a mainframe program execution request message
     * @throws TransformerException if wrapping fails
     */
    public abstract byte[] wrapHostData(
            final Map < String, byte[] > hostDataMap, final MuleMessage esbMessage) throws TransformerException;
    
    /**
     * Allows derived classes to set additional properties on the esb message.
     * @param esbMessage the original mule message
     */
    public abstract void setMessageProperties(final MuleMessage esbMessage);

    /**
     * Creates a correlation ID that is compatible with mainframe transports.
     * The rules applied are those of WMQ, the main consumer of correlation IDs.
     * @param id the ID as Mule would have it
     * @return the ID as the mainframe would propagate it
     */
    public String getHostCompatibleCorrelationId(final String id) {
        if (id == null) {
            return null;
        }
        char[] chars = id.toCharArray();
        StringBuffer output = new StringBuffer();
        output.append(WMQ_JMS_ID_PREFIX);
        for (int i = 0; i < chars.length && i < WMQ_ID_LEN; i++) {
            output.append(Integer.toHexString((int) chars[i]));
        }
        return output.toString();
    }

    /**
     * @return target mainframe program attributes
     */
    public HostProgram getHostProgram() {
        return _hostProgram;
    }

    /**
     * @param hostProgram target mainframe program attributes
     */
    public void setHostProgram(final HostProgram hostProgram) {
        _hostProgram = hostProgram;
    }

    /**
     * @return reply URL. This is useful when the request needs to instruct the mainframe on
     * where to store the reply
     */
    public String getReplyTo() {
        return _replyTo;
    }

    /**
     * @param to reply URL. This is useful when the request needs to instruct the mainframe on
     * where to store the reply
     */
    public void setReplyTo(final String to) {
        _replyTo = to;
    }

    /**
     * @return true if mainframe server is to produce detailed traces
     */
    public boolean isHostTraceOn() {
        return _hostTraceOn;
    }

    /**
     * @param traceOn true if mainframe server is to produce detailed traces
     */
    public void setHostTraceOn(final boolean traceOn) {
        _hostTraceOn = traceOn;
    }

}
