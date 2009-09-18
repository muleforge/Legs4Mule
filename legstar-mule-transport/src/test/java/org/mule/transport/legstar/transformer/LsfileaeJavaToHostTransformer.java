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
