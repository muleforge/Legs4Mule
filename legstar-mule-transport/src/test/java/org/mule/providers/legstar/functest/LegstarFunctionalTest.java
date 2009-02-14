/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.functest;

import org.mule.config.builders.QuickConfigurationBuilder;
import org.mule.extras.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.functional.FunctionalTestNotification;
import org.mule.tck.functional.FunctionalTestNotificationListener;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.manager.UMOServerNotification;

import junit.framework.TestCase;

/**
 * This functional test does not actually test any of the transformers since
 * they are meant to be generated. For this reason this does mostly check that
 * the legstar properties are complete.
 *
 */
public class LegstarFunctionalTest extends TestCase implements EventCallback, FunctionalTestNotificationListener {

    /** configuration helper. */
    private QuickConfigurationBuilder mBuilder;

    /**
     * {@inheritDoc}
     * This is where we create our configuration.
     */
    protected void setUp() throws Exception {
        mBuilder = new QuickConfigurationBuilder();

        // we create our MuleManager
        mBuilder.createStartedManager(true, null);

        // we create a "SINGLE" endpoint and set the address to legstar:http://localhost:8083
        UMOEndpoint legstarSingle = mBuilder.createEndpoint("legstar:http://localhost:8083", "SingleEndpoint", true);

        // we create a FunctionalTestComponent and call it myComponent
        FunctionalTestComponent myComponent = new FunctionalTestComponent();

        // we set out Event Callback on our test class
        myComponent.setEventCallback(this);

        // we register our component instance.
        mBuilder.registerComponentInstance(myComponent, "SINGLE", legstarSingle.getEndpointURI());

        // we register our listener which we called "SINGLE"
        mBuilder.getManager().registerListener(this, "SINGLE");
    }

    /** {@inheritDoc} */
    public void eventReceived(final UMOEventContext context, final Object component)
    throws Exception {
        FunctionalTestComponent fc = (FunctionalTestComponent) component;
        fc.setReturnMessage("Customized Return Message");
    }

    /** {@inheritDoc} */
    public void onNotification(final UMOServerNotification notification) {
        assertTrue(notification.getAction() == FunctionalTestNotification.EVENT_RECEIVED);
    }

    /**
     * Use a MuleClient to send a message on the legstar endpoint.
     * @throws Exception if fails
     */
    public void testSingleComponent() throws Exception {
        MuleClient client = new MuleClient();

        // we send a message on the endpoint we created, i.e. legstar:http://localhost:8083
        UMOMessage result = client.send("legstar:http://localhost:8083", "hello", null);
        assertNotNull(result);
        assertEquals("Customized Return Message", result.getPayloadAsString());
    }

    /** {@inheritDoc} */
    protected void tearDown() throws Exception {
        mBuilder.disposeCurrent();
    }

}
