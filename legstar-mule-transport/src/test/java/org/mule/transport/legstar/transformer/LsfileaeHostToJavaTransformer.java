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
package org.mule.transport.legstar.transformer;

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
