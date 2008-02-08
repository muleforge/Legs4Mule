package org.mule.providers.legstar.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

public class JVMQueryTest extends TestCase {
    
    public void testEmptyQuery() {
        Locale.setDefault(new Locale("en", "US"));
        JVMQueryRequest request = new JVMQueryRequest();
        JVMQuery query = new JVMQuery();
        JVMQueryReply reply = query.execute(request);
        assertEquals(0, reply.getEnvVarValues().size());
        assertEquals("United States", reply.getCountry());
        assertEquals("English", reply.getLanguage());
        assertEquals("$", reply.getCurrencySymbol());
        assertTrue(reply.getFormattedDate() != null);
    }

    public void testQuery() {
        Locale.setDefault(new Locale("jp", "JP"));
        JVMQueryRequest request = new JVMQueryRequest();
        List <String> envVarNames = new ArrayList <String>();
        envVarNames.add("JAVA_HOME");
        envVarNames.add("MULE_HOME");
        envVarNames.add("CATALINA_HOME");
        request.setEnvVarNames(envVarNames);
        
        JVMQuery query = new JVMQuery();
        JVMQueryReply reply = query.execute(request);
        assertEquals(3, reply.getEnvVarValues().size());
        assertTrue(reply.getEnvVarValues().get(0).contains("jdk"));
        assertTrue(reply.getEnvVarValues().get(1).contains("mule"));
        assertTrue(reply.getEnvVarValues().get(2).contains("tomcat"));
        assertEquals("Japan", reply.getCountry());
        assertEquals("jp", reply.getLanguage());
        assertEquals("JPY", reply.getCurrencySymbol());
        assertTrue(reply.getFormattedDate() != null);
    }

}
