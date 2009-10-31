/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.service.ejb;


import java.util.Collection;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import msnydera.swe645.domain.Airplane;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.AirlineHeadquartersService;
import msnydera.swe645.service.impl.AirlineHeadquartersServiceImpl;

/**
 * Session Bean implementation of the Remote HeadquartersEjb. This class is
 * basically a delegate object for the AirlineHeadquartersService business
 * implementation. This EJB is only used to provide an external communication
 * point. This class also posts information on a Topic. This class only deals
 * with Headquarters related business.
 */
@Stateless
public class HeadquartersEjb implements HeadquartersEjbRemote {
	private AirlineHeadquartersService service;

	/**
	 * Default constructor.
	 */
	public HeadquartersEjb() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#createAirplane(int,
	 * java.lang.String)
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException {
		this.getService().createAirplane(numberOfSeats, airplaneType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gmu.swe.service.ejb.HeadquartersEjbRemote#createAirport(java.lang.String)
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException {
		this.getService().createAirport(airportCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gmu.swe.service.ejb.HeadquartersEjbRemote#createFlight(gmu.swe.domain
	 * .Flight)
	 */
	public int createFlight(Flight flight) throws ValidationException, DataAccessException {
		Flight savedFlight = this.getService().createFlight(flight);

		this.sendMessage(savedFlight);

		return savedFlight.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#getAllAirplanes()
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException {
		return this.getService().getAllAirplanes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#getAllAirports()
	 */
	public Collection<String> getAllAirports() throws DataAccessException {
		return this.getService().getAllAirports();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#getAllFlights()
	 */
	public Collection<Flight> getAllFlights() throws DataAccessException {
		return this.getService().getAllFlights();
	}

	/**
	 * Sends a message with flight information to a Topic.
	 * 
	 * @param flight
	 *            Flight information to send to the Topic
	 * @return True if the message sent successfully, false if an error
	 *         occurred.
	 */
	public boolean sendMessage(Flight flight) {
		try {
			Context context = getInitialContext();
			TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");

			TopicConnection conn;

			System.out.println("** " + getClass().getSimpleName() + ": Sending Message for Flight #" + flight.getId());
			conn = connectionFactory.createTopicConnection();
			TopicSession session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			Topic topic = (Topic) context.lookup("topic/MsnyderaTopic");

			MessageProducer producer = session.createProducer(topic);

			// MessageProducer producer = session.createProducer(queue);
			MapMessage mapMsg = session.createMapMessage();
			mapMsg.setString("flightId", "" + flight.getId());
			mapMsg.setString("flightDate", flight.getDisplayDate());
			mapMsg.setString("departureAirport", flight.getDepartureAirportCode());
			mapMsg.setString("destinationAirport", flight.getDestinationAirportCode());
			mapMsg.setInt("numSeats", flight.getAvailableSeats());
			mapMsg.setDouble("cost", flight.getCost());
			mapMsg.setString("airplaneId", "" + flight.getAirplaneId());

			producer.send(mapMsg);

			conn.close();

			System.out.println("Sent Message!");

			return true;
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Used to get a context to the server.
	 * 
	 * @return Context to the server.
	 * @throws javax.naming.NamingException
	 *             Thrown if an error occurs during the lookup of the context.
	 */
	private static Context getInitialContext() throws javax.naming.NamingException {
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		return new InitialContext(props);
	}

	/**
	 * 
	 * @return The service implementation to use.
	 */
	public AirlineHeadquartersService getService() {
		if (this.service == null) {
			this.service = new AirlineHeadquartersServiceImpl();
		}

		return this.service;
	}

	/**
	 * Sets the service implementation to use. This method would be used when
	 * applying dependency injection principles.
	 * 
	 * @param service
	 *            Service implementation to use.
	 */
	public void setService(AirlineHeadquartersService service) {
		this.service = service;
	}
}
