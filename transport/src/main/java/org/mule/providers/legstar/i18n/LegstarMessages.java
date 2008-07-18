package org.mule.providers.legstar.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

/**
 * Indirect references to messages from a bundle.
 */
public class LegstarMessages extends MessageFactory {

    /** Bundle is found under org.mule.i18n legstar-messages.properties. */
    private static final String BUNDLE_PATH = getBundlePath("legstar");

    /**
     * @return invalid legstar header in incoming message.
     */
    public static Message invalidLegstarHeader() {
        return createMessage(BUNDLE_PATH, 1);
    }

    /**
     * @return something went wrong while receiving the host data.
     */
    public static Message errorReceivingHostData() {
        return createMessage(BUNDLE_PATH, 2);
    }

    /**
     * @return host data size must at least be large enough to hold
     * a header. It cannot be zero.
     */
    public static Message invalidHostDataSize() {
        return createMessage(BUNDLE_PATH, 3);
    }

    /**
     * @return was not able to create a host message.
     */
    public static Message errorFormattingHostData() {
        return createMessage(BUNDLE_PATH, 4);
    }

    /**
     * @param propertyName name of missing property
     * @return could not find an endpoint property.
     */
    public static Message missingEndpointProperty(String propertyName) {
        return createMessage(BUNDLE_PATH, 5, propertyName);
    }

    /**
     * @return was not able to initialize a transformer. Probably a setup 
     * issue like missing endpoint properties.
     */
    public static Message transformerInitializationFailure() {
        return createMessage(BUNDLE_PATH, 6);
    }
 
    /**
     * @param coxbClassName name of the binding class we are trying to
     *  instantiate
     * @return instantiation of binding class failed.
     */
    public static Message coxbInstantiationError(String coxbClassName) {
        return createMessage(BUNDLE_PATH, 7, coxbClassName);
    }

    /**
     * @param programPropFileName name of the program properties file
     * @return the file content is invalid.
     */
    public static Message invalidProgramPropertyFile(String programPropFileName) {
        return createMessage(BUNDLE_PATH, 8, programPropFileName);
    }

    /**
     * @return failed to unmarshal data from the host. The binding is 
     * probably incorrect (does not match the cobol layout).
     */
    public static Message unmarshalFailure() {
        return createMessage(BUNDLE_PATH, 9);
    }
 
    /**
     * @return the binding type does not match the jaxb data type.
     */
    public static Message bindingTypeMismatch() {
        return createMessage(BUNDLE_PATH, 10);
    }
 
    /**
     * @param jaxbClassName the jaxb class name
     * @return failed to load the jaxb class (classpath issue).
     */
    public static Message jaxbClassLoadFailure(String jaxbClassName) {
        return createMessage(BUNDLE_PATH, 11, jaxbClassName);
    }

    /**
     * @param coxbClassName the binding class name
     * @return failed to calculate the host byte size for this binding.
     */
    public static Message hostByteSizeCalcFailure(String coxbClassName) {
        return createMessage(BUNDLE_PATH, 12, coxbClassName);
    }

}
