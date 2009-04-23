package org.mule.transport.legstar.transformers;

import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaTransformers;

/**
 * A sample host to java transformer.
 *
 */
public class LsfileaeHostToJavaTransformer extends AbstractHostToJavaMuleTransformer {

    /**
     * Pass binding transformers for lsfileae single part Dfhcommarea.
     */
    public LsfileaeHostToJavaTransformer() {
        super(new DfhcommareaTransformers());
        setReturnClass(Dfhcommarea.class);
    }
}
