/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.legstar.gen.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This Ant task iteratively calls the Velocity template engine to create
 * a list of artifacts described in a cixsmake xml file.
 * TODO describe the format of cixsmake in an xsd
 */
public class CixsMake extends Task {

    /** The cixs make file name. */
    private String mCixsMakeFileName;
    
    /** Velocity templates are applied against a model. 
     * This is the model name. */
    private String mModelName;
    
    /** Velocity templates are applied against a model. 
     * This is the model itself. */
    private Object mModel;
    
    /** XML element representing a cixs target. */
    public static final String CIXS_TARGET_XML_E = "cixstarget";

    /** XML attribute representing a cixs target name. */
    public static final String CIXS_TARGET_NAME_XML_A = "name";

    /** XML attribute representing a cixs target directory. */
    public static final String CIXS_TARGET_DIR_XML_A = "targetDir";

    /** XML element representing a cixs target step with a template. */
    public static final String CIXS_TEMPLATE_XML_E = "cixstemplate";
    
    /** XML attribute representing a cixs template name. */
    public static final String CIXS_TEMPLATE_NAME_XML_A = "name";

    /** XML attribute representing a cixs template target file. */
    public static final String CIXS_TEMPLATE_TARGET_FILE_XML_A = "targetFile";
    
    /** Used t generated random serial version IDs. */
    private static Random mRandom = new Random();

    
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CixsMake.class);

    /** @{inheritDoc}*/
    @Override
   public final void init() {
        try {
            LOG.info("Initializing Cixs Make processor");
            CixsMuleGenUtil.initVelocity();
        } catch (Exception e) {
            throw new BuildException(e.getMessage());
        }
    }
    
    /** @{inheritDoc}*/
    @Override
    public final void execute() {
        long start = System.currentTimeMillis();
        try {
            LOG.debug("Start make processor");
            Document doc = getInput();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Make file name   = {}", mCixsMakeFileName);
                LOG.debug("Model name       = {}", mModelName);
            }
            processTargets(doc);
        } catch (CixsMakeException e) {
            LOG.error("Make processor failure", e);
            throw new BuildException(e.getMessage());
        }
        long end = System.currentTimeMillis();
        LOG.info("Make processing success for {}", mCixsMakeFileName);
        LOG.info("Duration = {} ms", (end - start));
    }

    /**
     * Check input values and load the cixs make file.
     * @return an in-memory DOM cixs make
     * @throws CixsMakeException if initialization fails
     */
    private Document getInput() throws CixsMakeException {
        if (mCixsMakeFileName == null || mCixsMakeFileName.length() == 0) {
            throw new CixsMakeException("Missing cixs make file parameter");
        }
        if (mModelName == null || mModelName.length() == 0) {
            throw new CixsMakeException("Missing model name parameter");
        }
        if (mModel == null) {
            throw new CixsMakeException("Missing model parameter");
        }
        File makeFile = new File(mCixsMakeFileName);
        
        if (!makeFile.exists()) {
            throw new CixsMakeException("Cixs make file "
                    + makeFile + " does not exist");
        }
        return load(makeFile);
    }
    
    /**
     * Process targets sequentially, invoking the velocity engine for each
     * target.
     * @param doc a valid DOM for a cixs make
     * @throws CixsMakeException if processing fails
     */
    private void processTargets(final Document doc) throws CixsMakeException {
        NodeList listOfElements = doc.getElementsByTagName(
                CIXS_TARGET_XML_E);
        if (listOfElements == null || listOfElements.getLength() == 0) {
            throw (new CixsMakeException(
                    "Empty or invalid cixs make file"));
        }
        Element targetElement = (Element) listOfElements.item(0);
        String targetName = targetElement.getAttribute(CIXS_TARGET_NAME_XML_A);
        if (targetName == null || targetName.length() == 0) {
            throw new CixsMakeException("Missing name attribute for "
                    + CIXS_TARGET_XML_E + " element");
        }
        String targetDir = targetElement.getAttribute(CIXS_TARGET_DIR_XML_A);
        if (targetDir != null && targetDir.length() > 0) {
            try {
                CixsMuleGenUtil.checkDirectory(targetDir, true);
            } catch (IllegalArgumentException e) {
                throw new CixsMakeException(e);
            }
        }
        listOfElements = targetElement.getElementsByTagName(
                CIXS_TEMPLATE_XML_E);
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing targets");
            LOG.debug("Target name      = {}", targetName);
            LOG.debug("Target directory = {}", targetDir);
        }
        for (int i = 0; i < listOfElements.getLength(); i++) {
            Element templateEl = (Element) listOfElements.item(i);
            String templateName = templateEl.getAttribute(
                    CIXS_TEMPLATE_NAME_XML_A);
            if (templateName == null || templateName.length() == 0) {
                throw new CixsMakeException(
                        "Missing template name attribute for "
                        + CIXS_TEMPLATE_XML_E + " element");
            }
            String templateTargetFileName = templateEl.getAttribute(
                    CIXS_TEMPLATE_TARGET_FILE_XML_A);
            if (templateTargetFileName == null
                    || templateTargetFileName.length() == 0) {
                throw new CixsMakeException(
                        "Missing template target file name attribute for "
                        + CIXS_TEMPLATE_XML_E + " element");
            }
            processTemplate(targetName, templateName, getParameters(templateEl),
                    CixsMuleGenUtil.getFile(targetDir, templateTargetFileName));
        }
    }
    
    /**
     * Template elements might have children. These are used to pass additional
     * parameters to the template generation process.
     * Children are expected to have a single value attribute otherwise, they
     * are ignored.
     * @param templateEl the current template element
     * @return a Map of parameters/values
     */
    private Map<String, String> getParameters(final Element templateEl) {
        Map<String, String> parameters = new HashMap <String, String>();
        NodeList childs = templateEl.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            if (childs.item(i) instanceof Element) {
                Element parmEl = (Element) childs.item(i);
                String value = parmEl.getAttribute("value");
                if (value != null) {
                    parameters.put(parmEl.getNodeName(), value);
                }
            }
        }
        return parameters;
    }
    
    /**
     * Apply a velocity template taken from a cixs make xml.
     * @param targetName the cixs make name
     * @param templateName the velocity template to apply
     * @param parameters additional parameters to pass to template
     * @param targetFile the file to generate
     * @throws CixsMakeException if processing fails
     */
    private void processTemplate(
            final String targetName,
            final String templateName,
            final Map<String, String> parameters, 
            final File targetFile) throws CixsMakeException {
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing template");
            LOG.debug("Target name      = {}", targetName);
            LOG.debug("Template name    = {}", templateName);
            LOG.debug("Target file      = {}", targetFile);
            for (String key : parameters.keySet()) {
                String value = parameters.get(key);
                LOG.debug("Parameter {} = {}", key, value);
            }
        }
        VelocityContext context = CixsMuleGenUtil.getContext();
        context.put(mModelName, mModel);
        context.put("serialVersionID", Long.toString(mRandom.nextLong()) + 'L');
        for (String key : parameters.keySet()) {
            context.put(key, parameters.get(key));
        }
        StringWriter w = new StringWriter();

        try {
            Velocity.mergeTemplate(templateName, "UTF-8", context, w);
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(targetFile));
                out.write(w.toString());
            } catch (IOException e) {
                throw new CixsMakeException(e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (ResourceNotFoundException e) {
            throw new CixsMakeException(e);
        } catch (ParseErrorException e) {
            throw new CixsMakeException(e);
        } catch (MethodInvocationException e) {
            throw new CixsMakeException(e);
        } catch (Exception e) {
            throw new CixsMakeException(e);
        }
    }
    
    /**
     * Loads cixs make file from a serialized XML.
     * @param makeFile the serialized file
     * @return an in-memory DOM cixs make
     * @throws CixsMakeException if load fails
     */
    public final Document load(final File makeFile) throws CixsMakeException {
        DocumentBuilderFactory docBuilderFactory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilderFactory.setNamespaceAware(false);
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(makeFile);
            return doc;
        } catch (ParserConfigurationException e) {
            throw (new CixsMakeException(e));
        } catch (SAXException e) {
            throw (new CixsMakeException(e));
        } catch (IOException e) {
            throw (new CixsMakeException(e));
        }
    }
    
    /**
     * @return the Cixs Make File name
     */
    public final String getCixsMakeFileName() {
        return mCixsMakeFileName;
    }
    
    /**
     * @param cixsMakeFileName the Cixs Make File name to set
     */
    public final void setCixsMakeFileName(final String cixsMakeFileName) {
        mCixsMakeFileName = cixsMakeFileName;
    }

    /**
     * @return the model name
     */
    public final String getModelName() {
        return mModelName;
    }

    /**
     * @param modelName the model name to set
     */
    public final void setModelName(final String modelName) {
        mModelName = modelName;
    }

    /**
     * @return the model used to apply templates to
     */
    public final Object getModel() {
        return mModel;
    }

    /**
     * @param model the model to set
     */
    public final void setModel(final Object model) {
        mModel = model;
    }

}
