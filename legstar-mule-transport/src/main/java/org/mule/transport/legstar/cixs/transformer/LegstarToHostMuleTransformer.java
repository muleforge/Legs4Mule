package org.mule.transport.legstar.cixs.transformer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transport.legstar.i18n.LegstarMessages;

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
public class LegstarToHostMuleTransformer extends AbstractMessageAwareTransformer {

    /** Message labels. */
    private final LegstarMessages mI18NMessages = new LegstarMessages();

    /** Logger. */
    private final Log _log = LogFactory.getLog(getClass());

    /**
     * Constructor registers source and return classes.
     * Because the output is a byte[] or a Map we can't be specific about the return type.
     */
    public LegstarToHostMuleTransformer() {
        registerSourceType(InputStream.class);
        registerSourceType(byte[].class);
        setReturnClass(Object.class);
    }

    /** {@inheritDoc} */
    @Override
    public Object transform(final MuleMessage esbMessage, final String outputEncoding)
            throws TransformerException {

        if (_log.isDebugEnabled()) {
            _log.debug("Transform request for type " + esbMessage.getPayload().getClass().getSimpleName());
        }
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

    /**
     * @return Message labels
     */
    public LegstarMessages getI18NMessages() {
        return mI18NMessages;
    }

}
