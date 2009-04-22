package org.mule.providers.legstar.transformers;

import com.legstar.test.coxb.lsfileae.Dfhcommarea;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaTransformers;

/**
 * A sample java to host transformer.
 *
 */
public class LsfileaeJavaToHostTransformer extends AbstractJavaToHostMuleTransformer {

    /**
     * Pass binding for lsfileae single part Dfhcommarea.
     */
    public LsfileaeJavaToHostTransformer() {
        super(new DfhcommareaTransformers());
        registerSourceType(Dfhcommarea.class);
    }
}
