package org.mule.providers.legstar.transformers;

import java.util.HashMap;

import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusTransformers;

/**
 * A simplistic implementation of the abstract class being tested.
 *
 */
public class LsfileacJavaToHostTransformer extends AbstractJavaToHostMuleTransformer {

    /**
     * Pass binding transformers for lsfileac multi part payload.
     */
    public LsfileacJavaToHostTransformer() {
        super(new HashMap < String, AbstractTransformers >());
        getBindingTransformersMap().put("ReplyData", new ReplyDataTransformers());
        getBindingTransformersMap().put("ReplyStatus", new ReplyStatusTransformers());
        registerSourceType(LsfileacHolder.class);
    }

    /** {@inheritDoc} */
    public Object getObjectFromHolder(
            final Object holderObject,
            final String partID) throws TransformerException {

        if (partID.equals("ReplyData")) {
            return ((LsfileacHolder) holderObject).getReplyData();
        } else if (partID.equals("ReplyStatus")) {
            return ((LsfileacHolder) holderObject).getReplyStatus();
        }
        return null;
    }
}

