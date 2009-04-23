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
package org.mule.transport.legstar.transformers;

import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;

/**
 * Test AbstractXmlToHostTransformer class.
 *
 */
public class LsfileaeXmlToHostTransformerTest extends AbstractTransformerTestCase {

    /** {@inheritDoc} */
    public Transformer getTransformer() throws Exception {
        return new LsfileaeXmlToHostTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return HostData.toByteArray(LsfileaeCases.getHostBytesHex());
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return LsfileaeCases.getXml();
    }

}
