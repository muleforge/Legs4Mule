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
package org.mule.transport.legstar.config;

/**
 * A container attributes.
 * <p/>
 * Container-driven programs receive and produce named containers.
 *
 */
public class HostContainer {
    
    /** Container name. */
    private String _name;
    
    /** Container maximum size. */
    private int _maxLength;

    /**
     * @return the container name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the _name to set
     */
    public void setName(final String name) {
       _name = name;
    }

    /**
     * @return the _maxLength
     */
    public int getMaxLength() {
        return _maxLength;
    }

    /**
     * @param length the _maxLength to set
     */
    public void setMaxLength(final int length) {
        _maxLength = length;
    }

}
