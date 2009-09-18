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

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsControlsGroup;
import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsGeneratorWizardPage;
import com.legstar.eclipse.plugin.common.wizards.AbstractWizardPage;
import com.legstar.eclipse.plugin.mulegen.Messages;

/**
 * The MOCK transport (Adapter to Mainframe) control group.
 * <p/>
 * Parameters needed by adapter to reach the mainframe over MOCK.
 *
 */
public class CixsAdapterToHostMockGroup extends AbstractCixsControlsGroup {

    /** The user id for basic authentication. */
    private Text mMockUserIdText = null;

    /** The password for basic authentication. */
    private Text mMockPasswordText = null;

    /**
     * Construct this control group attaching it to a wizard page.
     * @param wizardPage the parent wizard page
     */
    public CixsAdapterToHostMockGroup(final AbstractCixsGeneratorWizardPage wizardPage) {
        super(wizardPage);
    }
    
    /**
     * {@inheritDoc} 
     */
    public void createButton(final Composite composite) {
        super.createButton(composite, "MOCK");
    }

    /**
     * {@inheritDoc} 
     */
    public void createControls(final Composite composite) {
        
        super.createControls(composite, Messages.adapter_to_host_mock_transport_group_label, 2);

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_userid_label + ':');
        mMockUserIdText = AbstractWizardPage.createText(getGroup()); 

        AbstractWizardPage.createLabel(getGroup(), Messages.adapter_to_host_password_label + ':');
        mMockPasswordText = AbstractWizardPage.createText(getGroup()); 

    }

    /**
     * {@inheritDoc} 
     */
    public boolean validateControls() {
        return true;
    }

    /**
     * {@inheritDoc} 
     */
    public void createExtendedListeners() {

        mMockUserIdText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
        mMockPasswordText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                getWizardPage().dialogChanged();
            }
        });
    }

    
    /**
     * {@inheritDoc} 
     */
    public void initExtendedControls() {

    }

    /**
     * {@inheritDoc} 
     */
    public void storeExtendedProjectPreferences() {

    }

    /**
     * @return UserId used for basic authentication
     */
    public String getMockUserId() {
        return mMockUserIdText.getText();
    }

    /**
     * @param tcpUserId UserId used for basic authentication
     */
    public void setMockUserId(final String tcpUserId) {
        mMockUserIdText.setText(tcpUserId);
    }

    /**
     * @return Password used for basic authentication
     */
    public String getMockPassword() {
        return mMockPasswordText.getText();
    }

    /**
     * @param tcpPassword Password used for basic authentication
     */
    public void setMockPassword(final String tcpPassword) {
        mMockPasswordText.setText(tcpPassword);
    }

}
