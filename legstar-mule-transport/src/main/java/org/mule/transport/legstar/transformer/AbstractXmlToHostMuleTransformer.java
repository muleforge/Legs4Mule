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
package org.mule.transport.legstar.transformer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.legstar.coxb.transform.AbstractXmlTransformers;
import com.legstar.coxb.transform.HostTransformException;

/**
 * This ESB transformer converts XML into host data using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractXmlToHostMuleTransformer extends AbstractHostXmlMuleTransformer {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(AbstractXmlToHostMuleTransformer.class);

    /**
     * Constructor for single part transformers.
     * <p/>
     * Xml to Host transformers expect String or byte[] input and produces a 
     * byte array corresponding to mainframe raw data.
     * @param xmlBindingTransformers a set of transformers for the part type.
     */
    public AbstractXmlToHostMuleTransformer(
            final AbstractXmlTransformers xmlBindingTransformers) {
        super(xmlBindingTransformers);
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
        setReturnClass(byte[].class);
    }

    /**
     * Constructor for multi-part transformers.
     * <p/>
     * Xml to Host transformers expect a String or byte[] as input and produces a 
     * byte array corresponding to mainframe raw data.
     * @param xmlBindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractXmlToHostMuleTransformer(
            final Map < String, AbstractXmlTransformers > xmlBindingTransformersMap) {
        super(xmlBindingTransformersMap);
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
        setReturnClass(byte[].class);
    }

    /**
     * {@inheritDoc}
     * The nature of the binding transformers passed by inherited class determines
     * if this is a multi part transformer or not.
     * <p/>
     * Single part transformers can either serialize to raw mainframe data or be
     * encapsulated in an architected LegStar Message. This is determined by the 
     * presence of a boolean in the incoming esb message properties.
     * <p/>
     * When a formatted LegStar message needs to be produced, the target program
     * properties are collected from a string in the incoming esb message properties.
     *  */
    public Object transform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("ESB Message before processing:");
            LOG.debug(esbMessage);
        }
        try {

            /* Single part messages come with binding transformers */
            if (getXmlBindingTransformers() != null) {
                byte[] hostData = getXmlBindingTransformers().toHost(
                        getXmlSource(esbMessage.getPayload()),
                        getHostCharset(esbMessage));
                return wrapHostData(hostData, esbMessage);

            } else {
                Document holderDoc = getDocument(esbMessage.getPayload());
                Map < String, byte[] > hostDataMap = new HashMap < String, byte[] >();
                for (Entry < String, AbstractXmlTransformers > entry
                        : getXmlBindingTransformersMap().entrySet()) {
                    hostDataMap.put(entry.getKey(),
                            entry.getValue().toHost(
                                    getXmlFragmentFromHolder(holderDoc, entry.getKey()),
                                    getHostCharset(esbMessage)));
                }
                return wrapHostData(hostDataMap, esbMessage);
            }

        } catch (HostTransformException e) {
            throw new TransformerException(
                    getLegstarMessages().hostTransformFailure(), this, e);
        }

    }

    /**
     * Return the payload wrapped as a javax.xml.transform.Source.
     * @param payload the esb message payload
     * @return an XML source
     * @throws TransformerException if esb message payload is not an XML source
     */
    public Source getXmlSource(final Object payload) throws TransformerException {
        try {
            if (payload instanceof String) {
                return new StreamSource(new StringReader((String) payload));
            } else if (payload instanceof byte[]) {
                return new StreamSource(new InputStreamReader(
                        new ByteArrayInputStream((byte[]) payload), "UTF-8"));

            } else if (payload instanceof InputStream) {
                return new StreamSource(new InputStreamReader(
                        (InputStream) payload, "UTF-8"));
            } else {
                throw new TransformerException(
                        getLegstarMessages().payloadNotXmlSource(), this);
            }
        } catch (UnsupportedEncodingException e) {
            throw new TransformerException(
                    getLegstarMessages().encodingFailure("UTF-8"), this, e);
        }

    }

    /**
     * Return the payload wrapped as a org.xml.sax.InputSource.
     * @param payload the esb message payload
     * @return an XML InputSource
     * @throws TransformerException if esb message payload is not an XML source
     */
    public InputSource getXmlInputSource(final Object payload) throws TransformerException {
        if (payload instanceof String) {
            return new InputSource(new StringReader((String) payload));
        } else if (payload instanceof byte[]) {
            return new InputSource(
                    new ByteArrayInputStream((byte[]) payload));

        } else if (payload instanceof InputStream) {
            return new InputSource(
                    (InputStream) payload);
        } else {
            throw new TransformerException(
                    getLegstarMessages().payloadNotXmlSource(), this);
        }

    }
    
    /**
     * @param payload the ESB message payload
     * @return the esb message payload as a DOM document
     * @throws TransformerException if payload is not XML
     */
    public Document getDocument(final Object payload) throws TransformerException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(getXmlInputSource(payload));
        } catch (TransformerException e) {
            throw new TransformerException(
                    getLegstarMessages().payloadNotXmlSource(), this);
        } catch (ParserConfigurationException e) {
            throw new TransformerException(
                    getLegstarMessages().payloadNotXmlSource(), this);
        } catch (SAXException e) {
            throw new TransformerException(
                    getLegstarMessages().payloadNotXmlSource(), this);
        } catch (IOException e) {
            throw new TransformerException(
                    getLegstarMessages().payloadNotXmlSource(), this);
        }
    }

    /**
     * When a holder XML for multi part payload needs to be turned into
     * host data, we need to associate inner XML nodes with part IDs.
     * @param holderDoc holder XML document
     * @param partID the part identifier or container name
     * @return a holder object
     * @throws TransformerException if creating holder fails
     */
    public Source getXmlFragmentFromHolder(
            final Document holderDoc,
            final String partID) throws TransformerException {
        NodeList nodeList = holderDoc.getElementsByTagName(partID);
        if (nodeList.getLength() > 0) {
            return new DOMSource(nodeList.item(0));
        } else {
            return new StreamSource(new StringReader(""));
        }
    }

}
