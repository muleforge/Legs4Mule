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
package org.mule.providers.legstar.http;

import org.mule.providers.http.HttpMessageAdapter;
import org.mule.umo.MessagingException;

/**
 * <code>LegstarMessageAdapter</code> delegates processing to
 * <code>HttpMessageAdapter</code>.
 */
public class LegstarHttpMessageAdapter extends HttpMessageAdapter {

    /** Serial ID.   */
    private static final long serialVersionUID = 5737156381082603182L;

    /**
     * Constructs an adapter that basically wraps a message.
     * @param message the inner message
     * @throws MessagingException if construction fails
     */
    public LegstarHttpMessageAdapter(final Object message) throws MessagingException 
    {
        super(message);
    }


}
