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
package com.legstar.eclipse.plugin.mulegen.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.legstar.eclipse.plugin.mulegen.Messages;
import com.legstar.eclipse.plugin.mulegen.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class MuleCixsProxyPreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

    /**
     * Constructs a preference page.
     */
    public MuleCixsProxyPreferencePage() {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription(Messages.preference_proxy_page_description);
    }

    /**
     * Creates the field editors. Field editors are abstractions of
     * the common GUI blocks needed to manipulate various types
     * of preferences. Each field editor knows how to save and
     * restore itself.
     */
    public void createFieldEditors() {

        addField(new StringFieldEditor(
                PreferenceConstants.DEFAULT_COBOL_SAMPLE_FOLDER,
                Messages.preference_cobol_folder_label + ':',
                getFieldEditorParent()));

        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_HTTP_HOST,
                "HTTP " + Messages.preference_proxy_http_host_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_HTTP_PORT,
                "HTTP " + Messages.preference_proxy_http_port_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_HTTP_PATH_TEMPLATE,
                "HTTP " + Messages.preference_proxy_http_path_template_label + ':',
                getFieldEditorParent()));

        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_JNDI_URL,
                "WMQ " + Messages.proxy_wmq_jndi_url_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_JNDI_CONTEXT_FACTORY,
                "WMQ " + Messages.proxy_wmq_jndi_context_factory_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_CONNECTION_FACTORY,
                "WMQ " + Messages.proxy_wmq_connection_factory_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_ZOS_QUEUE_MANAGER,
                "WMQ " + Messages.proxy_wmq_zos_queue_manager_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_REQUEST_QUEUE_SUFFIX,
                "WMQ " + Messages.proxy_wmq_request_queue_suffix_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_REPLY_QUEUE_SUFFIX,
                "WMQ " + Messages.proxy_wmq_reply_queue_suffix_label + ':',
                getFieldEditorParent()));
        addField(new StringFieldEditor(
                PreferenceConstants.PROXY_DEFAULT_WMQ_ERROR_QUEUE_SUFFIX,
                "WMQ " + Messages.proxy_wmq_error_queue_suffix_label + ':',
                getFieldEditorParent()));
    }

    /**
     * {@inheritDoc}
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(
     * org.eclipse.ui.IWorkbench)
     */
    public void init(final IWorkbench workbench) {
    }

}
