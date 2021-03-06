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

import java.util.Properties;

import org.mule.transport.legstar.model.options.TcpTransportParameters;


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
    
    /* ====================================================================== */
    /* Following are this class default values.                             = */
    /* ====================================================================== */

    /** The default host on which the HTTP server listens. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST = "mainframe";

    /** The default port number on which the HTTP server listens. */
    public static final int ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT = 4081;

    /** Default pattern for server PATH. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH =
        "/CICS/CWBA/LSWEBBIN";

    /** The default host on which the TCP server listens. */
    public static final String ADAPTER_TO_MAINFRAME_DEFAULT_TCP_HOST = "mainframe";

    /** The default port number on which the TCP server listens. */
    public static final int ADAPTER_TO_MAINFRAME_DEFAULT_TCP_PORT = 3011;

    /* ====================================================================== */
    /* Following are this class fields that are persistent.                 = */
    /* ====================================================================== */

    /** Set of parameters needed for TCP transport. */
    private TcpTransportParameters _tcpTransportParameters;

    /** Construct the model. */
    public AntBuildMule2CixsModel() {
        super(MULE2CIXS_GENERATOR_NAME, MULE2CIXS_VELOCITY_MACRO_NAME);
        _tcpTransportParameters = new TcpTransportParameters();
        getHttpTransportParameters().setHost(ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_HOST);
        getHttpTransportParameters().setPort(ADAPTER_TO_MAINFRAME_DEFAULT_HTTP_PORT);
        getHttpTransportParameters().setPath(ADAPTER_TO_MAINFRAME_DEFAULT_SERVER_PATH);
        
        getTcpTransportParameters().setHost(ADAPTER_TO_MAINFRAME_DEFAULT_TCP_HOST);
        getTcpTransportParameters().setPort(ADAPTER_TO_MAINFRAME_DEFAULT_TCP_PORT);
    }

    /**
     * Construct from a properties file.
     * 
     * @param props the property file
     */
    public AntBuildMule2CixsModel(final Properties props) {
        super(MULE2CIXS_GENERATOR_NAME, MULE2CIXS_VELOCITY_MACRO_NAME, props);
        _tcpTransportParameters = new TcpTransportParameters(props);
        
    }
    
    /**
     * @return set of parameters needed for TCP transport
     */
    public TcpTransportParameters getTcpTransportParameters() {
        return _tcpTransportParameters;
    }

    /**
     * @param tcpTransportParameters set of parameters needed for TCP transport
     */
    public void setTcpTransportParameters(
            final TcpTransportParameters tcpTransportParameters) {
        _tcpTransportParameters = tcpTransportParameters;
    }

    /**
     * @return a properties file holding the values of this object fields
     */
    public Properties toProperties() {
        Properties props = super.toProperties();
        props.putAll(getTcpTransportParameters().toProperties());
        return props;
    }
}
