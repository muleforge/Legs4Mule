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
import java.util.Properties;

import com.legstar.codegen.CodeGenMakeException;
import com.legstar.codegen.models.AbstractPropertiesModel;

/**
 * Set of parameters describing UMO component target.
 */
public class UmoComponentParameters extends AbstractPropertiesModel {

    /* ====================================================================== */
    /* Following are this class fields that are persistent.                 = */
    /* ====================================================================== */

    /** The target UMO component category. */
    private String _implementationName;
    
    /* ====================================================================== */
    /* Following are key identifiers for this model persistence. = */
    /* ====================================================================== */

    /** The class of the object or an object reference of the component being registered as an UMO. */ 
    public static final String UMO_COMPONENT_IMPLEMENTATION_NAME_PROPERTY = "umoComponentImplementationName";

    /**
     * Construct an empty model.
     */
    public UmoComponentParameters() {
        
    }
    
    /**
     * Construct from a properties file.
     * 
     * @param props
     *            the property file
     */
    public UmoComponentParameters(final Properties props) {
        super(props);
        setImplementationName(getString(props,
                UMO_COMPONENT_IMPLEMENTATION_NAME_PROPERTY, null));
    }
 
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
        return _implementationName;
    }

    /**
     * @param implementationName the target UMO component implementation name to set
     */
    public void setImplementationName(final String implementationName) {
        _implementationName = implementationName;
    }

    /**
     * @return a properties file holding the values of this object fields
     */
    public Properties toProperties() {
        Properties props = super.toProperties();
        putString(props, UMO_COMPONENT_IMPLEMENTATION_NAME_PROPERTY, getImplementationName());
        return props;
    }
}
