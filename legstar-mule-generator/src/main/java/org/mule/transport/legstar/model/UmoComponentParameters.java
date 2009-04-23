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
package org.mule.transport.legstar.model;

import java.util.Map;

import com.legstar.codegen.CodeGenMakeException;

/**
 * Set of parameters describing UMO component target.
 */
public class UmoComponentParameters {

    /* ====================================================================== */
    /* = Constants section                                                  = */
    /* ====================================================================== */
    /** The class of the object or an object reference of the component being registered as an UMO. */ 
    public static final String UMO_COMPONENT_IMPLEMENTATION_NAME_PROPERTY = "umoComponentImplementationName";

    /* ====================================================================== */
    /* = Properties section                                                 = */
    /* ====================================================================== */
    /** The target UMO component category. */
    private String mImplementationName;
    
    /**
     * UMO component parameters are expected by templates to come from a parameters map.
     * @param parameters a parameters map to which UMO component parameters must be added
     */
    public void add(final Map < String, Object > parameters) {
        parameters.put(UMO_COMPONENT_IMPLEMENTATION_NAME_PROPERTY, getImplementationName());
    }

    /**
     * When target is an UMO component, check that corresponding parameters are set correctly.
     * @throws CodeGenMakeException if parameters are missing or wrong
     */
    public void check() throws CodeGenMakeException {
        if (getImplementationName() == null || getImplementationName().length() == 0) {
            throw new CodeGenMakeException(
            "Missing target UMO component implementation");
        }
    }

    /**
     * @return the target UMO component implementation name
     */
    public String getImplementationName() {
        return mImplementationName;
    }

    /**
     * @param implementationName the target UMO component implementation name to set
     */
    public void setImplementationName(final String implementationName) {
        mImplementationName = implementationName;
    }

}
