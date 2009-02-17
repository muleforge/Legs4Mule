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
package org.mule.providers.legstar.transformers;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;
import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaTransformers;

/**
 * Test AbstractJavaToHostTransformer class.
 *
 */
public class LsfileaeJavaToHostTransformerTest extends AbstractTransformerTestCase {

    /**
     * A simplistic implementation of the abstract class being tested.
     *
     */
    public static class LsfileaeJavaToHostTransformer extends AbstractJavaToHostEsbTransformer {

        /**
         * Pass binding transformers for lsfileae single part Dfhcommarea.
         */
        public LsfileaeJavaToHostTransformer() {
            super(new DfhcommareaTransformers());
            registerSourceType(Dfhcommarea.class);
        }

    }

    /** {@inheritDoc} */
    public UMOTransformer getTransformer() throws Exception {
        return new LsfileaeJavaToHostTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return HostData.toByteArray(LsfileaeCases.getHostBytesHex());
    }

    /** {@inheritDoc} */
    public UMOTransformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return LsfileaeCases.getJavaObject();
    }

}
