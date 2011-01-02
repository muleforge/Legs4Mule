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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.types.DataTypeFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.legstar.coxb.transform.AbstractXmlTransformers;
import com.legstar.coxb.transform.HostTransformException;

/**
 * This ESB Transformer converts host data into XML using the regular
 * LegStar binding transformers.
 */
public abstract class AbstractHostToXmlMuleTransformer extends AbstractHostXmlMuleTransformer {

    /**
     * Constructor for single part transformers.
     * <p/>
     * Host to XML transformers expect byte array sources corresponding to
     * mainframe raw data.
     * Alternatively, source can be an inputStream in which case, it is 
     * assumed to stream a byte array.
     * The returned XML is serialized in a String.
     * @param xmlBindingTransformers a set of transformers for the part type.
     */
    public AbstractHostToXmlMuleTransformer(
            final AbstractXmlTransformers xmlBindingTransformers) {
        super(xmlBindingTransformers);
        registerSourceType(DataTypeFactory.create(InputStream.class));
        registerSourceType(DataTypeFactory.BYTE_ARRAY);
        setReturnDataType(DataTypeFactory.TEXT_STRING);
    }

    /**
     * Constructor for multi-part transformers.
     * <p/>
     * Host to XML transformers expects a map of byte arrays for multi part
     * payloads.
     * The XML is serialized in a String.
     * @param xmlBindingTransformersMap map of transformer sets, one for each part type.
     */
    public AbstractHostToXmlMuleTransformer(
            final Map < String, AbstractXmlTransformers > xmlBindingTransformersMap) {
        super(xmlBindingTransformersMap);
        registerSourceType(DataTypeFactory.create(Map.class));
        setReturnDataType(DataTypeFactory.TEXT_STRING);
    }

    /**
     * {@inheritDoc}
     * Detect if client is using LegStar messaging. If he does,
     * store that information in the ESB message properties so
     * that downstream actions know that the caller is expecting
     * a LegStarMessage reply.
     */
    @SuppressWarnings("unchecked")
    public Object hostTransform(
            final MuleMessage esbMessage,
            final String encoding) throws TransformerException {

        try {

            Object src = esbMessage.getPayload();
            StringWriter writer = new StringWriter();
            
            if (src instanceof byte[]) {
                getXmlBindingTransformers().toXml((byte[]) src,
                        writer, getHostCharset(esbMessage));
                return writer.toString();

            } else if (src instanceof InputStream) {
                getXmlBindingTransformers().toXml(
                        IOUtils.toByteArray((InputStream) src),
                        writer, getHostCharset(esbMessage));

            } else if (src instanceof Map) {
                return toXml((Map < String, byte[] >) src,
                        getHostCharset(esbMessage));
            }
            return null;

        } catch (HostTransformException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (Exception e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }

    }

    /**
     * When the host is sending an multiple parts part, this code transforms each part.
     * Each part is transformed individually then they are all wrapped in a holder
     * XML which is returned.
     * @param hostParts a map of byte arrays, one for each part
     * @param hostCharset the host character set
     * @return a java object
     * @throws TransformerException if transformation failed
     */
    public Object toXml(
            final Map < String, byte[] > hostParts,
            final String hostCharset) throws TransformerException {

        try {
            Map < String, Writer > transformedParts = new HashMap < String, Writer >();
            for (Entry < String, byte[] > hostPart : hostParts.entrySet()) {
                StringWriter writer = new StringWriter();
                getXmlBindingTransformersMap().get(hostPart.getKey()).toXml(
                        hostPart.getValue(), writer, hostCharset);
                transformedParts.put(hostPart.getKey(), writer);
            }
            return createXmlHolder(transformedParts);
        } catch (HostTransformException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }

    }

    /**
     * When multiple part were received from the host, each transformed parts XML
     * is stored in a map.
     * <p/>
     * Here we merge the XML fragments as children of a holder element.
     * @param transformedParts a map of transformed types
     * @return a holder XML
     * @throws TransformerException if creating holder fails
     */
    public Object createXmlHolder(
            final Map < String, Writer > transformedParts) throws TransformerException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document docResult = docBuilder.newDocument();
            Element elHolder = docResult.createElementNS(
                    getHolderQName().getNamespaceURI(), getHolderQName().getLocalPart());
            docResult.appendChild(elHolder);

            for (Entry < String, Writer > entry : transformedParts.entrySet()) {
                mergeXml(docBuilder, transformedParts, entry.getKey(), docResult, elHolder);
            }

            StringWriter writer = new StringWriter();
            TransformerFactory tfactory = TransformerFactory.newInstance();
            Transformer serializer = tfactory.newTransformer();
            serializer.transform(new DOMSource(docResult), new StreamResult(writer));
            return writer.toString();

        } catch (ParserConfigurationException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (TransformerException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (TransformerConfigurationException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (javax.xml.transform.TransformerException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }
    }

    /**
     * Merges an XML fragment as a child of a holder element.
     * @param docBuilder the document builder for DOM documents
     * @param transformedParts the map of XML fragments per part ID
     * @param partID a part identifier
     * @param docResult the result DOM document
     * @param elHolder the holder DOM element
     * @throws TransformerException if merge operation fails
     */
    public void mergeXml(
            final DocumentBuilder docBuilder,
            final Map < String, Writer > transformedParts,
            final String partID,
            final Document docResult,
            final Element elHolder) throws TransformerException {
        try {
            Writer writer = transformedParts.get(partID);
            Document docInput = docBuilder.parse(
                    new InputSource(new StringReader(writer.toString())));
            NodeList nodeList = docInput.getElementsByTagName(partID);
            if (nodeList.getLength() > 0) {
                Node nodeInDocInput = nodeList.item(0);
                /* Import foreign node */
                Node nodeInDocResult = docResult.importNode(nodeInDocInput, true);
                elHolder.appendChild(nodeInDocResult);
            }
        } catch (DOMException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (SAXException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        } catch (IOException e) {
            throw new TransformerException(
                    getI18NMessages().hostTransformFailure(), this, e);
        }
    }

    /**
     * Generated classes must implement this method for multi part messages.
     * @return the qualified XML name of the holder element
     * @throws TransformerException if qualified name cannot be returned
     */
    public QName getHolderQName() throws TransformerException {
        throw new TransformerException(
                getI18NMessages().noMultiPartSupportFailure(), this);

    }

}
