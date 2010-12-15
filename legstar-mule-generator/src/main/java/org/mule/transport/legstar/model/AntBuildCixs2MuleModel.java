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

import java.io.File;
import java.util.Properties;

import com.legstar.cixs.gen.model.options.CobolHttpClientType;

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
    
    /* ====================================================================== */
    /* Following are this class default values.                             = */
    /* ====================================================================== */

    /** The default port number on which the HTTP server listens. */
    public static final int DEFAULT_HTTP_PORT = 8083;

    /** Default pattern for server PATH. Must be kept in sync with
     * various velocity templates. */
    public static final String DEFAULT_SERVER_PATH_TEMPLATE =
        "/legstar/services/${service.name}";

    /** Default sample COBOL http client type. */
    public static final CobolHttpClientType DEFAULT_COBOL_HTTP_CLIENT_TYPE =
        CobolHttpClientType.DFHWBCLI;

    /* ====================================================================== */
    /* Following are this class fields that are persistent.                 = */
    /* ====================================================================== */

    /** The target directory where COBOL files will be created. */
    private File _targetCobolDir;
    
    /** The type of Http sample Cobol client to generate. */
    private CobolHttpClientType _sampleCobolHttpClientType = DEFAULT_COBOL_HTTP_CLIENT_TYPE;

    /** Set of parameters needed to invoke a UMO component. */
    private UmoComponentParameters _umoComponentTargetParameters;

    /* ====================================================================== */
    /* Following are key identifiers for this model persistence. = */
    /* ====================================================================== */

    /** Target source directory. */
    public static final String TARGET_COBOL_DIR = "targetCobolDir";

    /** Target source directory. */
    public static final String SAMPLE_COBOL_HTTP_CLIENT_TYPE = "sampleCobolHttpClientType";

    /** Target source directory. */
    public static final String UMO_COMPONENT_PARAMETERS = "umoComponentTargetParameters";

    /**
     * Construct the model.
     */
    public AntBuildCixs2MuleModel() {
        super(CIXS2MULE_GENERATOR_NAME, CIXS2MULE_VELOCITY_MACRO_NAME);
        getHttpTransportParameters().setPort(DEFAULT_HTTP_PORT);
        _umoComponentTargetParameters = new UmoComponentParameters();
    }

    /**
     * Construct from a properties file.
     * Construct the model.
     */
    public AntBuildCixs2MuleModel(final Properties props) {
        super(CIXS2MULE_GENERATOR_NAME, CIXS2MULE_VELOCITY_MACRO_NAME, props);
        setTargetCobolDir(getFile(props, TARGET_COBOL_DIR, null));
        setSampleCobolHttpClientType(getString(props,
                SAMPLE_COBOL_HTTP_CLIENT_TYPE, DEFAULT_COBOL_HTTP_CLIENT_TYPE
                        .toString()));
        _umoComponentTargetParameters = new UmoComponentParameters(props);
    }

    /**
     * @return the directory where COBOL files will be created
     */
    public final File getTargetCobolDir() {
        return _targetCobolDir;
    }

    /**
     * @param targetCobolDir the directory where COBOL files will be created to set
     */
    public final void setTargetCobolDir(final File targetCobolDir) {
        _targetCobolDir = targetCobolDir;
    }

    /**
     * @return The type of Http sample Cobol client to generate
     */
    public CobolHttpClientType getSampleCobolHttpClientType() {
        return _sampleCobolHttpClientType;
    }

    /**
     * @param sampleCobolHttpClientType The type of Http sample Cobol client to generate
     */
    public void setSampleCobolHttpClientType(
            final CobolHttpClientType sampleCobolHttpClientType) {
        _sampleCobolHttpClientType = sampleCobolHttpClientType;
    }

    /**
     * @param sampleCobolHttpClientType
     *            The type of Http sample Cobol client to generate
     */
    public void setSampleCobolHttpClientType(
            final String sampleCobolHttpClientType) {
        setSampleCobolHttpClientType(CobolHttpClientType
                .valueOf(sampleCobolHttpClientType));
    }

    /**
     * @return the set of parameters needed to invoke a UMO component
     */
    public UmoComponentParameters getUmoComponentTargetParameters() {
        return _umoComponentTargetParameters;
    }

    /**
     * @param umoComponentTargetParameters the set of parameters needed to invoke a UMO component to set
     */
    public void setUmoComponentTargetParameters(
            final UmoComponentParameters umoComponentTargetParameters) {
        _umoComponentTargetParameters = umoComponentTargetParameters;
    }

    /**
     * @return a properties file holding the values of this object fields
     */
    public Properties toProperties() {
        Properties props = super.toProperties();
        putFile(props, TARGET_COBOL_DIR, getTargetCobolDir());
        putString(props, SAMPLE_COBOL_HTTP_CLIENT_TYPE,
                getSampleCobolHttpClientType().toString());
        props.putAll(getUmoComponentTargetParameters().toProperties());
        return props;
    }
}
