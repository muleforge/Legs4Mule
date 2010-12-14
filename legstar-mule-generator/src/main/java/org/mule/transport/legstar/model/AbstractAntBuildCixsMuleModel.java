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

import com.legstar.cixs.gen.ant.model.AbstractAntBuildCixsModel;
import com.legstar.cixs.gen.model.options.HttpTransportParameters;
import com.legstar.cixs.gen.model.options.WmqTransportParameters;

/**
 * Holds the set of parameters needed to generate an ant script file.
 * This can be used by Eclipse plugins to generate ant files.
 * This model is common to all Mule generation types.
 */
public abstract class AbstractAntBuildCixsMuleModel extends AbstractAntBuildCixsModel
{
    
    /* ====================================================================== */
    /* Following are default values.                                        = */
    /* ====================================================================== */
    public static final SampleConfigurationTransport DEFAULT_SAMPLE_CONFIGURATION_TRANSPORT = SampleConfigurationTransport.HTTP;
    
    public static final SampleConfigurationHostMessagingType DEFAULT_SAMPLE_CONFIGURATION_HOST_MESSAGING_TYPE = SampleConfigurationHostMessagingType.LEGSTAR;

    /* ====================================================================== */
    /* Following are key identifiers for this model persistence.            = */
    /* ====================================================================== */

    /** Mule product location on file system. */
    public static final String MULE_HOME = "muleHome";

    /** Where the LegStar Mule generator product is installed. */
    public static final String MULEGEN_PRODUCT_LOCATION = "mulegenProductLocation";

    /** Target directory where Mule configuration files will be created. */
    public static final String TARGET_MULE_CONFIG_DIR = "targetMuleConfigDir";

    /** Target location for mule jar files. */
    public static final String TARGET_JAR_DIR = "targetJarDir";

    /** Transports supported by generated service configuration samples. */
    public static final String SAMPLE_CONFIGURATION_TRANSPORT = "sampleConfigurationTransport";

    /** Host messaging used by generated service configuration samples. */
    public static final String SAMPLE_CONFIGURATION_HOST_MESSAGING_TYPE = "sampleConfigurationHostMessagingType";

    /* ====================================================================== */
    /* Following are this class fields that are persistent.                 = */
    /* ====================================================================== */

    /** Mule product location on file system.*/
    private String _muleHome;
    
    /** Where the LegStar Mule generator product is installed on the file system.
     * The Mule generator might reside somewhere else than the LegStar core product. */
    private String _mulegenProductLocation;
    
    /** The target directory where Mule configuration files will be created. */
    private File _targetMuleConfigDir;
    
    /** The target location for mule jar files. */
    private File _targetJarDir;
    
    /** The transports supported by generated service configuration samples. */
    private SampleConfigurationTransport _sampleConfigurationTransport = DEFAULT_SAMPLE_CONFIGURATION_TRANSPORT;

    /** Set of parameters needed for HTTP transport. */
    private HttpTransportParameters _httpTransportParameters = new HttpTransportParameters();

    /** Set of parameters needed for Websphere MQ transport. */
    private WmqTransportParameters _wmqTransportParameters = new WmqTransportParameters();

    /** The host messaging used by generated service configuration samples. */
    private SampleConfigurationHostMessagingType _sampleConfigurationHostMessagingType =
        DEFAULT_SAMPLE_CONFIGURATION_HOST_MESSAGING_TYPE;

    /**
     * Construct the model with a generator name and velocity template.
     * @param generatorName to designate the generator
     * @param vlcTemplate a velocity template that accepts this model
     */
    public AbstractAntBuildCixsMuleModel(
            final String generatorName, final String vlcTemplate) {
        super(generatorName, vlcTemplate);
        _httpTransportParameters = new HttpTransportParameters();
        _wmqTransportParameters = new WmqTransportParameters();
        setCixsMuleComponent(new CixsMuleComponent());
    }
    
    /**
     * Construct from a properties file.
     * 
     * @param generatorName to designate the generator
     * @param vlcTemplate a velocity template that accepts this model
     */
    public AbstractAntBuildCixsMuleModel(final String generatorName,
            final String vlcTemplate, final Properties props) {
        super(generatorName, vlcTemplate);
        setMuleHome(getString(props, MULE_HOME, null));
        setMulegenProductLocation(getString(props, MULEGEN_PRODUCT_LOCATION, null));
        setSampleConfigurationTransport(getString(props, SAMPLE_CONFIGURATION_TRANSPORT,
                DEFAULT_SAMPLE_CONFIGURATION_TRANSPORT.toString()));
        _httpTransportParameters = new HttpTransportParameters(props);
        _wmqTransportParameters = new WmqTransportParameters(props);
        setSampleConfigurationHostMessagingType(getString(props, SAMPLE_CONFIGURATION_HOST_MESSAGING_TYPE,
                DEFAULT_SAMPLE_CONFIGURATION_HOST_MESSAGING_TYPE.toString()));
        setCixsMuleComponent(new CixsMuleComponent(props));
    }
    
    /**
     * @return the Mule product location on file system
     */
    public final String getMuleHome() {
        return _muleHome;
    }

    /**
     * @param muleHome the Mule product location on file system to set
     */
    public final void setMuleHome(final String muleHome) {
        _muleHome = muleHome;
    }

    /**
     * @return the target directory where Mule configuration files will be
     *  created
     */
    public final File getTargetMuleConfigDir() {
        return _targetMuleConfigDir;
    }

    /**
     * @param targetMuleConfigDir the target directory where Mule configuration
     *  files will be created to set
     */
    public final void setTargetMuleConfigDir(
            final File targetMuleConfigDir) {
        _targetMuleConfigDir = targetMuleConfigDir;
    }

    /**
     * @return the target location for mule jar files
     */
    public final File getTargetJarDir() {
        return _targetJarDir;
    }

    /**
     * @param targetJarDir the target location for mule jar files to set
     */
    public final void setTargetJarDir(final File targetJarDir) {
        _targetJarDir = targetJarDir;
    }

    /**
     * @return the the Mule-Legstar component being generated
     */
    public final CixsMuleComponent getCixsMuleComponent() {
        return (CixsMuleComponent) getCixsService();
    }

    /**
     * @param cixsMuleComponent the the Mule-Legstar component being generated
     *  to set
     */
    public final void setCixsMuleComponent(
            final CixsMuleComponent cixsMuleComponent) {
        setCixsService(cixsMuleComponent);
    }

    /**
     * @return Where the LegStar Mule generator product is installed on the file system.
     * The Mule generator might reside somewhere else than the LegStar core product.
     * This is needed for ant scripts classpaths.
     */
    public final String getMulegenProductLocation() {
        return _mulegenProductLocation;
    }

    /**
     * @param mulegenProductLocation Where the LegStar Mule generator product
     *  is installed
     */
    public final void setMulegenProductLocation(
            final String mulegenProductLocation) {
        _mulegenProductLocation = mulegenProductLocation;
    }

    /**
     * Sample configurations are generated for one of these transports.
     * For proxies this is the transport from mainframe to proxy.
     * For adapters this is the transport from adapter to mainframe.
     */
    public enum SampleConfigurationTransport {
        /** Websphere MQ. */
        WMQ,
        /** HTTP. */
        HTTP,
        /** TCP. */
        TCP,
        /** MOCK. */
        MOCK
    }
    
    /**
     * Sample configurations are generated either for java object payloads
     * or XML. Clients using these sample configurations are then expected to send
     * this type of payload.
     */
    public enum SampleConfigurationPayloadType {
        /** Java object. */
        JAVA,
        /** XML. */
        XML
    }
    
    /**
     * Sample configurations are generated for a certain type of host
     * messaging. Depending on messaging, host data will be wrapped into a
     * different type of message.
     */
    public enum SampleConfigurationHostMessagingType {
        /** LegStar messaging (LegStar is installed on the mainframe). */
        LEGSTAR,
        /** CICS MQ Bridge. */
        MQCIH
    }
    
    /**
     * @return the transport used by generated configuration samples.
     */
    public SampleConfigurationTransport getSampleConfigurationTransport() {
        return _sampleConfigurationTransport;
    }

    /**
     * @param sampleConfigurationTransport the transport used by generated configuration samples.
     */
    public void setSampleConfigurationTransport(
            final SampleConfigurationTransport sampleConfigurationTransport) {
        _sampleConfigurationTransport = sampleConfigurationTransport;
    }

    /**
     * @param sampleConfigurationTransport the transport used by generated configuration samples.
     */
    public void setSampleConfigurationTransport(
            final String sampleConfigurationTransport) {
        _sampleConfigurationTransport = SampleConfigurationTransport.valueOf(sampleConfigurationTransport);
    }

    /**
     * @return set of parameters needed for HTTP transport
     */
    public HttpTransportParameters getHttpTransportParameters() {
        return _httpTransportParameters;
    }

    /**
     * @param httpTransportParameters set of parameters needed for HTTP transport
     */
    public void setHttpTransportParameters(
            final HttpTransportParameters httpTransportParameters) {
        _httpTransportParameters = httpTransportParameters;
    }

    /**
     * @return set of parameters needed for WebSphere MQ transport
     */
    public WmqTransportParameters getWmqTransportParameters() {
        return _wmqTransportParameters;
    }

    /**
     * @param wmqTransportParameters set of parameters needed for WebSphere MQ transport
     */
    public void setWmqTransportParameters(
            final WmqTransportParameters wmqTransportParameters) {
        _wmqTransportParameters = wmqTransportParameters;
    }

    /**
     * @return the host messaging used by generated service configuration samples
     */
    public SampleConfigurationHostMessagingType getSampleConfigurationHostMessagingType() {
        return _sampleConfigurationHostMessagingType;
    }

    /**
     * @param sampleConfigurationHostMessagingType the host messaging used by generated
     *  service configuration samples
     */
    public void setSampleConfigurationHostMessagingType(
            final SampleConfigurationHostMessagingType sampleConfigurationHostMessagingType) {
        _sampleConfigurationHostMessagingType = sampleConfigurationHostMessagingType;
    }

    /**
     * @param sampleConfigurationHostMessagingType
     *            the host messaging used by generated service configuration
     *            samples
     */
    public void setSampleConfigurationHostMessagingType(
            final String sampleConfigurationHostMessagingType) {
        _sampleConfigurationHostMessagingType = SampleConfigurationHostMessagingType
                .valueOf(sampleConfigurationHostMessagingType);
    }

    /**
     * @return a properties file holding the values of this object fields
     */
    public Properties toProperties() {
        Properties props = super.toProperties();
        putString(props, MULE_HOME, getMuleHome());
        putString(props, MULEGEN_PRODUCT_LOCATION, getMulegenProductLocation());
        putString(props, SAMPLE_CONFIGURATION_TRANSPORT,
                getSampleConfigurationTransport().toString());
        props.putAll(getHttpTransportParameters().toProperties());
        props.putAll(getWmqTransportParameters().toProperties());
        putString(props, SAMPLE_CONFIGURATION_HOST_MESSAGING_TYPE,
                getSampleConfigurationHostMessagingType().toString());
        return props;
    }
}
