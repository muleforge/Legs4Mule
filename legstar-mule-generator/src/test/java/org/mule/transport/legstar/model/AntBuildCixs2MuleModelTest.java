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

import org.mule.transport.legstar.gen.Samples;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;

/**
 * Proxy Generator ant tests.
 * <p/>
 * Tests the velocity templates used to produce ant scripts which in
 * turn generate artifacts. These templates are usually used by IDEs,
 * they are not needed for a batch usage of the generators.
 *
 */
public class AntBuildCixs2MuleModelTest extends AbstractAntBuildCixsMuleModelTest {

    public AntBuildCixs2MuleModelTest() {
    	super(new AntBuildCixs2MuleModel(),
    			"vlc/build-cixs2mule-xml.vm");
    }

    /**
     * Proxy case for an POJO Target over HTTP.
     * @throws Exception if generation fails
     */
    public void testJvmqueryHttpGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        muleComponent.setName(muleComponent.getName());
        initCixsMuleComponent(muleComponent);

        getAntModel().setSampleConfigurationTransport(SampleConfigurationTransport.HTTP);
        getAntModel().getHttpTransportParameters().setHost("localhost");
        getAntModel().getHttpTransportParameters().setPort(8083);
        getAntModel().getHttpTransportParameters().setPath("/legstar/services/" + muleComponent.getName());
       
        processAnt("generate.xml");
    }

    /**
     * Proxy case for an POJO Target over WMQ.
     * @throws Exception if generation fails
     */
    public void testJvmqueryWmqGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getJvmQueryMuleComponent();
        muleComponent.setName(muleComponent.getName() + "wmq");
        initCixsMuleComponent(muleComponent);

        getAntModel().setSampleConfigurationTransport(SampleConfigurationTransport.WMQ);
        getAntModel().getWmqTransportParameters().setConnectionFactory("ConnectionFactory");
        getAntModel().getWmqTransportParameters().setJndiUrl(
                "src/test/resources/host-jndi");
        getAntModel().getWmqTransportParameters().setJndiContextFactory(
                "org.mule.transport.legstar.config.HostContextFactory");
        getAntModel().getWmqTransportParameters().setZosQueueManager("CSQ1");
        getAntModel().getWmqTransportParameters().setRequestQueue(
                "JVMQUERY.POJO.REQUEST.QUEUE");
        getAntModel().getWmqTransportParameters().setReplyQueue(
                "JVMQUERY.POJO.REPLY.QUEUE");

        processAnt("generate.xml");
    }

    /**
     * Common initialization. Segregate output so that various tests
     * do not overwrite each other.
     * @param muleComponent the service
     */
    @Override
    public void initCixsMuleComponent(final CixsMuleComponent muleComponent) {
    	super.initCixsMuleComponent(muleComponent);
        getAntModel().setTargetCobolDir(new File(getTargetDir(), "cobol"));
        
        getAntModel().getUmoComponentTargetParameters().setImplementationName(
                "com.legstar.xsdc.test.cases.jvmquery.JVMQuery");
        
    }

    /**
     * @return the ant generation model
     */
    @Override
    public AntBuildCixs2MuleModel getAntModel() {
		return (AntBuildCixs2MuleModel) super.getAntModel();
	}

    /**
     * Generate the ant script, check it and run it.
     * @param antName the generated and script name
     * @throws Exception if something goes wrong
     */
    public void processAnt(final String antName) throws Exception {
        getAntModel().setSampleConfigurationFileName(
        		getProxyConfigurationFileName(
        				getAntModel().getCixsMuleComponent().getName(),
        				getAntModel().getSampleConfigurationTransport()));

        super.processAnt(antName);
    }

}
