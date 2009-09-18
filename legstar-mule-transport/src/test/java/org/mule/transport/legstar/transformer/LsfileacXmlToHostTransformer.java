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

import com.legstar.coxb.transform.AbstractXmlTransformers;
import com.legstar.coxb.transform.HostTransformException;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataXmlTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusXmlTransformers;

/**
 * A sample java to host transformer.
 *
 */
public class LsfileacXmlToHostTransformer extends AbstractXmlToHostMuleTransformer {

    /**
     * Pass binding for lsfileac multi part payload.
     * @throws HostTransformException if transformer cannot be created
     */
    public LsfileacXmlToHostTransformer() throws HostTransformException {
        super(new HashMap < String, AbstractXmlTransformers >());
        getXmlBindingTransformersMap().put("ReplyData", new ReplyDataXmlTransformers());
        getXmlBindingTransformersMap().put("ReplyStatus", new ReplyStatusXmlTransformers());
    }
}
