package gmu.swe.service.impl;

import gmu.swe.dao.AirlineHeadquartersDao;
import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.AirlineHeadquartersService;
import gmu.swe.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Collection;

public class AirlineHeadquartersServiceImpl implements AirlineHeadquartersService {
	private AirlineHeadquartersDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#getAllAirplanes()
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException {
		return this.getDao().getAllAirplanes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#getallAirports()
	 */
	public Collection<String> getAllAirports() throws DataAccessException {
		return this.getDao().getAllAirports();
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.AirlineHeadquartersService#getAllFlights()
	 */
	public Collection<Flight> getAllFlights() throws DataAccessException {
		return this.getDao().getAllFlights();
	}
	
	/**
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#search(gmu.swe.domain.SearchFilters)
	 * 
	 *      Fails validation if searchFilters is null or if all of the values in
	 *      searchFilters are null.
	 */
	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException {
		validateSearchCriteria(searchFilters);

		return this.getDao().search(searchFilters);
	}

	/**
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createAirplane(int,
	 *      java.lang.String) <br>
	 * <br>
	 *      Fails validation if the numberOfSeats < 1, or if the airplaneType is
	 *      null or empty String "".
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException {
		validateAirplane(numberOfSeats, airplaneType);

		this.getDao().createAirplane(numberOfSeats, airplaneType);
	}

	/**
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createAirport(java.lang
	 *      .String)<br>
	 * <br>
	 *      Fails validation if the airportCode is null, empty, or doesn't exist
	 *      in the system.
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException {
		validateAirport(airportCode);

		this.getDao().createAirport(airportCode);
	}

	/**
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createFlight(gmu.swe.
	 *      domain.Flight)<br>
	 * <br>
	 *      Fails validation if:<br>
	 *      - The flight is null<br>
	 *      - The departure date is null or it is earlier than today<br>
	 *      - The departure airport code is null or doesn't exist in the system<br>
	 *      - The destination airport code is null or doesn't exist in the
	 *      system<br>
	 *      - The departure & destination airport codes are the same value<br>
	 *      - The cost of the flight !> 0<br>
	 *      - The airplaneId is not valid (i.e. not >= 0 or doesn't exist in the
	 *      system.)
	 */
	public Flight createFlight(Flight flight) throws ValidationException, DataAccessException {
		validateFlight(flight);

		return this.getDao().createFlight(flight);
	}

	/**
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createReservation(int,
	 *      int)<br>
	 * <br>
	 *      Fails validation if the provided flightId < 0 or does not exist in
	 *      the system. Also fails if the numSeats < 1 or if the flight doesn't
	 *      have enough available seats.
	 */
	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException {
		validateReservationData(flightId, numSeats);

		return this.getDao().createReservation(flightId, numSeats);
	}

	/**
	 * Fails validation if the provided flightId < 0 or does not exist in the
	 * system. Also fails if the numSeats < 1 or if the flight doesn't have
	 * enough available seats.
	 * 
	 * @param flightId
	 *            Field to validate
	 * @param numSeats
	 *            Field to validate
	 * @throws ValidationException
	 *             Thrown if there are validation errors
	 * @throws DataAccessException
	 *             Thrown if there is an error when looking up values in the
	 *             system.
	 */
	private void validateReservationData(int flightId, int numSeats) throws ValidationException, DataAccessException {
		ValidationException validationException = new ValidationException();

		if (flightId < 0) {
			validationException.addErrorMessage("An invalid flight Id was provided, it must be >= 0");
		} else if (!this.getDao().doesFlightExist(flightId)) {
			validationException.addErrorMessage("The provided flight Id does not exist");
		}
		if (numSeats < 1) {
			validationException.addErrorMessage("An invalid number of seats was provided, it must be >= 1");
		} else {
			int numAvailableSeats = this.getDao().getNumberOfAvailableSeats(flightId);
			if (numAvailableSeats < numSeats) {
				validationException.addErrorMessage("The flight does not have enough seats, it only has "
						+ numAvailableSeats + " seats available");
			}
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	/**
	 * Fails validation if searchFilters is null or if all of the values in
	 * searchFilters are null.
	 * 
	 * @param searchFilters
	 *            Filters to validate
	 * @throws ValidationException
	 *             Thrown if validation fails.
	 */
	private void validateSearchCriteria(SearchFilters searchFilters) throws ValidationException {
		ValidationException validationException = new ValidationException();

		if (searchFilters == null || searchFilters.isAllEmpty()) {
			validationException.addErrorMessage("No search filters were not provided");
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	/**
	 * Fails validation if the numberOfSeats < 1, or if the airplaneType is null
	 * or empty String "".
	 * 
	 * @param numberOfSeats
	 *            field to validate
	 * @param airplaneType
	 *            field to validate
	 * @throws ValidationException
	 *             Thrown if there are validation errors.
	 */
	protected void validateAirplane(int numberOfSeats, String airplaneType) throws ValidationException {
		ValidationException validationException = new ValidationException();

		if (numberOfSeats < 1) {
			validationException.addErrorMessage("The number of seats on a plane may not be < 1");
		}
		if (airplaneType == null || airplaneType.trim().equals("")) {
			validationException.addErrorMessage("The airplane type was not provided");
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	/**
	 * Fails validation if the airportCode is null, empty, or doesn't exist in
	 * the system.
	 * 
	 * @param airportCode
	 *            Fied to validate
	 * @throws ValidationException
	 *             Thrown if validation fails
	 * @throws DataAccessException
	 *             Thrown if there is a problem with looking up the airport
	 *             code.
	 */
	protected void validateAirport(String airportCode) throws ValidationException, DataAccessException {
		ValidationException validationException = new ValidationException();

		if (airportCode == null || airportCode.trim().equals("")) {
			validationException.addErrorMessage("The airport code was not provided");
		} else if (this.getDao().doesAirportExist(airportCode)) {
			validationException.addErrorMessage("The airport code provided already exists");
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	/**
	 * Fails validation if:<br>
	 * - The flight is null<br>
	 * - The departure date is null or it is earlier than today<br>
	 * - The departure airport code is null or doesn't exist in the system<br>
	 * - The destination airport code is null or doesn't exist in the system<br>
	 * - The departure & destination airport codes are the same value<br>
	 * - The cost of the flight !> 0<br>
	 * - The airplaneId is not valid (i.e. not >= 0 or doesn't exist in the
	 * system.)
	 * 
	 * @param flight
	 *            Field to validate
	 * @throws ValidationException
	 *             Thrown if there are validation errors
	 * @throws DataAccessException
	 *             Thrown if there is a problem with looking up information in
	 *             the system.
	 */
	protected void validateFlight(Flight flight) throws ValidationException, DataAccessException {
		ValidationException validationException = new ValidationException();

		if (flight == null) {
			validationException.addErrorMessage("No Flight information was provided");
		} else {
			if (flight.getDepartureDate() == null) {
				validationException.addErrorMessage("No departure date was provided");
			} else if (!DateUtil.isTodayOrLater(flight.getDepartureDate())) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				validationException
						.addErrorMessage("The departure date must be no earlier than tomorrow.  The provided date was "
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
			} else if (!this.getDao().doesAirplaneExist(flight.getAirplaneId())) {
				validationException.addErrorMessage("The provided airplane Id does not exist.");
			}
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	/**
	 * This method is used to get the correct DAO implementation. This method
	 * makes this class loosely coupled in that someone could set a different
	 * implementation of a DAO by calling the setDao() method. If no DAO is
	 * explicitly set, then this method will instantiate a known implementation.
	 * 
	 * @return DAO to use.
	 */
	public AirlineHeadquartersDao getDao() {
		if (this.dao == null) {
			this.dao = new AirlineHeadquartersDao();
		}
		return this.dao;
	}

	/**
	 * Used to set an implementation of a DAO.
	 * 
	 * @param dao
	 *            DAO to set.
	 */
	public void setDao(AirlineHeadquartersDao dao) {
		this.dao = dao;
	}
}
