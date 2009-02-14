/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.gen;

import org.mule.providers.legstar.model.CixsMuleComponent;

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
        "org.mule.providers.legstar.test";

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
