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

    private QuickConfigurationBuilder mBuilder;

    // this is where we create our configuration

    protected void setUp() throws Exception
    {
        mBuilder = new QuickConfigurationBuilder();

        // we create our MuleManager
        mBuilder.createStartedManager(true, null);

        // we create a "SINGLE" endpoint and set the address to legstar:http://localhost:8083
        UMOEndpoint legstarSingle = mBuilder.createEndpoint("legstar:http://localhost:8083","SingleEndpoint", true);
        
        // we create a FunctionalTestComponent and call it myComponent
        FunctionalTestComponent myComponent = new FunctionalTestComponent();

        // we set out Event Callback on our test class
        myComponent.setEventCallback(this);

        // we register our component instance.
        mBuilder.registerComponentInstance(myComponent,"SINGLE",legstarSingle.getEndpointURI());

        // we register our listener which we called "SINGLE"
        mBuilder.getManager().registerListener(this,"SINGLE");
    }

    @Override
    public void eventReceived(UMOEventContext context, Object component)
            throws Exception {
        FunctionalTestComponent fc = (FunctionalTestComponent) component;
        fc.setReturnMessage("Customized Return Message");
    }

    @Override
    public void onNotification(UMOServerNotification notification)
    {
        assertTrue(notification.getAction() == FunctionalTestNotification.EVENT_RECEIVED);
    }

    public void testSingleComponent() throws Exception {
        MuleClient client = new MuleClient();

        // we send a message on the endpoint we created, i.e. legstar:http://localhost:8083
        UMOMessage result = client.send("legstar:http://localhost:8083", "hello", null);
        assertNotNull(result);
        assertEquals("Customized Return Message", result.getPayloadAsString());
    }
    
    protected void tearDown() throws Exception
    {
        mBuilder.disposeCurrent();
    }

}
