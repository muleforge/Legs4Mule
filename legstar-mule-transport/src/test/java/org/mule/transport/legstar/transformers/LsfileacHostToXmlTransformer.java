package org.mule.transport.legstar.transformers;

import java.util.HashMap;

import javax.xml.namespace.QName;

import org.mule.api.transformer.TransformerException;

import com.legstar.coxb.transform.AbstractXmlTransformers;
import com.legstar.coxb.transform.HostTransformException;
import com.legstar.test.coxb.lsfileac.bind.ReplyDataXmlTransformers;
import com.legstar.test.coxb.lsfileac.bind.ReplyStatusXmlTransformers;

/**
 * A sample host to xml transformer.
 *
 */
public class LsfileacHostToXmlTransformer extends AbstractHostToXmlMuleTransformer {

    /**
     * Pass binding transformers for lsfileac multi part payload.
     * @throws HostTransformException if transformer cannot be created
     */
    public LsfileacHostToXmlTransformer() throws HostTransformException {
        super(new HashMap < String, AbstractXmlTransformers >());
        getXmlBindingTransformersMap().put("ReplyData", new ReplyDataXmlTransformers());
        getXmlBindingTransformersMap().put("ReplyStatus", new ReplyStatusXmlTransformers());
    }

    /** {@inheritDoc} */
    public QName getHolderQName() throws TransformerException {
        return new QName("http://cixs.test.legstar.com/lsfileac",
                "LsfileacResponseHolder");
        
    }
}
