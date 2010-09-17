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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transport.legstar.i18n.LegstarMessages;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.util.ClassUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.host.HostContext;

/**
 * ESB Transformers derived from this class deal with esb messages which content is
 * pure zos data.
 */
public abstract class AbstractHostMuleTransformer extends AbstractMessageTransformer {

    /** Name of property holding the mainframe character set. */
    public static final String HOST_CHARSET_PROPERTY = "hostCharset";

    /** Configurable host character set.*/
    private String _hostCharset = HostContext.getDefaultHostCharsetName();

    /** Message labels. */
    private final LegstarMessages mI18NMessages = new LegstarMessages();

    /** Logger. */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    public Object transformMessage(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {

        if (_log.isDebugEnabled()) {
            _log.debug("Transform for message id: "
                    + esbMessage.getUniqueId()
                    + " source object is: "
                    + ClassUtils.getSimpleName(esbMessage.getPayload().getClass()));
        }
        /* Don't transform a message content if an exception is reported */
        if (esbMessage.getExceptionPayload() != null) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(),
                    this, esbMessage.getExceptionPayload().getException());
        }
        
        Object result = hostTransform(esbMessage, encoding);

        if (_log.isDebugEnabled()) {
            _log.debug("Resulting object is " + ClassUtils.getSimpleName(result.getClass()));
        }
        return result;
    }
    
    
    /**
     * Specialized classes perform the actual transformation.
     * @param esbMessage the Mule message
     * @param encoding the payload encoding
     * @return the transformed payload
     * @throws TransformerException if transform fails
     */
    public abstract Object hostTransform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException;
    
    /**
     * Gives a chance for a message to carry the mainframe character set.
     * @param message the esb message
     * @return the Target Mainframe character set from the
     * message properties or the configured one
     */
    public String getHostCharset(final MuleMessage message) {
        return message.getInboundProperty(HOST_CHARSET_PROPERTY,
        		getHostCharset());
    }
    
    /**
     * @return the configurable host character set
     */
    public String getHostCharset() {
        return _hostCharset;
    }

    /**
     * @param charset configurable host character set
     */
    public void setHostCharset(final String charset) {
        _hostCharset = charset;
    }
    
    /**
     * @return Message labels
     */
    public LegstarMessages getI18NMessages() {
        return mI18NMessages;
    }

}
