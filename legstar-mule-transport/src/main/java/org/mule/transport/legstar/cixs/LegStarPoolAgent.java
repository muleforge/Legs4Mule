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
package org.mule.transport.legstar.cixs;

import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.agent.Agent;
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
public class LegStarPoolAgent implements Agent {

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
    private final Log _log = LogFactory.getLog(getClass());

    /** {@inheritDoc} */
    public final String getDescription() {
        return DESCRIPTION;
    }

    /** {@inheritDoc} */
    public final String getName() {
        return mName;
    }

    /** {@inheritDoc} */
    public final void registered() {
        _log.info("registered");
    }

    /** {@inheritDoc} */
    public final void setName(final String name) {
        mName = name;
    }

    /** {@inheritDoc} */
    public final void unregistered() {
        _log.info("unregistered");
    }

    /** {@inheritDoc} */
    public final void start() throws MuleException {
        _log.info("Pool engine started");
    }

    /** {@inheritDoc} */
    public final void stop() throws MuleException {
        _log.info("Pool engine stopped");
        if (mServerHandler != null) {
            mServerHandler.stop();
        }
    }

    /** {@inheritDoc} */
    public final void dispose() {
        mServerHandler = null;
        _log.info("Pool engine destroyed");
    }

    /** {@inheritDoc} */
    public final void initialise() throws InitialisationException {
        _log.info("Initializing with " + CONFIG_FILE_NAME
                + " configuration file.");

        try {
            mServerHandler = new EngineHandler(loadConfigFile(CONFIG_FILE_NAME));
            mServerHandler.init();
        } catch (ConfigurationException e) {
            _log.error("Failed to initialize.", e);
            throw new InitialisationException(e, this);
        } catch (EngineStartupException e) {
            _log.error("Failed to start engine.", e);
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
    private HierarchicalConfiguration loadConfigFile(final String configFileName) throws ConfigurationException {
        _log.debug("Attempting to load " + configFileName);
        DefaultConfigurationBuilder dcb = new DefaultConfigurationBuilder();
        dcb.setFileName(configFileName);
        CombinedConfiguration config = (CombinedConfiguration)
        dcb.getConfiguration(true).getConfiguration(
                DefaultConfigurationBuilder.ADDITIONAL_NAME);
        config.setExpressionEngine(new XPathExpressionEngine());
        _log.debug("Load success for " + configFileName);
        return config; 

    }

    /** {@inheritDoc} */
    public List < Class < ? extends Agent > > getDependentAgents() {
        return new ArrayList < Class < ? extends Agent > >();
    }

}