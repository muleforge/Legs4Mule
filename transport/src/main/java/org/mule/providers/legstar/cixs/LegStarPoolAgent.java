/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.cixs;

import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.manager.UMOAgent;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import com.legstar.host.server.EngineHandler;
import com.legstar.host.server.EngineStartupException;

/**
 * This is an encapsulation of the standard legStar pooling engine into
 * a Mule Agent. Such an Agent can be attached to a Mule Configuration
 * and will result in all LegStar connections to be pooled. 
 *
 */
public class LegStarPoolAgent implements UMOAgent
{

    /** Describes the Agent. */
    private static final String DESCRIPTION =
        "Agent used to drive the legStar pooling engine";

    /** A unique name for this agent. */
    private static final String AGENT_NAME = "LegStarPoolAgent";

    /** This agent instance name. */
    private String mName = AGENT_NAME;

    /** Engine handler implementation. */   
    private EngineHandler mServerHandler = null;

    /** TODO Should not be hardcoded. See how to get it from agent properties. */
    private static final String CONFIG_FILE_NAME = "legstar-engine-config.xml";

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(LegStarPoolAgent.class);

    /** {@inheritDoc} */
    public final String getDescription()
    {
        return DESCRIPTION;
    }

    /** {@inheritDoc} */
    public final String getName()
    {
        return mName;
    }

    /** {@inheritDoc} */
    public final void registered()
    {
        LOG.info("registered");
    }

    /** {@inheritDoc} */
    public final void setName(final String name)
    {
        mName = name;
    }

    /** {@inheritDoc} */
    public final void unregistered()
    {
        LOG.info("unregistered");
    }

    /** {@inheritDoc} */
    public final void start() throws UMOException
    {
        LOG.info("Pool engine started");
    }

    /** {@inheritDoc} */
    public final void stop() throws UMOException
    {
        LOG.info("Pool engine stopped");
        if (mServerHandler != null)
        {
            mServerHandler.stop();
        }
    }

    /** {@inheritDoc} */
    public final void dispose()
    {
        mServerHandler = null;
        LOG.info("Pool engine destroyed");
    }

    /** {@inheritDoc} */
    public final void initialise() throws InitialisationException
    {
        LOG.info("Initializing with " + CONFIG_FILE_NAME
                + " configuration file.");

        try
        {
            mServerHandler = new EngineHandler(loadConfigFile(CONFIG_FILE_NAME));
            mServerHandler.init();
        }
        catch (ConfigurationException e)
        {
            LOG.error("Failed to initialize.", e);
            throw new InitialisationException(e, this);
        }
        catch (EngineStartupException e)
        {
            LOG.error("Failed to start engine.", e);
            throw new InitialisationException(e, this);
        }
    }

    /**
     * Use the Apache configuration API to retrieve the configuration file.
     * This gives a lot of flexibility to locate the file.
     * 
     * @param configFileName name of the configuration file
     * @return the configuration retrieved
     * @throws ConfigurationException if configuration cannot be retrieved
     */
    private HierarchicalConfiguration loadConfigFile(final String configFileName) throws ConfigurationException
    {
        LOG.debug("Attempting to load " + configFileName);
        DefaultConfigurationBuilder dcb = new DefaultConfigurationBuilder();
        dcb.setFileName(configFileName);
        CombinedConfiguration config = (CombinedConfiguration)
        dcb.getConfiguration(true).getConfiguration(
                DefaultConfigurationBuilder.ADDITIONAL_NAME);
        config.setExpressionEngine(new XPathExpressionEngine());
        LOG.debug("Load success for " + configFileName);
        return config; 

    }

}
