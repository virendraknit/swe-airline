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

	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#getAllAirplanes()
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException {
		return this.getDao().getAllAirplanes();
	}
	
	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#getallAirports()
	 */
	public Collection<String> getallAirports() throws DataAccessException {
		return this.getDao().getallAirports();
	}
	
	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#search(gmu.swe.domain.SearchFilters)
	 */
	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException {
		validateSearchCriteria(searchFilters);

		return this.getDao().search(searchFilters);
	}

	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createAirplane(int, java.lang.String)
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException {
		validateAirplane(numberOfSeats, airplaneType);

		this.getDao().createAirplane(numberOfSeats, airplaneType);
	}

	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createAirport(java.lang.String)
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException {
		validateAirport(airportCode);

		this.getDao().createAirport(airportCode);
	}

	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createFlight(gmu.swe.domain.Flight)
	 */
	public int createFlight(Flight flight) throws ValidationException, DataAccessException {
		validateFlight(flight);

		return this.getDao().createFlight(flight);
	}

	/* (non-Javadoc)
	 * @see gmu.swe.service.impl.AirlineHeadquartersService#createReservation(int, int)
	 */
	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException {
		validateReservationData(flightId, numSeats);

		return this.getDao().createReservation(flightId, numSeats);
	}

	private void validateReservationData(int flightId, int numSeats) throws ValidationException, DataAccessException {
		ValidationException validationException = new ValidationException();

		if (flightId < 0) {
			validationException.addErrorMessage("An invalid flight Id was provided, it must be >= 0");
		} else if (!this.getDao().doesFlightExist(flightId)) {
			validationException.addErrorMessage("The provided flight Id does not exist");
		}
		if (numSeats < 0) {
			validationException.addErrorMessage("An invalid number of seats was provided, it must be >= 1");
		} else {
			int numAvailableSeats = this.getDao().getNumberOfAvailableSeats(flightId);
			if (numAvailableSeats < numSeats) {
				validationException
						.addErrorMessage("The flight does not have enough seats, it only has " + numAvailableSeats + " seats available");
			}
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

	private void validateSearchCriteria(SearchFilters searchFilters) throws ValidationException {
		ValidationException validationException = new ValidationException();

		if (searchFilters == null || searchFilters.isAllNull()) {
			validationException.addErrorMessage("No search filters were not provided");
		}

		if (validationException.hasErrors()) {
			throw validationException;
		}
	}

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
			}else if(!this.getDao().doesAirplaneExist(flight.getAirplaneId())){
				validationException.addErrorMessage("The provided airplane Id does not exist.");
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
