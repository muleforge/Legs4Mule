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
package org.mule.transport.legstar.http;

import org.mule.transport.http.HttpMuleMessageFactory;
import org.mule.api.MuleContext;

/**
 * <code>LegstarMessageAdapter</code> delegates processing to
 * <code>HttpMuleMessageFactory</code>.
 */
public class LegstarHttpMuleMessageFactory extends HttpMuleMessageFactory {
    
    /** Serial ID.   */
    private static final long serialVersionUID = 5737156381082603182L;

    /**
     * @param context the Mule context
     */
    public LegstarHttpMuleMessageFactory(final MuleContext context) 
    {
        super(context);
    }
    
}
