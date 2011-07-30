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

import org.mule.transport.legstar.gen.Samples;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationHostMessagingType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationPayloadType;
import org.mule.transport.legstar.model.AbstractAntBuildCixsMuleModel.SampleConfigurationTransport;

/**
 * Adapter Generator ant tests.
 * <p/>
 * Tests the velocity templates used to produce ant scripts which in
 * turn generate artifacts. These templates are usually used by IDEs,
 * they are not needed for a batch usage of the generators.
 *
 */
public class AntBuildMule2CixsModelTest extends AbstractAntBuildCixsMuleModelTest {

    public AntBuildMule2CixsModelTest() {
    	super(new AntBuildMule2CixsModel(),
    			"vlc/build-mule2cixs-xml.vm");
    }

    /**
     * Adapter case for an LSFILEAE program over legstar-mule HTTP.
     * @throws Exception if generation fails
     */
    public void testLsfileaeGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        initCixsMuleComponent(muleComponent);
        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAE program over legstar-mule WMQ.
     * @throws Exception if generation fails
     */
    public void testLsfileaeWmqGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        muleComponent.setName(muleComponent.getName() + "wmq");
        initCixsMuleComponent(muleComponent);

        getAntModel().setSampleConfigurationTransport(
                SampleConfigurationTransport.WMQ);
        getAntModel().setSampleConfigurationHostMessagingType(
                SampleConfigurationHostMessagingType.MQCIH);

        getAntModel().getWmqTransportParameters().setConnectionFactory("ConnectionFactory");
        getAntModel().getWmqTransportParameters().setJndiUrl(
                "src/test/resources/host-jndi");
        getAntModel().getWmqTransportParameters().setJndiContextFactory(
                "org.mule.transport.legstar.config.HostContextFactory");
        getAntModel().getWmqTransportParameters().setZosQueueManager("CSQ1");
        getAntModel().getWmqTransportParameters().setRequestQueue(
                "CICS01.BRIDGE.REQUEST.QUEUE");
        getAntModel().getWmqTransportParameters().setReplyQueue(
                "CICS01.BRIDGE.REPLY.QUEUE");

        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAE program over legstar-mule TCP.
     * @throws Exception if generation fails
     */
    public void testLsfileaeTcpGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        muleComponent.setName(muleComponent.getName() + "tcp");
        initCixsMuleComponent(muleComponent);

        getAntModel().setSampleConfigurationTransport(
                SampleConfigurationTransport.TCP);

        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAE program over legstar-mule MOCK.
     * @throws Exception if generation fails
     */
    public void testLsfileaeMockGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileaeMuleComponent();
        muleComponent.setName(muleComponent.getName() + "mock");
        initCixsMuleComponent(muleComponent);

        getAntModel().setSampleConfigurationTransport(
                SampleConfigurationTransport.MOCK);

        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAL program over legstar-mule HTTP.
     * @throws Exception if generation fails
     */
    public void testLsfilealGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfilealMuleComponent();
        initCixsMuleComponent(muleComponent);
        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAC program over legstar-mule HTTP.
     * @throws Exception if generation fails
     */
    public void testLsfileacGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        initCixsMuleComponent(muleComponent);
        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAC program over legstar-mule HTTP with XML payload.
     * @throws Exception if generation fails
     */
    public void testLsfileacXmlGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileacMuleComponent();
        initCixsMuleComponent(muleComponent);
        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }

    /**
     * Adapter case for an LSFILEAX program over legstar-mule HTTP.
     * @throws Exception if generation fails
     */
    public void testLsfileaxGenerate() throws Exception {
        /* Build the model */
        CixsMuleComponent muleComponent = Samples.getLsfileaxMuleComponent();
        initCixsMuleComponent(muleComponent);
        processAnt("generate.xml");

        getAntModel().setSampleConfigurationPayloadType(SampleConfigurationPayloadType.XML);
        processAnt("generate-xml.xml");
    }
    
    /**
     * Generate the ant script, check it and run it.
     * @param antName the generated and script name
     * @throws Exception if something goes wrong
     */
    public void processAnt(final String antName) throws Exception {
		getAntModel().setSampleConfigurationFileName(
				getAdapterConfigurationFileName(
						getAntModel().getCixsMuleComponent().getName(),
						getAntModel().getSampleConfigurationTransport(),
						getAntModel().getSampleConfigurationPayloadType(),
						getAntModel().getSampleConfigurationHostMessagingType()));

        super.processAnt(antName);
    }


}
