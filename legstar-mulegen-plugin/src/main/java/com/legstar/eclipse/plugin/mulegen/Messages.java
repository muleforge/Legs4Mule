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
    /** Mainframe legstar listening URI label.*/
    public static String preference_mainframe_uri_label;
    /** Cobol samples folder preference label.*/
    public static String preference_cobol_folder_label;
    /** Proxy URI preference label.*/
    public static String preference_proxy_uri_label;

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

    /** Cixs to mule generator name.*/
    public static String cixs_to_mule_wizard_page_title;
    /** Cixs to mule generator description.*/
    public static String cixs_to_mule_wizard_page_description;
    /** Umo group label.*/
    public static String umo_group_label;
    /** Umo class name label.*/
    public static String umo_class_name_label;
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
    /** Invalid UMO class name message.*/
    public static String invalid_umo_class_name_msg;
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

    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

}
