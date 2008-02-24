package org.mule.providers.legstar.cixs;

import org.mule.umo.UMOMessage;

/**
 * Utility class for generated Mule-LegStar host headers. This allows
 * runtime parameters needed to access a host from being shipped as
 * regular UMO message properties.
 */
public final class MuleHostHeaderFactory {

    /** Host request identifier. */
    private static final String L4M_KEY_HOSTREQUESTID = "L4MHostRequestID";
    
    /** Host endpoint name property key. */
    private static final String L4M_KEY_HOSTENDPOINT = "L4MhostEndPoint";
    
    /** Host character set property key. */
    private static final String L4M_KEY_HOSTCHARSET = "L4MHostCharset";
    
    /** Host user ID property key. */
    private static final String L4M_KEY_HOSTUSERID = "L4MHostUserID";
    
    /** Host password property key. */
    private static final String L4M_KEY_HOSTPASSWORD = "L4MHostPassword";
    
    /** Host trace mode property key. */
    private static final String L4M_KEY_HOSTTRACEMODE = "L4MHostTraceMode";
    
    
     /**
      * Extracts header data from a Mule message properties and create an
      * Host header.
      * @param umoMessage the Mule message
      * @return the new host header
      */
     public static MuleHostHeader createHostHeader(final UMOMessage umoMessage) {
         MuleHostHeader hostHeader = new MuleHostHeader();
         hostHeader.setHostEndPoint((String) umoMessage.getProperty(L4M_KEY_HOSTENDPOINT));
         hostHeader.setHostCharset((String) umoMessage.getProperty(L4M_KEY_HOSTCHARSET));
         hostHeader.setHostUserID((String) umoMessage.getProperty(L4M_KEY_HOSTUSERID));
         hostHeader.setHostPassword((String) umoMessage.getProperty(L4M_KEY_HOSTPASSWORD));
         hostHeader.setTraceMode(umoMessage.getBooleanProperty(L4M_KEY_HOSTTRACEMODE, false));
         hostHeader.setHostRequestID((String) umoMessage.getProperty(L4M_KEY_HOSTREQUESTID));
         return hostHeader;
     }
}
