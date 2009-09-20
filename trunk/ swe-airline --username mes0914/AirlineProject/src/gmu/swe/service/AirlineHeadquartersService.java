package gmu.swe.service;

import gmu.swe.dao.AirlineHeadquartersDao;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class AirlineHeadquartersService {
	private AirlineHeadquartersDao dao;

	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException {
		System.out.println("Call to run search");
		return null;
	}

	public Reservation reserveFlight(String flightNumber, int numberOfSeats) throws ValidationException {
		System.out.println("Call to reserve flight");
		return null;
	}

	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException {
		validateAirplane(numberOfSeats, airplaneType);
		
		this.getDao().createAirplane(numberOfSeats, airplaneType);
	}

	public void createAirport(String airportCode) throws ValidationException, DataAccessException {
		validateAirport(airportCode);
		
		this.getDao().createAirport(airportCode);
	}
	
	public void createFlight(Flight flight) throws ValidationException, DataAccessException {
		validateFlight(flight);

		this.getDao().createFlight(flight);
	}

	protected void validateAirplane(int numberOfSeats, String airplaneType) throws ValidationException {
		ValidationException validationException = new ValidationException();
		
		if(numberOfSeats < 1){
			validationException.addErrorMessage("The number of seats on a plane may not be < 1");
		}
		if(airplaneType == null || airplaneType.trim().equals("")){
			validationException.addErrorMessage("The airplane type was not provided");
		}
		
		if(validationException.hasErrors()){
			throw validationException;
		}
	}
	
	protected void validateAirport(String airportCode) throws ValidationException, DataAccessException {
		ValidationException validationException = new ValidationException();
		
		if(airportCode == null || airportCode.trim().equals("")){
			validationException.addErrorMessage("The airport code was not provided");
		}else if(this.getDao().doesAirportExist(airportCode)){
			validationException.addErrorMessage("The airport code provided already exists");
		}
		
		if(validationException.hasErrors()){
			throw validationException;
		}
	}
	
	protected void validateFlight(Flight flight) throws ValidationException, DataAccessException {
		ValidationException validationException = new ValidationException();

		if (flight == null) {
			validationException.addErrorMessage("No Flight information was provided");
		} else {
			if (flight.getDepartureDate() == null) {
				validationException.addErrorMessage("No departure date was provided");
			} else if (flight.getDepartureDate().before(new Date())) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				validationException
						.addErrorMessage("The departure date must be no earlier than today.  The provided date was "
								+ sdf.format(flight.getDepartureDate()) + ".");
			}

			if (flight.getDepartureAirportCode() == null) {
				validationException.addErrorMessage("No departing airport code was provided");
			} else if (!this.getDao().doesAirportExist(flight.getDepartureAirportCode())) {
				validationException.addErrorMessage("The provided departing airport code does not exist");
			}

			if (flight.getDestinationAirportCode() == null) {
				validationException.addErrorMessage("No destination airport code was provided");
			} else if (!this.getDao().doesAirportExist(flight.getDestinationAirportCode())) {
				validationException.addErrorMessage("The provided destination airport code does not exist");
			}

			if (flight.getDepartureAirportCode() != null && flight.getDestinationAirportCode() != null
					&& flight.getDepartureAirportCode().equalsIgnoreCase(flight.getDestinationAirportCode())) {
				validationException.addErrorMessage("The destination and departure codes may not be the same");
			}

			if (flight.getCost() < 0.0) {
				validationException.addErrorMessage("The flight cost must be >= $0");
			}

			if (flight.getAirplaneId() < 0) {
				validationException.addErrorMessage("The provided airplane Id is invalid.  The Id must be > 0");
			}
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	public AirlineHeadquartersDao getDao() {
		if (this.dao == null) {
			this.dao = new AirlineHeadquartersDao();
		}
		return this.dao;
	}

	public void setDao(AirlineHeadquartersDao dao) {
		this.dao = dao;
	}
}
