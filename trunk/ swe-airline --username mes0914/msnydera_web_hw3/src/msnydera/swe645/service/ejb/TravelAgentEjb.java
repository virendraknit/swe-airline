/**
 * TravelAgentEjb.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package msnydera.swe645.service.ejb;

public interface TravelAgentEjb extends java.rmi.Remote {
    public msnydera.swe645.service.ejb.Flight[] search(msnydera.swe645.service.ejb.SearchFilters arg0) throws java.rmi.RemoteException, msnydera.swe645.service.ejb.ValidationException, msnydera.swe645.service.ejb.DataAccessException;
}
