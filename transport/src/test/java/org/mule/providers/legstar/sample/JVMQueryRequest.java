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
