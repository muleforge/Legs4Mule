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
    private Text _wmqJndiUrlText = null;

    /** The context factory class to do naming lookups for WMQ resources. */
    private Text _wmqJndiContextFactoryText = null;

    /** The connection-factory used to lookup queues/topics in a naming directory (JNDI). */
    private Text _wmqConnectionFactoryText = null;

    /** The ZOS WMQ Queue Manager. */
    private Text _wmqZosQueueManagerText = null;

    /** The WMQ Queue name receiving requests. */
    private Text _wmqRequestQueueText = null;

    /** The WMQ Queue name receiving replies. */
    private Text _wmqReplyQueueText = null;

    /** The WMQ Queue name receiving errors. */
    private Text _wmqErrorQueueText = null;
    
    /** The data model. */
    private WmqTransportParameters _genModel;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     * @param genModel the data model
     * @param selected whether this group should initially be selected
     */
    public AbstractCixsWmqGroup(
            final AbstractCixsGeneratorWizardPage wizardPage,
            final WmqTransportParameters genModel,
            final boolean selected) {
        super(wizardPage, selected);
        _genModel = genModel;
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
        _wmqJndiUrlText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_jndi_context_factory_label + ':');
        _wmqJndiContextFactoryText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_connection_factory_label + ':');
        _wmqConnectionFactoryText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_zos_queue_manager_label + ':');
        _wmqZosQueueManagerText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_request_queue_label + ':');
        _wmqRequestQueueText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_reply_queue_label + ':');
        _wmqReplyQueueText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.wmq_error_queue_label + ':');
        _wmqErrorQueueText = AbstractWizardPage.createText(getGroup()); 

    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {
        _wmqJndiUrlText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _wmqJndiContextFactoryText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _wmqConnectionFactoryText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _wmqZosQueueManagerText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _wmqRequestQueueText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _wmqReplyQueueText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _wmqErrorQueueText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {
        setWmqJndiUrl(getInitWmqJndiUrl());
        setWmqJndiContextFactory(getInitWmqJndiContextFactory());
        setWmqConnectionFactory(getInitWmqConnectionFactory());
        setWmqZosQueueManager(getInitWmqZosQueueManager());
        setWmqRequestQueue(getInitWmqRequestQueue());
        setWmqReplyQueue(getInitWmqReplyQueue());
        setWmqErrorQueue(getInitWmqErrorQueue());
        
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqJndiUrl() {
        String initValue = _genModel.getJndiUrl();
        if (initValue == null) {
            initValue = getDefaultWmqJndiUrl();
        }
        return initValue;
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqJndiContextFactory() {
        String initValue = _genModel.getJndiContextFactory();
        if (initValue == null) {
            initValue = getDefaultWmqJndiContextFactory();
        }
        return initValue;
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqConnectionFactory() {
        String initValue = _genModel.getConnectionFactory();
        if (initValue == null) {
            initValue = getDefaultWmqConnectionFactory();
        }
        return initValue;
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqZosQueueManager() {
        String initValue = _genModel.getZosQueueManager();
        if (initValue == null) {
            initValue = getDefaultWmqZosQueueManager();
        }
        return initValue;
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqRequestQueue() {
        String initValue = _genModel.getRequestQueue();
        if (initValue == null) {
            initValue = getDefaultWmqRequestQueue();
        }
        return initValue;
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqReplyQueue() {
        String initValue = _genModel.getReplyQueue();
        if (initValue == null) {
            initValue = getDefaultWmqReplyQueue();
        }
        return initValue;
    }
    
    /**
     * @return a safe initial value
     */
    protected String getInitWmqErrorQueue() {
        String initValue = _genModel.getErrorQueue();
        if (initValue == null) {
            initValue = getDefaultWmqErrorQueue();
        }
        return initValue;
    }
    
    /**
     * @return a default value for WMQ JNDI URL
     */
    public abstract String getDefaultWmqJndiUrl();

    /**
     * @return a default value for WMQ JNDI context factory
     */
    public abstract String getDefaultWmqJndiContextFactory();

    /**
     * @return a default value for WMQ connection factory
     */
    public abstract String getDefaultWmqConnectionFactory();

    /**
     * @return a default value for WMQ zos queue manager
     */
    public abstract String getDefaultWmqZosQueueManager();

    /**
     * @return a default value for WMQ request queue
     */
    public abstract String getDefaultWmqRequestQueue();

    /**
     * @return a default value for WMQ reply queue
     */
    public abstract String getDefaultWmqReplyQueue();

    /**
     * @return a default value for WMQ error queue
     */
    public abstract String getDefaultWmqErrorQueue();

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

    /** {@inheritDoc} */
    @Override
    public void updateGenModelExtended() {
        getGenModel().setJndiUrl(getWmqJndiUrl());
        getGenModel().setJndiContextFactory(getWmqJndiContextFactory());
        getGenModel().setConnectionFactory(getWmqConnectionFactory());
        getGenModel().setZosQueueManager(getWmqZosQueueManager());
        getGenModel().setRequestQueue(getWmqRequestQueue());
        getGenModel().setReplyQueue(getWmqReplyQueue());
        getGenModel().setErrorQueue(getWmqErrorQueue());
    }
    
    /**
     * @return URL used to do naming lookups for WMQ resources
     */
    public String getWmqJndiUrl() {
        return _wmqJndiUrlText.getText();
    }

    /**
     * @param wmqJndiUrl URL used to do naming lookups for WMQ resources
     */
    public void setWmqJndiUrl(final String wmqJndiUrl) {
        _wmqJndiUrlText.setText(wmqJndiUrl);
    }

    /**
     * @return context factory class to do naming lookups for WMQ resources
     */
    public String getWmqJndiContextFactory() {
        return _wmqJndiContextFactoryText.getText();
    }

    /**
     * @param wmqJndiContextFactory context factory class JBossESB uses to do naming lookups for WMQ resources
     */
    public void setWmqJndiContextFactory(final String wmqJndiContextFactory) {
        _wmqJndiContextFactoryText.setText(wmqJndiContextFactory);
    }

    /**
     * @return connection-factory used to lookup queues/topics in a naming directory (JNDI)
     */
    public String getWmqConnectionFactory() {
        return _wmqConnectionFactoryText.getText();
    }

    /**
     * @param wmqConnectionFactory connection-factory used to lookup queues/topics in a naming directory (JNDI)
     */
    public void setWmqConnectionFactory(final String wmqConnectionFactory) {
        _wmqConnectionFactoryText.setText(wmqConnectionFactory);
    }

    /**
     * @return ZOS WMQ Manager
     */
    public String getWmqZosQueueManager() {
        return _wmqZosQueueManagerText.getText();
    }

    /**
     * @param wmqZosQueueManager ZOS WMQ Manager
     */
    public void setWmqZosQueueManager(final String wmqZosQueueManager) {
        _wmqZosQueueManagerText.setText(wmqZosQueueManager);
    }

    /**
     * @return WMQ Queue name receiving requests
     */
    public String getWmqRequestQueue() {
        return _wmqRequestQueueText.getText();
    }

    /**
     * @param wmqRequestQueue WMQ Queue name receiving requests
     */
    public void setWmqRequestQueue(final String wmqRequestQueue) {
        _wmqRequestQueueText.setText(wmqRequestQueue);
    }

    /**
     * @return WMQ Queue name receiving replies
     */
    public String getWmqReplyQueue() {
        return _wmqReplyQueueText.getText();
    }

    /**
     * @param wmqReplyQueue WMQ Queue name receiving replies
     */
    public void setWmqReplyQueue(final String wmqReplyQueue) {
        _wmqReplyQueueText.setText(wmqReplyQueue);
    }

    /**
     * @return WMQ Queue name receiving errors
     */
    public String getWmqErrorQueue() {
        return _wmqErrorQueueText.getText();
    }

    /**
     * @param wmqErrorQueue WMQ Queue name receiving errors
     */
    public void setWmqErrorQueue(final String wmqErrorQueue) {
        _wmqErrorQueueText.setText(wmqErrorQueue);
    }
    

    /**
     * @return the data model associated with this group
     */
    public WmqTransportParameters getGenModel() {
        return _genModel;
    }

}
