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
package com.legstar.eclipse.plugin.mulegen;

import org.eclipse.osgi.util.NLS;

/**
 * This plugin localized message class.
 */
public class Messages extends NLS {

    /** All messages come from this property file.*/
    private static final String BUNDLE_NAME =
        "com.legstar.eclipse.plugin.mulegen.messages";

    /** Preference page title.*/
    public static String mulegen_preference_page_title;
    /** Preference page description.*/
    public static String preference_page_description;
    /** Mule install location label.*/
    public static String preference_mule_install_location_label;
    /** Mule jar deployment preference label.*/
    public static String preference_user_jar_location_label;
    /** Mule configurations folder preference label.*/
    public static String preference_mule_config_folder_label;
    /** Cobol samples folder preference label.*/
    public static String preference_cobol_folder_label;
 
    /** Preference page title.*/
    public static String mulegen_proxy_preference_page_title;
    /** Preference page description.*/
    public static String preference_proxy_page_description;

    /** Proxy http scheme template preference label.*/
    public static String preference_proxy_http_scheme_label;
    /** Proxy http host template preference label.*/
    public static String preference_proxy_http_host_label;
    /** Proxy http port template preference label.*/
    public static String preference_proxy_http_port_label;
    /** Proxy http path template preference label.*/
    public static String preference_proxy_http_path_template_label;

    /** Preference page title.*/
    public static String mulegen_adapter_preference_page_title;
    /** Preference page description.*/
    public static String preference_adapter_page_description;

    /** The name filter to which this wizard is associated.*/
    public static String filename_filter;
    /** Right click on cixs files shows this menu item.*/
    public static String popup_menu_label;
    /** The mule to cixs wizard icon.*/
    public static String mule_to_cixs_generator_icon;
    /** mule to cixs action menu label.*/
    public static String mule_to_cixs_action_label;
    /** The cixs to mule wizard icon.*/
    public static String cixs_to_mule_generator_icon;
    /** Cixs to mule action menu label.*/
    public static String cixs_to_mule_action_label;


    /** Target mule configuration files location label.*/
    public static String target_mule_config_location_label;
    /** Target mule user libraries location label.*/
    public static String target_mule_jar_location_label;
    /** Invalid mule configuration files location message.*/
    public static String invalid_mule_config_location_msg;
    /** Unable to locate plugin installation message.*/
    public static String unable_to_locate_plugin_installation_msg;

    /** Cixs to mule generator name.*/
    public static String cixs_to_mule_wizard_page_title;
    /** Cixs to mule generator description.*/
    public static String cixs_to_mule_wizard_page_description;
    /** Umo group label.*/
    public static String target_umo_component_group_label;
    /** Umo class name label.*/
    public static String target_umo_component_implementation_name_label;
    /** Class selection dialog title.*/
    public static String class_selection_error_dialog_title;
    /** Class selection short error message.*/
    public static String class_selection_error_short_msg;
    /** Class selection long error message.*/
    public static String class_selection_error_long_msg;
    /** Target location for COBOL samples label.*/
    public static String cobol_target_location_label;
    /** URI exposed to mainframe label.*/
    public static String proxy_uri_label;
    /** Invalid UMO implementation name message.*/
    public static String invalid_target_umo_component_implementation_name_msg;
    /** Invalid proxy URI message.*/
    public static String invalid_proxy_uri_msg;
    /** Invalid cobol samples location message.*/
    public static String invalid_cobol_target_location_msg;

    /** mule to cixs generator name.*/
    public static String mule_to_cixs_wizard_page_title;
    /** mule to cixs generator description.*/
    public static String mule_to_cixs_wizard_page_description;
    /** Mainframe URI exposed to Mule clients label.*/
    public static String mainframe_uri_label;
    /** Invalid mainframe URI message.*/
    public static String invalid_mainframe_uri_msg;

    /** Unable to setup classpath for the target java project. */
    public static String classpath_setup_failure_msg;

    /** Target selection group label. */
    public static String target_selection_group_label;
    /** Target selection label. */
    public static String target_selection_label;

    /** Sample configuration transport.*/
    public static String sample_configuration_transport_label;

    /** Adapter to mainframe transport label.*/
    public static String adapter_transport_label;

    /** Settings for target mainframe HTTP group label.*/
    public static String adapter_http_transport_group_label;
    /** Host address on which mainframe listens to HTTP clients label.*/
    public static String adapter_http_host_label;
    /** Port on which mainframe listens to HTTP clients label.*/
    public static String adapter_http_port_label;
    /** Path on which mainframe listens to HTTP clients label.*/
    public static String adapter_http_path_label;

    /** URL used to do naming lookups for WMQ resources label. */
    public static String adapter_wmq_jndi_url_label;
    /** context factory class to do naming lookups for WMQ resources label. */
    public static String adapter_wmq_jndi_context_factory_label;
    /**
     * connection-factory used to lookup queues/topics in a naming directory
     * (JNDI) label.
     */
    public static String adapter_wmq_connection_factory_label;
    /** ZOS WMQ Queue Manager label. */
    public static String adapter_wmq_zos_queue_manager_label;
    /** WMQ Queue name receiving requests label. */
    public static String adapter_wmq_request_queue_label;
    /** WMQ Queue name receiving replies label. */
    public static String adapter_wmq_reply_queue_label;
    /** WMQ Queue name receiving errors label. */
    public static String adapter_wmq_error_queue_label;

    
    /** URL used to do naming lookups for WMQ resources label. */
    public static String proxy_wmq_jndi_url_label;
    /** context factory class to do naming lookups for WMQ resources label. */
    public static String proxy_wmq_jndi_context_factory_label;
    /**
     * connection-factory used to lookup queues/topics in a naming directory
     * (JNDI) label.
     */
    public static String proxy_wmq_connection_factory_label;
    /** ZOS WMQ Queue Manager label. */
    public static String proxy_wmq_zos_queue_manager_label;
    /** WMQ suffix for Queue name receiving requests label.*/
    public static String proxy_wmq_request_queue_suffix_label;
    /** WMQ suffix for Queue name receiving replies label.*/
    public static String proxy_wmq_reply_queue_suffix_label;
    /** WMQ suffix for Queue name receiving errors label.*/
    public static String proxy_wmq_error_queue_suffix_label;


    /** Client to Adapter payload.*/
    public static String client_to_adapter_payload_label;

    /** User id for mainframe authentication.*/
    public static String adapter_userid_label;
    /** Password for mainframe authentication.*/
    public static String adapter_password_label;

    /** Invalid port number.*/
    public static String invalid_http_port_number_msg;
    /** Invalid host.*/
    public static String invalid_http_host_msg;
    /** Invalid path.*/
    public static String invalid_http_path_msg;

    /** Choice of sample host messaging label.*/
    public static String sample_adapter_host_messaging_type_label;

    /** Settings for WMQ group label. */
    public static String adapter_wmq_transport_group_label;

    /** Settings for WMQ group label. */
    public static String proxy_wmq_transport_group_label;

    /** Settings for target mainframe TCP group label.*/
    public static String adapter_tcp_transport_group_label;
    /** Host address on which mainframe listens to TCP clients label.*/
    public static String adapter_tcp_host_label;
    /** Port on which mainframe listens to TCP clients label.*/
    public static String adapter_tcp_port_label;

    /** Invalid port number.*/
    public static String invalid_tcp_port_number_msg;
    /** Invalid host.*/
    public static String invalid_tcp_host_msg;

    /** Settings for target mainframe MOCK group label.*/
    public static String adapter_mock_transport_group_label;

    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

}
