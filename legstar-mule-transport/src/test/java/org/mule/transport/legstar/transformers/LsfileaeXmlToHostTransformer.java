package org.mule.transport.legstar.transformers;

import com.legstar.coxb.transform.HostTransformException;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaXmlTransformers;

/**
 * A sample java to host transformer.
 *
 */
public class LsfileaeXmlToHostTransformer extends AbstractXmlToHostMuleTransformer {

    /**
     * Pass binding for lsfileae single part Dfhcommarea.
     * @throws HostTransformException if transformer cannot be created
     */
    public LsfileaeXmlToHostTransformer() throws HostTransformException {
        super(new DfhcommareaXmlTransformers());
    }
}
