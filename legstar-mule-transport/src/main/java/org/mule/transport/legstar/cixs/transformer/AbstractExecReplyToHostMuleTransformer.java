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
package org.mule.transport.legstar.cixs.transformer;

import java.io.InputStream;

import org.mule.transformer.types.DataTypeFactory;
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
        setReturnDataType(DataTypeFactory.OBJECT);
    }

}
