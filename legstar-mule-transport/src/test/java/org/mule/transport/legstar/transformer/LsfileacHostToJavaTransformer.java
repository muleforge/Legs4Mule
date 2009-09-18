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

