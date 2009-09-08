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

import com.legstar.cixs.gen.model.options.HttpTransportParameters;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.preferences.PreferenceConstants;

/**
 * The HTTP transport (Adapter to Mainframe) control group.
 * <p/>
 * Parameters needed by adapter to reach the mainframe over HTTP.
 *
 */
public class CixsAdapterToHostHttpGroup extends AbstractCixsControlsGroup {

    /** The Host address on which mainframe HTTP server listens to clients. */
    private Text mHttpHostText = null;

    /** The Port on which mainframe HTTP server listens to clients. */
    private Text mHttpPortText = null;

    /** The Path on which mainframe HTTP server listens to clients. */
    private Text mHttpPathText = null;

    /** The user id for basic authentication. */
    private Text mHttpUserIdText = null;

    /** The password for basic authentication. */
    private Text mHttpPasswordText = null;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     */
    public CixsAdapterToHostHttpGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }
    
    /**
     * {@inheritDoc} 
     */
    public void createButton(final Composite composite) {
        super.createButton(composite, "HTTP");
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        
        super.createControls(composite, Messages.adapter_to_host_http_transport_group_label, 2);

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_http_host_label + ':');
        mHttpHostText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_http_port_label + ':');
        mHttpPortText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_http_path_label + ':');
        mHttpPathText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_http_userid_label + ':');
        mHttpUserIdText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_http_password_label + ':');
        mHttpPasswordText = AbstractWizardPage.createText(getGroup()); 

    }

    /**
     * {@inheritDoc} 
     */
    public boolean validateControls() {
        if (getHttpHost() == null || getHttpHost().length() == 0) {
            getWizardPage().updateStatus(Messages.invalid_http_host_msg);
            return false;
        }
        try {
            if (Integer.parseInt(getHttpPort()) < 0 
                    || Integer.parseInt(getHttpPort()) > 65536) {
                getWizardPage().updateStatus(Messages.invalid_http_port_number_msg);
                return false;
            }
        } catch (NumberFormatException e) {
            getWizardPage().updateStatus(Messages.invalid_http_port_number_msg);
            return false;
        }
        if (getHttpPath() != null && getHttpPath().length() > 0) {
            if (getHttpPath().charAt(0) != '/') {
                getWizardPage().updateStatus(Messages.invalid_http_path_msg);
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {

        mHttpHostText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mHttpPortText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mHttpPathText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mHttpUserIdText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mHttpPasswordText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    
    /**
     * @return the http parameters as a formatted http transport parameters object
     */
    public HttpTransportParameters getHttpTransportParameters() {
        HttpTransportParameters httpTransportParameters = new HttpTransportParameters();
        httpTransportParameters.setHost(getHttpHost());
        httpTransportParameters.setPort(Integer.parseInt(getHttpPort()));
        httpTransportParameters.setPath(getHttpPath());
        httpTransportParameters.setUserId(getHttpUserId());
        httpTransportParameters.setPassword(getHttpPassword());
        return httpTransportParameters;
    }

    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {

        setHttpHost(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_HTTP_HOST,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_HTTP_HOST)));
        
        setHttpPort(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_HTTP_PORT,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_DEFAULT_HTTP_PORT)));

        setHttpPath(getProjectPreferences().get(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_HTTP_PATH,
                getWizardPage().getStore().getString(
                        PreferenceConstants.ADAPTER_TO_HOST_HTTP_PATH)));
    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {

        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_HTTP_HOST, getHttpHost());
        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_HTTP_PORT, getHttpPort());
        getProjectPreferences().put(
                PreferenceConstants.ADAPTER_TO_HOST_LAST_HTTP_PATH, getHttpPath());
        
    }

    /**
     * @return Host address on which HTTP listens to mainframe clients
     */
    public String getHttpHost() {
        return mHttpHostText.getText();
    }

    /**
     * @param httpHost Host address on which HTTP listens to mainframe clients
     */
    public void setHttpHost(final String httpHost) {
        mHttpHostText.setText(httpHost);
    }

    /**
     * @return Port on which HTTP listens to mainframe clients
     */
    public String getHttpPort() {
        return mHttpPortText.getText();
    }

    /**
     * @param httpPort Port on which HTTP listens to mainframe clients
     */
    public void setHttpPort(final String httpPort) {
        mHttpPortText.setText(httpPort);
    }

    /**
     * @return Path on which HTTP listens to mainframe clients
     */
    public String getHttpPath() {
        return mHttpPathText.getText();
    }

    /**
     * @param httpPath Path on which HTTP listens to mainframe clients
     */
    public void setHttpPath(final String httpPath) {
        mHttpPathText.setText(httpPath);
    }

    /**
     * @return UserId used for basic authentication
     */
    public String getHttpUserId() {
        return mHttpUserIdText.getText();
    }

    /**
     * @param httpUserId UserId used for basic authentication
     */
    public void setHttpUserId(final String httpUserId) {
        mHttpUserIdText.setText(httpUserId);
    }

    /**
     * @return Password used for basic authentication
     */
    public String getHttpPassword() {
        return mHttpPasswordText.getText();
    }

    /**
     * @param httpPassword Password used for basic authentication
     */
    public void setHttpPassword(final String httpPassword) {
        mHttpPasswordText.setText(httpPassword);
    }

}
