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

import com.legstar.cixs.gen.model.options.HttpTransportParameters;
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
public class CixsAdapterTcpGroup extends AbstractCixsControlsGroup {

    /** The Host address on which mainframe TCP server listens to clients. */
    private Text _tcpHostText = null;

    /** The Port on which mainframe TCP server listens to clients. */
    private Text _tcpPortText = null;

    /** The user id for basic authentication. */
    private Text _tcpUserIdText = null;

    /** The password for basic authentication. */
    private Text _tcpPasswordText = null;
    
    /** The data model. */
    private TcpTransportParameters _genModel;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     * @param genModel the data model
     * @param selected whether this group should initially be selected
     */
    public CixsAdapterTcpGroup(
            final AbstractCixsGeneratorWizardPage wizardPage,
            final TcpTransportParameters genModel,
            final boolean selected) {
        super(wizardPage, selected);
        _genModel = genModel;
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
        
        super.createControls(composite, Messages.adapter_tcp_transport_group_label, 2);

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_tcp_host_label + ':');
        _tcpHostText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_tcp_port_label + ':');
        _tcpPortText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_userid_label + ':');
        _tcpUserIdText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_password_label + ':');
        _tcpPasswordText = AbstractWizardPage.createText(getGroup()); 

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

        _tcpHostText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _tcpPortText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _tcpUserIdText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        _tcpPasswordText.addModifyListener(new ModifyListener() {
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

        setTcpHost(getInitTcpHost());
        setTcpPort(getInitTcpPort());
        setTcpUserId(getInitTcpUserId());
        setTcpPassword(getInitTcpPassword());

    }

    /**
     * @return a safe initial value
     */
    protected String getInitTcpHost() {
        String initValue = _genModel.getHost();
        if (initValue == null) {
            initValue = getDefaultTcpHost();
        }
        return initValue;
    }

    /**
     * @return a default value
     */
    public String getDefaultTcpHost() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_TCP_HOST);
    }

    /**
     * @return a safe initial value
     */
    protected String getInitTcpPort() {
        int initValue = _genModel.getPort();
        if (initValue == HttpTransportParameters.PORT_NOT_SET) {
            return getDefaultTcpPort();
        }
        return Integer.toString(initValue);
    }

    /**
     * @return a default value
     */
    public String getDefaultTcpPort() {
        return getWizardPage().getStore().getString(
                PreferenceConstants.ADAPTER_DEFAULT_TCP_PORT);
    }

    /**
     * @return a safe class name initial value
     */
    protected String getInitTcpUserId() {
        String initValue = _genModel.getUserId();
        if (initValue == null) {
            initValue = "";
        }
        return initValue;
    }

    /**
     * @return a safe class name initial value
     */
    protected String getInitTcpPassword() {
        String initValue = _genModel.getPassword();
        if (initValue == null) {
            initValue = "";
        }
        return initValue;
    }

    @Override
    public void updateGenModelExtended() {
        getGenModel().setHost(getTcpHost());
        getGenModel().setPort(Integer.parseInt(getTcpPort()));
        getGenModel().setUserId(getTcpUserId());
        getGenModel().setPassword(getTcpPassword());
        
    }


    /**
     * @return Host address on which TCP listens to mainframe clients
     */
    public String getTcpHost() {
        return _tcpHostText.getText();
    }

    /**
     * @param tcpHost Host address on which TCP listens to mainframe clients
     */
    public void setTcpHost(final String tcpHost) {
        _tcpHostText.setText(tcpHost);
    }

    /**
     * @return Port on which TCP listens to mainframe clients
     */
    public String getTcpPort() {
        return _tcpPortText.getText();
    }

    /**
     * @param tcpPort Port on which TCP listens to mainframe clients
     */
    public void setTcpPort(final String tcpPort) {
        _tcpPortText.setText(tcpPort);
    }

    /**
     * @return UserId used for basic authentication
     */
    public String getTcpUserId() {
        return _tcpUserIdText.getText();
    }

    /**
     * @param tcpUserId UserId used for basic authentication
     */
    public void setTcpUserId(final String tcpUserId) {
        _tcpUserIdText.setText(tcpUserId);
    }

    /**
     * @return Password used for basic authentication
     */
    public String getTcpPassword() {
        return _tcpPasswordText.getText();
    }

    /**
     * @param tcpPassword Password used for basic authentication
     */
    public void setTcpPassword(final String tcpPassword) {
        _tcpPasswordText.setText(tcpPassword);
    }

    /**
     * @return the data model associated with this group
     */
    public TcpTransportParameters getGenModel() {
        return _genModel;
    }

}
