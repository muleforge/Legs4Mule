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
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.legstar.wmq;

import javax.jms.TextMessage;

import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnectorTestCase;
import org.mule.transport.jms.jndi.SimpleJndiNameResolver;

import com.ibm.disthub2.impl.jms.TextMessageImpl;

/**
 * Test LegstarWmqConnector class.
 *
 */
public class LegstarWmqConnectorTestCase extends AbstractConnectorTestCase {

    /** {@inheritDoc} */
    public Connector createConnector() throws Exception {
        SimpleJndiNameResolver jndiNameResolver = new SimpleJndiNameResolver();
        jndiNameResolver.setJndiProviderUrl("file:///JNDI-Directory");
        jndiNameResolver
                .setJndiInitialFactory("com.sun.jndi.fscontext.RefFSContextFactory");

        LegstarWmqConnector c = new LegstarWmqConnector(muleContext);
        c.setName("Test");
        c.setHostUserID("champ");
        c.setHostPassword("polion");
        c.setJndiNameResolver(jndiNameResolver);
        c.setConnectionFactoryJndiName("ConnectionFactory");
        return c;
    }

    /** {@inheritDoc} */
    public String getTestEndpointURI() {
        return "legstar-wmq://CICS01.BRIDGE.REQUEST.QUEUE"
        + "?jndiInitialFactory=com.sun.jndi.fscontext.RefFSContextFactory"
        + "&jndiProviderUrl=file:///JNDI-Directory"
        + "&connectionFactoryJndiName=ConnectionFactory";
    }

    /** {@inheritDoc} */
    public Object getValidMessage() throws Exception {
        TextMessage message = new TextMessageImpl();
        message.setText("test messsage");
        return message;
    }


}
