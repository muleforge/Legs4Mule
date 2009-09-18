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
