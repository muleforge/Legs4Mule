package org.mule.providers.legstar.test.lsfileae;

import org.mule.providers.legstar.cixs.MuleHostHeader;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.Callable;

import com.legstar.host.invoke.HostInvoker;
import com.legstar.host.invoke.HostInvokerException;
import com.legstar.host.invoke.HostInvokerFactory;

import org.mule.providers.legstar.cixs.MuleHostHeaderFactory;
import org.mule.providers.legstar.cixs.MuleCixsException;

import com.legstar.test.coxb.lsfileae.DfhcommareaType;
import com.legstar.test.coxb.lsfileae.bind.DfhcommareaTypeBinding;

/**
 * LegStar/Mule Component implementation.
 * Each method maps to a CICS program. 
 * 
 * This class was generated by LegStar Mule Component generator.
 * Generated on 2008-07-17 08:59:55
 */
public class LsfileaeImpl implements Lsfileae, Callable {

    /** The JNDI locator for the configuration file name.*/
    private static final String JNDI_CONFIG_FILE =
        "java:comp/env/legstar/configFileName";
    
    /** The default configuration file name if not recovered from JNDI. */
    private static final String DEFAULT_CONFIG_FILE = "legstar-invoker-config.xml";

    /** The configuration file name. */
    private String mConfigFileName;

    /** Lookup the configuration file name at construction time. */
    public LsfileaeImpl() {
        try {
            Context initCtx = new InitialContext();
            mConfigFileName = (String) initCtx.lookup(JNDI_CONFIG_FILE);
        } catch (NamingException e) {
            mConfigFileName = DEFAULT_CONFIG_FILE;
        }
    }

    /** Properties for operation lsfileae. */
    private static final String LSFILEAE_PROP_FILE = "lsfileae.properties";

    /** Synchronous execution of a remote host program. */
    /** {@inheritDoc} */
    public final DfhcommareaType lsfileae(
               final DfhcommareaType request,
               final MuleHostHeader hostHeader)
        throws LsfileaeException {

        DfhcommareaType reply = null;
        try {
              
            /* Initialize invoker with static data and data from headers */
            HostInvoker mInvoker = HostInvokerFactory.createHostInvoker(
                mConfigFileName, hostHeader.getAddress(), LSFILEAE_PROP_FILE);

            /* Prepare the input parameter set using static binding */
            DfhcommareaTypeBinding inputDfhcommareaTypeBinding =
                  new DfhcommareaTypeBinding(request);
 
            /* Prepare the output parameter set using static binding */
            DfhcommareaTypeBinding outputDfhcommareaTypeBinding =
                  new DfhcommareaTypeBinding();
 
            /* Call remote program */
            mInvoker.invoke(hostHeader.getHostRequestID(),
                    inputDfhcommareaTypeBinding,
                    outputDfhcommareaTypeBinding);

            /* Get reply object */
            reply = outputDfhcommareaTypeBinding.getDfhcommareaType();

        } catch (HostInvokerException e) {
            throw new LsfileaeException(e);
        }

        return reply;
    }

    @Override
    public Object onCall(UMOEventContext eventContext) throws Exception {
        MuleHostHeader hostHeader =
            MuleHostHeaderFactory.createHostHeader(eventContext.getMessage());
        Object request  = eventContext.getTransformedMessage();
        if (request instanceof DfhcommareaType) {
            if (hostHeader.getHostRequestID() == null) {
                hostHeader.setHostRequestID("lsfileae");
            }
            return lsfileae((DfhcommareaType) request, hostHeader);
        }
        throw new MuleCixsException("Unrecognized request " + request);
    }

}