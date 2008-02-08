package org.mule.providers.legstar.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

/**
 * Indirect references to messages from a bundle.
 */
public class LegstarMessages extends MessageFactory {
    
    /** Bundle is found under org.mule.i18n legstar-messages.properties. */
    private static final String BUNDLE_PATH = getBundlePath("legstar");

    /**
     * @return invalid legstar header in incoming message.
     */
    public static Message invalidLegstarHeader() {
        return createMessage(BUNDLE_PATH, 1);
    }

    /**
     * @return something went wrong while receiving the host data.
     */
    public static Message errorReceivingHostData() {
        return createMessage(BUNDLE_PATH, 2);
    }

    /**
     * @return host data size must at least be large enough to hold
     * a header. It cannot be zero.
     */
    public static Message invalidHostDataSize() {
        return createMessage(BUNDLE_PATH, 3);
    }

    /**
     * @return was not able to create a host message.
     */
   public static Message errorFormattingHostData() {
        return createMessage(BUNDLE_PATH, 4);
    }
}
