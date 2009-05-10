package org.mule.transport.legstar.cixs.transformer;

import java.io.InputStream;

import org.mule.transport.legstar.transformer.AbstractHostMuleTransformer;

/**
 * Code common to host to transformers that extract reply payload from
 * a mainframe execution reply.
 * <p/>
 * Source data is a byte array or a stream and the return type is either a
 * byte array if the reply is single part or a map of byte arrays if the
 * reply is multi-part.
 */
public abstract class AbstractExecReplyToHostMuleTransformer extends AbstractHostMuleTransformer {

    /**
     * Constructor registers source and return classes.
     * Because the output is a byte[] or a Map we can't be specific about the return type.
     */
    public AbstractExecReplyToHostMuleTransformer() {
        registerSourceType(InputStream.class);
        registerSourceType(byte[].class);
        setReturnClass(Object.class);
    }

}
