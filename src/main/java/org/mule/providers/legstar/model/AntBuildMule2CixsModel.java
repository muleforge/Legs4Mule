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

    /** The URI that the host exposes to consumers. */
    private String mHostURI;

    /** This velocity template that creates an ant build which in turn
     * generates the target component. */
    public static final String MULE2CIXS_VELOCITY_MACRO_NAME =
        "vlc/build-mule2cixs-xml.vm";
    
    /** The host URI is not mandatory at generation time so we
     * provide a placeholder. */
    private static final String DEFAULT_HOST_URI = "http://hosturi";

    public AntBuildMule2CixsModel() {
        super(MULE2CIXS_GENERATOR_NAME, MULE2CIXS_VELOCITY_MACRO_NAME);
    }

    /**
     * @return the URI that the host exposes to consumers
     */
    public final String getHostURI() {
        if (mHostURI == null || mHostURI.length() == 0) {
            return DEFAULT_HOST_URI;
        }
       return mHostURI;
    }

    /**
     * @param hostURI the URI that the host exposes to consumers to set
     */
    public final void setHostURI(final String hostURI) {
        mHostURI = hostURI;
    }

}
