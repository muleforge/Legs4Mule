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
package org.mule.transport.legstar.transformers;

import java.util.Map;

import com.legstar.coxb.transform.AbstractTransformers;

/**
 * Transform java objects to and from host data.
 * <p/>
 * Esb messages, in and out, are assumed to hold java content at the default
 * body location.
 */
public abstract class AbstractHostJavaMuleTransformer extends AbstractHostMuleTransformer {

   /**
     * Single part transformers are associated with a set of binding transformers
     * which are responsible for marshaling/unmarshaling the data payload.
     */
    private final AbstractTransformers mBindingTransformers;

    /**
     * Multi-part transformers are associated with a map of binding transformers
     * which are responsible to marshaling/unmarshaling the data payload.
     * Each binding transformers set is identified by a unique name.
     */
    private final Map < String, AbstractTransformers > mBindingTransformersMap;
    
    /**
     * Constructor for single part transformers.
     * @param bindingTransformers a set of transformers for the part type.
     */
    public AbstractHostJavaMuleTransformer(
            final AbstractTransformers bindingTransformers) {
        mBindingTransformers = bindingTransformers;
        mBindingTransformersMap = null;
    }
    
    /**
     * Constructor for multi-part transformers.
     * @param bindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractHostJavaMuleTransformer(
            final Map < String, AbstractTransformers > bindingTransformersMap) {
        mBindingTransformers = null;
        mBindingTransformersMap = bindingTransformersMap;
    }
    
    /**
     * @return the transformers set to use for java to host transformations
     */
    public AbstractTransformers getBindingTransformers() {
        return mBindingTransformers;
    }

    /**
     * @return the transformers sets map to use for java to host transformations
     */
    public Map < String, AbstractTransformers > getBindingTransformersMap() {
        return mBindingTransformersMap;
    }

}
