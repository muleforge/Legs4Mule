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
package org.mule.providers.legstar.model;

/**
 * This is a model for Mule to Cixs component generation. The generated 
 * component runs under Mule and wraps a CICS transaction.
 *
 */
public class AntBuildMule2CixsModel extends AbstractAntBuildCixsMuleModel {

    /** This generator name. */
    public static final String MULE2CIXS_GENERATOR_NAME =
        "Mule adapter component generator";

    /** This velocity template that creates an ant build which in turn
     * generates the target component. */
    public static final String MULE2CIXS_VELOCITY_MACRO_NAME =
        "vlc/build-mule2cixs-xml.vm";
    
    /** The default host on which the HTTP server listens. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST = "mainframe";

    /** The default port number on which the HTTP server listens. */
    public static final int ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT = 4081;

    /** Default pattern for server PATH. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH =
        "/CICS/CWBA/LSWEBBIN";

    /** Construct the model. */
    public AntBuildMule2CixsModel() {
        super(MULE2CIXS_GENERATOR_NAME, MULE2CIXS_VELOCITY_MACRO_NAME);
        getHttpTransportParameters().setHost(ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST);
        getHttpTransportParameters().setPort(ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT);
        getHttpTransportParameters().setPath(ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH);
    }

}
