/*******************************************************************************
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 * 
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file
 ******************************************************************************/
package org.mule.providers.legstar.sample;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple POJO.
 *
 */
public class JVMQuery {
    
    public JVMQueryReply execute(JVMQueryRequest request) {
        
        JVMQueryReply reply = new JVMQueryReply();
        
        List <String> envVarValues = new ArrayList <String>();
        for (String envVarName : request.getEnvVarNames()) {
            envVarValues.add(System.getenv(envVarName));
        }
        reply.setEnvVarValues(envVarValues);
        Locale locale = Locale.getDefault();
        reply.setCountry(locale.getDisplayCountry());
        reply.setLanguage(locale.getDisplayLanguage());
        reply.setFormattedDate(
                DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL,
                        locale).format(new Date()));
        reply.setCurrencySymbol(Currency.getInstance(locale).getSymbol());
        return reply;
    }

}
