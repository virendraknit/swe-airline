/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.domain;

/**
 * This object is used to represent a user that is logged into the system.
 */
public class AirlineUser {
	private String username;

	private String password;

	/**
	 * @return Username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username
	 * 
	 * @param username
	 *            Username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password
	 * 
	 * @param password
	 *            Password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
