/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.AirlineUser;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;

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
//		Properties props = new Properties();
//		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
//		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
//		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
//		return new InitialContext(props);
		return new InitialContext();
	}

	/**
	 * Utility method for logging a user into a JBoss server.
	 * 
	 * @param user
	 *            AirlineUser that contains the username and password
	 * @return Context that has the user logged in.
	 * @throws NamingException
	 *             Thrown if an error occurs while creating the initial context.
	 * @throws LoginException
	 *             Thrown if an error occurs while logging into the JBoss
	 *             server.
	 * @throws Exception
	 *             Thrown if an error occurs with getting a security client from
	 *             the security client factory.
	 */
	public static Context getLoggedInContext(AirlineUser user) throws NamingException, LoginException, Exception {
		SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
		securityClient.setSimple(user.getUsername(), user.getPassword());
		securityClient.login();

		Context ctx = new InitialContext();

		return ctx;
	}

	/**
	 * Determines if a user is logged into the provided session. This method
	 * assumes the user would be in the session under Constants.CURRENT_USER.
	 * 
	 * @param session
	 *            HttpSession to check for the user in
	 * @return True if the user is logged in (i.e. the AirlineUser object is in
	 *         the session), otherwise returns false.
	 */
	public static boolean isLoggedIn(HttpSession session) {
		AirlineUser user = ResourceUtil.getLoggedInUser(session);

		return user != null;
	}

	/**
	 * Returns the current user that is logged into the HttpSession under
	 * Constants.CURRENT_USER in the session object. If the user isn't logged
	 * in, then null is returned.
	 * 
	 * @param session
	 *            HttpSession to check for the user in.
	 * @return The logged in user, or null if no user is logged in.
	 */
	public static AirlineUser getLoggedInUser(HttpSession session) {
		Object user = session.getAttribute(Constants.CURRENT_USER);

		return user != null ? (AirlineUser) user : null;
	}
}
