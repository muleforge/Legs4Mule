package org.mule.transport.legstar.test.lsfileae;

import org.mule.api.MuleMessage;
import org.mule.routing.MuleMessageInfoMapping;

/**
 * Regular Mule message ID cannot be used as correlation criteria for WMQ.
 * WMQ has limitations on the ID size and also considers IDs to be byte arrays
 * not strings.
 */
public class WmqMessageInfoMapping extends MuleMessageInfoMapping {
    
    /** WMQ message IDs and correlation IDs are exactly 24 bytes long. */
    private static final int WMQ_ID_LEN = 24;
    
    /** WMQ message IDs and correlation IDs are actually byte arrays. The MQ-JMS
     * library converts the content into a String starting with prefix ID: 
     * followed by the hex representation of the 24 bytes. */
    private static final String WMQ_JMS_ID_PREFIX = "ID:";

    /** {@inheritDoc} */
    public String getMessageId(final MuleMessage message) {
        char[] chars = message.getUniqueId().toCharArray();
        StringBuffer output = new StringBuffer();
        output.append(WMQ_JMS_ID_PREFIX);
        for (int i = 0; i < chars.length && i < WMQ_ID_LEN; i++) {
            output.append(Integer.toHexString((int) chars[i]));
        }
        return output.toString();
    }

}
