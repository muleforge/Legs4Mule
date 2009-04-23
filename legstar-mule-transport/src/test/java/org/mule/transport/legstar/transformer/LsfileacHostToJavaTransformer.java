package org.mule.transport.legstar.transformer;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractTransformers;
import com.legstar.test.coxb.lsfileac.ReplyData;
import com.legstar.test.coxb.lsfileac.ReplyStatus;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusTransformers;

/**
 * A simplistic implementation of the abstract class being tested.
 *
 */
public class LsfileacHostToJavaTransformer extends AbstractHostToJavaMuleTransformer {

    /**
     * Pass binding transformers for lsfileac multi part holder.
     */
    public LsfileacHostToJavaTransformer() {
        super(new HashMap < String, AbstractTransformers >());
        getBindingTransformersMap().put("ReplyData", new ReplyDataTransformers());
        getBindingTransformersMap().put("ReplyStatus", new ReplyStatusTransformers());
        setReturnClass(LsfileacHolder.class);
    }

    /** {@inheritDoc} */
    public Object createHolder(
            final Map < String, Object > transformedParts) throws TransformerException {
        LsfileacHolder holder = new LsfileacHolder();
        holder.setReplyData((ReplyData) transformedParts.get("ReplyData"));
        holder.setReplyStatus((ReplyStatus) transformedParts.get("ReplyStatus"));
        return holder;
    }
}

