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
    
    /** The URI that the host exposes to consumers using the legstar scheme in
     * a bridge configuration (using the legstar-mule http transport). */
    private String mHostURI;

    /** Construct the model. */
    public AntBuildMule2CixsModel() {
        super(MULE2CIXS_GENERATOR_NAME, MULE2CIXS_VELOCITY_MACRO_NAME);
    }

    /**
     * The URI that the host exposes to consumers using the legstar scheme in
     * a bridge configuration (using the legstar-mule http transport).
     * @return the URI that the host exposes to consumers
     */
    public final String getHostURI() {
       return mHostURI;
    }

    /**
     * The URI that the host exposes to consumers using the legstar scheme in
     * a bridge configuration (using the legstar-mule http transport).
     * @param hostURI the URI that the host exposes to consumers to set
     */
    public final void setHostURI(final String hostURI) {
        mHostURI = hostURI;
    }
}
