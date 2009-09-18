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

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.mule.transport.legstar.model.options.TcpTransportParameters;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * The TCP transport (Adapter to Mainframe) control group.
 * <p/>
 * Parameters needed by adapter to reach the mainframe over TCP.
 *
 */
public class CixsAdapterToHostTcpGroup extends AbstractCixsControlsGroup {

    /** The Host address on which mainframe TCP server listens to clients. */
    private Text mTcpHostText = null;

    /** The Port on which mainframe TCP server listens to clients. */
    private Text mTcpPortText = null;

    /** The user id for basic authentication. */
    private Text mTcpUserIdText = null;

    /** The password for basic authentication. */
    private Text mTcpPasswordText = null;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     */
    public CixsAdapterToHostTcpGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }
    
    /**
     * {@inheritDoc} 
     */
    public void createButton(final Composite composite) {
        super.createButton(composite, "TCP");
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        
        super.createControls(composite, Messages.adapter_to_host_tcp_transport_group_label, 2);

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_tcp_host_label + ':');
        mTcpHostText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_tcp_port_label + ':');
        mTcpPortText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_userid_label + ':');
        mTcpUserIdText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_password_label + ':');
        mTcpPasswordText = AbstractWizardPage.createText(getGroup()); 

    }

    /**
     * {@inheritDoc} 
     */
    public boolean validateControls() {
        if (getTcpHost() == null || getTcpHost().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_tcp_host_msg);
            return false;
        }
        try {
            if (Integer.parseInt(getTcpPort()) < 0 
                    || Integer.parseInt(getTcpPort()) > 65536) {
                getWizardPage().updateStatus(Messages.invalid_tcp_port_number_msg);
                return false;
            }
        } catch (NumberFormatException e) {
            getWizardPage().updateStatus(Messages.invalid_tcp_port_number_msg);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {

        mTcpHostText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mTcpPortText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mTcpUserIdText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mTcpPasswordText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    
    /**
     * @return the tcp parameters as a formatted tcp transport parameters object
     */
    public TcpTransportParameters getTcpTransportParameters() {
        TcpTransportParameters tcpTransportParameters = new TcpTransportParameters();
        tcpTransportParameters.setHost(getTcpHost());
        tcpTransportParameters.setPort(Integer.parseInt(getTcpPort()));
        tcpTransportParameters.setUserId(getTcpUserId());
        tcpTransportParameters.setPassword(getTcpPassword());
        return tcpTransportParameters;
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {

        setTcpHost(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_TCP_HOST,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_TCP_HOST)));
        
        setTcpPort(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_TCP_PORT,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_TCP_PORT)));

    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {

        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_TCP_HOST, getTcpHost());
        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_TCP_PORT, getTcpPort());
        
    }

    /**
     * @return Host address on which TCP listens to mainframe clients
     */
    public String getTcpHost() {
        return mTcpHostText.getText();
    }

    /**
     * @param tcpHost Host address on which TCP listens to mainframe clients
     */
    public void setTcpHost(final String tcpHost) {
        mTcpHostText.setText(tcpHost);
    }

    /**
     * @return Port on which TCP listens to mainframe clients
     */
    public String getTcpPort() {
        return mTcpPortText.getText();
    }

    /**
     * @param tcpPort Port on which TCP listens to mainframe clients
     */
    public void setTcpPort(final String tcpPort) {
        mTcpPortText.setText(tcpPort);
    }

    /**
     * @return UserId used for basic authentication
     */
    public String getTcpUserId() {
        return mTcpUserIdText.getText();
    }

    /**
     * @param tcpUserId UserId used for basic authentication
     */
    public void setTcpUserId(final String tcpUserId) {
        mTcpUserIdText.setText(tcpUserId);
    }

    /**
     * @return Password used for basic authentication
     */
    public String getTcpPassword() {
        return mTcpPasswordText.getText();
    }

    /**
     * @param tcpPassword Password used for basic authentication
     */
    public void setTcpPassword(final String tcpPassword) {
        mTcpPasswordText.setText(tcpPassword);
    }

}
