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
package org.mule.providers.legstar.transformers;

import org.mule.DefaultMuleMessage;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transformer.AbstractTransformerTestCase;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.Transformer;

import com.legstar.coxb.host.HostData;

/**
 * Test AbstractXmlToHostTransformer class.
 *
 */
public class LsfileacXmlToHostTransformerTest extends AbstractTransformerTestCase {

    
    /** {@inheritDoc} */
    public AbstractMessageAwareTransformer getTransformer() throws Exception {
        return new LsfileacXmlToHostTransformer();
    }

    /** {@inheritDoc} */
    public Object getResultData() {
        return HostData.toByteArray(
                LsfileacHostToJavaTransformerTest.LSFILEAC_MESSAGE_HOST_DATA);
    }

    /** {@inheritDoc} */
    public Transformer getRoundTripTransformer() throws Exception {
        return null;
    }

    /** {@inheritDoc} */
    public Object getTestData() {
        return LsfileacHostToXmlTransformerTest.LSFILEAC_XML_SAMPLE;
    }

    /**
     *  {@inheritDoc}
     * We override this method in order to set the message property that
     * commands legstar messaging and program properties.
     *      */
    public void testTransform() throws Exception  {
        MuleMessage muleMessage = new DefaultMuleMessage(getTestData());
        muleMessage.setBooleanProperty(
                AbstractHostJavaMuleTransformer.IS_LEGSTAR_MESSAGING, true);
        muleMessage.setStringProperty(
                AbstractJavaToHostMuleTransformer.PROGRAM_PROP_FILE_NAME,
                "lsfileac.properties");
        
        Object result = this.getTransformer().transform(muleMessage, "");
        assertNotNull(result);

        Object expectedResult = this.getResultData();
        assertNotNull(expectedResult);

        assertTrue(this.compareResults(expectedResult, result));
    }

}
