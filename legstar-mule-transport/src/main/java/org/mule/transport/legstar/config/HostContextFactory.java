package org.mule.transport.legstar.config;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;

import org.osjava.sj.SimpleContext;
import org.osjava.sj.SimpleContextFactory;
import org.osjava.sj.loader.JndiLoader;

/**
 * A cheap alternative to RefFSContextFactory based on simple-jndi.
 * <p/>
 * The idea is to help declaring z/OS resources when no JNDI environment is
 * provided.
 * <p/>
 * 
 * @see http://www.dimsumcoding.com/simple-jndi/ for documentation.
 * 
 */
public class HostContextFactory extends SimpleContextFactory {

    /**
     * Redirect the common URL parameter to become simple JNDI root folder.
     * <p/>
     * We replace the '.' as a delimiter so that resources can resemble z/OS PDS
     * names or MQ Queue names.
     * 
     * @see javax.naming.spi.InitialContextFactory#getInitialContext(java.util.Hashtable)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Context getInitialContext(Hashtable environment)
            throws NamingException {

        String url = (String) environment.get(Context.PROVIDER_URL);
        if (url != null) {
            environment.put(SimpleContext.SIMPLE_ROOT, url);
            environment.put(JndiLoader.SIMPLE_DELIMITER, "/");
        }
        return super.getInitialContext(environment);
    }
}
