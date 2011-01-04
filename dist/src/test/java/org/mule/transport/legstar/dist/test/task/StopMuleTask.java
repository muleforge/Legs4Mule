package org.mule.transport.legstar.dist.test.task;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.tanukisoftware.wrapper.jmx.WrapperManagerMBean;

/**
 * A very simple ant task used to stop a Mule server using JMX.
 * 
 */
public class StopMuleTask extends Task {

	/** Default JNDI JMX URL used by Mule. */
	public static final String DEFAULT_JMX_SERVICE_URL = "service:jmx:rmi:///jndi/rmi://localhost:1099/server";
	
	/** Default Tanuki wrapper bean name.*/
	public static final String DEFAULT_WRAPPER_BEAN_NAME = "Mule:name=WrapperManager";
	
	/**
	 * The JMX JNDI URL used to retrieve a connector. Assumes Mule has been
	 * started with &lt;jmx-default-config&gt;
	 */
	private String _jmxServiceURL = DEFAULT_JMX_SERVICE_URL;
	
	/** The Tanuki bean registered by Mule.*/
	private String _wrapperBeanName = DEFAULT_WRAPPER_BEAN_NAME;

	/** {@inheritDoc} */
	@Override
	public void execute() {
		try {
			JMXServiceURL url = new JMXServiceURL(_jmxServiceURL);
			JMXConnector jmxc = JMXConnectorFactory.connect(url);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			ObjectName mbeanName = new ObjectName(_wrapperBeanName);
			WrapperManagerMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName,
					WrapperManagerMBean.class, true);

			mbeanProxy.stop(0);

			jmxc.close();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
            throw new BuildException(e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
            throw new BuildException(e);
		} catch (NullPointerException e) {
			e.printStackTrace();
            throw new BuildException(e);
		} catch (IOException e) {
			e.printStackTrace();
            throw new BuildException(e);
		}
	}

	/**
	 * @return the JMX JNDI URL used to retrieve a connector
	 */
	public String getJmxServiceURL() {
		return _jmxServiceURL;
	}

	/**
	 * @param jmxServiceURL
	 *            the JMX JNDI URL used to retrieve a connector to set
	 */
	public void setJmxServiceURL(String jmxServiceURL) {
		_jmxServiceURL = jmxServiceURL;
	}

	/**
	 * @return the Tanuki bean registered by Mule
	 */
	public String getWrapperBeanName() {
		return _wrapperBeanName;
	}

	/**
	 * @param wrapperBeanName the Tanuki bean registered by Mule to set
	 */
	public void setWrapperBeanName(String wrapperBeanName) {
		_wrapperBeanName = wrapperBeanName;
	}

}
