/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.model;

import java.io.File;

import com.legstar.cixs.jaxws.model.CobolHttpClientType;
import com.legstar.cixs.jaxws.model.HttpTransportParameters;

/**
 * This is a model for Cixs to Mule component generation. The generated 
 * artifacts allow a CICS transaction to call a Mule component.
 *
 */
public class AntBuildCixs2MuleModel extends AbstractAntBuildCixsMuleModel {

    /** This generator name. */
    public static final String CIXS2MULE_GENERATOR_NAME =
        "Mule server artifacts generator";

    /** This velocity template that creates an ant build which in turn
     * generates the target component. */
    public static final String CIXS2MULE_VELOCITY_MACRO_NAME =
        "vlc/build-cixs2mule-xml.vm";
    
    /** The target directory where COBOL files will be created. */
    private File mTargetCobolDir;
    
    /** Set of parameters needed for HTTP transport. */
    private HttpTransportParameters mHttpTransportParameters;

    /** The default port number on which the HTTP server listens. */
    public static final int DEFAULT_HTTP_PORT = 8083;

    /** Default pattern for server PATH. Must be kept in sync with
     * various velocity templates. */
    public static final String DEFAULT_SERVER_PATH_TEMPLATE =
        "/legstar/services/${service.name}/";

    /** The type of Http sample Cobol client to generate. */
    private CobolHttpClientType mSampleCobolHttpClientType = CobolHttpClientType.DFHWBCLI;

    /** Set of parameters needed to invoke a UMO component. */
    private UmoComponentParameters mUmoComponentTargetParameters;

    /**
     * Construct the model.
     */
    public AntBuildCixs2MuleModel() {
        super(CIXS2MULE_GENERATOR_NAME, CIXS2MULE_VELOCITY_MACRO_NAME);
        mHttpTransportParameters = new HttpTransportParameters();
        mHttpTransportParameters.setPort(DEFAULT_HTTP_PORT);
        mUmoComponentTargetParameters = new UmoComponentParameters();
    }

    /**
     * @return the directory where COBOL files will be created
     */
    public final File getTargetCobolDir() {
        return mTargetCobolDir;
    }

    /**
     * @param targetCobolDir the directory where COBOL files will be created to set
     */
    public final void setTargetCobolDir(final File targetCobolDir) {
        mTargetCobolDir = targetCobolDir;
    }

    /**
     * @return The type of Http sample Cobol client to generate
     */
    public CobolHttpClientType getSampleCobolHttpClientType() {
        return mSampleCobolHttpClientType;
    }

    /**
     * @param sampleCobolHttpClientType The type of Http sample Cobol client to generate
     */
    public void setSampleCobolHttpClientType(
            final CobolHttpClientType sampleCobolHttpClientType) {
        mSampleCobolHttpClientType = sampleCobolHttpClientType;
    }

    /**
     * @return set of parameters needed for HTTP transport
     */
    public HttpTransportParameters getHttpTransportParameters() {
        return mHttpTransportParameters;
    }

    /**
     * @param httpTransportParameters set of parameters needed for HTTP transport
     */
    public void setHttpTransportParameters(
            final HttpTransportParameters httpTransportParameters) {
        mHttpTransportParameters = httpTransportParameters;
    }

    /**
     * @return the set of parameters needed to invoke a UMO component
     */
    public UmoComponentParameters getUmoComponentTargetParameters() {
        return mUmoComponentTargetParameters;
    }

    /**
     * @param umoComponentTargetParameters the set of parameters needed to invoke a UMO component to set
     */
    public void setUmoComponentTargetParameters(
            final UmoComponentParameters umoComponentTargetParameters) {
        mUmoComponentTargetParameters = umoComponentTargetParameters;
    }

}
