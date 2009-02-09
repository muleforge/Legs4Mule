/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.sample;

import java.util.ArrayList;
import java.util.List;

public class JVMQueryRequest {
    
    private List <String> mEnvVarNames = new ArrayList <String>();

    /**
     * @return the environment variable names to get
     */
    public final List <String> getEnvVarNames() {
        return mEnvVarNames;
    }

    /**
     * @param envVarNames the the environment variable names to set
     */
    public final void setEnvVarNames(List<String> envVarNames) {
        mEnvVarNames = envVarNames;
    }

}
