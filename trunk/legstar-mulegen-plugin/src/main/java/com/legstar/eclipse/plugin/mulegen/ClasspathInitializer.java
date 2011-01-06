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
package com.legstar.eclipse.plugin.mulegen;

import com.legstar.eclipse.plugin.common.AbstractClasspathInitializer;

/**
 * This extension allows us to declare the LegStar library container which
 * projects might use to get all necessary LegStar dependencies.
 *
 */
public class ClasspathInitializer extends AbstractClasspathInitializer {

    /** The library container identifier for LegStar. */
    public static final String LIBRARY_NAME =
        "com.legstar.eclipse.mule.LIBRARY";

    /** The library container description. */
    public static final String LIBRARY_DESCRIPTION =
        "LegStar Mule library container";

    /**
     * Pass all parameters needed to the parent class.
     */
    public ClasspathInitializer() {
        super(Activator.PLUGIN_ID, LIBRARY_NAME, LIBRARY_DESCRIPTION);
    }

}
