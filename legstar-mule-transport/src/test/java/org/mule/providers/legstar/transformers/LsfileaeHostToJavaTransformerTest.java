package org.mule.providers.legstar.transformers;


import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import com.legstar.coxb.host.HostData;
import com.legstar.test.coxb.LsfileaeCases;
import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaTransformers;

/**
 * Test AbstractHostToJavaTransformer class.
 *
 */
public class LsfileaeHostToJavaTransformerTest extends AbstractTransformerTestCase {

    /**
     * A simplistic implementation of the abstract class being tested.
     *
     */
    public static class LsfileaeHostToJavaTransformer extends AbstractHostToJavaEsbTransformer {

        /**
         * Pass binding transformers for lsfileae single part Dfhcommarea.
         */
        public LsfileaeHostToJavaTransformer() {
            super(new DfhcommareaTransformers());
            setReturnClass(Dfhcommarea.class);
        }

    }

    /** {@inheritDoc} */
    public UMOTransformer getTransformer() throws Exception {
        return new LsfileaeHostToJavaTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return LsfileaeCases.getJavaObject();
    }

    /** {@inheritDoc} */
    public UMOTransformer getRoundTripTransformer() throws Exception {
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
