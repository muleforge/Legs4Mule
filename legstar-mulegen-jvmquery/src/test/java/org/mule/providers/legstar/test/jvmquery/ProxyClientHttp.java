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
package org.mule.providers.legstar.test.jvmquery;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Simulates the behavior of a Mainframe program consuming a remote resource.
 */
public class ProxyClientHttp {
    
    /** HTTP client instance.*/
    private HttpClient mHttpClient;
    
    /** Listening URL of LegStar Proxy to target. The URL might contain a
     * variable ${host} to be dynamically replaced. */
    private String mURLString;
    
    /**
     * Contruct the simulator for a specific URL.
     * @param urlString Listening URL of LegStar Proxy to target
     */
    public ProxyClientHttp(final String urlString) {
        mURLString = urlString.replace("${host}", getLocalIPAddress());
    }
    
    /**
     * Invoke the proxy deployed at the specified.
     * @param mainframeRequestData a hex string of the raw mainframe data
     * @param expectedResponseSize the size in bytes of the expected response
     * @return a hex string of the mainframe response data
     */
    public String invoke(
            final String mainframeRequestData,
            final int expectedResponseSize) {
        try {
            if (mHttpClient == null) {
                mHttpClient = new HttpClient();
            }
            PostMethod postMethod = new PostMethod(mURLString);
            ByteArrayRequestEntity requestEntity = new ByteArrayRequestEntity(
                    toByteArray(mainframeRequestData), "application/octet-stream");
            postMethod.setRequestEntity(requestEntity);
            if (200 != mHttpClient.executeMethod(postMethod)) {
               throw new RuntimeException("POST on URL " + mURLString
                       + " has returned " + postMethod.getStatusCode()
                       + " " + postMethod.getStatusText()); 
            }
            if (expectedResponseSize != postMethod.getResponseContentLength()) {
                throw new RuntimeException("Content length returned does not match");
            }
            return toHexString(postMethod.getResponseBody());
        } catch (HttpException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    /**
     * Helper method to dump field content in hexadecimal.
     * 
     * @param hostBytes a byte array to get hexadecimal representation for
     * @return a string with hexadecimal representation of the field content
     */
    private static String toHexString(final byte[] hostBytes) {
        return toHexString(hostBytes, 0, hostBytes.length);
    }

    /**
     * Helper method to dump field content in hexadecimal.
     * 
     * @param hostBytes a byte array to get hexadecimal representation for
     * @param start 0-based position of first byte to dump
     * @param length number of bytes to dump
     * @return a string with hexadecimal representation of the field content
     */
    private static String toHexString(
            final byte[] hostBytes, final int start, final int length) {
        
        if (hostBytes == null) {
            return null;
        }
        assert (start + length < hostBytes.length + 1);
        
        StringBuilder hexString = new StringBuilder("");
        for (int i = start; i < length; i++) {
            hexString.append(
                    Integer.toHexString(
                            hostBytes[i] & 0xFF | 0x100).substring(1, 3));
        }
        
        return hexString.toString();
    }

    /**
     * Helper method to populate a byte array from a hex string representation.
     * 
     * @param string a string of hexadecimal characters to be turned
     *  into a byte array
     * @return an initialized byte array
     */
    private static byte[] toByteArray(final String string) {
        if (string == null) {
            return new byte[0];
        }
        byte[] hostBytes = new byte[string.length() / 2];
        for (int i = 0; i < string.length(); i += 2) {
            hostBytes[i / 2] = 
                (byte) Integer.parseInt(string.substring(i, i + 2), 16);
        }
        return hostBytes;
    }

    /**
     * Assuming the local machine is running Server.
     * @return the local machine IP address
     */
    private String getLocalIPAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            byte[] ipAddr = addr.getAddress();
            String ipAddrStr = "";
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }
            return ipAddrStr;
        } catch (UnknownHostException e) {
            return "";
        }

    }

}
