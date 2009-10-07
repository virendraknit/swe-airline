/*
 * Created by: Matt Snyder
 */
package gmu.swe.service.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message-Driven Bean implementation class for: AirlineSubscriberTopicA.
 * 
 * This MDB is a subscriber to a shared topic and will print out information 
 * posted to the topic.
 *
 */
@MessageDriven(
	activationConfig = { 
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="topic/MsnyderaTopic")
	})
public class AirlineSubscriberTopicA implements MessageListener {

    /**
     * Default constructor. 
     */
    public AirlineSubscriberTopicA() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     * 
     * This method expects a MapMessage containing information about a flight that has been
     * added to the database.  This method will print out the information about the flight.
     */
    public void onMessage(Message message) {
    	try {
			if (message instanceof MapMessage) {
				MapMessage msg = (MapMessage) message;

				StringBuffer sb = new StringBuffer();
				
				sb.append("Flight #: " + msg.getString("flightId") + "\n");
				sb.append("Flight Date: " + msg.getString("flightDate") + "\n");
				sb.append("Departure Airport: " + msg.getString("departureAirport") + "\n");
				sb.append("Destination Airport: " + msg.getString("destinationAirport") + "\n");
				sb.append("Number of Seats: " + msg.getInt("numSeats") + "\n");
				sb.append("Seat Cost: $" + msg.getDouble("cost") + "\n");
				sb.append("Airplane #: " + msg.getString("airplaneId") + "\n");
				sb.append("***************************************************\n\n");
				
				System.out.println("\n**" + getClass().getSimpleName() + ": Flight Added!\n" + sb.toString());
				
			} else {
				System.out.println(getClass().getSimpleName() + " - Error: The message provided was not of type MapMessage!");
			}
		} catch (JMSException e) {
			System.out.println(getClass().getSimpleName() + ": Error occuredwhen attempting to read the MapMessage");
			e.printStackTrace();
		}
    }

}
