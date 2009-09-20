package gmu.swe.service;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;

import java.util.Collection;

public interface TempInterface {

	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException;

	public Reservation reserveFlight(String flightNumber, int numberOfSeats) throws ValidationException;

	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException;

	public void createAirport(String airportCode) throws ValidationException, DataAccessException;

	public void createFlight(Flight flight) throws ValidationException, DataAccessException;

}