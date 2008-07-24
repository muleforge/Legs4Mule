/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

/**
 * Indirect references to messages from a bundle.
 */
public class LegstarMessages extends MessageFactory
{

    /** Bundle is found under org.mule.i18n legstar-messages.properties. */
    private static final String BUNDLE_PATH = getBundlePath("legstar");
    
    /** Invalid legstar header in incoming message message number.*/
    private static final int MSG_NUM_1 = 1;

    /**
     * @return invalid legstar header in incoming message.
     */
    public static Message invalidLegstarHeader()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_1);
    }

    /** Something went wrong while receiving the host data message number.*/
    private static final int MSG_NUM_2 = 2;
    
   /**
     * @return something went wrong while receiving the host data.
     */
    public static Message errorReceivingHostData()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_2);
    }

    /** Host data size must at least be large enough to hold
     * a header message number.*/
    private static final int MSG_NUM_3 = 3;
    
   /**
     * @return host data size must at least be large enough to hold
     * a header. It cannot be zero.
     */
    public static Message invalidHostDataSize()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_3);
    }

    /** Was not able to create a host message number.*/
    private static final int MSG_NUM_4 = 4;
    
    /**
     * @return was not able to create a host message.
     */
    public static Message errorFormattingHostData()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_4);
    }

    /** Could not find an endpoint property message number.*/
    private static final int MSG_NUM_5 = 5;
    
    /**
     * @param propertyName name of missing property
     * @return could not find an endpoint property.
     */
    public static Message missingEndpointProperty(final String propertyName)
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_5, propertyName);
    }

    /** Was not able to initialize a transformer message number.*/
    private static final int MSG_NUM_6 = 6;
    
    /**
     * @return was not able to initialize a transformer. Probably a setup 
     * issue like missing endpoint properties.
     */
    public static Message transformerInitializationFailure()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_6);
    }

    /** Instantiation of binding class failed message number.*/
    private static final int MSG_NUM_7 = 7;
    
    /**
     * @param coxbClassName name of the binding class we are trying to
     *  instantiate
     * @return instantiation of binding class failed.
     */
    public static Message coxbInstantiationError(final String coxbClassName)
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_7, coxbClassName);
    }

    /** The file content is invalid message number.*/
    private static final int MSG_NUM_8 = 8;
    
    /**
     * @param programPropFileName name of the program properties file
     * @return the file content is invalid.
     */
    public static Message invalidProgramPropertyFile(final String programPropFileName)
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_8, programPropFileName);
    }

    /** Failed to unmarshal data from the host message number.*/
    private static final int MSG_NUM_9 = 9;
    
    /**
     * @return failed to unmarshal data from the host. The binding is 
     * probably incorrect (does not match the cobol layout).
     */
    public static Message unmarshalFailure()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_9);
    }

    /** The binding type does not match the jaxb data type message number.*/
    private static final int MSG_NUM_10 = 10;
    
    /**
     * @return the binding type does not match the jaxb data type.
     */
    public static Message bindingTypeMismatch()
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_10);
    }

    /** Failed to load the jaxb class (classpath issue) message number.*/
    private static final int MSG_NUM_11 = 11;
    
    /**
     * @param jaxbClassName the jaxb class name
     * @return failed to load the jaxb class (classpath issue).
     */
    public static Message jaxbClassLoadFailure(final String jaxbClassName)
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_11, jaxbClassName);
    }

    /** Failed to calculate the host byte size for this binding message number.*/
    private static final int MSG_NUM_12 = 12;
    
    /**
     * @param coxbClassName the binding class name
     * @return failed to calculate the host byte size for this binding.
     */
    public static Message hostByteSizeCalcFailure(final String coxbClassName)
    {
        return createMessage(BUNDLE_PATH, MSG_NUM_12, coxbClassName);
    }

}
