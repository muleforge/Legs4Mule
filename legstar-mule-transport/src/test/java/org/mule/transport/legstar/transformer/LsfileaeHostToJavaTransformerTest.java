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


import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;
import com.legstar.test.coxb.lsfileae.Dfhcommarea;

/**
 * Test AbstractHostToJavaTransformer class.
 *
 */
public class LsfileaeHostToJavaTransformerTest extends AbstractTransformerTestCase {

    /** {@inheritDoc} */
    public Transformer getTransformer() throws Exception {
        return new LsfileaeHostToJavaTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return LsfileaeCases.getJavaObject();
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return HostData.toByteArray(LsfileaeCases.getHostBytesHex());
    }

    /** {@inheritDoc} */
    public boolean compareResults(final Object expected, final Object result) {
        if (result == null || !(result instanceof Dfhcommarea)) {
            return false;
        }
        LsfileaeCases.checkJavaObject((Dfhcommarea) result);
        return true;
    }
}
