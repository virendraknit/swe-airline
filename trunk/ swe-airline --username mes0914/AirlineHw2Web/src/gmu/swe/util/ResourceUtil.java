package gmu.swe.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ResourceUtil {
	public static Context getInitialContext() throws javax.naming.NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new InitialContext(props);
	}
}
