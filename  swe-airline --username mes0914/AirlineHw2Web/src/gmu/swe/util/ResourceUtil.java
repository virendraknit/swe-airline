/*
 * Created by: Matt Snyder
 */
package gmu.swe.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Utility class used to extract common code for reusability.
 * 
 */
public class ResourceUtil {
	/**
	 * @return An InitialContext to a JBoss server running on localhost on port
	 *         1099.
	 * @throws javax.naming.NamingException
	 *             Thrown if an error occurs while creating the initial context.
	 */
	public static Context getInitialContext() throws javax.naming.NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new InitialContext(props);
	}
}
