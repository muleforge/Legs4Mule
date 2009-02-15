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

import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsActivator;
import com.legstar.eclipse.plugin.mulegen.wizards
.Cixs2MuleGeneratorWizardLauncher;
import com.legstar.eclipse.plugin.mulegen.wizards
.Mule2CixsGeneratorWizardLauncher;

/**
 * This generator plugin register itself for dynamic discovery.
 * @see com.legstar.eclipse.plugin.cixscom.wizards.AbstractCixsActivator.
 */
public class Activator extends AbstractCixsActivator {

    /** The plug-in ID. */
    public static final String PLUGIN_ID =
        "com.legstar.eclipse.plugin.mulegen";

    /** The shared instance. */
    private static Activator mPlugin;

    /** The result of registering a Mule2Cixs generator service. */
    private ServiceRegistration mMule2CixsGeneratorService;

    /** The result of registering a Cixs2Mule generator service. */
    private ServiceRegistration mCixs2MuleGeneratorService;

    /**
     * The constructor.
     */
    public Activator() {
        super(PLUGIN_ID);
    }

    /**
     * {@inheritDoc}
     */
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        mPlugin = this;
        mMule2CixsGeneratorService = 
            Mule2CixsGeneratorWizardLauncher.register(context);
        mCixs2MuleGeneratorService =
            Cixs2MuleGeneratorWizardLauncher.register(context);
    }

    /**
     * {@inheritDoc}
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(
     * org.osgi.framework.BundleContext)
     */
    public void stop(final BundleContext context) throws Exception {
        if (mMule2CixsGeneratorService != null) {
            mMule2CixsGeneratorService.unregister();
        }
        if (mCixs2MuleGeneratorService != null) {
            mCixs2MuleGeneratorService.unregister();
        }
        mPlugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * @return the shared instance
     */
    public static Activator getDefault() {
        return mPlugin;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path.
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(final String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

}
