package org.mule.transport.legstar.http.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.transformer.TransformerException;

/**
 * This is an ugly hack to overcome the loss of the sub-scheme in Mule 3.0
 *
 */
public class ObjectToHttpClientMethodRequest extends org.mule.transport.http.transformers.ObjectToHttpClientMethodRequest {

	@Override
	public Object transformMessage(MuleMessage msg, String outputEncoding)
			throws TransformerException {
        
		// Substitute a regular HTTP URI where the legstar scheme is used
        String endpointString = msg.getOutboundProperty(MuleProperties.MULE_ENDPOINT_PROPERTY, null);
		if (endpointString != null) {
			endpointString = endpointString.replace("legstar:", "http:");
			msg.setOutboundProperty(MuleProperties.MULE_ENDPOINT_PROPERTY, endpointString);
		}
		return super.transformMessage(msg, outputEncoding);
	}

	
}
