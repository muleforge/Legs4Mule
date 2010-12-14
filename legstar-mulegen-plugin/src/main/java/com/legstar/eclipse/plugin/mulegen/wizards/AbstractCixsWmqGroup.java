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
package com.legstar.eclipse.plugin.mulegen.wizards;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.legstar.cixs.gen.model.options.WmqTransportParameters;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Messages;

/**
 * The WebSphere MQ deployment control group.
 * <p/>
 * Parameters shared by adapter or proxies to communicate with the mainframe
 * over WebSphere MQ.
 *
 */
public abstract class AbstractCixsWmqGroup extends AbstractCixsControlsGroup {

    /** The URL used to do naming lookups for WMQ resources. */
    private Text mWmqJndiUrlText = null;

    /** The context factory class to do naming lookups for WMQ resources. */
    private Text mWmqJndiContextFactoryText = null;

    /** The connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    private Text mWmqConnectionFactoryText = null;

    /** The ZOS WMQ Queue Manager. */
    private Text mWmqZosQueueManagerText = null;

    /** The WMQ Queue name receiving requests. */
    private Text mWmqRequestQueueText = null;

    /** The WMQ Queue name receiving replies. */
    private Text mWmqReplyQueueText = null;

    /** The WMQ Queue name receiving errors. */
    private Text mWmqErrorQueueText = null;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     * @param selected whether this group should initially be selected
     */
    public AbstractCixsWmqGroup(
            final AbstractCixsGeneratorWizardPage wizardPage,
            final boolean selected) {
        super(wizardPage, selected);
    }

    /**
     * {@inheritDoc} 
     */
    public void createButton(final Composite composite) {
        super.createButton(composite, "WebSphere MQ");
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        
        super.createControls(composite, Messages.wmq_transport_group_label, 2);

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_jndi_url_label + ':');
        mWmqJndiUrlText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_jndi_context_factory_label + ':');
        mWmqJndiContextFactoryText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_connection_factory_label + ':');
        mWmqConnectionFactoryText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_zos_queue_manager_label + ':');
        mWmqZosQueueManagerText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_request_queue_label + ':');
        mWmqRequestQueueText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_reply_queue_label + ':');
        mWmqReplyQueueText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_error_queue_label + ':');
        mWmqErrorQueueText = AbstractWizardPage.createText(getGroup()); 

    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {
        mWmqJndiUrlText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mWmqJndiContextFactoryText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mWmqConnectionFactoryText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mWmqZosQueueManagerText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mWmqRequestQueueText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mWmqReplyQueueText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mWmqErrorQueueText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    /**
     * {@inheritDoc} 
     */
    public boolean validateControls() {
        try {
            new URI(getWmqJndiUrl());
        } catch (URISyntaxException e1) {
            getWizardPage().updateStatus(Messages.invalid_wmq_jndi_url_msg);
            return false;
        }
        if (getWmqJndiContextFactory() == null || getWmqJndiContextFactory().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_wmq_jndi_context_factory_msg);
            return false;
        }
        if (getWmqConnectionFactory() == null || getWmqConnectionFactory().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_wmq_connection_factory_msg);
            return false;
        }
        if (getWmqZosQueueManager() == null || getWmqZosQueueManager().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_wmq_zos_queue_manager_msg);
            return false;
        }
        if (getWmqRequestQueue() == null || getWmqRequestQueue().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_wmq_target_queue_msg);
            return false;
        }
        if (getWmqReplyQueue() == null || getWmqReplyQueue().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_wmq_target_reply_queue_msg);
            return false;
        }
        return true;
    }

    /**
     * @return URL used to do naming lookups for WMQ resources
     */
    public String getWmqJndiUrl() {
        return mWmqJndiUrlText.getText();
    }

    /**
     * @param wmqJndiUrl URL used to do naming lookups for WMQ resources
     */
    public void setWmqJndiUrl(final String wmqJndiUrl) {
        mWmqJndiUrlText.setText(wmqJndiUrl);
    }

    /**
     * @return context factory class to do naming lookups for WMQ resources
     */
    public String getWmqJndiContextFactory() {
        return mWmqJndiContextFactoryText.getText();
    }

    /**
     * @param wmqJndiContextFactory context factory class JBossESB uses to do naming lookups for WMQ resources
     */
    public void setWmqJndiContextFactory(final String wmqJndiContextFactory) {
        mWmqJndiContextFactoryText.setText(wmqJndiContextFactory);
    }

    /**
     * @return connection-factory used to lookup queues/topics in a naming directory (JNDI)
     */
    public String getWmqConnectionFactory() {
        return mWmqConnectionFactoryText.getText();
    }

    /**
     * @param wmqConnectionFactory connection-factory used to lookup queues/topics in a naming directory (JNDI)
     */
    public void setWmqConnectionFactory(final String wmqConnectionFactory) {
        mWmqConnectionFactoryText.setText(wmqConnectionFactory);
    }

    /**
     * @return ZOS WMQ Manager
     */
    public String getWmqZosQueueManager() {
        return mWmqZosQueueManagerText.getText();
    }

    /**
     * @param wmqZosQueueManager ZOS WMQ Manager
     */
    public void setWmqZosQueueManager(final String wmqZosQueueManager) {
        mWmqZosQueueManagerText.setText(wmqZosQueueManager);
    }

    /**
     * @return WMQ Queue name receiving requests
     */
    public String getWmqRequestQueue() {
        return mWmqRequestQueueText.getText();
    }

    /**
     * @param wmqRequestQueue WMQ Queue name receiving requests
     */
    public void setWmqRequestQueue(final String wmqRequestQueue) {
        mWmqRequestQueueText.setText(wmqRequestQueue);
    }

    /**
     * @return WMQ Queue name receiving replies
     */
    public String getWmqReplyQueue() {
        return mWmqReplyQueueText.getText();
    }

    /**
     * @param wmqReplyQueue WMQ Queue name receiving replies
     */
    public void setWmqReplyQueue(final String wmqReplyQueue) {
        mWmqReplyQueueText.setText(wmqReplyQueue);
    }

    /**
     * @return WMQ Queue name receiving errors
     */
    public String getWmqErrorQueue() {
        return mWmqErrorQueueText.getText();
    }

    /**
     * @param wmqErrorQueue WMQ Queue name receiving errors
     */
    public void setWmqErrorQueue(final String wmqErrorQueue) {
        mWmqErrorQueueText.setText(wmqErrorQueue);
    }
    
    /**
     * @return the WebSphere MQ parameters as a formatted transport parameters object
     */
    public WmqTransportParameters getWmqTransportParameters() {
        WmqTransportParameters wmqTransportParameters = new WmqTransportParameters();
        wmqTransportParameters.setJndiUrl(getWmqJndiUrl());
        wmqTransportParameters.setJndiContextFactory(getWmqJndiContextFactory());
        wmqTransportParameters.setConnectionFactory(getWmqConnectionFactory());
        wmqTransportParameters.setZosQueueManager(getWmqZosQueueManager());
        wmqTransportParameters.setRequestQueue(getWmqRequestQueue());
        wmqTransportParameters.setReplyQueue(getWmqReplyQueue());
        wmqTransportParameters.setErrorQueue(getWmqErrorQueue());
        return wmqTransportParameters;
        
    }

}
