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

