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
package org.mule.transport.legstar.gen;

import org.mule.transport.legstar.model.CixsMuleComponent;

import com.legstar.test.cixs.JvmqueryOperationCases;
import com.legstar.test.cixs.LsfileacOperationCases;
import com.legstar.test.cixs.LsfileaeOperationCases;
import com.legstar.test.cixs.LsfilealOperationCases;

/**
 * Helper class. Creates models ready for code generation.
 */
public final class Samples {

    /** Target package for generated Mule component. */
    public static final String LEGS4MULE_PKG_PREFIX =
        "org.mule.transport.legstar.test";

    /** Defeats instantiation.*/
    private Samples() {
        
    }

    /**
     * Create a service without any operations.
     * @param serviceName the service name
     * @return a new service
     */
    public static CixsMuleComponent getNewService(final String serviceName) {
        CixsMuleComponent service = new CixsMuleComponent();
        service.setPackageName(LEGS4MULE_PKG_PREFIX + '.' + serviceName);
        service.setName(serviceName);
        return service;
    }

    /**
     * Case with a regular commarea.
     * @return a Mule component to serve as model for velocity templates.
     */
    public static CixsMuleComponent getLsfileaeMuleComponent() {
        CixsMuleComponent muleComponent = getNewService("lsfileae");
        muleComponent.getCixsOperations().add(
                LsfileaeOperationCases.getOperation(
                        muleComponent.getName(), muleComponent.getPackageName()));
        return muleComponent;
    }

    /**
     * @return a Mule component to serve as model for velocity templates.
     */
    public static CixsMuleComponent getLsfilealMuleComponent() {
        CixsMuleComponent muleComponent = getNewService("lsfileal");
        muleComponent.getCixsOperations().add(
                LsfilealOperationCases.getOperation(
                        muleComponent.getName(), muleComponent.getPackageName()));
        return muleComponent;
    }
    /**
     * @return a Mule component to serve as model for velocity templates.
     */
    public static CixsMuleComponent getLsfileacMuleComponent() {
        CixsMuleComponent muleComponent = getNewService("lsfileac");
        muleComponent.getCixsOperations().add(
                LsfileacOperationCases.getOperation(
                        muleComponent.getName(), muleComponent.getPackageName()));
        return muleComponent;
    }
    /**
     * @return a Mule component to serve as model for velocity templates.
     */
    public static CixsMuleComponent getLsfileaxMuleComponent() {
        CixsMuleComponent muleComponent = getNewService("lsfileax");
        muleComponent.getCixsOperations().add(
                LsfileaeOperationCases.getOperation(
                        muleComponent.getName(), muleComponent.getPackageName()));
        muleComponent.getCixsOperations().add(
                LsfileacOperationCases.getOperation(
                        muleComponent.getName(), muleComponent.getPackageName()));
        return muleComponent;
    }
    /**
     * @return a Mule component to serve as model for velocity templates.
     */
    public static CixsMuleComponent getJvmQueryMuleComponent() {
        CixsMuleComponent muleComponent = getNewService("jvmquery");
        muleComponent.getCixsOperations().add(
                JvmqueryOperationCases.getOperation(
                        muleComponent.getName(), muleComponent.getPackageName()));
        return muleComponent;
    }
}
