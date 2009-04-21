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
package org.mule.providers.legstar.cixs;
import java.rmi.server.UID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

/**
 * Abstract class shields generated actions from Mule.
 * Deals with configuration parameters, particularly those regarding mainframe addressing. 
 */
public abstract class AbstractAdapterCallable implements Callable {

    /** An identifier for this adapter action.*/
    private String mActionAdapterName;
    
    /** The JNDI locator for the configuration file name.*/
    private static final String JNDI_CONFIG_FILE =
        "java:comp/env/legstar/configFileName";
    
    /** The default configuration file name if not recovered from JNDI. */
    private static final String DEFAULT_CONFIG_FILE = "legstar-invoker-config.xml";

    /** The LegStar configuration file name. */
    private String mLegStarConfigFileName;

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(AbstractAdapterCallable.class);

    /**
     * Standard constructor.
     * Lookup the LegStar invoker configuration file name at construction time.
     * <p/>
     * The invoker configuration file name parameter is legstar-invoker-config.
     * If not available from configuration, use the default one.
     * @param actionAdapterName the action adapter identifier
     */
    public AbstractAdapterCallable(final String actionAdapterName) {
        mActionAdapterName = actionAdapterName;
        try {
            Context initCtx = new InitialContext();
            mLegStarConfigFileName = (String) initCtx.lookup(JNDI_CONFIG_FILE);
        } catch (NamingException e) {
            mLegStarConfigFileName = DEFAULT_CONFIG_FILE;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialize AbstractInvokerAction from " + mLegStarConfigFileName);
        }
    }
 
    /** {@inheritDoc} */
    public Object onCall(final MuleEventContext eventContext) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onCall request for " + getActionAdapterName()
                    + " Message before:" + eventContext.getMessage());
        }

        MuleHostHeader hostHeader =
            MuleHostHeaderFactory.createHostHeader(eventContext.getMessage());
        Object requestContent = eventContext.getMessage();
        Object response = call(requestContent, hostHeader);

        if (LOG.isDebugEnabled()) {
            LOG.debug("onCall request for " + getActionAdapterName()
                    + " Message after:" + response);
        }
        return response;
    }

    /**
     * Implementing classes will perform the actual call to the mainframe
     * program as well as adapting java types to mainframe format.
     * @param request the request java type
     * @param hostHeader mainframe addressing parameters
     * @return the reply java type
     * @throws MuleCixsException signals problems running mainframe program
     */
    public abstract Object call(
            final Object request, final MuleHostHeader hostHeader) throws MuleCixsException;

    /**
     * Generates a unique ID for a request. This can be passed from the client
     * using the host header.
     * @param hostHeader the java object mapping the SOAP header element
     * @return  a unique request ID
     */
    public String getRequestID(final MuleHostHeader hostHeader) {
        if (hostHeader != null && hostHeader.getHostRequestID() != null) {
            return hostHeader.getHostRequestID();
        } else {
            return getActionAdapterName() + ":" + new UID().toString();
        }
    }

    /**
     * @return the LegStar configuration file name
     */
    public String getLegStarConfigFileName() {
        return mLegStarConfigFileName;
    }

    /**
     * @return a friendly name identifying this action adapter
     */
    public String getActionAdapterName() {
        return mActionAdapterName;
    }

}
