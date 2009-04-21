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
package org.mule.providers.legstar.transformers;

import java.util.Map;

import org.mule.providers.legstar.i18n.LegstarMessages;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.api.MuleMessage;

import com.legstar.coxb.host.HostContext;
import com.legstar.coxb.transform.AbstractTransformers;

/**
 * ESB Transformers derived from this class deal with esb messages which content is
 * pure zos data.
 * <p/>
 * The zos data is either a formatted LegStarMessage (if the correspondent on zos
 * is LegStar-aware) or raw zos data which is the case when the correspondent does
 * not use LegStar (case of WMQ for instance).
 * <p/>
 * Usage of LegStarMessage or not is determined dynamically, therefore a single
 * service can service both LegStar-aware and non LegStar-aware correspondents
 * at the same time.
 * <p/>
 * Esb messages, in and out, are assumed to hold content at the default
 * body location.
 */
public abstract class AbstractHostEsbTransformer extends AbstractMessageAwareTransformer {

    /** Name of property holding the mainframe character set. */
    public static final String HOST_CHARSET_PROPERTY = "hostCharset";

    /** Name of property holding if mainframe needs to trace incoming requests. */
    public static final String HOST_TRACE_MODE_PROPERTY = "hostTraceMode";

    /** Mainframe character set. */
    private String mHostCharset = HostContext.getDefaultHostCharsetName();

    /** A boolean property indicating that peer expects host data to be wrapped as a LegStarMessage. */
    public static final String IS_LEGSTAR_MESSAGING = "isLegStarMessaging";
    
    /** Message labels. */
    private LegstarMessages mLegstarMessages = new LegstarMessages();

   /**
     * Single part transformers are associated with a set of binding transformers
     * which are responsible to marshaling/unmarshaling the data payload.
     */
    private AbstractTransformers mBindingTransformers;

    /**
     * Multi-part transformers are associated with a map of binding transformers
     * which are responsible to marshaling/unmarshaling the data payload.
     * Each binding transformers set is identified by a unique name.
     */
    private Map < String, AbstractTransformers > mBindingTransformersMap;
    
    /**
     * Constructor for single part transformers.
     * @param bindingTransformers a set of transformers for the part type.
     */
    public AbstractHostEsbTransformer(
            final AbstractTransformers bindingTransformers) {
        mBindingTransformers = bindingTransformers;
    }
    
    /**
     * Constructor for multi-part transformers.
     * @param bindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractHostEsbTransformer(
            final Map < String, AbstractTransformers > bindingTransformersMap) {
        mBindingTransformersMap = bindingTransformersMap;
    }
    
    /**
     * @return the transformers set to use for java to host transformations
     */
    public final AbstractTransformers getBindingTransformers() {
        return mBindingTransformers;
    }

    /**
     * @return the transformers sets map to use for java to host transformations
     */
    public final Map < String, AbstractTransformers > getBindingTransformersMap() {
        return mBindingTransformersMap;
    }

    /**
     * Checks an ESB message properties to detect if peer is expecting host data
     * to be wrapped as a LegStarMessage.
     * @param esbMessage the ESB message containing host data
     * @return true if there is proof that upstream action requested LegStarMessage wrapping
     */
    public static boolean isLegStarMessaging(final MuleMessage esbMessage) {
        return esbMessage.getBooleanProperty(IS_LEGSTAR_MESSAGING, false);
    }
    
    /**
     * Mark an ESB Message to signal if the peer requests the host data to be
     * wrapped in a LegStarMessage or not.
     * @param esbMessage the ESB message to mark
     * @param isLegStarMessaging whether to mark with true or false
     */
    public static void setLegStarMessaging(
            final MuleMessage esbMessage, final boolean isLegStarMessaging) {
        esbMessage.setBooleanProperty(
                IS_LEGSTAR_MESSAGING, Boolean.valueOf(isLegStarMessaging));
    }

    /**
     * Gives a chance for a message to carry the mainframe character set.
     * @param message the esb message
     * @return the Target Mainframe character set from the
     * message properties or the configured one
     */
    public final String getHostCharset(final MuleMessage message) {
        return message.getStringProperty(
                HOST_CHARSET_PROPERTY, getHostCharset());
    }

    /**
     * @return the Mainframe character set
     */
    public final String getHostCharset() {
        if (mHostCharset == null || mHostCharset.length() == 0) {
            return HostContext.getDefaultHostCharsetName();
        }
        return mHostCharset;
    }

    /**
     * @param hostCharset the Mainframe character set to set
     */
    public final void setHostCharset(final String hostCharset) {
        mHostCharset = hostCharset;
    }

    /**
     * @return Message labels
     */
    public LegstarMessages getLegstarMessages() {
        return mLegstarMessages;
    }

}
