package org.mule.transport.legstar.transformers;

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
