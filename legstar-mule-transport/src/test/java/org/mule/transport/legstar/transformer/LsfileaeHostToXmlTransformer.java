package org.mule.transport.legstar.transformer;

import com.legstar.coxb.transform.HostTransformException;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaXmlTransformers;

/**
 * A sample host to xml transformer.
 *
 */
public class LsfileaeHostToXmlTransformer extends AbstractHostToXmlMuleTransformer {

    /**
     * Pass binding transformers for lsfileae single part Dfhcommarea.
     * @throws HostTransformException if transformer cannot be created
     */
    public LsfileaeHostToXmlTransformer() throws HostTransformException {
        super(new DfhcommareaXmlTransformers());
    }
}
