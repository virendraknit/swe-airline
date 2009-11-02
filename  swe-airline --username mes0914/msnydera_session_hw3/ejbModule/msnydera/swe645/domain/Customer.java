/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Domain object used to represent a customer.
 * 
 * Note that the only unique constraint is the ID property. Therefore it is
 * possible to have multiple records containing the same name, address, etc.
 */
@Entity
@Table(name = "CUSTOMER")
@SequenceGenerator(name = "CUSTOMER_SEQUENCE", sequenceName = "IDENTITY")
public class Customer implements Serializable {

	private static final long serialVersionUID = 2285628387225980252L;

	// Unique Id of the customer
	private int id;

	// Name of the customer
	private String name;

	// Address of the customer
	private String address;

	// Phone number of the customer
	private String phone;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CUSTOMER_SEQUENCE")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
